package testfirst;

import java.math.BigInteger;
import java.util.Objects;

public final class Term {
    private final BigInteger coe;
    private final BigInteger exp;

    public Term(BigInteger coe, BigInteger exp) {
        this.coe = coe;
        this.exp = exp;
    }

    public BigInteger getCoe() {
        return coe;
    }

    public BigInteger getCoed() {
        return coe.multiply(exp);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Term)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        Term term = (Term) o;
        if (coe.equals(term.coe) && exp.equals(term.exp)) {   //TODO
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(coe, exp);
    }

    @Override
    public String toString() {
        if (exp.equals(BigInteger.ZERO)) {
            return "0";
        } else {
            BigInteger a = coe.multiply(exp);
            BigInteger b = exp.add(BigInteger.valueOf(-1));
            if (b.equals(BigInteger.ZERO)) {
                return a.toString();
            } else if (a.equals(BigInteger.ONE) && b.equals(BigInteger.ONE)) {
                return "x";
            } else if (a.equals(BigInteger.ONE)) {
                return "x**" + b.toString();
            } else if (a.equals(BigInteger.valueOf(-1)) && b.equals(BigInteger.ONE)) {
                return "-x";
            } else if (a.equals(BigInteger.valueOf(-1))) {
                return "-x**" + b.toString();
            } else if (b.equals(BigInteger.ONE)) {
                return a.toString() + "*x";
            } else {
                return a.toString() + "*x**" + b.toString();
            }

        }
    }
}
