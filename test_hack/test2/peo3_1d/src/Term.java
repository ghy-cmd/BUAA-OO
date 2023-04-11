import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Term extends Expression {
    private ArrayList<Expression> al = new ArrayList<>();
    private BigInteger coef = BigInteger.ONE;
    private BigInteger indexPow = BigInteger.ZERO;
    private BigInteger indexSin = BigInteger.ZERO;
    private BigInteger indexCos = BigInteger.ZERO;
    private boolean isNest = false;

    public BigInteger getCoef() {
        return coef;
    }

    public void init() {
        al.add(0, new Num("1"));
        al.add(1, new Pow("x^0"));
        al.add(2, new Trig("S^0"));
        al.add(3, new Trig("C^0"));
    }

    public static ArrayList<String> split(String t) {
        int stack = 0;
        StringBuilder str = new StringBuilder(t);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                stack++;
            }
            else if (str.charAt(i) == ')') {
                stack--;
            }
            if (str.charAt(i) == '*' && stack == 0) {
                str.setCharAt(i, 'M');
            }
        }
        String [] temp = str.toString().split("M");
        ArrayList<String> words = new ArrayList<>();
        for (String s : temp) {
            words.add(s);
        }
        return words;
    }

    public Term(String t) {
        init();
        ArrayList<String> words = split(t);
        String commNum = "^[+-]?[0-9]+$";
        String commPow = "^([+-]?)x(\\^([+-]?[0-9]+))?$";
        String commSin = "^([+-]?)S(\\^([+-]?[0-9]+))?$";
        String commCos = "^([+-]?)C(\\^([+-]?[0-9]+))?$";
        Pattern patternNum = Pattern.compile(commNum);
        Pattern patternPow = Pattern.compile(commPow);
        Pattern patternSin = Pattern.compile(commSin);
        Pattern patternCos = Pattern.compile(commCos);
        for (int i = 0; i < words.size(); i++) {
            String w = words.get(i);
            Matcher matcherNum = patternNum.matcher(w);
            Matcher matcherPow = patternPow.matcher(w);
            Matcher matcherSin = patternSin.matcher(w);
            Matcher matcherCos = patternCos.matcher(w);
            if (matcherNum.find()) {
                Num num = new Num(matcherNum.group(0));
                Num temp = (Num) al.get(0);
                Expression r = num.multiply(temp);
                al.set(0, r);
                coef = coef.multiply(new BigInteger(matcherNum.group(0)));
                if (coef.equals(BigInteger.ZERO)) {
                    break;
                }
            }
            else if (matcherPow.find()) {
                Pow pow = new Pow(matcherPow.group(0));
                Pow temp = (Pow) al.get(1);
                Expression r = pow.multiply(temp);
                al.set(1, r);
                indexPow = indexPow.add(pow.getDegree());
                coef = coef.multiply(pow.getCoef());
            }
            else if (matcherSin.find()) {
                Trig sin = new Trig(matcherSin.group(0));
                Trig temp = (Trig) al.get(2);
                Expression r = sin.multiply(temp);
                al.set(2, r);
                indexSin = indexSin.add(sin.getDegree());
                coef = coef.multiply(sin.getCoef());
            }
            else if (matcherCos.find()) {
                Trig cos = new Trig(matcherCos.group(0));
                Trig temp = (Trig) al.get(3);
                Expression r = cos.multiply(temp);
                al.set(3, r);
                indexCos = indexCos.add(cos.getDegree());
                coef = coef.multiply(cos.getCoef());
            }
            else {
                matchPoly(w,words);
            }
        }
    }

    public void matchPoly(String w, ArrayList words) {
        Expression exp = new Poly(w);
        al.add(exp);
        Poly poly = (Poly) exp;
        if ((poly.getTermsNum() == 1)
                && (!poly.getFirstTerm().isNest)) {
            ArrayList<String> newAl =
                    split(Factory.replace(poly.getFirstTerm().toString()));
            for (String s : newAl) {
                words.add(s);
            }
            al.remove(exp);
        }
        else {
            isNest = true;
        }
    }

    public boolean merge(Term t) {  //注：会修改对象本身
        boolean r = false;
        if ((!isNest) && (!t.isNest)) {
            if ((indexPow.equals(t.indexPow)) && (indexSin.equals(t.indexSin))
                    && (indexCos.equals(t.indexCos))) {
                //System.out.println("merge");
                coef = coef.add(t.coef);

                r = true;
            }
            else if (t.coef.equals(BigInteger.ZERO)) {
                r = true;
            }
        }
        return r;
    }

    @Override
    public String derive() {
        String r = "";
        if (coef.equals(BigInteger.ZERO)) {
            r = "0";
        }
        else {
            ArrayList<Expression> expressions = new ArrayList<>();
            if (!indexPow.equals(BigInteger.ZERO)) {
                expressions.add(al.get(1));
            }
            if (!indexSin.equals(BigInteger.ZERO)) {
                expressions.add(al.get(2));
            }
            if (!indexCos.equals(BigInteger.ZERO)) {
                expressions.add(al.get(3));
            }
            if (!isNest) {
                if (expressions.size() == 0) {  //所有指数都为0
                    r = "0";
                }
                else {
                    r = coef.toString() + "*(";
                    for (int i = 0; i < expressions.size(); i++) {
                        r = r + "+" + expressions.get(i).derive();
                        for (int j = 0; j < expressions.size(); j++) {
                            if (j != i) {
                                r = r + "*" + expressions.get(j).toString();
                            }
                        }
                    }
                    r = r + ")";
                }
            }
            else {
                for (int i = 4; i < al.size(); i++) {
                    expressions.add(al.get(i));
                }
                r = coef.toString() + "*(";
                for (int i = 0; i < expressions.size(); i++) {
                    r = r + "+" + expressions.get(i).derive();
                    for (int j = 0; j < expressions.size(); j++) {
                        if (j != i) {
                            r = r + "*" + expressions.get(j).toString();
                        }
                    }
                }
                r = r + ")";
            }
        }
        return r;
    }

    @Override
    public String toString() {
        String r = "";
        if (coef.equals(BigInteger.ZERO)) {
            r = "0";
        }
        else {
            if (!isNest) {  //无嵌套
                r = noNest();
            }
            else {  //有嵌套
                if (coef.equals(BigInteger.ONE)) {
                    r = "";
                }
                else if (coef.equals(new BigInteger("-1"))) {
                    r = "-";
                }
                else {
                    r = coef.toString() + "*";
                }
                r = r + getFactors();
            }
        }
        r = Poly.rmOuterPare(r);
        return r;
    }

    public String noNest() {
        String r = "";
        String all = "";
        int flag = 1;   //是否全为0次幂
        int first = 0;
        if (!indexPow.equals(BigInteger.ZERO)) {
            flag = 0;
            all = all + al.get(1).toString();
            first = 1;
        }
        if (!indexSin.equals(BigInteger.ZERO)) {
            flag = 0;
            if (first == 0) {
                all = all + al.get(2).toString();
                first = 1;
            }
            else {
                all = all + "*" + al.get(2).toString();
            }
        }
        if (!indexCos.equals(BigInteger.ZERO)) {
            flag = 0;
            if (first == 0) {
                all = all + al.get(3).toString();
                first = 1;
            }
            else {
                all = all + "*" + al.get(3).toString();
            }
        }
        if (flag == 1) {    //全为0
            r = coef.toString();
        }
        else {      //指数不全为0
            if (coef.equals(BigInteger.ONE)) {
                r = all;
            }
            else if (coef.equals(new BigInteger("-1"))) {
                r = "-" + all;
            }
            else {
                r = coef.toString() + "*" + all;
            }
        }
        return r;
    }

    public String getFactors() {
        String all = "";
        int first = 0;
        if (!indexPow.equals(BigInteger.ZERO)) {
            all = all + al.get(1).toString();
            first = 1;
        }
        if (!indexSin.equals(BigInteger.ZERO)) {
            if (first == 0) {
                all = all + al.get(2).toString();
                first = 1;
            }
            else {
                all = all + "*" + al.get(2).toString();
            }
        }
        if (!indexCos.equals(BigInteger.ZERO)) {
            if (first == 0) {
                all = all + al.get(3).toString();
                first = 1;
            }
            else {
                all = all + "*" + al.get(3).toString();
            }
        }
        for (int i = 4; i < al.size(); i++) {
            if (first == 0) {
                all = all + al.get(i).toString();
                first = 1;
            }
            else {
                all = all + "*" + al.get(i).toString();
            }
        }
        return all;
    }
}
