import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Power extends AllFactor {
    private BigInteger coe;
    private BigInteger exp;
    private int isPC;
    private int isPS;
    
    public Power(BigInteger coe, BigInteger exp, int isPS, int isPC, int op) {
        super(op);
        //系数
        this.coe = coe;
        //次方
        this.exp = exp;
        //幂(isPowerFunc = 1/sin -1/cos)
        this.isPC = isPC;
        this.isPS = isPS;
    }
    
    public int getIsPC() {
        return isPC;
    }
    
    public int getIsPS() {
        return isPS;
    }
    
    public void setIsPF(int isPS, int isPC) {
        this.isPS = isPS;
        this.isPC = isPC;
    }
    
    public BigInteger getCoe() {
        return coe;
    }
    
    public BigInteger getExp() {
        return exp;
    }
    
    public void setCoe(BigInteger i) {
        this.coe = i;
    }
    
    public void setExp(BigInteger i) {
        this.exp = i;
    }
    
    public boolean equalsExp(Object o) {
        if (!(o instanceof Power)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        Power t = (Power) o;
        boolean r = this.getExp().equals(t.getExp());
        return (r && this.getIsPC() == t.getIsPC() && this.getIsPS() == t.getIsPS());
    }
    
    public void toMerge(Power b) {
        BigInteger i = this.getCoe().add(b.getCoe());
        this.setCoe(i);
    }
    
    public Power line(String value) {
        String x0 = "[+-]?\\d+";
        String x = "[+-]?x";
        String xn = "[+-]?x\\^(([+-]?\\d)+)";
        Power p;
        Pattern pattern0 = Pattern.compile(x0);
        Matcher m0 = pattern0.matcher(value);
        String str = "\\d+";
        Pattern pattern = Pattern.compile(str);
        if (m0.matches()) {
            Matcher m = pattern.matcher(value);
            if (m.find()) {
                String result = m.group();
                BigInteger n = new BigInteger(result);
                if (value.charAt(0) == '-') {
                    p = new Power(n.multiply(BigInteger.valueOf(-1)), BigInteger.ZERO, 0, 0, 1);
                } else {
                    p = new Power(n.multiply(BigInteger.valueOf(1)), BigInteger.ZERO, 0, 0, 1);
                }
                return p;
            }
        }
        Pattern pattern1 = Pattern.compile(x);
        Matcher m1 = pattern1.matcher(value);
        if (m1.matches()) {
            // System.out.println("m1");
            if (value.charAt(0) == '-') {
                p = new Power(BigInteger.valueOf(-1), BigInteger.ONE, 0, 0, 1);
            } else {
                p = new Power(BigInteger.ONE, BigInteger.ONE, 0, 0, 1);
            }
            return p;
        }
        Pattern patternn = Pattern.compile(xn);
        Matcher mn = patternn.matcher(value);
        BigInteger minus = (value.charAt(0) == '-') ? BigInteger.valueOf(-1) : BigInteger.ONE;
        if (mn.matches()) {
            //System.out.println("mn");
            Matcher m = pattern.matcher(value);
            int j = value.indexOf('^');
            if (m.find()) {
                String result = m.group();
                BigInteger n = new BigInteger(result);
                if (value.charAt(j + 1) == '-') {
                    p = new Power(minus, n.multiply(BigInteger.valueOf(-1)), 0, 0, 1);
                } else {
                    p = new Power(minus, n, 0, 0, 1);
                }
                return p;
            }
        }
        p = this.line1(value);
        // p1.print();
        //System.out.println("null?");
        // p.print();
        assert p != null;
        this.coe = p.getCoe();
        this.exp = p.getExp();
        this.isPC = p.getIsPC();
        this.isPS = p.getIsPS();
        this.setOp(p.getOp());
        return p;
    }
    
    private TreA line1(String s) {
        //System.out.println("line1");
        final String xs = "[+-]?s";
        final String xc = "[+-]?c";
        final String xsn = "[+-]?s\\^(([+-]?\\d)+)";
        final String xcn = "[+-]?c\\^(([+-]?\\d)+)";
        Pattern patterns = Pattern.compile(xs);
        Matcher ms = patterns.matcher(s);
        TreA p;
        if (ms.matches()) {
            if (s.charAt(0) == '-') {
                p = new TreA(BigInteger.valueOf(-1), BigInteger.ONE, 1, 0, 1);
            } else {
                p = new TreA(BigInteger.ONE, BigInteger.ONE, 1, 0, 1);
            }
            return p;
        }
        Pattern patternc = Pattern.compile(xc);
        Matcher mc = patternc.matcher(s);
        if (mc.matches()) {
            if (s.charAt(0) == '-') {
                p = new TreA(BigInteger.valueOf(-1), BigInteger.ONE, 0, 1, 1);
            } else {
                p = new TreA(BigInteger.ONE, BigInteger.ONE, 0, 1, 1);
            }
            return p;
        }
        String str = "\\d+";
        Pattern pattern = Pattern.compile(str);
        Matcher m = pattern.matcher(s);
        BigInteger minus = (s.charAt(0) == '-') ? BigInteger.valueOf(-1) : BigInteger.ONE;
        if (m.find()) {
            String result = m.group();
            BigInteger num = new BigInteger(result);
            Pattern patternsn = Pattern.compile(xsn);
            Matcher msn = patternsn.matcher(s);
            if (msn.matches()) {
                int numi = Integer.parseInt(result);
                if (s.charAt(s.indexOf('^') + 1) == '-') {
                    p = new TreA(minus, num.multiply(BigInteger.valueOf(-1)), -numi, 0, 1);
                } else {
                    p = new TreA(minus, num, numi, 0, 1);
                }
                return p;
                
            }
            Pattern patterncn = Pattern.compile(xcn);
            Matcher mcn = patterncn.matcher(s);
            if (mcn.matches()) {
                int numi = Integer.parseInt(result);
                if (s.charAt(s.indexOf('^') + 1) == '-') {
                    p = new TreA(minus, num.multiply(BigInteger.valueOf(-1)), 0, -numi, 1);
                } else {
                    p = new TreA(minus, num, 0, numi, 1);
                }
                return p;
            }
        }
        return null;
    }
    
    public Power der() {
        Power der;
        //System.out.println("coe:" + this.getCoe() + "exp:" + this.getExp());
        if (this.getExp().equals(BigInteger.valueOf(0))) {
            der = new Power(BigInteger.valueOf(0), BigInteger.valueOf(0), 0, 0, 1);
        } else if (this.getExp().equals(BigInteger.valueOf(1))) {
            der = new Power(this.getCoe(), BigInteger.valueOf(0), 0, 0, 1);
        } else {
            BigInteger tempC = this.getCoe();
            BigInteger tempE = this.getExp();
            der = new Power(tempC.multiply(this.getExp()), tempE.subtract(BigInteger.ONE), 0, 0, 1);
        }
        
        return der;
    }
    
    public void print() {
        if (this.getExp().equals(BigInteger.valueOf(0))) {
            System.out.print(this.getCoe());
        } else if (this.getExp().equals(BigInteger.valueOf(1))) {
            if (this.getCoe().equals(BigInteger.valueOf(1))) {
                System.out.print("x");
            } else if (this.getCoe().equals(BigInteger.valueOf(-1))) {
                System.out.print("-1*x");
            } else {
                System.out.print(this.getCoe() + "*x");
            }
        } else {
            if (this.getCoe().equals(BigInteger.valueOf(1))) {
                System.out.print("x**" + this.getExp());
            } else if (this.getCoe().equals(BigInteger.valueOf(-1))) {
                System.out.print("-1*x**" + this.getExp());
            } else {
                System.out.print(this.getCoe() + "*x**" + this.getExp());
            }
        }
    }
}
