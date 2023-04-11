package factor;

import java.math.BigInteger;
import java.util.ArrayList;

public class PowFunc extends Factor implements Derivable {
    private BigInteger exp;
    
    public PowFunc(BigInteger exp) {
        this.exp = exp;
    }
    
    public ArrayList derive() {
        ArrayList ret = new ArrayList();
        if (exp.equals(BigInteger.ZERO)) {
            ret.add(new Constant(BigInteger.ZERO));
            return ret;
        }
        if (exp.equals(BigInteger.ONE)) {
            ret.add(new Constant(BigInteger.ONE));
            return ret;
        }
        ret.add(new Constant(exp));
        ret.add(new PowFunc(exp.subtract(BigInteger.ONE)));
        return ret;
    }
    
    public BigInteger getExp() {
        return exp;
    }
    
    public PowFunc multiply(PowFunc p) {
        BigInteger powExp = p.getExp();
        return new PowFunc(exp.add(powExp));
    }
    
    public String toString() {
        if (exp.equals(BigInteger.ZERO)) {
            return "1";
        }
        if (exp.equals(BigInteger.ONE)) {
            return "x";
        }
        if (exp.equals(BigInteger.valueOf(2))) {
            return "x*x";
        }
        return ("x**" + exp.toString());
    }
}
