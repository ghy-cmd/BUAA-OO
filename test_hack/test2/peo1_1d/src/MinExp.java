import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinExp extends AllFactor {
    private ArrayList<FactorAdd> factorAdds = new ArrayList<>();
    private ArrayList<FactorMul> factorMuls = new ArrayList<>();
    
    public MinExp(int op) {
        super(op);
    }
    
    public int getNum() {
        int num = 0;
        for (FactorAdd fa : factorAdds) {
            num = num + fa.getNum();
        }
        for (FactorMul fa : factorMuls) {
            num = num + fa.getNum();
        }
        return num;
    }
    
    public ArrayList<FactorAdd> getFactorAdds() {
        return factorAdds;
    }
    
    public ArrayList<FactorMul> getFactorMuls() {
        return factorMuls;
    }
    
    public void add(AllFactor i) {
        if (i instanceof FactorAdd) {
            FactorAdd t = (FactorAdd) i;
            factorAdds.add(t);
        } else if (i instanceof FactorMul) {
            FactorMul t = (FactorMul) i;
            factorMuls.add(t);
        } else if (i instanceof Power) {
            Power t = (Power) i;
            if (factorAdds.size() == 0) {
                FactorAdd f = new FactorAdd(1);
                factorAdds.add(f);
            }
            factorAdds.get(0).add(t);
        } else if (i instanceof MinExp) {
            MinExp t = (MinExp) i;
            if (factorAdds.size() == 0) {
                FactorAdd f = new FactorAdd(1);
                factorAdds.add(f);
            }
            factorAdds.get(0).add(t);
        }
    }
    
    public MinExp der() {
        MinExp s = new MinExp(this.getOp());
        for (FactorAdd i : this.factorAdds) {
            s.getFactorAdds().add(i.der());
        }
        for (FactorMul i : this.getFactorMuls()) {
            s.getFactorMuls().add(i.der());
        }
        return s;
    }
    
    public void line(String s) {
        /* for(int q=0;q<f.length;q++){
            System.out.println(f[q]);
        }*/
        //System.out.println(s);
        String[] f = dealLine(s);
        String str = "^[+-]?(\\d+|[xsc]|[xsc]\\^[+-]?\\d+)$";
        Pattern p = Pattern.compile(str);
        for (String value : f) {
            ///System.out.println("value is "+value);
            //System.out.print("\n");
            Matcher m = p.matcher(value);
            int matchmin = 0;
            int sign = 0;
            if (value.length() > 1 && value.charAt(value.length() - 1) == ')') {
                boolean pre1 = value.charAt(1) == '(';
                boolean pre0 = value.charAt(0) == '(';
                if (pre0 || (value.charAt(0) == '+' && pre1) || (value.charAt(0) == '-' && pre1)) {
                    for (int g = 0; g < value.length() - 1; g++) {
                        if (value.charAt(g) == '(') {
                            boolean k0 = value.charAt(0) == '+';
                            boolean k = !(g == 1 && (value.charAt(0) == '-' || k0));
                            if (g != 0 && k) {
                                matchmin++;
                            }
                        } else if (value.charAt(g) == ')') {
                            matchmin--;
                            if (matchmin < 0) {
                                sign = 1;
                            }
                        }
                    }
                }
            }
            if (m.find()) {
                //System.out.println("isP ");
                Power power = new Power(BigInteger.ONE, BigInteger.ONE, 0, 0, 1);
                Power power1 = power.line(value);
                //System.out.println("?");
                //power1.print();
                //System.out.println("\npowerm");
                this.add(power1);
            } else if (sign == 1) {
                int o = (value.charAt(0) == '-') ? -1 : 1;
                MinExp minExp = new MinExp(o);
                minExp.line(value);
                this.add(minExp);
            } else {
                int match = 0;
                StringBuilder k = new StringBuilder(value);
                for (int j = 0; j < value.length(); j++) {
                    if (value.charAt(j) == '(') {
                        match++;
                    } else if (value.charAt(j) == ')') {
                        match--;
                    }
                    if (match == 0 && (value.charAt(j) == '*')) {
                        k.replace(j, j + 1, " ");
                    }
                }
                String[] ksplit = k.toString().split(" ");
                this.dealLine2(ksplit);
            }
        }
    }
    
    public String[] dealLine(String s) {
        StringBuilder s1 = new StringBuilder(s);
        //System.out.println("s is"+s);
        if (s.charAt(0) == '-') {
            this.setOp(-1);
        }
        if (s.charAt(0) == '-' || s.charAt(0) == '+') {
            s1.replace(0, 1, "");
        }
        s1.replace(0, 1, "");
        s1.replace(s1.length() - 1, s1.length(), "");
        //System.out.println("minline is " + s1);
        int match = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == '(') {
                match++;
            } else if (s1.charAt(i) == ')') {
                match--;
            } else if (i > 0) {
                if (match == 0 && s1.charAt(i) == '+') {
                    s1.replace(i, i + 1, " ");
                } else {
                    boolean k = s1.charAt(i - 1) != '^';
                    if (match == 0 && s1.charAt(i) == '-' && s1.charAt(i - 1) != '*' && k) {
                        s1.replace(i, i + 1, "#");
                    }
                }
            }
        }
        String s2 = s1.toString().replace("#", " -");
        return s2.split(" ");
    }
    
    public void dealLine2(String[] ksplit) {
        FactorMul l;
        l = new FactorMul(1);
        l.line(ksplit);
        this.add(l);
    }
    
    public void simply() {
        for (FactorAdd fa : factorAdds) {
            fa.simply();
            // fa.print();
            // System.out.print(" fa??\n");
        }
        for (FactorMul fm : factorMuls) {
            //System.out.print("fbpre\n");
            fm.simply();
            //fm.print();
            //System.out.print(" fb??\n");
        }
    }
    
    public void print() {
        if (this.getFactorAdds().size() == 0 && this.getFactorMuls().size() == 0) {
            return;
        }
        /*if (this.getOp() == -1) {
            System.out.print("-(e)");
        }*/
        
        //int bao = ((this.factorAdds.size()+this.getFactorMuls().size())==1)?1:0;
        /*if(bao==0) {
            System.out.print("(");
        }*/
        for (int i = 0; i < this.getFactorAdds().size(); i++) {
            if (i != 0) {
                if (this.getFactorAdds().get(i).getOp() > 0) {
                    System.out.print("+");
                }
            }
            //System.out.println("endminprint\n");
            this.getFactorAdds().get(i).print();
        }
        if (this.getFactorAdds().size() > 0) {
            if (this.getFactorMuls().size() > 0 && this.getFactorMuls().get(0).getOp() > 0) {
                System.out.print("+");
            }
        }
        for (int i = 0; i < this.getFactorMuls().size(); i++) {
            if (i != 0) {
                if (this.getFactorMuls().get(i).getOp() > 0) {
                    System.out.print("+");
                }
            }
            this.getFactorMuls().get(i).print();
        }
        /*if(bao==0) {
            System.out.print(")");
        }*/
    }
}
