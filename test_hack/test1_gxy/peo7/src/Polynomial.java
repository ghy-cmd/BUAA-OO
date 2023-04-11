import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Polynomial {
    private ArrayList<Monomial> polynomialList;

    public Polynomial(String s0) {
        polynomialList = new ArrayList<>();
        String s = formatString(s0);
        s = mergeSubtract(s);
        int last = 0;
        for (int i = 1;i < s.length();i++) {
            char c = s.charAt(i);
            if ((c == '+' || c == '-') && s.charAt(i - 1) != '^' && s.charAt(i - 1) != '*') {
                polynomialList.add(new Monomial(s.substring(last,i)));
                last = i;
            }
        }
        polynomialList.add(new Monomial(s.substring(last)));
    }

    private String formatString(String s) {
        String res = "";
        for (int i = 0;i < s.length();i++) {
            char c = s.charAt(i);
            if (c == ' ' || c == '\t') {
                continue;
            } else if (c == '*' && s.charAt(i + 1) == '*') {
                res = res + "^";
                i++;
            } else {
                res = res + c;
            }
        }
        return res;
    }

    private String mergeSubtract(String s) {
        String res = "";
        for (int i = 0;i < s.length();i++) {
            char c = s.charAt(i);
            if (c == '-' || c == '+') {
                int cnt = 0;
                while (s.charAt(i) == '-' || s.charAt(i) == '+') {
                    if (s.charAt(i) == '-') {
                        cnt++;
                    }
                    i++;
                }
                i--;
                if (cnt % 2 == 1) {
                    res = res + "-";
                } else {
                    res = res + "+";
                }
            } else {
                res = res + c;
            }
        }
        return res;
    }

    public void differentiate() {
        ArrayList<Monomial> p = new ArrayList<>();
        for (Monomial i : polynomialList) {
            p.add(i.differentiate());
        }
        polynomialList = p;
    }

    public void combineLikeTerms() {
        Map<BigInteger,BigInteger> map = new HashMap<>();
        for (Monomial i : polynomialList) {
            if (map.get(i.getIndex()) == null) {
                map.put(i.getIndex(),i.getCoefficient());
            } else {
                BigInteger bigInteger = map.get(i.getIndex());
                bigInteger = bigInteger.add(i.getCoefficient());
                map.remove(i.getIndex());
                map.put(i.getIndex(),bigInteger);
            }
        }
        ArrayList<Monomial> p = new ArrayList<>();
        for (BigInteger i : map.keySet()) {
            p.add(new Monomial(map.get(i),i));
        }
        polynomialList = p;
    }

    public void print() {
        BigInteger one = new BigInteger("1");
        BigInteger zero = new BigInteger("0");
        boolean flag = false;
        for (Monomial i : polynomialList) {
            if (i.getCoefficient().compareTo(zero) == 0) {
                continue;
            } else if (i.getCoefficient().compareTo(zero) > 0 && flag) {
                System.out.print("+");
            } else if (i.getCoefficient().compareTo(zero) < 0) {
                System.out.print("-");
            }
            flag = true;
            if (i.getIndex().equals(zero)) {
                System.out.print(i.getCoefficient().abs());
                continue;
            }
            if (!i.getCoefficient().abs().equals(one)) {
                System.out.print(i.getCoefficient().abs() + "*");
            }
            System.out.print("x");
            if (!i.getIndex().equals(one)) {
                System.out.print("**" + i.getIndex());
            }
        }
        if (!flag) {
            System.out.print("0");
        }
    }
}
