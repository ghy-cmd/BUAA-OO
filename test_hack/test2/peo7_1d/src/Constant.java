import java.math.BigInteger;

public class Constant extends Expression {
    private BigInteger constant = BigInteger.valueOf(1);
    private String symbol;
    private Tuple dtuple;
    private Tuple tuple;

    public Constant(String str, String symbol) {
        super();
        BigInteger zero = BigInteger.ZERO;
        this.symbol = symbol;
        this.constant = new BigInteger(symbol + str);
        this.tuple = new Tuple(constant, zero, zero, zero);
        this.dtuple = new Tuple(zero, zero, zero, zero);
    }

    public Tuple getDtuple() {
        return dtuple;
    }

    public Tuple getTuple() {
        return tuple;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.valueOf(constant));
        return stringBuffer.toString();
    }

    public String dToString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("0");
        return stringBuffer.toString();
    }
}
