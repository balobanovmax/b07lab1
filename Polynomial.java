public class Polynomial {
   private double[] coefficients;

   public Polynomial(){
    this.coefficients = new double[1];
    this.coefficients[0] = 0.0;
   }

   public Polynomial (double[] coefficients){
    this.coefficients = new double [coefficients.length];
    for (int i = 0; i < coefficients.length; i++){
        this.coefficients[i] = coefficients[i];
    }
   }

   public Polynomial add (Polynomial polynomialToAdd){
    double[] polynomialToAddCoefficients = polynomialToAdd.coefficients;
    int outputArrLength = Math.max(this.coefficients.length, polynomialToAdd.coefficients.length);
    double[] outputArr = new double[outputArrLength];

    for (int i = 0; i < this.coefficients.length; i++){
        outputArr[i] += this.coefficients[i];
    }

    for (int i = 0; i < polynomialToAddCoefficients.length; i++){
        outputArr[i] += polynomialToAddCoefficients[i];
    }

    return new Polynomial(outputArr);
   }

   public double evaluate (double x){
    if (this.coefficients.length == 1){
        return this.coefficients[0];
    }

    double output = 0.0;

    for (int i = 0; i < this.coefficients.length; i++){
        output += this.coefficients[i] * Math.pow(x, i);
    }

    return output;
   }

   public boolean hasRoot (double x) {
    double output = this.evaluate(x);
    return output == 0.0;
   }

}
