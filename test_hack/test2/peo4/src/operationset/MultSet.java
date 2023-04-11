package operationset;

import factor.Constant;
import factor.Cos;
import factor.PowFunc;
import factor.Sin;

import java.math.BigInteger;
import java.util.ArrayList;

public class MultSet {
    private ArrayList<Constant> constants = new ArrayList<>();
    private ArrayList<PowFunc> powFuncs = new ArrayList<>();
    private ArrayList<Sin> sins = new ArrayList<>();
    private ArrayList<Cos> coses = new ArrayList<>();
    private ArrayList<AddSet> addSets = new ArrayList<>();
    private Constant finalConst = new Constant(BigInteger.ONE);
    private PowFunc finalPowFunc = new PowFunc(BigInteger.ZERO);
    private Sin finalSin = new Sin(BigInteger.ZERO);
    private Cos finalCos = new Cos(BigInteger.ZERO);
    private AddSet finalAddSet = new AddSet();
    
    public Constant getFinalConst() {
        return finalConst;
    }
    
    public PowFunc getFinalPowFunc() {
        return finalPowFunc;
    }
    
    public Sin getFinalSin() {
        return finalSin;
    }
    
    public Cos getFinalCos() {
        return finalCos;
    }
    
    public AddSet getFinalAddSet() {
        return finalAddSet;
    }
    
    public void setFinalConst(Constant finalConst) {
        this.finalConst = finalConst;
    }
    
    public void setFinalPowFunc(PowFunc finalPowFunc) {
        this.finalPowFunc = finalPowFunc;
    }
    
    public void setFinalSin(Sin finalSin) {
        this.finalSin = finalSin;
    }
    
    public void setFinalCos(Cos finalCos) {
        this.finalCos = finalCos;
    }
    
    public void setFinalAddSet(AddSet finalAddSet) {
        this.finalAddSet = finalAddSet;
    }
    
    private void mergeConstant() {
        for (Constant c : constants) {
            finalConst = finalConst.multiply(c);
        }
    }
    
    private void mergePowFunc() {
        for (PowFunc p : powFuncs) {
            finalPowFunc = finalPowFunc.multiply(p);
        }
    }
    
    private void mergeSin() {
        for (Sin s : sins) {
            finalSin = finalSin.multiply(s);
        }
    }
    
    private void mergeCos() {
        for (Cos c : coses) {
            finalCos = finalCos.multiply(c);
        }
    }
    
    public void openParenthese() {
        MultSet self = new MultSet();
        self.setFinalConst(this.finalConst);
        self.setFinalPowFunc(this.finalPowFunc);
        self.setFinalSin(this.finalSin);
        self.setFinalCos(this.finalCos);
        finalAddSet.add(self);
        AddSet temp = new AddSet();
        for (AddSet a : addSets) {
            for (MultSet m1 : finalAddSet.getMultSets()) {
                for (MultSet m2 : a.getMultSets()) {
                    temp.add(m1.multiply(m2));
                }
            }
            finalAddSet = temp;
            temp = new AddSet();
        }
    }
    //开括号，将finalAddSet变为展开后的结果,用前先merge
    
    private MultSet multiply(MultSet m) {
        MultSet ret = new MultSet();
        ret.setFinalConst(this.finalConst.multiply(m.finalConst));
        ret.setFinalPowFunc(this.finalPowFunc.multiply(m.finalPowFunc));
        ret.setFinalSin(this.finalSin.multiply(m.finalSin));
        ret.setFinalCos(this.finalCos.multiply(m.finalCos));
        return ret;
    }
    //两项最简multiset相乘，返回最简
    
    public void add(Object o) {
        if (o instanceof Constant) {
            Constant c = (Constant) o;
            this.constants.add(c);
            return;
        }
        if (o instanceof PowFunc) {
            PowFunc p = (PowFunc) o;
            this.powFuncs.add(p);
            return;
        }
        if (o instanceof Sin) {
            Sin s = (Sin) o;
            this.sins.add(s);
            return;
        }
        if (o instanceof Cos) {
            Cos c = (Cos) o;
            this.coses.add(c);
        }
        if (o instanceof AddSet) {
            AddSet a = (AddSet) o;
            this.addSets.add(a);
        }
    }
    //把Factor加入初始组
    
    public ArrayList<MultSet> derive() {
        MultSet m1 = new MultSet();
        m1.add(this.finalConst);
        m1.add(this.finalSin);
        m1.add(this.finalCos);
        for (Object o : finalPowFunc.derive()) {
            m1.add(o);
        }
        MultSet m2 = new MultSet();
        m2.add(this.finalConst);
        m2.add(this.finalPowFunc);
        m2.add(this.finalCos);
        for (Object o : finalSin.derive()) {
            m2.add(o);
        }
        MultSet m3 = new MultSet();
        m3.add(this.finalConst);
        m3.add(this.finalPowFunc);
        m3.add(this.finalSin);
        for (Object o : finalCos.derive()) {
            m3.add(o);
        }
        m1.merge();
        m2.merge();
        m3.merge();
        ArrayList<MultSet> multList = new ArrayList<>();
        multList.add(m1);
        multList.add(m2);
        multList.add(m3);
        return multList;
    }
    //返回求导后的multiset组，使用前提：该multiset已最简
    
    public void merge() {
        mergeConstant();
        mergePowFunc();
        mergeSin();
        mergeCos();
        constants.clear();
        powFuncs.clear();
        sins.clear();
        coses.clear();
    }
    //一次性合并除括号以外的各factor，注：只能使用一次！
    
    public String toString() {
        StringBuilder strb = new StringBuilder();
        String pow = finalPowFunc.toString();
        String sin = finalSin.toString();
        String cos = finalCos.toString();
        ArrayList<String> psc = new ArrayList<>();
        psc.add(pow);
        psc.add(sin);
        psc.add(cos);
        String con = finalConst.toString();
        if (con.equals("0")) {
            return "";
        }
        if (!con.equals("1")) {
            if (con.equals("-1")) {
                strb.append('-');
            } else {
                strb.append(con);
            }
        }
        for (String s : psc) {
            if (s.equals("1")) {
                continue;
            } else {
                if (!(strb.length() == 0 || strb.charAt(strb.length() - 1) == '-')) {
                    strb.append('*');
                }
                strb.append(s);
            }
        }
        if (strb.length() == 0 || strb.charAt(strb.length() - 1) == '-') {
            strb.append(1);
        }
        return strb.toString();
    }
}
