import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        input = simplify(input);
        //System.out.println(input);
        String[] strings = input.split("(?=(?<!\\*)\\+)\\b|(?=(?!<\\*)-)\\b");
        TermMap termMap = new TermMap();
        termMap.extract(strings);
        //System.out.println(termMap.toString());
        termMap.calDerivation();
        System.out.println(termMap.toString());
    }

    private static String simplify(String input) {
        String str = input;
        str = str.replaceAll("\\s+", "");
        str = str.replaceAll("(?<=\\+|-)-x(?=\\*\\*\\d*(2|4|6|8|0))","x");
        str = str.replaceAll("---|\\+\\+-", "-");
        str = str.replaceAll("-\\+\\+|\\+-\\+", "-");
        str = str.replaceAll("\\+--|-\\+-", "+");
        str = str.replaceAll("--\\+|\\+\\+\\+", "+");
        str = str.replaceAll("--|\\+\\+", "+");
        str = str.replaceAll("\\+-|-\\+", "-");
        str = str.replaceAll("\\*(?=\\d)", "\\*+");
        if (Character.isDigit(input.charAt(0))) {
            str = "+" + str;
        }
        return str;
    }
}
