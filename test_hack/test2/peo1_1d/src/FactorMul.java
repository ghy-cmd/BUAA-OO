import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FactorMul extends AllFactor {
    private ArrayList<Power> powers = new ArrayList<>();
    private ArrayList<MinExp> minExps = new ArrayList<>();
    
    public FactorMul(int op) {
        super(op);
    }
    
    public ArrayList<MinExp> getMinExps() {
        return minExps;
    }
    
    public ArrayList<Power> getPowers() {
        return powers;
    }
    
    public int getNum() {
        int num = 0;
        for (Power ignored : powers) {
            num = num + 1;
        }
        for (MinExp fa : minExps) {
            num = num + fa.getNum();
        }
        return num;
    }
    
    public void line(String[] s) {
        //System.out.println("line");
        String x0 = "[+-]?\\d+";
        String x = "[+-]?x";
        String xn = "[+-]?x\\^(([+-]?\\d)+)";
        String str = "\\d+";
        for (String value : s) {
            Pattern pattern0 = Pattern.compile(x0);
            Matcher m0 = pattern0.matcher(value);
            Power p;
            if (m0.matches()) {
                Pattern pattern = Pattern.compile(str);
                Matcher m = pattern.matcher(value);
                if (m.find()) {
                    String result = m.group();
                    BigInteger n = new BigInteger(result);
                    if (value.charAt(0) == '-') {
                        p = new Power(n.multiply(BigInteger.valueOf(-1)), BigInteger.ZERO, 0, 0, 1);
                    } else {
                        p = new Power(n.multiply(BigInteger.valueOf(1)), BigInteger.ZERO, 0, 0, 1);
                    }
                    this.getPowers().add(p);
                }
            }
            Pattern pattern1 = Pattern.compile(x);
            Matcher m1 = pattern1.matcher(value);
            if (m1.matches()) {
                if (value.charAt(0) == '-') {
                    p = new Power(BigInteger.valueOf(-1), BigInteger.ONE, 0, 0, 1);
                } else {
                    p = new Power(BigInteger.ONE, BigInteger.ONE, 0, 0, 1);
                }
                this.getPowers().add(p);
            }
            Pattern patternn = Pattern.compile(xn);
            Matcher mn = patternn.matcher(value);
            BigInteger minus = (value.charAt(0) == '-') ? BigInteger.valueOf(-1) : BigInteger.ONE;
            if (mn.matches()) {
                //System.out.println("mn");
                Pattern pattern = Pattern.compile(str);
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
                    //p.print();
                    // System.out.println("mnend");
                    this.getPowers().add(p);
                }
            }
            this.line1(value);
            if (value.charAt(value.length() - 1) == ')') {
                int o = (value.charAt(0) == '-') ? -1 : 1;
                // System.out.println("o id"+o);
                MinExp m = new MinExp(o);
                //System.out.print("BeforeAddM: " + value + "\n");
                m.line(value);
                // System.out.println("m?");
                //m.print();
                // System.out.println("？"+m.getOp());
                this.getMinExps().add(m);
            }
        }
    }
    
    private void line1(String s) {
        BigInteger minus = (s.charAt(0) == '-') ? BigInteger.valueOf(-1) : BigInteger.ONE;
        final String xs = "[+-]?s";
        final String xc = "[+-]?c";
        final String xsn = "[+-]?s\\^(([+-]?\\d)+)";
        final String xcn = "[+-]?c\\^(([+-]?\\d)+)";
        Pattern patterns = Pattern.compile(xs);
        Matcher ms = patterns.matcher(s);
        Power p;
        if (ms.matches()) {
            //System.out.println("msMatch");
            p = new TreA(minus, BigInteger.valueOf(1), 1, 0, 1);
            // System.out.println(p.getExp());
            TreA p1 = (TreA) p.line(s);
            // System.out.println("p1 "+p1.getExp());
            // p1.print();
            // System.out.println("p "+ p.getExp());
            // p.print();
            this.getPowers().add(p1);
            //this.getPowers().get(0).print();;
        }
        Pattern patternc = Pattern.compile(xc);
        Matcher mc = patternc.matcher(s);
        if (mc.matches()) {
            if (s.charAt(0) == '-') {
                p = new TreA(BigInteger.valueOf(-1), BigInteger.ONE, 0, 1, 1);
            } else {
                p = new TreA(BigInteger.ONE, BigInteger.ONE, 0, 1, 1);
            }
            TreA p1 = (TreA) p.line(s);
            this.getPowers().add(p1);
        }
        Pattern patternsn = Pattern.compile(xsn);
        Matcher msn = patternsn.matcher(s);
        String str = "\\d+";
        Pattern pattern = Pattern.compile(str);
        Matcher m = pattern.matcher(s);
        int j = s.indexOf('^');
        if (m.find()) {
            String result = m.group();
            BigInteger num = new BigInteger(result);
            if (msn.matches()) {
                //System.out.println("msn");
                int numi = Integer.parseInt(result);
                if (s.charAt(j + 1) == '-') {
                    // System.out.println("-");
                    p = new TreA(minus, num.multiply(BigInteger.valueOf(-1)), -numi, 0, 1);
                } else {
                    p = new TreA(minus, num, numi, 0, 1);
                }
                //p.print();
                TreA p1 = (TreA) p.line(s);
                //p1.print();
                //System.out.println("p1");
                this.getPowers().add(p1);
            }
            Pattern patterncn = Pattern.compile(xcn);
            Matcher mcn = patterncn.matcher(s);
            if (mcn.matches()) {
                //System.out.println("mcn");
                int numi = Integer.parseInt(result);
                if (s.charAt(j + 1) == '-') {
                    p = new TreA(minus, num.multiply(BigInteger.valueOf(-1)), 0, -numi, 1);
                    // p.print();
                } else {
                    p = new TreA(minus, num, 0, numi, 1);
                }
                TreA p1 = (TreA) p.line(s);
                // p1.print();
                this.getPowers().add(p1);
            }
        }
    }
    
    public void add(AllFactor i) {
        if (i instanceof Power) {
            Power t = (Power) i;
            powers.add(t);
        } else if (i instanceof MinExp) {
            MinExp t = (MinExp) i;
            minExps.add(t);
        }
    }
    
    public FactorMul der() {
        /* System.out.print("MULnoder: ");
        for (Power p : powers) {
            p.print();
            System.out.print(" ");
        }
        for (MinExp m : minExps) {
            m.print();
            System.out.print(" ");
        }*/
        // System.out.print("\n");
        FactorMul m = new FactorMul(this.getOp());
        MinExp e = new MinExp(this.getOp());
        for (Power power : powers) {
            //System.out.print("power is:");
            // power.print();
            //  System.out.print("\n");
            FactorMul m1 = new FactorMul(this.getOp());
            for (Power power1 : powers) {
                if (power != power1) {
                    m1.add(power1);
                } else {
                    // power.print();
                    m1.add(power.der());
                    //power.print();
                }
            }
            // System.out.println(m1.getPowers().size());
            // System.out.println(m1.getMinExps().size());
            for (MinExp minExp : minExps) {
                m1.add(minExp);
                // System.out.println("minExps is");
                // m1.print();
                //System.out.print("\n");
            }
            //System.out.println("\n");
            //System.out.print("1:m1: ");
            // m1.print();
            // System.out.print("\n");
            e.getFactorMuls().add(m1);
        }
        //System.out.println("2lun");
        for (MinExp minExp : minExps) {
            FactorMul m1 = new FactorMul(this.getOp());
            // System.out.println("m1?");
            //  m1.print();
            for (Power power1 : powers) {
                m1.add(power1);
                // power1.print();
                // System.out.println("?");
                // m1.print();
                // System.out.println("\n");
                
            }
            for (MinExp minExp1 : minExps) {
                if (minExp != minExp1) {
                    m1.add(minExp1);
                } else {
                    m1.add(minExp1.der());
                }
            }
            // System.out.print("2:m1: ");
            // m1.print();
            // System.out.print("\n");
            e.getFactorMuls().add(m1);
        }
        /*for(FactorMul k:e.getFactorMuls()){
         System.out.println("min:");
        k.print();
        System.out.println("\n");
        }*/
        m.getMinExps().add(e);
        //System.out.println("0:?");
        // m.getMinExps().get(0).print();
        //System.out.println("?");
        return m;
    }
    
    public void simply() {
        ArrayList<Power> temp = new ArrayList<>();
        Iterator iterator = this.getPowers().iterator();
        int zero = 1;
        while (iterator.hasNext()) {
            Power i = (Power) iterator.next();
            int flag = 1;
            for (Power j : temp) {
                int is = j.getIsPS();
                int ic = j.getIsPC();
                if (i.getExp().equals(BigInteger.ZERO) && i.getIsPS() == 0 && i.getIsPC() == 0) {
                    j.setCoe(j.getCoe().multiply(i.getCoe()));
                    flag = 0;
                    iterator.remove();
                    break;
                } else if (i.getIsPS() == is && i.getIsPC() == ic && is == 0 && ic == 0) {
                    j.setCoe(j.getCoe().multiply(i.getCoe()));
                    j.setExp(j.getExp().add(i.getExp()));
                    j.setIsPF(j.getIsPS() + i.getIsPS(), j.getIsPC() + i.getIsPC());
                    flag = 0;
                    iterator.remove();
                    break;
                } else if ((is != 0 || ic != 0) && (i.getIsPC() != 0 || i.getIsPS() != 0)) {
                    j.setCoe(j.getCoe().multiply(i.getCoe()));
                    j.setExp(j.getExp().add(i.getExp()));
                    j.setIsPF(j.getIsPS() + i.getIsPS(), j.getIsPC() + i.getIsPC());
                    flag = 0;
                    iterator.remove();
                    break;
                }
            }
            if (flag == 1) {
                if (i.getIsPC() != 0 || i.getIsPS() != 0) {
                    TreA j = new TreA(i.getCoe(), i.getExp(), i.getIsPS(), i.getIsPC(), 1);
                    temp.add(j);
                } else {
                    Power j = new Power(i.getCoe(), i.getExp(), i.getIsPS(), i.getIsPC(), 1);
                    temp.add(j);
                }
                iterator.remove();
            }
        }
        this.getPowers().clear();
        for (Power i : temp) {
            if (i.getCoe().equals(BigInteger.ZERO)) {
                zero = 0;
                break;
            }
        }
        if (zero == 0) {
            this.getMinExps().clear();
            temp.clear();
            Power z = new Power(BigInteger.ZERO, BigInteger.ZERO, 0, 0, 1);
            temp.add(z);
        }
        this.getPowers().addAll(temp);
    }
    
    public void print() {
        this.simply();
        if (this.getPowers().size() == 0 && this.getMinExps().size() == 0) {
            return;
        }
        if (this.getPowers().size() != 0) {
            //System.out.println("!0");
            this.getPowers().get(0).print();
            // System.out.print("!\n");
            for (int i = 1; i < this.getPowers().size(); i++) {
                System.out.print("*");
                
                this.getPowers().get(i).print();
            }
        }
        if (this.getPowers().size() == 0) {
            if (this.getMinExps().size() != 0) {
                //System.out.println("mulmin");
                int z = this.getMinExps().get(0).getOp();
                int size = this.getMinExps().size();
                //System.out.print("SIMPLY\n");
                //this.getMinExps().get(0).print();
                this.getMinExps().get(0).simply();
                // this.getMinExps().get(0).print();
                if ((this.getMinExps().get(0).getNum() > 1 && (size > 1 || z == -1))) {
                    if (z == -1) {
                        System.out.print("-");
                    }
                    System.out.print("(");
                }
                this.getMinExps().get(0).print();
                if ((this.getMinExps().get(0).getNum() > 1 && (size > 1 || z == -1))) {
                    System.out.print(")");
                }
                for (int i = 1; i < this.getMinExps().size(); i++) {
                    System.out.print("*");
                    z = this.getMinExps().get(i).getOp();
                    this.getMinExps().get(i).simply();
                    if (z == -1) {
                        System.out.print("-");
                    }
                    System.out.print("(");
                    this.getMinExps().get(i).print();
                    System.out.print(")");
                }
            }
        } else {
            //System.out.println("minexp" + this.getMinExps().size());
            for (int i = 0; i < this.getMinExps().size(); i++) {
                System.out.print("*");
                int z = this.getMinExps().get(i).getOp();
                this.getMinExps().get(i).simply();
                if (z == -1) {
                    System.out.print("-");
                }
                System.out.print("(");
                //System.out.print("?");
                this.getMinExps().get(i).print();
                //System.out.print("?");
                System.out.print(")");
            }
        }
    }
}
