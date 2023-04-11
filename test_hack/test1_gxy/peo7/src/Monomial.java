import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monomial {
    private BigInteger coefficient;
    private BigInteger index;

    public Monomial(BigInteger coefficient, BigInteger index) {
        this.coefficient = coefficient;
        this.index = index;
    }

    public Monomial(String s0) {
        coefficient = new BigInteger("+1");
        index = new BigInteger("0");
        String s = formatString(s0);
        String regex = "(\\^)(?<num>(\\+|-)[0-9]+)";
        for (int i = 0; i < s.length();) {
            Pattern num = Pattern.compile(regex);
            Matcher m = num.matcher(s);
            if (!m.find(i)) {
                break;
            }
            i = m.end();
            index = index.add(new BigInteger(m.group("num")));
        }
        regex = "(^|[^\\^])(?<num>(\\+|-)[0-9]+)";
        for (int i = 0; i < s.length();) {
            Pattern num = Pattern.compile(regex);
            Matcher m = num.matcher(s);
            if (!m.find(i)) {
                break;
            }
            i = m.end();
            coefficient = coefficient.multiply(new BigInteger(m.group("num")));
        }
    }

    public String formatString(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                if (i == 0 || (s.charAt(i - 1) != '+' && s.charAt(i - 1) != '-' &&
                        !Character.isDigit(s.charAt(i - 1)))) {
                    res = res + "+";
                }
            }
            res = res + c;
            if ((c == '+' || c == '-') && !Character.isDigit(s.charAt(i + 1))) {
                res = res + "1*";
            }
            if (c == 'x' && (i == s.length() - 1 || s.charAt(i + 1) != '^')) {
                res = res + "^+1";
            }
        }
        return res;
    }

    public Monomial differentiate() {
        BigInteger one = new BigInteger("1");
        BigInteger zero = new BigInteger("0");
        if (index.equals(zero)) {
            return new Monomial(zero, zero);
        }
        return new Monomial(coefficient.multiply(index), index.subtract(one));
    }

    public BigInteger getCoefficient() {
        return coefficient;
    }

    public BigInteger getIndex() {
        return index;
    }
}
