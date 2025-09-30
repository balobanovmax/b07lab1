import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Polynomial {


   private double[] coeffs;
   private int [] exps;


   public Polynomial(){
    this.coeffs = new double[0];
    this.exps = new int[0];
   }


   public Polynomial (double[] coeffs, int[] exps){
    if (coeffs == null || exps == null || coeffs.length != exps.length){
        throw new IllegalArgumentException("coefficients and exponents must both be non null and be the same lenth.");
    }
    this.coeffs = Arrays.copyOf(coeffs, coeffs.length);
    this.exps = Arrays.copyOf(exps, exps.length);
    normalize();
   }


 
   public int size() {
    return coeffs.length;
   }


   public boolean isZero () {
    return coeffs.length == 0;
   }


   public double[] getCoeffsCopy(){
    return Arrays.copyOf(coeffs, coeffs.length);
   }


   public int[] getExpsCopy () {
    return Arrays.copyOf(exps, exps.length);
   }




   public Polynomial add(Polynomial other) {
    if (other == null) throw new IllegalArgumentException(" 'other' polynomial must be non null");

    int n = this.coeffs.length; 
    int m = other.coeffs.length;
    double[] outC = new double[n + m];
    int[] outE = new int[n + m];

    int i = 0;
    int j = 0;
    int k = 0;
    while (i < n && j < m) {
        int ei = this.exps[i];
        int ej = other.exps[j];
        if (ei == ej) {
            double c = this.coeffs[i] + other.coeffs[j];
            if (c != 0.0) {
                outE[k] = ei;
                outC[k] = c;
                k++;
            }
            i++; 
            j++;
        } else if (ei < ej) {
            outE[k] = ei;
            outC[k] = this.coeffs[i];
            i++; 
            k++;
        } else { // ei > ej
            outE[k] = ej;
            outC[k] = other.coeffs[j];
            j++;
            k++;
        }
    }
    while (i < n) {
        outE[k] = this.exps[i];
        outC[k] = this.coeffs[i];
        i++;
        k++;
    }
    while (j < m) {
        outE[k] = other.exps[j];
        outC[k] = other.coeffs[j];
        j++;
        k++;
    }

    return new Polynomial(Arrays.copyOf(outC, k), Arrays.copyOf(outE, k));
    }



   public double evaluate(double x) {
    double sum = 0.0;
    for (int i = 0; i < coeffs.length; i++) {
        sum += coeffs[i] * Math.pow(x, exps[i]);
    }
    return sum;
    }



   public Polynomial multiply(Polynomial other) {
    if (other == null) {
        throw new IllegalArgumentException("other must be non-null");
    }
    if (this.isZero() || other.isZero()) {
        return new Polynomial();
    }
    int n = this.coeffs.length;
    int m = other.coeffs.length;
    double[] tempC = new double[n * m];
    int[] tempE = new int[n * m];
    int k = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            double c = this.coeffs[i] * other.coeffs[j];
            if (c != 0.0) {
                int e = this.exps[i] + other.exps[j];
                tempC[k] = c;
                tempE[k] = e;
                k++;
            }
        }
    }
    double[] outC = Arrays.copyOf(tempC, k);
    int[] outE = Arrays.copyOf(tempE, k);
    return new Polynomial(outC, outE);
}



   public boolean hasRoot (double x) {
        return evaluate(x) == 0.0;
   }


   public Polynomial(File file) throws IOException {
    java.util.Scanner sc = null;
    String line;
    try {
        sc = new java.util.Scanner(file);
        if (!sc.hasNextLine()) {
            this.coeffs = new double[0];
            this.exps = new int[0];
            return;
        }
        line = sc.nextLine().trim();
    } catch (Exception e) {
        throw new IOException("Failed to read the file", e);
    } finally {
        if (sc != null) sc.close();
    }

    if (line.isEmpty()) {
        this.coeffs = new double[0];
        this.exps = new int[0];
        return;
    }

    String canonical = line.replace("-", "+-");
    String[] parts = canonical.split("\\+");
    double[] tmpC = new double[parts.length];
    int[]    tmpE = new int[parts.length];
    int k = 0;
    for (int i = 0; i < parts.length; i++) {
        String raw = parts[i];
        if (raw == null || raw.length() == 0){
            continue;
        } 
        String term = raw.trim();
        if (term.length() == 0){
            continue;
        } 
        double coef;
        int exp;
        int xPos = term.indexOf('x');
        if (xPos >= 0) {
            String cPart = term.substring(0, xPos); 
            String ePart = term.substring(xPos + 1);

            if (cPart.equals("") || cPart.equals("+")) {
                coef = 1.0;
            } else if (cPart.equals("-")) {
                coef = -1.0;
            } else {
                coef = Double.parseDouble(cPart);
            }

            if (ePart.equals("")) {
                exp = 1;
            } else {
                exp = Integer.parseInt(ePart);
            }
        } else {
            coef = Double.parseDouble(term);
            exp = 0;
        }
        if (coef != 0.0) {
            tmpC[k] = coef;
            tmpE[k] = exp;
            k++;
        }
    }
    this.coeffs = java.util.Arrays.copyOf(tmpC, k);
    this.exps   = java.util.Arrays.copyOf(tmpE, k);
    normalize(); 
}


  public void saveToFile(String filename) throws java.io.IOException {
    String text;
    if (isZero()) {
        text = "0";
    } else {
        String s = "";
        for (int i = 0; i < coeffs.length; i++) {
            double c = coeffs[i];
            int e = exps[i];
            boolean first = (i == 0);

            if (c < 0) {
                s += "-";
            } else if (!first) {
                s += "+";
            }

            double abs = Math.abs(c);

            if (e == 0) {
                s += formatNumber(abs);
            } else {
                if (abs != 1.0) {
                    s += formatNumber(abs);
                }
                s += "x";
                if (e != 1) {
                    s += Integer.toString(e);
                }
            }
        }
        text = s;
    }
    java.io.PrintWriter out = null;
    try {
        out = new java.io.PrintWriter(filename);
        out.print(text);
    } finally {
        if (out != null) out.close();
    }
}


