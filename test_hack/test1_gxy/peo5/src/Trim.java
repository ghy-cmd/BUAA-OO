import java.math.BigInteger;

public class Trim {
    private BigInteger ceo;
    private BigInteger exp;
    
    public Trim(BigInteger ceo, BigInteger exp) {
        this.ceo = ceo;
        this.exp = exp;
    }
    
    public Trim() {
        this.ceo = new BigInteger("0");
        this.exp = new BigInteger("0");
    }
    
    public void setCeo(BigInteger ceo) {
        this.ceo = ceo;
    }
    
    public void setExp(BigInteger exp) {
        this.exp = exp;
    }
    
    public String printTrim() {
        //是否为0
        //是否为1
        if (ceo.equals(new BigInteger("0"))) {
            return "0";
        } else if (exp.equals(new BigInteger("0"))) {
            return ceo.toString();
        } else if (exp.equals(new BigInteger("1")) && ceo.equals(new BigInteger("1"))) {
            return "x";
        } else if (ceo.equals(new BigInteger("1"))) {
            return "x" + "**" + exp.toString();
        } else if (exp.equals(new BigInteger("1"))) {
            return ceo.toString() + "*" + "x";
        } else {
            return ceo.toString() + "*" + "x" + "**" + exp.toString();
        }
    }
    
    public Trim derive() {
        BigInteger suber = new BigInteger("-1");
        BigInteger newceo = ceo.multiply(exp);
        BigInteger newexp = exp.add(suber);
        return new Trim(newceo, newexp);
    }
    
    public BigInteger getCeo() {
        return ceo;
    }
    
    public BigInteger getExp() {
        return exp;
    }
}
