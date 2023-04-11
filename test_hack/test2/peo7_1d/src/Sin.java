import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sin extends Expression {
    private BigInteger zero = BigInteger.ZERO;
    private BigInteger one = BigInteger.ONE;
    private BigInteger cosIndex = zero;
    private BigInteger sinIndex = zero;
    private BigInteger constant = BigInteger.valueOf(1);
    private String symbol;
    private Tuple tuple;
    private Tuple dtuple;

    public Sin(String str, String symbol) {
        super();
        String index = "sin\\(x\\)(\\^([+-]?\\d*))?";
        Pattern p = Pattern.compile(index);
        Matcher m = p.matcher(str);
        m.matches();
        if (m.group(1) == null) {
            this.sinIndex = new BigInteger("1");
        }
        else {
            this.sinIndex = new BigInteger(m.group(2));
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
        this.tuple = new Tuple(constant, sinIndex, zero, zero);
        this.dtuple = new Tuple(constant.multiply(sinIndex), sinIndex.subtract(one), one, zero);
    }

    public Tuple getTuple() {
        return tuple;
    }

    public Tuple getDtuple() {
        return dtuple;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (sinIndex.compareTo(BigInteger.valueOf(1)) == 0) {
            stringBuffer.append(symbol + "sin(x)");
        }
        else {
            stringBuffer.append(symbol + "sin(x)**" + sinIndex);
        }
        return stringBuffer.toString();
    }

    public String dToString() {
        BigInteger dcosindex = BigInteger.valueOf(1);
        BigInteger dconstant = constant.multiply(sinIndex);
        BigInteger dsinindex = sinIndex.subtract(BigInteger.valueOf(1));
        StringBuffer stringBuffer = new StringBuffer();
        if (dsinindex.compareTo(BigInteger.ZERO) == 0) {
            stringBuffer.append(symbol + "cos(x)");
        }
        else {
            stringBuffer.append(dconstant + "*cos(x)*" + "sin(x)**" + dsinindex);
        }
        return stringBuffer.toString();
    }
}
