import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cos extends Expression {
    private String symbol;
    private BigInteger cosIndex = BigInteger.valueOf(0);
    private BigInteger sinIndex = BigInteger.valueOf(0);
    private BigInteger constant = BigInteger.valueOf(1);
    private Tuple dtuple;
    private Tuple tuple;

    public Cos(String str, String symbol) {
        super();
        final BigInteger zero = BigInteger.ZERO;
        final BigInteger one = BigInteger.ONE;
        String index = "cos\\(x\\)(\\^([+-]?\\d*))?";
        Pattern p = Pattern.compile(index);
        Matcher m = p.matcher(str);
        m.matches();
        if (m.group(1) == null) {
            this.cosIndex = new BigInteger("1");
        }
        else {
            this.cosIndex = new BigInteger(m.group(2));
        }
        if (symbol.equals("+")) {
            this.constant = BigInteger.valueOf(1);
        }
        else {
            if (symbol.equals("-")) {
                this.constant = BigInteger.valueOf(-1);
            }
        }
        if (symbol.equals("+")) {
            this.symbol = "";
        }
        else {
            this.symbol = symbol;
        }
        this.tuple = new Tuple(constant, zero, cosIndex, zero);
        this.dtuple = new Tuple(constant.multiply(cosIndex).multiply(BigInteger.valueOf(-1)), one,
                cosIndex.subtract(one), zero);
    }

    public Tuple getDtuple() {
        return dtuple;
    }

    public Tuple getTuple() {
        return tuple;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (cosIndex.compareTo(BigInteger.valueOf(1)) == 0) {
            stringBuffer.append(symbol + "cos(x)");
        }
        else {
            stringBuffer.append(symbol + "cos(x)**" + cosIndex);
        }
        return stringBuffer.toString();
    }

    public String dToString() {
        StringBuffer stringBuffer = new StringBuffer();
        BigInteger dcosindex = cosIndex.subtract(BigInteger.ONE);
        BigInteger dconstant = constant.multiply(cosIndex).multiply(BigInteger.valueOf(-1));
        if (dcosindex.compareTo(BigInteger.valueOf(0)) == 0) {
            stringBuffer.append("-" + symbol + "sin(x)");
        }
        else {
            stringBuffer.append(dconstant + "*sin(x)*" + "cos(x)**" + dcosindex);
        }
        return stringBuffer.toString();
    }
}
