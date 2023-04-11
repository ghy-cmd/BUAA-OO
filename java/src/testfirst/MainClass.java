package testfirst;

import java.util.Scanner;

public class MainClass {
    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        str = str.replace(" ", "");
        str = str.replace("\t", "");
        Expression a = new Expression(str);
        System.out.println(a.toString());
    }
}
