import java.math.BigInteger;

public class Constant extends TreeNode {
    private final BigInteger constant;
    
    public Constant(String string) {
        super(null, null);
        this.constant = new BigInteger(string);
    }
    
    @Override
    public TreeNode derive() {
        Constant constant = new Constant("0");
        TreeNode result = constant;
        return result;
    }
    
    @Override
    public String toString() {
        return constant.toString();
    }
}
