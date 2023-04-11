import java.util.*;

public final class Expression {
    private final Map<Integer, Term> expToTerm = new TreeMap<>();

    public Expression(String str) {
        String[] coeAndExp = str.split(" ");
        for (int i = 0; i < coeAndExp.length; ) {
            // 解析出系数 coe 和指数 exp
            int coe = Integer.parseInt(coeAndExp[i++]);
            int exp = Integer.parseInt(coeAndExp[i++]);
            // 添加到 TreeMap 中，将指数相同的项进行合并
            Term toMerge = expToTerm.get(exp);
            Term toPut;
            if (toMerge == null) {
                toPut = new Term(coe, exp);
            } else {
                toPut = new Term(coe + toMerge.getCoe(), exp);
            }
            expToTerm.put(exp, toPut);
        }
        // 去除系数为0的项
        expToTerm.entrySet().removeIf(entry -> entry.getValue().getCoe() == 0);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Expression)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        Expression that = (Expression) o;
        return Objects.equals(expToTerm, that.expToTerm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expToTerm);
    }

    @Override
    public String toString() {
        ArrayList<Integer> keyset = new ArrayList<>();
        String a = "";
        for (Integer item : expToTerm.keySet()) {
            keyset.add(item);
        }
        Collections.sort(keyset);
        if (keyset.size() == 0) {
            a = a + "0";
        } else if (expToTerm.get(keyset.get(0)).getCoe() > 0) {
            a = a + (expToTerm.get(keyset.get(0)).toString());
            for (int i = 1; i < keyset.size(); i++) {
                if (expToTerm.get(keyset.get(i)).getCoe() > 0) {
                    a = a + "+" + expToTerm.get(keyset.get(i)).toString();
                } else {
                    a = a + expToTerm.get(keyset.get(i)).toString();
                }
            }
        } else {
            for (int i = 0; i < keyset.size(); i++) {
                if (expToTerm.get(keyset.get(i)).getCoe() > 0) {
                    a = a + "+" + expToTerm.get(keyset.get(i)).toString();
                } else {
                    a = a + expToTerm.get(keyset.get(i)).toString();
                }
            }
        }
        return a;
    }
}
