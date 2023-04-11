import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Power extends TreeNode {
    private BigInteger exp;
    
    public Power(String string) {
        Pattern pattern = Pattern.compile("(x)(\\*{2}([+-]?\\d+))?");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            if (matcher.group(3) == null) {
                exp = new BigInteger("1");
            } else {
                exp = new BigInteger(matcher.group(3));
            }
        }
    }
    
    public Power(BigInteger exp) {
        this.exp = exp;
    }
    
    @Override
    public TreeNode derive() {
        TreeNode left = new Constant(exp.toString());
        TreeNode right = new Power(exp.add(new BigInteger("-1")));
        TreeNode result = new Multi(left, right);
        return result;
    }
    
    @Override
    public String toString() {
        if (exp.toString().equals("0")) {
            return "1";
        }
        if (exp.toString().equals("1")) {
            return "x";
        }
        return "x**" + exp.toString();
    }
}
