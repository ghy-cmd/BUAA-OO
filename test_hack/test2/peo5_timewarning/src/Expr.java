import java.util.ArrayList;
import java.util.List;

public class Expr {
    private List<Term> terms = new ArrayList<>();
    
    public Expr(String string) {
        String str = string;
        List<Term> result = new ArrayList<>();
        int top = 0;
        //扫一遍字符串，如果top==0可以换，top!=0,+换成！，-换成#，*换成@
        int i;
        char[] stringChar = str.toCharArray();
        for (i = 0; i < str.length(); i++) {
            char word = stringChar[i];
            if (word == '(') {
                top++;
            } else if (word == ')') {
                top--;
            } else {
                if (top != 0) {
                    if (word == '+') {
                        stringChar[i] = '!';
                    } else if (word == '-') {
                        stringChar[i] = '#';
                    } else if (word == '*') {
                        stringChar[i] = '@';
                    }
                }
            }
        }
        str = String.copyValueOf(stringChar);
        str = str.replaceAll("\\*\\*\\+", "^");
        str = str.replaceAll("\\*\\*-", "^.");
        str = str.replaceAll("\\*\\*", "^");
        str = str.replaceAll("\\*\\+", "*");
        str = str.replaceAll("\\*-", "*.");
        str = str.replaceAll("\\+\\+\\+", "+");
        str = str.replaceAll("-\\+\\+", "+-");
        str = str.replaceAll("\\+-\\+", "+-");
        str = str.replaceAll("\\+\\+-", "+-");
        str = str.replaceAll("--\\+", "+");
        str = str.replaceAll("-\\+-", "+");
        str = str.replaceAll("\\+--", "+");
        str = str.replaceAll("---", "+-");
        str = str.replaceAll("\\+\\+", "+");
        str = str.replaceAll("-\\+", "+-");
        str = str.replaceAll("--", "+");
        str = str.replaceAll("-", "+-");
        String[] buf = str.split("\\+");
        for (String term : buf) {
            if (!term.isEmpty()) {
                term = term.replaceAll("\\.", "-");
                term = term.replaceAll("\\^", "**");
                term = term.replaceAll("!", "+");
                term = term.replaceAll("#", "-");
                term = term.replaceAll("@", "*");
                Term term1 = new Term(term);
                result.add(term1);
            }
        }
        terms = result;
    }
    
    public TreeNode createTree() {
        TreeNode root = new Constant("0");
        for (Term node : terms) {
            root = new Add(root, node.createTree());
        }
        return root;
    }
    
}
