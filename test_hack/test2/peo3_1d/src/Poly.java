import java.math.BigInteger;
import java.util.ArrayList;

public class Poly extends Expression {
    private ArrayList<Term> al = new ArrayList<>();
    private String polyStr;
    private int termsNum = 0;

    public int getTermsNum() {
        return termsNum;
    }

    public void add(Term term) {
        al.add(term);
    }

    public Term getFirstTerm() {
        return al.get(0);
    }

    public String simplify(String s1) {
        String s2 = s1.replace("+-", "-");
        String s3 = s2.replace("-+", "-");
        String s4 = s3.replace("++", "+");
        String s5 = s4.replace("--", "+");
        return s5;
    }

    public static boolean hasPare(String line) {   //判断最外层是否还有括号
        StringBuilder sb = new StringBuilder(line);
        int stack = 0;
        int flag = 0;
        int len = sb.length();
        if (sb.charAt(0) == '(') {
            stack++;
            flag = 1;
            for (int i = 1; i < len - 1; i++) {
                if (sb.charAt(i) == '(') {
                    stack++;
                }
                else if (sb.charAt(i) == ')') {
                    stack--;
                }
                if (stack == 0) {
                    flag = 0;
                }
            }
            if ((flag == 1) && (sb.charAt(len - 1) == ')')) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static String rmOuterPare(String s) {    //去掉最外层括号
        String t = s;
        while (hasPare(t)) {
            StringBuilder sb = new StringBuilder(t);
            int len = sb.length();
            sb.setCharAt(0, 'P');
            sb.setCharAt(len - 1, 'P');
            t = sb.toString();
            t = t.replace("P", "");
        }
        return t;
    }

    public Poly(String line) {
        String t = line;
        ArrayList<Integer> stack = new ArrayList<>();
        polyStr = rmOuterPare(t);
        //System.out.println(polyStr);
        StringBuilder str = new StringBuilder(polyStr);
        if (str.charAt(0) == '(') {
            stack.add(1);
        }
        for (int i = 1; i < str.length() - 1; i++) {
            if (str.charAt(i) == '(') {
                stack.add(1);
            }
            else if (str.charAt(i) == ')') {
                int len = stack.size();
                stack.remove(len - 1);
            }
            if (str.charAt(i) == '+' && stack.size() == 0) {
                if ((str.charAt(i - 1) != '*') && (str.charAt(i + 1) != '*')
                    && str.charAt(i - 1) != '^') {
                    str.setCharAt(i, 'A');
                }
            }
            else if (str.charAt(i) == '-' && stack.size() == 0) {
                if ((str.charAt(i - 1) != '*') && (str.charAt(i + 1) != '*')
                        && str.charAt(i - 1) != '^') {
                    str.setCharAt(i, 'B');
                }
            }
        }
        String s = str.toString();
        s = s.replace("B", "B-");
        String [] terms = s.split("[AB]");
        for (int i = 0; i < terms.length; i++) {
            //System.out.println("terms---------------------:" + terms[i]);
            Term term = new Term(terms[i]);
            polyMerge(term);
        }
    }

    public void polyMerge(Term term) {  //每加入一项，考虑合并
        boolean b = false;
        if ((al.size() > 0) && (term.getCoef().equals(BigInteger.ZERO))) {
            return;
        }
        else {
            for (Term t : al) {
                b = t.merge(term);
                if (b) {
                    break;
                }
            }
            if (!b) {
                int flag = 0;
                for (int i = 0; i < al.size(); i++) {
                    if (al.get(i).getCoef().equals(BigInteger.ZERO)) {
                        al.set(i, term);
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    al.add(term);
                    termsNum++;
                }
            }
        }
    }

    @Override
    public String derive() {
        String r = "(";
        int flag = 0;
        for (Term term : al) {
            if (flag == 0) {
                r = r + term.derive();
                flag = 1;
            }
            else {
                r = r + "+" + term.derive();
            }
        }
        r = r + ")";
        return r;
    }

    @Override
    public String toString() {
        String r = al.get(0).toString();
        for (int i = 1; i < al.size(); i++) {
            r = r + "+" + al.get(i).toString();
        }
        r = Factory.simplify(r);
        return "(" + r + ")";
    }
}
