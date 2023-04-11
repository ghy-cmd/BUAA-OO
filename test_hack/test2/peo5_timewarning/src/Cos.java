import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cos extends TreeNode {
    private BigInteger exp;
    
    public Cos(String string) {
        super(null, null);
        Pattern pattern = Pattern.compile("cos\\((x)\\)(\\*{2}([+-]?\\d+))?");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            if (matcher.group(3) == null) {
                exp = new BigInteger("1");
            } else {
                exp = new BigInteger(matcher.group(3));
            }
        }
    }
    
    public Cos(BigInteger exp) {
        this.exp = exp;
    }
    
    @Override
    public TreeNode derive() {
        TreeNode expNode = new Constant(exp.multiply(new BigInteger("-1")).toString());
        TreeNode cosNode = new Cos(exp.add(new BigInteger("-1")));
        TreeNode multi1 = new Multi(expNode, cosNode);
        TreeNode sinNode = new Sin(new BigInteger("1"));
        TreeNode result = new Multi(multi1, sinNode);
        return result;
    }
    
    @Override
    public String toString() {
        if (exp.toString().equals("0")) {
            return "1";
        }
        if (exp.toString().equals("1")) {
            return "cos(x)";
        }
        return "cos(x)**" + exp.toString();
    }
}
