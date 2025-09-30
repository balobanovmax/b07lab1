import java.io.File;
import java.util.Arrays;

public class Driver {
    public static void main(String[] args) throws Exception {
        Polynomial z = new Polynomial();
        System.out.println(z.size());
        System.out.println(z.isZero());
        System.out.println(Arrays.toString(z.getCoeffsCopy()));
        System.out.println(Arrays.toString(z.getExpsCopy()));

        Polynomial a = new Polynomial(new double[]{6, 0, -2, 5}, new int[]{0, 2, 1, 3});
        System.out.println(Arrays.toString(a.getCoeffsCopy()));
        System.out.println(Arrays.toString(a.getExpsCopy()));

        Polynomial b = new Polynomial(new double[]{2, 2, 0, 3}, new int[]{1, 1, 2, 0});
        System.out.println(Arrays.toString(b.getCoeffsCopy()));
        System.out.println(Arrays.toString(b.getExpsCopy()));

        Polynomial e = new Polynomial(new double[]{3, 4}, new int[]{0, 1});
        System.out.println(e.evaluate(2));

        Polynomial p1 = new Polynomial(new double[]{6, -2, 5}, new int[]{0, 1, 3});
        Polynomial p2 = new Polynomial(new double[]{-2, -9}, new int[]{1, 4});
        Polynomial s = p1.add(p2);
        System.out.println(Arrays.toString(s.getCoeffsCopy()));
        System.out.println(Arrays.toString(s.getExpsCopy()));

        Polynomial m1a = new Polynomial(new double[]{3, 4}, new int[]{0, 1});
        Polynomial m1b = new Polynomial(new double[]{2, -1}, new int[]{0, 1});
        Polynomial m1 = m1a.multiply(m1b);
        System.out.println(Arrays.toString(m1.getCoeffsCopy()));
        System.out.println(Arrays.toString(m1.getExpsCopy()));

        Polynomial r = new Polynomial(new double[]{-3, 1}, new int[]{0, 1});
        System.out.println(r.hasRoot(3));

        Polynomial f1 = new Polynomial(new File("poly1.txt"));
        System.out.println(Arrays.toString(f1.getCoeffsCopy()));
        System.out.println(Arrays.toString(f1.getExpsCopy()));

        Polynomial w = new Polynomial(new double[]{5, -3, 7}, new int[]{0, 2, 8});
        w.saveToFile("out.txt");
        Polynomial w2 = new Polynomial(new File("out.txt"));
        System.out.println(Arrays.toString(w2.getCoeffsCopy()));
        System.out.println(Arrays.toString(w2.getExpsCopy()));
    }
}


