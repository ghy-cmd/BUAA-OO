import java.math.BigInteger;

public class Multi {
    private final Trim left;
    private final Trim right;
    
    public Multi(Trim left, Trim right) {
        this.left = left;
        this.right = right;
    }
    
    public Trim getValue() {
        BigInteger newceo = left.getCeo().multiply(right.getCeo());
        BigInteger newexp = left.getExp().add(right.getExp());
        return new Trim(newceo, newexp);
    }
    
    public Trim getLeft() {
        return left;
    }
    
    public Trim getRight() {
        return right;
    }
}