private static String formatNumber(double v) {
    double r = Math.rint(v);
    if (Math.abs(v - r) < 1e-12) {
        return Long.toString((long) r);
    }
    return Double.toString(v);
}


   private void normalize(){
        if (exps.length == 0) {
            return;
        }
        sortByExponent();
        combineLikeTerms();
        removeZeroTerms();
   }


   private void combineLikeTerms() {
    if (exps.length <= 1) return;

    int n = exps.length;
    int write = 0;

    int curExp = exps[0];
    double curCoef = coeffs[0];

    for (int i = 1; i < n; i++) {
        if (exps[i] == curExp) {
            curCoef += coeffs[i];
        } else {
            exps[write] = curExp;
            coeffs[write] = curCoef;
            write++;
            curExp = exps[i];
            curCoef = coeffs[i];
        }
    }
    exps[write] = curExp;
    coeffs[write] = curCoef;
    write++;

    if (write < n) {
        exps = Arrays.copyOf(exps, write);
        coeffs = Arrays.copyOf(coeffs, write);
    }
}


   private void removeZeroTerms() {
    int count = 0;
    for (int i = 0; i < coeffs.length; i++) {
        if (coeffs[i] != 0.0) {
            count++;
        }
    }

    if (count == coeffs.length) {
        return;
    }
    double[] newCoeffs = new double[count];
    int[] newExps = new int[count];
    int w = 0;
    for (int i = 0; i < coeffs.length; i++) {
        if (coeffs[i] != 0.0) {
            newCoeffs[w] = coeffs[i];
            newExps[w] = exps[i];
            w++;
        }
    }
    coeffs = newCoeffs;
    exps = newExps;
}


   private void sortByExponent() {
    for (int i = 1; i < exps.length; i++) {
        int keyExp = exps[i];
        double keyCoef = coeffs[i];
        int j = i - 1;
        while (j >= 0 && exps[j] > keyExp) {
            exps[j + 1] = exps[j];
            coeffs[j + 1] = coeffs[j];
            j--;
        }
        exps[j + 1] = keyExp;
        coeffs[j + 1] = keyCoef;
    }
}


   @Override
   public String toString(){
    return super.toString();
   }

}
