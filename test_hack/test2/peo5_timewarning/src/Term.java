import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Term {
    private final List<TreeNode> treeNodes;
    
    public Term(String string1) {
        List<TreeNode> result = new ArrayList<>();
        String string = string1 + "*";
        Pattern pattern = Pattern.compile("([+-])((((x)|(sin\\(x\\))|(cos\\(x\\)))" +
                "(\\*{2}[+-]?\\d+){0,1})|([+-]?\\d+)|(\\(.*?\\)))\\*");
        Matcher matcher = pattern.matcher(string);
        TreeNode treeNode;
        if (matcher.lookingAt()) {
            //处理开头有符号的
            if (matcher.group(1).equals("-")) {
                treeNode = new Constant("-1");
                result.add(treeNode);
                string = string.substring(1);
            } else {
                treeNode = new Constant("1");
                result.add(treeNode);
                string = string.substring(1);
            }
        }
        int top = 0;
        string = string.replaceAll("sin\\(x\\)", "sin@x@");
        string = string.replaceAll("cos\\(x\\)", "cos@x@");
        char[] stringArray = string.toCharArray();
        for (int i = 0; i < string.length(); i++) {
            if (stringArray[i] == '(') {
                if (top == 0) {
                    stringArray[i] = '[';
                }
                top++;
            } else if (stringArray[i] == ')') {
                if (top == 1) {
                    stringArray[i] = ']';
                }
                top--;
            }
        }
        string = String.copyValueOf(stringArray);
        string = string.replaceAll("sin@x@", "sin\\(x\\)");
        string = string.replaceAll("cos@x@", "cos\\(x\\)");
        Pattern pattern1 = Pattern.compile("((((x)|(sin\\(x\\))|(cos\\(x\\)))(\\*{2}[+-]?\\d+){0,1})|([+-]?\\d+)|(\\[(.*?)\\]))\\**");
        Matcher matcher1 = pattern1.matcher(string);
        while (matcher1.find()) {
            if (matcher1.group(4) != null) {
                //幂函数
                TreeNode nodeToadd = new Power(matcher1.group(2));
                result.add(nodeToadd);
            } else if (matcher1.group(5) != null) {
                TreeNode nodeToadd = new Sin(matcher1.group(2));
                result.add(nodeToadd);
            } else if (matcher1.group(6) != null) {
                TreeNode nodeToadd = new Cos(matcher1.group(2));
                result.add(nodeToadd);
            } else if (matcher1.group(8) != null) {
                TreeNode nodeToadd = new Constant(matcher1.group(8));
                result.add(nodeToadd);
            } else if (matcher1.group(10) != null) {
                Expr expr = new Expr(matcher1.group(10));
                result.add(expr.createTree());
            }
        }
        //根据一个因子List来建树，返回根节点
        treeNodes = result;
    }
    
    public TreeNode createTree() {
        List<TreeNode> list = this.treeNodes;
        TreeNode root = new Constant("1");
        for (TreeNode treeNode : list) {
            root = new Multi(root, treeNode);
        }
        return root;
    }
}
