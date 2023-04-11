import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

public class FactorAdd extends AllFactor {
    private ArrayList<Power> powers = new ArrayList<>();
    private ArrayList<MinExp> minExps = new ArrayList<>();
    
    public FactorAdd(int op) {
        super(op);
    }
    
    public int getNum() {
        int num = 0;
        for (Power fa : powers) {
            num = num + 1;
        }
        for (MinExp fa : minExps) {
            num = num + fa.getNum();
        }
        return num;
    }
    
    public ArrayList<MinExp> getMinExps() {
        return minExps;
    }
    
    public ArrayList<Power> getPowers() {
        return powers;
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
    
    public FactorAdd der() {
        FactorAdd a = new FactorAdd(this.getOp());
        
        for (Power power : powers) {
            a.getPowers().add(power.der());
        }
        for (MinExp minExp : minExps) {
            a.getMinExps().add(minExp.der());
        }
        return a;
    }
    
    public void simply() {
        ArrayList<Power> temp = new ArrayList<>();
        Iterator iterator = this.getPowers().iterator();
        while (iterator.hasNext()) {
            Power i = (Power) iterator.next();
            int flag = 1;
            for (Power j : temp) {
                if (i.equalsExp(j)) {
                    j.toMerge(i);
                    iterator.remove();
                    flag = 0;
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
        this.getPowers().addAll(temp);
        for (MinExp m : this.getMinExps()) {
            m.simply();
        }
    }
    
    public void print() {
        this.simply();
        if (this.getPowers().size() == 0 && this.getMinExps().size() == 0) {
            return;
        }
        if (this.getOp() == -1) {
            System.out.print("-");
        }
        for (int i = 0; i < this.getPowers().size(); i++) {
            if (i != 0) {
                if (this.getPowers().get(i).getCoe().compareTo(BigInteger.ZERO) >= 0) {
                    System.out.print("+");
                }
            }
            this.getPowers().get(i).print();
        }
        if (this.getMinExps().size() > 0) {
            if (this.getMinExps().get(0).getOp() > 0 && this.getPowers().size() > 0) {
                System.out.print("+");
            }
            if (this.getMinExps().get(0).getOp() < 0) {
                System.out.print("-(");
            }
            this.getMinExps().get(0).print();
            if (this.getMinExps().get(0).getOp() < 0) {
                System.out.print(")");
            }
        }
        for (int i = 1; i < this.getMinExps().size(); i++) {
            
            if (this.getMinExps().get(i).getOp() == 1) {
                System.out.print("+");
            }
            if (this.getMinExps().get(i).getOp() < 0) {
                System.out.print("-(");
            }
            this.getMinExps().get(i).print();
            if (this.getMinExps().get(i).getOp() < 0) {
                System.out.print(")");
            }
        }
    }
}
