import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String readin = scanner.nextLine();
        String expression = readin.replace(" ", "");
        expression = expression.replace("\t", "");
        Expr expr = new Expr(expression);
        TreeNode root = expr.createTree();
        System.out.println(root.derive().toString());
    }
}
