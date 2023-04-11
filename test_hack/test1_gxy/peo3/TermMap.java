import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TermMap {
    private Map<BigInteger, Term> termMap = new TreeMap<>();

    public void extract(String[] strings) {
        BigInteger zero = new BigInteger("0");
        for (String str : strings) {
            BigInteger exp = calExp(str);
            if (exp.compareTo(zero) == 0) {
                continue;
            }
            Term toMerge = termMap.get(exp);
            Term toPut = new Term();
            toPut.addTerm(str);
            if (toMerge != null) {
                toPut.addCoe(toMerge.getCoe());
            }
            termMap.put(exp, toPut);
        }
        termMap.entrySet().removeIf(entry -> entry.getValue().getCoe().compareTo(zero) == 0);
        // 消除0项
    }

    @Override
    public String toString() {
        Boolean flag = false;
        Boolean first = true;
        String str = "";
        BigInteger zero = new BigInteger("0");
        for (Term term : termMap.values()) {
            flag = true;
            if (!first && term.getCoe().compareTo(zero) == 1) {
                str = str + "+" + term.toString();
            } else {
                str = str + term.toString();
            }
            first = false;
        }
        if (flag) {
            return str;
        } else {
            return "0";
        }
    }

    public void calDerivation() {
        for (Term term : termMap.values()) {
            term.derivation();
        }
    }

    public BigInteger calExp(String str) {
        int count = 0;
        BigInteger zero = new BigInteger("0");
        Pattern patternExp = Pattern.compile("(?<=\\*\\*)(\\+|-)?\\d+");
        Matcher matcherExp = patternExp.matcher(str);
        BigInteger exp;
        BigInteger tempExp = zero;
        while (matcherExp.find()) {
            if (matcherExp.group().charAt(0) == '-') {
                BigInteger temp = new BigInteger(matcherExp.group().substring(1));
                exp = zero.subtract(temp);
            } else if (matcherExp.group().charAt(0) == '+') {
                BigInteger temp = new BigInteger(matcherExp.group().substring(1));
                exp = temp;
            } else {
                BigInteger temp = new BigInteger(matcherExp.group());
                exp = temp;
            }
            if (count == 0) {
                tempExp = exp;
            } else {
                tempExp = tempExp.add(exp);
            }
            count++;
        }
        if (count == 0) {
            tempExp = zero;
        }
        BigInteger onlyX = new BigInteger(String.valueOf(findOnlyX(str)));
        tempExp = tempExp.add(onlyX);
        return tempExp;
    }

    public int findOnlyX(String str) {
        Pattern pattern = Pattern.compile("x(?!\\*\\*)");
        Matcher matcher = pattern.matcher(str);
        int onlyX = 0;
        while (matcher.find()) {
            onlyX++;
        }
        return onlyX;
    }

}
