import java.math.BigInteger;

public class Poly {
    private final BigInteger xishu;
    private final BigInteger cishu;
    
    public Poly(BigInteger xishu, BigInteger cishu) {
        this.xishu = xishu;
        this.cishu = cishu;
    }
    
    private static Poly merge(Poly firstPoly, Poly secondPoly, Operator operator) {
        if (operator.getOpStr().equals("+")) {
            return new Poly(firstPoly.xishu.add(secondPoly.xishu),
                    firstPoly.cishu);
        } else {
            return new Poly(firstPoly.xishu.subtract(secondPoly.xishu),
                    
                    firstPoly.cishu);
        }
    }
    
    private static Poly calculate(Poly firstPoly, Poly secondPoly, Operator operator) {
        switch (operator.getOpStr()) {
            case "*":
            case "*+":
                return new Poly(firstPoly.xishu.multiply(secondPoly.xishu),
                        firstPoly.cishu.add(secondPoly.cishu));
            case "*-":
                return new Poly(firstPoly.xishu.multiply(secondPoly.xishu.negate()),
                        firstPoly.cishu.add(secondPoly.cishu));
            case "^":
            case "^+":
                return new Poly(firstPoly.xishu,
                        firstPoly.cishu.multiply(secondPoly.xishu));
            case "^-":
                return new Poly(firstPoly.xishu,
                        firstPoly.cishu.multiply(secondPoly.xishu.negate()));
            default:
                return firstPoly;
        }
    }
    
    public Poly neg() {
        return new Poly(this.xishu.negate(), this.cishu);
    }
    
    public Poly qiudao() {
        if (!this.cishu.equals(new BigInteger("0"))) {
            return new Poly(this.xishu.multiply(this.cishu),
                    this.cishu.subtract(new BigInteger("1")));
        } else {
            return new Poly(new BigInteger("0"),
                    new BigInteger("0"));
        }
    }
    
    public BigInteger getXishu() {
        return xishu;
    }
    
    public BigInteger getCishu() {
        return cishu;
    }
    
    public Poly deal(Poly secondPoly, Operator operator) {
        if (operator.getOpStr().equals("+") || operator.getOpStr().equals("-")) {
            return merge(this, secondPoly, operator);
        } else {
            return calculate(this, secondPoly, operator);
        }
    }
}
