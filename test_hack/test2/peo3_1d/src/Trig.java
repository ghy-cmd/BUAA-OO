import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Trig extends Expression {
    private BigInteger degree = BigInteger.ZERO;
    private BigInteger coef = BigInteger.ONE;
    private int type = 0;   //sin(x): 1; cos(x): 2

    public int getType() {
        return type;
    }

    public BigInteger getDegree() {
        return degree;
    }

    public BigInteger getCoef() {
        return coef;
    }

    public Trig multiply(Trig trig) {
        Trig r = null;
        if (type == trig.getType()) {
            r = new Trig(type, coef.multiply(trig.getCoef()), degree.add(trig.getDegree()));
        }
        return r;
    }

    public Trig(int type, BigInteger coef, BigInteger degree) {
        this.type = type;
        this.coef = coef;
        this.degree = degree;
    }

    public Trig(String s) {
        String commSin = "S";
        String commCos = "C";
        Pattern patternSin = Pattern.compile(commSin);
        Pattern patternCos = Pattern.compile(commCos);
        Matcher matcherSin = patternSin.matcher(s);
        Matcher matcherCos = patternCos.matcher(s);
        if (matcherSin.find()) {
            type = 1;
            if (s.equals("-S")) {
                coef = new BigInteger("-1");
                degree = BigInteger.ONE;
            }
            else if (s.equals("S") || s.equals("+S")) {
                coef = BigInteger.ONE;
                degree = BigInteger.ONE;
            }
            else {
                String comm = "^(([+-]?)S\\^([+-]?[0-9]+))$";
                Pattern pattern = Pattern.compile(comm);
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    degree = new BigInteger(matcher.group(3));
                    if (matcher.group(2).equals("-")) {
                        coef = new BigInteger("-1");
                    }
                }
            }
        }
        else if (matcherCos.find()) {
            type = 2;
            if (s.equals("-C")) {
                coef = new BigInteger("-1");
                degree = BigInteger.ONE;
            }
            else if (s.equals("C") || s.equals("+C")) {
                coef = BigInteger.ONE;
                degree = BigInteger.ONE;
            }
            else {
                String comm = "^(([+-]?)C\\^([+-]?[0-9]+))$";
                Pattern pattern = Pattern.compile(comm);
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    degree = new BigInteger(matcher.group(3));
                    if (matcher.group(2).equals("-")) {
                        coef = new BigInteger("-1");
                    }
                }
            }
        }
    }

    @Override
    public String derive() {
        String r = "";
        if (degree.equals(BigInteger.ZERO)) {
            r = "0";
        }
        else {
            if (degree.equals(BigInteger.ONE)) {
                if (type == 1) {
                    r = "cos(x)";
                }
                else {
                    r = "-1*sin(x)";
                }
            }
            else if (degree.equals(new BigInteger("2"))) {
                if (type == 1) {
                    r = "2*sin(x)*cos(x)";
                }
                else {
                    r = "-2*sin(x)*cos(x)";
                }
            }
            else {
                if (type == 1) {
                    r = degree.toString() + "*sin(x)**" +
                            degree.subtract(BigInteger.ONE).toString() + "*cos(x)";
                }
                else {
                    r = degree.multiply(new BigInteger("-1")).toString() +
                            "*cos(x)**" + degree.subtract(BigInteger.ONE).toString() + "*sin(x)";
                }
            }
        }
        return r;
    }

    @Override
    public String toString() {
        if (degree.equals(BigInteger.ZERO)) {
            return coef.toString();
        }
        else if (degree.equals(BigInteger.ONE)) {
            if (type == 1) {
                return "sin(x)";
            }
            else {
                return "cos(x)";
            }
        }
        else {
            if (type == 1) {
                return "sin(x)**" + degree.toString();
            }
            else {
                return "cos(x)**" + degree.toString();
            }
        }
    }
}
