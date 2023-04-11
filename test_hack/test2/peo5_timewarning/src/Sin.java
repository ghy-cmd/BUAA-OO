import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sin extends TreeNode {
    private BigInteger exp;
    
    public Sin(String string) {
        super(null, null);
        Pattern pattern = Pattern.compile("sin\\((x)\\)(\\*{2}([+-]?\\d+))?");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            if (matcher.group(3) == null) {
                exp = new BigInteger("1");
            } else {
                exp = new BigInteger(matcher.group(3));
            }
        }
    }
    
    public Sin(BigInteger exp) {
        this.exp = exp;
    }
    
    @Override
    public TreeNode derive() {
        TreeNode expNode = new Constant(exp.toString());
        TreeNode sinNode = new Sin(exp.add(new BigInteger("-1")));
        TreeNode multi1 = new Multi(expNode, sinNode);
        TreeNode cosNode = new Cos(new BigInteger("1"));
        TreeNode result = new Multi(multi1, cosNode);
        return result;
    }
    
    @Override
    public String toString() {
        if (exp.toString().equals("0")) {
            return "1";
        }
        if (exp.toString().equals("1")) {
            return "sin(x)";
        }
        return "sin(x)**" + exp.toString();
    }
}
