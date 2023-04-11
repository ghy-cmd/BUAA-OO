import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Term {
    private BigInteger coe;
    private BigInteger exp;

    public void addCoe(BigInteger coe) {
        this.coe = this.coe.add(coe);
    }

    public BigInteger getCoe() {
        return this.coe;
    }

    public void derivation() {
        BigInteger one = new BigInteger("1");
        this.coe = this.coe.multiply(this.exp);
        this.exp = this.exp.subtract(one);
    }

    public void addTerm(String str) {
        //System.out.println(str);
        Pattern patternCoe = Pattern.compile("(?<!\\*\\*)[+-]\\d+");
        Matcher matcherCoe = patternCoe.matcher(str);
        int count = 0;
        BigInteger coe;
        BigInteger zero = new BigInteger("0");
        while (matcherCoe.find()) {
            if (matcherCoe.group().charAt(0) == '-') {
                BigInteger temp = new BigInteger(matcherCoe.group().substring(1));
                coe = zero.subtract(temp);
            } else if (matcherCoe.group().charAt(0) == '+') {
                BigInteger temp = new BigInteger(matcherCoe.group().substring(1));
                coe = temp;
            } else {
                BigInteger temp = new BigInteger(matcherCoe.group());
                coe = temp;
            }
            if (count == 0) {
                this.coe = coe;
            } else {
                this.coe = this.coe.multiply(coe);
            }
            count++;
        }
        if (count == 0) {
            BigInteger one = new BigInteger("1");
            this.coe = one;
        }
        BigInteger sign = new BigInteger(String.valueOf(findMinus(str)));
        this.coe = this.coe.multiply(sign);
        count = 0;
        Pattern patternExp = Pattern.compile("(?<=\\*\\*)(\\+|-)?\\d+");
        Matcher matcherExp = patternExp.matcher(str);
        BigInteger exp;
        while (matcherExp.find()) {
            if (matcherExp.group().charAt(0) == '-') {
                BigInteger temp = new BigInteger(matcherExp.group().substring(1));
                exp = zero.subtract(temp);
            } else if (matcherExp.group().charAt(0) == '+') {
                BigInteger temp = new BigInteger(matcherExp.group().substring(1));
                exp = temp;
            } else {
                BigInteger temp = new BigInteger(matcherExp.group());
                exp = temp;
            }
            if (count == 0) {
                this.exp = exp;
            } else {
                this.exp = this.exp.add(exp);
            }
            count++;
        }
        if (count == 0) {
            this.exp = zero;
        }
        BigInteger onlyX = new BigInteger(String.valueOf(findOnlyX(str)));
        this.exp = this.exp.add(onlyX);
        return;
    }

    public int findOnlyX(String str) {
        Pattern pattern = Pattern.compile("x(?!\\*\\*)");
        Matcher matcher = pattern.matcher(str);
        int onlyX = 0;
        while (matcher.find()) {
            onlyX++;
        }
        return onlyX;
    }

    public int findMinus(String str) {
        //System.out.println(str);
        Pattern pattern = Pattern.compile("(?<=-)x");
        Matcher matcher = pattern.matcher(str);
        int minus = 1;
        while (matcher.find()) {
            minus = minus * -1;
        }
        return minus;
    }

    @Override
    public String toString() {
        // TODO
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        BigInteger oneNegative = zero.subtract(one);
        if (coe.compareTo(zero) == 0) {
            return "0";
        } else if (coe.compareTo(zero) != 0 && exp.compareTo(zero) == 0) {
            return "" + coe;
        } else if (coe.compareTo(one) == 0 && exp.compareTo(one) == 0) {
            return "x";
        } else if (coe.compareTo(one) == 0 && exp.compareTo(one) != 0) {
            return "x**" + exp;
        } else if (coe.compareTo(oneNegative) == 0 && exp.compareTo(one) == 0) {
            return "-x";
        } else if (coe.compareTo(oneNegative) == 0 && exp.compareTo(one) != 0) {
            return "-x**" + exp;
        } else if (exp.compareTo(one) == 0) {
            return coe + "*x";
        } else {
            return coe + "*x**" + exp;
        }

    }

}
