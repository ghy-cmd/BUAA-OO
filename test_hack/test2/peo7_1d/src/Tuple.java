import java.math.BigInteger;

public class Tuple implements Cloneable {
    private BigInteger constant;
    private BigInteger sinIndex;
    private BigInteger cosIndex;
    private BigInteger xindex;

    public Tuple(BigInteger dc, BigInteger dsinIndex, BigInteger dcosIndex, BigInteger dxIndex) {
        this.constant = dc;
        this.sinIndex = dsinIndex;
        this.cosIndex = dcosIndex;
        this.xindex = dxIndex;
    }

    public void combo(Tuple temp) {
        this.constant = this.constant.multiply(temp.constant);
        this.sinIndex = this.sinIndex.add(temp.sinIndex);
        this.cosIndex = this.cosIndex.add(temp.cosIndex);
        this.xindex = this.xindex.add(temp.xindex);
    }

    public Object clone() {
        BigInteger zero = BigInteger.ZERO;
        Tuple temp = new Tuple(zero, zero, zero, zero);
        temp.constant = constant;
        temp.cosIndex = cosIndex;
        temp.sinIndex = sinIndex;
        temp.xindex = xindex;
        return temp;
    }

    public String toString(int flag) {
        StringBuffer stringBuffer = new StringBuffer();
        BigInteger zero = BigInteger.ZERO;
        BigInteger one = BigInteger.ONE;
        String constant = this.constant.compareTo(zero) == 0 ? "" :
                this.constant.compareTo(one) == 0 ? "" : String.valueOf(this.constant);
        String sin = sinIndex.compareTo(zero) == 0 ? "" :
                sinIndex.compareTo(one) == 0 ? "*sin(x)" : "*sin(x)**" + String.valueOf(sinIndex);
        String cos = cosIndex.compareTo(zero) == 0 ? "" :
                cosIndex.compareTo(one) == 0 ? "*cos(x)" : "*cos(x)**" + String.valueOf(cosIndex);
        String x = xindex.compareTo(zero) == 0 ? "" :
                xindex.compareTo(one) == 0 ? "*x" : xindex.compareTo(BigInteger.valueOf(2))
                        == 0 ? "*x*x" : "*x**" + String.valueOf(xindex);
        if (flag == 1) {
            stringBuffer.append(constant + sin + cos + x);
            if (!stringBuffer.toString().equals("")) {
                stringBuffer.append("*");
            }
        }
        else {
            stringBuffer.append(constant + sin + cos + x);
            if (stringBuffer.toString().equals("")) {
                stringBuffer.append("1");
            }
        }
        String out = stringBuffer.toString();
        if (!out.equals("")) {
            if (out.charAt(0) == '*') {
                out = out.substring(1);
            }
        }
        if (this.constant.compareTo(BigInteger.valueOf(-1)) == 0) {
            if (out.length() > 2) {
                out = "-" + out.substring(3);
            }
        }
        return out;
    }
}
