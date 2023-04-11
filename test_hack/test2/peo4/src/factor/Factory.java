package factor;

import java.math.BigInteger;

public class Factory {
    public static Factor get(String type, String target) {
        Factor ret;
        if (type.equals("Constant")) {
            ret = new Constant(new BigInteger(target));
            return ret;
        }
        if (type.equals("PowFunc")) {
            if (target.length() == 1) {
                ret = new PowFunc(BigInteger.ONE);
            }
            else {
                ret = new PowFunc(new BigInteger(target.substring(3)));
            }
            return ret;
        }
        if (type.equals("Sin")) {
            if (target.equals("sin(x)")) {
                ret = new Sin(BigInteger.ONE);
            }
            else {
                ret = new Sin(new BigInteger(target.substring(8)));
            }
            return ret;
        }
        if (type.equals("Cos")) {
            if (target.equals("cos(x)")) {
                ret = new Cos(BigInteger.ONE);
            }
            else {
                ret = new Cos(new BigInteger(target.substring(8)));
            }
            return ret;
        }
        return null;
    }
}
