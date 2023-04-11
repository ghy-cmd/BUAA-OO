import java.util.Scanner;

public class MainClass {
    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        line = line.replaceAll("\\s*", "");
        line = line.replaceAll("(\\+\\+)|(--)", "+");
        line = line.replaceAll("(\\+-)|(-\\+)", "-");
        line = line.replaceAll("(\\+\\+)|(--)", "+");
        line = line.replaceAll("(\\+-)|(-\\+)", "-");
        line = line.replaceAll("\\*\\*", "^");
        StringBuffer string = new StringBuffer();
        string.append(line.charAt(0));
        int flag = 0;
        for (int i = 1; i < line.length(); i++) {
            if ((line.charAt(i - 1) > '9' || line.charAt(i - 1) < '0' || flag == 1)
                    && (i < line.length() - 1 && line.charAt(i + 1) >= '0'
                    && line.charAt(i + 1) <= '9') && line.charAt(i) == '0') {
                flag = 1;
            }
            else {
                flag = 0;
            }
            if (flag == 0) {
                string.append(line.charAt(i));
            }
        }
        line = string.toString();
        line = line.replaceAll("sin\\(x\\)\\^[+-]?0|cos\\(x\\)\\^[+-]?0|x\\^[+-]?0", "1");
        Expression ep = new Expression(line, "");
        String out = ep.dToString();
        out = out.replaceAll("(\\+\\+)|(--)", "+");
        out = out.replaceAll("(\\+-)|(-\\+)", "-");
        out = out.replaceAll("(\\+\\+)|(--)", "+");
        out = out.replaceAll("(\\+-)|(-\\+)", "-");
        out = out.replaceAll("\\^", "**");
        if (out.equals("")) {
            out = "0";
        }
        else {
            if (out.charAt(0) == '+' || out.equals("-")) {
                out = out.substring(1);
            }
        }
        if (out.equals("")) {
            out = "0";
        }
        System.out.println(out);

    }
}
