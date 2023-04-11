import java.math.BigInteger;

public class Num extends Expression {
    private BigInteger value;

    public BigInteger getValue() {
        return value;
    }

    public Num(BigInteger value) {
        this.value = value;
    }

    public Num(String s) {
        value = new BigInteger(s);
    }

    public String derive() {
        return "0";
    }

    public Num multiply(Num num) {
        BigInteger bi = num.getValue().multiply(value);
        return new Num(bi);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Num temp = (Num) obj;
        if (value.equals(temp.getValue())) {
            return true;
        }
        return false;
    }
}
