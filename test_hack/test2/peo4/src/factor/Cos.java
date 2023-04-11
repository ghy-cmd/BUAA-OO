package factor;

import java.math.BigInteger;
import java.util.ArrayList;

public class Cos extends Factor implements Derivable {
    private BigInteger exp;
    
    public Cos(BigInteger exp) {
        this.exp = exp;
    }
    
    public ArrayList derive() {
        ArrayList ret = new ArrayList();
        if (exp.equals(BigInteger.ZERO)) {
            ret.add(new Constant(BigInteger.ZERO));
            return ret;
        }
        if (exp.equals(BigInteger.ONE)) {
            ret.add(new Constant(BigInteger.valueOf(-1)));
            ret.add(new Sin(BigInteger.ONE));
            return ret;
        }
        ret.add(new Constant(exp.multiply(BigInteger.valueOf(-1))));
        ret.add(new Cos(exp.subtract(BigInteger.ONE)));
        ret.add(new Sin(BigInteger.ONE));
        return ret;
    }
    
    public BigInteger getExp() {
        return exp;
    }
    
    public Cos multiply(Cos s) {
        BigInteger cosExp = s.getExp();
        return new Cos(exp.add(cosExp));
    }
    
    public String toString() {
        if (exp.equals(BigInteger.ZERO)) {
            return "1";
        }
        else if (exp.equals(BigInteger.ONE)) {
            return "cos(x)";
        }
        return ("cos(x)**" + exp.toString());
    }
}
