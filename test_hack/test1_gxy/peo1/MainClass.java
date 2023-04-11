import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        String input;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            input = scanner.nextLine();
            Format format = new Format(input);
            format.formatLine();
            PolyCompute polyCompute = new PolyCompute(format.getTerms());
            polyCompute.compute();
        }
    }
}
