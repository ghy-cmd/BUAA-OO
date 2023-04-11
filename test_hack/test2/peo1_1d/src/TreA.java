
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreA extends Power {
    
    public TreA(BigInteger coe, BigInteger exp, int isPS, int isPC, int op) {
        super(coe, exp, isPS, isPC, op);
    }
    
    public boolean equalsExp(Object o) {
        if (!(o instanceof TreA)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        TreA t = (TreA) o;
        boolean r = this.getExp().equals(t.getExp());
        return (r && this.getIsPC() == t.getIsPC() && this.getIsPS() == t.getIsPS());
    }
    
    public void toMerge(TreA b) {
        BigInteger i = this.getCoe().add(b.getCoe());
        this.setCoe(i);
    }
    
    public Power line(String s) {
        //System.out.println("ist");
        final String xs = "[+-]?s";
        final String xc = "[+-]?c";
        final String xsn = "[+-]?s\\^(([+-]?\\d)+)";
        final String xcn = "[+-]?c\\^(([+-]?\\d)+)";
        Pattern patterns = Pattern.compile(xs);
        Matcher ms = patterns.matcher(s);
        Power p;
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
        Pattern patternsn = Pattern.compile(xsn);
        Matcher msn = patternsn.matcher(s);
        String str = "\\d+";
        Pattern pattern = Pattern.compile(str);
        Matcher m = pattern.matcher(s);
        int j = s.indexOf('^');
        BigInteger minus = (s.charAt(0) == '-') ? BigInteger.valueOf(-1) : BigInteger.ONE;
        if (m.find()) {
            String result = m.group();
            //System.out.println("m"+ result);
            BigInteger num = new BigInteger(result);
            if (msn.matches()) {
                int numi = Integer.parseInt(result);
                if (s.charAt(j + 1) == '-') {
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
                if (s.charAt(j + 1) == '-') {
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
        int s = this.getIsPS();
        int c = this.getIsPC();
        BigInteger i = BigInteger.valueOf(1);
        //System.out.println("coe:" + this.getCoe() + "exp:" + this.getExp());
        //System.out.println("s:"+this.getIsPS()+" c:"+this.getIsPC());
        if (this.getExp().equals(BigInteger.valueOf(0))) {
            der = new TreA(BigInteger.valueOf(0), BigInteger.valueOf(0), 0, 0, 1);
        } else {
            int k = (s != 0) ? 1 : (c != 0) ? -1 : 0;
            int isPs = (k == 1) ? 0 : 1;
            int isPc = (k == -1) ? 0 : 1;
            BigInteger k1 = BigInteger.valueOf(k);
            if (this.getExp().equals(i)) {
                der = new TreA(this.getCoe().multiply(k1), i, isPs, isPc, 1);
            } else {
                BigInteger tempC = this.getCoe();
                BigInteger tempE = this.getExp();
                // System.out.println("S" + this.getIsPS());
                int newS = (s != 0) ? this.getIsPS() - 1 : 1;
                // System.out.println("news" + newS);
                int newC = (c != 0) ? this.getIsPC() - 1 : 1;
                BigInteger tempCm = tempC.multiply(this.getExp()).multiply(k1);
                der = new TreA(tempCm, tempE.subtract(i), newS, newC, 1);
                //der.print();
            }
        }
        return der;
    }
    
    public void print() {
        // System.out.println("isSANJIAO");
        BigInteger i1 = BigInteger.valueOf(1);
        BigInteger im1 = BigInteger.valueOf(-1);
        if (this.getIsPS() == 0 && this.getIsPC() == 0) {
            System.out.print(this.getCoe());
        } else {
            if (!this.getCoe().equals(i1) && !this.getCoe().equals(im1)) {
                System.out.print(this.getCoe() + "*");
            } else if (this.getCoe().equals(im1)) {
                System.out.print("-1*");
            }
            if (this.getIsPS() == 1) {
                System.out.print("sin(x)");
            } else if (this.getIsPS() != 0) {
                System.out.print("sin(x)**" + this.getIsPS());
            }
            if (this.getIsPS() != 0 && this.getIsPC() != 0) {
                System.out.print("*");
            }
            if (this.getIsPC() == 1) {
                System.out.print("cos(x)");
            } else if (this.getIsPC() != 0) {
                System.out.print("cos(x)**" + this.getIsPC());
            }
        }
    }
}
