import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            String str = scanner.nextLine();
            Poly poly = PolyFactory.setPolyClass(str);
            System.out.println(poly.derivation().toString());
        }
    }
}
