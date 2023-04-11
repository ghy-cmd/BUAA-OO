import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Fac {
    private Map<Integer, Term> id = new TreeMap<>();

    public Fac(BigInteger coe, BigInteger exp0, BigInteger exp1, BigInteger exp2) {
        id.put(Objects.hash(exp0, exp1, exp2), new Term(coe, exp0, exp1, exp2));
    }

    public Fac(Map<Integer, Term> idNew) {
        id = idNew;
    }

    public Map<Integer, Term> getId() {
        return id;
    }

    private void insert(Map<Integer, Term> res, Term temp) {
        Integer hashval = Objects.hash(temp.getExp0(), temp.getExp1(), temp.getExp2());
        Term toMerge = res.get(hashval);
        Term toPut;
        if (toMerge == null) {
            toPut = temp;
        } else {
            toPut = new Term(temp.getCoe().add(toMerge.getCoe()),
                    temp.getExp0(), temp.getExp1(), temp.getExp2());
        }
        hashval = Objects.hash(toPut.getExp0(), toPut.getExp1(), toPut.getExp2());
        res.put(hashval, toPut);
    }

    private Fac solvemul(Fac other) {
        Map<Integer, Term> res = new TreeMap<>();
        for (Integer key1 : other.id.keySet()) {
            Term val1 = other.id.get(key1);
            for (Integer key2 : id.keySet()) {
                Term val2 = id.get(key2);
                //System.out.println(val1.coe);
                //System.out.println(val2.coe);
                Term temp = new Term(val1.getCoe().multiply(val2.getCoe()),
                        val1.getExp0().add(val2.getExp0()),
                        val1.getExp1().add(val2.getExp1()),
                        val1.getExp2().add(val2.getExp2()));
                insert(res, temp);
            }
        }
        return new Fac(res);
    }

    private Fac solveadd(Fac other) {
        Map<Integer, Term> res = new TreeMap<>();
        for (Integer key : other.id.keySet()) {
            Term val = other.id.get(key);
            res.put(key, val);
        }

        for (Integer key : id.keySet()) {
            Term val = id.get(key);
            Integer hashval = Objects.hash(val.getExp0(), val.getExp1(), val.getExp2());
            Term toMerge = res.get(hashval);
            Term toPut;
            if (toMerge == null) {
                toPut = val;
            } else {
                toPut = new Term(val.getCoe().add(toMerge.getCoe()),
                        val.getExp0(), val.getExp1(), val.getExp2());
            }
            hashval = Objects.hash(toPut.getExp0(), toPut.getExp1(), toPut.getExp2());
            res.put(hashval, toPut);
        }

        return new Fac(res);
    }

    private Fac solvesub(Fac other) {
        Map<Integer, Term> res = new TreeMap<>();
        for (Integer key : other.id.keySet()) {
            Term val = other.id.get(key);
            val.setCoe(val.getCoe().negate());
            res.put(key, val);
        }

        for (Integer key : id.keySet()) {
            Term val = id.get(key);
            Integer hashval = Objects.hash(val.getExp0(), val.getExp1(), val.getExp2());
            Term toMerge = res.get(hashval);
            Term toPut;
            if (toMerge == null) {
                toPut = val;
            } else {
                toPut = new Term(val.getCoe().add(toMerge.getCoe()),
                        val.getExp0(), val.getExp1(), val.getExp2());
            }
            hashval = Objects.hash(toPut.getExp0(), toPut.getExp1(), toPut.getExp2());
            res.put(hashval, toPut);
        }

        return new Fac(res);
    }

    public Fac cal(Fac other, char op) {
        //System.out.println("cal");
        //System.out.println(op);
        if (op == '*') {
            return solvemul(other);
        } else if (op == '-') {
            return solvesub(other);
        } else {
            return solveadd(other);
        }
    }

    public Fac der() {
        Map<Integer, Term> res = new TreeMap<>();
        for (Integer key : id.keySet()) {
            Term val = id.get(key);
            BigInteger zero = BigInteger.ZERO;
            BigInteger one = BigInteger.ONE;
            //System.out.println(val.exp0);
            //val.exp0.subtract(one);
            //System.out.println(val.exp0);
            Term temp1 = new Term(val.getCoe().multiply(val.getExp0()),
                    val.getExp0().subtract(one), val.getExp1(), val.getExp2());
            Term temp2 = new Term(val.getCoe().multiply(val.getExp1()).negate(), val.getExp0(),
                    val.getExp1().subtract(one), val.getExp2().add(one));
            Term temp3 = new Term(val.getCoe().multiply(val.getExp2()), val.getExp0(),
                    val.getExp1().add(one), val.getExp2().subtract(one));
            insert(res, temp1);
            insert(res, temp2);
            insert(res, temp3);
        }
        return new Fac(res);
    }

    @Override
    public String toString() {
        return "";
    }
}
