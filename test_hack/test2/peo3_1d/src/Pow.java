import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pow extends Expression {
    private BigInteger degree = BigInteger.ZERO;
    private BigInteger coef = BigInteger.ONE;

    public BigInteger getDegree() {
        return degree;
    }

    public BigInteger getCoef() {
        return coef;
    }

    public Pow(BigInteger coef, BigInteger degree) {
        this.degree = degree;
        this.coef = coef;
    }

    public Pow multiply(Pow p) {
        return new Pow(coef.multiply(p.getCoef()), degree.add(p.getDegree()));
    }

    public Pow(String s) {
        String comm = "^(([+-]?)x\\^([+-]?[0-9]+))$";
        Pattern pattern = Pattern.compile(comm);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            degree = new BigInteger(matcher.group(3));
            if (matcher.group(2).equals("-")) {
                coef = new BigInteger("-1");
            }
        }
        else {
            if (s.equals("x") || s.equals("+x")) {
                degree = BigInteger.ONE;
            }
            else if (s.equals("-x")) {
                degree = BigInteger.ONE;
                coef = new BigInteger("-1");
            }
        }
    }

    public String derive() {
        String r = "";
        if (degree.equals(BigInteger.ZERO)) {
            r = "0";
        }
        else if (degree.equals(BigInteger.ONE)) {
            r = coef.toString();
        }
        else if (degree.equals(new BigInteger("2"))) {
            r =  coef.multiply(degree).toString() + "*x";
        }
        else {
            r = coef.multiply(degree).toString() + "*x**" + degree.subtract(BigInteger.ONE);
        }
        return r;
    }

    @Override
    public String toString() {
        if (degree.equals(BigInteger.ZERO)) {
            return coef.toString();
        }
        else if (degree.equals(BigInteger.ONE)) {
            return "x";
        }
        else if (degree.equals(new BigInteger("2"))) {
            return "x*x";
        }
        else {
            return "x**" + degree.toString();
        }
    }
}
