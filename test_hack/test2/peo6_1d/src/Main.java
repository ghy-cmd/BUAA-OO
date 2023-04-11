import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static int tot = 1;
    private static String p = "x\\*\\*([+-]{0,1}[0-9]+)|cos[(]x[)]\\*\\*([+-]{0,1}[0-9]+)"
            + "|sin[(]x[)]\\*\\*([+-]{0,1}[0-9]+)|(cos[(]x[)])|(sin[(]x[)])|([+-]{0,1}[0-9]+)|(x)";
    private static Pattern pattern = Pattern.compile(p);
    private static ArrayList<Fac> expTree = new ArrayList<>(2000);
    private static ArrayList<Integer> lson = new ArrayList<Integer>(2000);
    private static ArrayList<Integer> rson = new ArrayList<Integer>(2000);

    private static void init() {
        for (int i = 0; i <= 2000; i++) {
            BigInteger zero = BigInteger.ZERO;
            expTree.add(new Fac(zero, zero, zero, zero));
            lson.add(0);
            rson.add(1);
        }
    }

    private static String getPattern() {
        String int1 = "([+-]{0,1}[0-9]+)";
        String fac1 = int1;
        String fac2 = "(x)";
        String fac3 = "x\\*\\*" + "" + int1 + "";
        String fac4 = "(cos(x))";
        String fac5 = "(sin(x))";
        String fac6 = "cos(x)\\*\\*" + int1;
        String fac7 = "cos(x)\\*\\*" + int1;
        String facs = fac3 + "|" + fac6 + "|" + fac7 + "|" + fac4 + "|" + fac5 + "|" +
                fac1 + "|" + fac2;
        //System.out.println(facs);
        return facs;
    }

    private static Fac getFac(String s) {
        //System.out.println("gg");
        //System.out.println(s);
        Matcher matched = pattern.matcher(s);
        matched.find();
        BigInteger zero = BigInteger.ZERO;
        BigInteger one = BigInteger.ONE;
        if (matched.group(1) != null) {
            //System.out.println("+1");
            return new Fac(one, new BigInteger(matched.group(1)), zero, zero);
        }
        if (matched.group(2) != null) {
            //System.out.println("+2");
            return new Fac(one, zero, new BigInteger(matched.group(2)), zero);
        }
        if (matched.group(3) != null) {
            //System.out.println("+3");
            //BigInteger temp = new BigInteger(matched.group(3));
            //System.out.println(temp.toString());
            //out(new Fac(one, zero, zero, new BigInteger(matched.group(3))));
            return new Fac(one, zero, zero, new BigInteger(matched.group(3)));
        }
        if (matched.group(4) != null) {
            //System.out.println("+4");
            return new Fac(one, zero, one, zero);
        }
        if (matched.group(5) != null) {
            //System.out.println("+5");
            return new Fac(one, zero, zero, one);
        }
        if (matched.group(6) != null) {
            //System.out.println("+6");
            return new Fac(new BigInteger(matched.group(6)), zero, zero, zero);
        }
        if (matched.group(7) != null) {
            //System.out.println("+7");
            return new Fac(one, one, zero, zero);
        }
        return new Fac(zero, zero, zero, zero);
    }

    public static Fac buildTree(String s, int nl, int nr, int now) {
        if (nl > nr) {
            BigInteger zero = BigInteger.ZERO;
            expTree.set(now, new Fac(zero, zero, zero, zero));
            return expTree.get(now);
        }
        //System.out.println(s);
        //System.out.println(String.valueOf(nl) + " " + String.valueOf(nr));
        int lastPS = -2;
        int lastMD = -2;
        int cnt = 0;
        for (int i = nr; i >= nl; i--) {
            if (s.charAt(i) == '(') {
                cnt++;
            }
            if (s.charAt(i) == ')') {
                cnt--;
            }
            if (cnt == 0 && s.charAt(i) == '+'
                    && (i <= nl + 1 || s.charAt(i - 1) != '*' || s.charAt(i - 2) != '*')) {
                lastPS = i;
                lastMD = -2;
                break;
            }
            if (cnt == 0 && s.charAt(i) == '-'
                    && (i <= nl + 1 || s.charAt(i - 1) != '*' || s.charAt(i - 2) != '*')) {
                lastPS = i;
                lastMD = -2;
                break;
            }
            if (cnt == 0 && s.charAt(i) == '*') {
                if ((i == nr || s.charAt(i + 1) != '*') && (i == nl || s.charAt(i - 1) != '*')) {
                    lastMD = i;
                    ;
                }
            }
        }
        if (lastPS == -2) {
            lastPS = lastMD;
        }
        if (lastPS == -2) {
            if (s.charAt(nl) == '(') {
                tot++;
                //System.out.println(tot);
                lson.set(now, tot);
                expTree.set(now, buildTree(s, nl + 1, nr - 1, tot));
            } else {
                //System.out.println(nl);
                expTree.set(now, getFac(s.substring(nl, nr + 1)));
            }

        } else {
            tot++;
            lson.set(now, tot);
            Fac temp1 = buildTree(s, nl, lastPS - 1, tot);
            tot++;
            rson.set(now, tot);
            Fac temp2 = buildTree(s, lastPS + 1, nr, tot);
            //out(temp1);
            //out(temp2);
            //System.out.println("gg");
            //System.out.println(lastPS);
            expTree.set(now, temp1.cal(temp2, s.charAt(lastPS)));
        }
        //System.out.println("fuck");
        //out(expTree.get(now));
        //System.out.println(String.valueOf(nl) + " " + String.valueOf(nr));
        return expTree.get(now);
    }

    public static void out(Fac now) {
        String ans = "";
        for (Integer key : now.getId().keySet()) {
            Term val = now.getId().get(key);
            if (!val.getCoe().equals(BigInteger.ZERO)) {
                String res = val.toString();
                if (ans.equals("")) {
                    ans = ans + res;
                } else {
                    if (res.charAt(0) == '+' || res.charAt(0) == '-') {
                        ans = ans + res;
                    } else {
                        ans = ans + "+" + res;
                    }
                }
            }
        }
        if (ans.equals("")) {
            ans = "0";
        }
        System.out.println(ans);
    }

    public static void main(String[] args) {
        init();
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        s = s.replace(" ", "");
        s = s.replace("\n", "");
        s = s.replace("\t", "");
        s = s.replace("++", "+");
        s = s.replace("+-", "-");
        s = s.replace("-+", "-");
        s = s.replace("--", "+");
        s = s.replace("++", "+");
        s = s.replace("+-", "-");
        s = s.replace("-+", "-");
        s = s.replace("--", "+");
        Fac ans = buildTree(s, 0, s.length() - 1, 1);
        out(ans.der());
    }
}
