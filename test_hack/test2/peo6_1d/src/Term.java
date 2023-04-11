import java.math.BigInteger;
import java.util.Objects;

public final class Term {
    private BigInteger coe;
    private BigInteger exp0;
    private BigInteger exp1;
    private BigInteger exp2;

    public void setCoe(BigInteger c) {
        coe = c;
    }

    public BigInteger getExp0() {
        return exp0;
    }

    public BigInteger getExp1() {
        return exp1;
    }

    public BigInteger getExp2() {
        return exp2;
    }

    public Term(BigInteger coe, BigInteger exp0, BigInteger exp1, BigInteger exp2) {
        this.coe = coe;
        this.exp0 = exp0;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    public BigInteger getCoe() {
        return coe;
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
        if (this.exp0.equals(term.exp0) && this.exp1.equals(term.exp1)
                && this.exp2.equals(term.exp2)) {   //TODO
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(coe, exp0, exp1, exp2);
    }

    @Override
    public String toString() {
        String res = "";
        int flag = 0;
        if (coe.equals(BigInteger.ONE)) {
            if (((exp0.abs()).add(
                    exp1.abs()).add(exp2.abs())).compareTo(BigInteger.valueOf(0)) > 0) {
                flag = 1;
            }
        } else if (coe.negate().equals(BigInteger.ONE)) {
            if (((exp0.abs()).add(
                    exp1.abs()).add(exp2.abs())).compareTo(BigInteger.valueOf(0)) > 0) {
                flag = -1;
            }
            res = res + "-";
        } else {
            res = res + String.valueOf(coe);
        }

        if (exp0.compareTo(BigInteger.ZERO) != 0) {
            if (flag == 0) {
                res = res + "*x";
            } else {
                flag = 0;
                res = res + "x";
            }
            if (exp0.compareTo(BigInteger.ONE) != 0) {
                res = res + "**" + String.valueOf(exp0);

            }
        }

        if (exp1.compareTo(BigInteger.ZERO) != 0) {
            if (flag == 0) {
                res = res + "*cos(x)";
            } else {
                flag = 0;
                res = res + "cos(x)";
            }
            if (exp1.compareTo(BigInteger.ONE) != 0) {
                res = res + "**" + String.valueOf(exp1);

            }
        }

        if (exp2.compareTo(BigInteger.ZERO) != 0) {
            if (flag == 0) {
                res = res + "*sin(x)";
            } else {
                flag = 0;
                res = res + "sin(x)";
            }
            if (exp2.compareTo(BigInteger.ONE) != 0) {
                res = res + "**" + String.valueOf(exp2);

            }
        }
        return res;
    }
}
