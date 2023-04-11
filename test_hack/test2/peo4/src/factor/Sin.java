package factor;

import java.math.BigInteger;
import java.util.ArrayList;

public class Sin extends Factor implements Derivable {
    private BigInteger exp;
    
    public Sin(BigInteger exp) {
        this.exp = exp;
    }
    
    public ArrayList derive() {
        ArrayList ret = new ArrayList();
        if (exp.equals(BigInteger.ZERO)) {
            ret.add(new Constant(BigInteger.ZERO));
            return ret;
        }
        if (exp.equals(BigInteger.ONE)) {
            ret.add(new Cos(BigInteger.ONE));
            return ret;
        }
        ret.add(new Constant(exp));
        ret.add(new Sin(exp.subtract(BigInteger.ONE)));
        ret.add(new Cos(BigInteger.ONE));
        return ret;
    }
    
    public BigInteger getExp() {
        return exp;
    }
    
    public Sin multiply(Sin s) {
        BigInteger sinExp = s.getExp();
        return new Sin(exp.add(sinExp));
    }
    
    public String toString() {
        if (exp.equals(BigInteger.ZERO)) {
            return "1";
        } else if (exp.equals(BigInteger.ONE)) {
            return "sin(x)";
        }
        return ("sin(x)**" + exp.toString());
    }
}
