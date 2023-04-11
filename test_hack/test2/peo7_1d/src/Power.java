import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Power extends Expression {
    private BigInteger zero = BigInteger.ZERO;
    private BigInteger one = BigInteger.valueOf(1);
    private BigInteger xindex = one;
    private BigInteger constant = one;
    private String symbol;
    private Tuple tuple;
    private Tuple dtuple;

    public Power(String str, String symbol) {
        super();
        String index = "x(\\^([+-]?\\d*))?";
        Pattern p = Pattern.compile(index);
        Matcher m = p.matcher(str);
        m.matches();
        if (m.group(1) == null) {
            this.xindex = one;
        }
        else {
            this.xindex = new BigInteger(m.group(2));
        }
        this.constant = new BigInteger(symbol + "1");
        if (symbol.equals("+")) {
            this.symbol = "";
        }
        else {
            this.symbol = symbol;
        }
        this.tuple = new Tuple(constant, zero, zero, xindex);
        this.dtuple = new Tuple(constant.multiply(xindex), zero, zero, xindex.subtract(one));
    }

    public Tuple getTuple() {
        return tuple;
    }

    public Tuple getDtuple() {
        return dtuple;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (xindex.compareTo(BigInteger.valueOf(1)) == 0) {
            stringBuffer.append(symbol + "x");
        }
        else {
            stringBuffer.append(symbol + "x**" + xindex);
        }
        return stringBuffer.toString();
    }

    public String dToString() {
        StringBuffer stringBuffer = new StringBuffer();
        BigInteger dxindex = xindex.subtract(BigInteger.ONE);
        BigInteger dconstant = constant.multiply(xindex);
        if (dxindex.compareTo(BigInteger.ZERO) == 0) {
            if (dconstant.compareTo(BigInteger.ONE) == 0) {
                stringBuffer.append("1");
            }
            else {
                if (dconstant.compareTo(BigInteger.valueOf(-1)) == 0) {
                    stringBuffer.append("-1");
                }
                else {
                    stringBuffer.append(dconstant);
                }
            }
        }
        else {
            if (dxindex.compareTo(BigInteger.valueOf(1)) == 0) {
                if (dconstant.compareTo(BigInteger.ONE) == 0) {
                    stringBuffer.append("x");
                }
                else {
                    if (dconstant.compareTo(BigInteger.valueOf(-1)) == 0) {
                        stringBuffer.append("-x");
                    }
                    else {
                        stringBuffer.append(dconstant + "*x");
                    }
                }
            }
            else {
                if (dxindex.compareTo(BigInteger.valueOf(2)) == 0) {
                    if (dconstant.compareTo(BigInteger.ONE) == 0) {
                        stringBuffer.append("x*x");
                    }
                    else {
                        if (dconstant.compareTo(BigInteger.valueOf(-1)) == 0) {
                            stringBuffer.append("-x*x");
                        } else {
                            stringBuffer.append(dconstant + "*x*x");
                        }
                    }
                }
                else {
                    if (dconstant.compareTo(BigInteger.ONE) == 0) {
                        stringBuffer.append("x**" + dxindex);
                    }
                    else {
                        if (dconstant.compareTo(BigInteger.valueOf(-1)) == 0) {
                            stringBuffer.append("-x**" + dxindex);
                        } else {
                            stringBuffer.append(dconstant + "*x**" + dxindex);
                        }
                    }
                }
            }
        }
        return stringBuffer.toString();
    }
}
