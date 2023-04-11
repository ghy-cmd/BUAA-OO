import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        Polynomial polynomial = new Polynomial(s);
        polynomial.differentiate();
        polynomial.combineLikeTerms();
        polynomial.print();
    }
}