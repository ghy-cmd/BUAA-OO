import java.util.Scanner;

public class MainClass {
    public static void main(String [] argv) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        Factory factory = new Factory();
        Poly poly = factory.generate(line);
        String result = poly.derive();
        System.out.println(Output.outputSimplify(result));
    }
}
