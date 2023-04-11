import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

public class Expression {
    private Map<BigInteger, Term> expToTerm = new TreeMap<>();

    public Expression() {
    }

    public void addExp(Term term) {
        BigInteger coe = term.getCoe();
        BigInteger exp = term.getExp();
        //添加到treemap并对指数相同的项进行合并
        Term toMerge = expToTerm.get(exp);
        Term toPut;
        if (toMerge == null) {
            toPut = term;
        } else {
            toPut = new Term();
            toPut.updSetTerm(coe.add(toMerge.getCoe()),exp);
        }
        expToTerm.put(exp,toPut);
    }

    public Map<BigInteger, Term> getExpToTerm() { return expToTerm; }
}
