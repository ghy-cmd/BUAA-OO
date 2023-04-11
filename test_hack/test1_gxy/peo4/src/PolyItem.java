import java.math.BigInteger;

public class PolyItem {
    private BigInteger coeff;
    private BigInteger index;

    public PolyItem(BigInteger coeff, BigInteger index) {
        this.coeff = coeff;
        this.index = index;
    }

    public BigInteger getCoeff() {
        return coeff;
    }

    public void setCoeff(BigInteger coeff) {
        this.coeff = coeff;
    }

    public BigInteger getIndex() {
        return index;
    }

    public void setIndex(BigInteger index) {
        this.index = index;
    }

    public PolyItem derivation() {
        if (this.index.equals(new BigInteger("0"))) {
            return null;
        }
        else {
            BigInteger newCoeff = this.getCoeff().multiply(this.getIndex());
            BigInteger newIndex = this.getIndex().subtract(new BigInteger("1"));
            return new PolyItem(newCoeff,newIndex);
        }
    }

    @Override
    public String toString() {
        String str = "";
        if (!(this.coeff.equals(new BigInteger("0")))) {
            if (this.index.equals(new BigInteger("0"))) {
                str = str + this.coeff;
            }
            else {
                if ((this.coeff.equals(new BigInteger("1"))) &&
                        (this.index.equals(new BigInteger("1")))) {
                    str = str + "x";
                }
                else if ((this.coeff.equals(new BigInteger("1"))) &&
                        (!(this.index.equals(new BigInteger("1"))))) {
                    str = str + "x**" + this.index;
                }
                else if ((this.coeff.equals(new BigInteger("-1"))) &&
                        (this.index.equals(new BigInteger("1")))) {
                    str = str + "-x";
                }
                else if ((this.coeff.equals(new BigInteger("-1"))) &&
                        (!(this.index.equals(new BigInteger("1"))))) {
                    str = str + "-x**" + this.index;
                }
                else if (this.index.equals(new BigInteger("1"))) {
                    str = str + this.coeff + "*x";
                }
                else {
                    str = str + this.coeff + "*x**" + this.index;
                }
            }
        }
        return str;
    }
}
