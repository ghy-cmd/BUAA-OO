import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Term {
    private BigInteger coe;
    private BigInteger exp;

    public Term() {
    }

    public BigInteger getCoe() { return coe; }

    public BigInteger getExp() { return exp; }

    public void setTerm(String s) {
        //BigInteger resMul11 = BigInteger.valueOf(1);
        //BigInteger resMul21 = BigInteger.valueOf(1);
        //BigInteger resAdd21 = BigInteger.valueOf(0);
        ArrayList<String> rcdAll = new ArrayList<>();
        ArrayList<String> rcdExp = new ArrayList<>();

        //匹配所有数
        Pattern p11 = Pattern.compile("[+|-]{0,1}[0-9]{1,}");
        Matcher m11 = p11.matcher(s);
        while (m11.find()) {
            //System.out.print("coe + exp :")；
            //System.out.println(m11.group());
            rcdAll.add(m11.group());
            //出现的所有数字连乘
            //BigInteger tmpMul11 = new BigInteger(m11.group());
            //resMul11 = resMul11.multiply(tmpMul11);

        }

        //匹配指数
        Pattern p21 = Pattern.compile("\\*\\*[+|-]{0,1}[0-9]{1,}");
        Matcher m21 = p21.matcher(s);
        while (m21.find()) {
            //System.out.print("exp:");
            //System.out.println(m21.group().substring(2));
            rcdExp.add(m21.group().substring(2));
            //出现的所有数字连加
            //BigInteger tmpAdd21 = new BigInteger(m21.group().substring(2));
            //resAdd21 = resAdd21.add(tmpAdd21);
            //出现的所有数字连乘
            //BigInteger tmpMul21 = new BigInteger(m21.group().substring(2));
            //resMul21 = resMul21.multiply(tmpMul21);
        }
        //标记rcdAll -> signAll = 1则rcdAll = rcdCoe
        Integer []signAll = new Integer[1050];
        for (int i = 0; i < rcdAll.size(); i++) {
            signAll[i] = 1;
        }

        for (String tmpExp : rcdExp) {
            for (int i = 0; i < rcdAll.size(); i++) {
                if (tmpExp.equals(rcdAll.get(i)) && signAll[i].equals(1)) {
                    signAll[i] = 0;
                    break;
                }
            }
        }

        //判断x后面有没有跟着**即计数未按照规则出现的指数1
        BigInteger cnt = BigInteger.valueOf(0);
        if (s.charAt(s.length() - 1) == 'x') { cnt = cnt.add(BigInteger.valueOf(1)); }
        for (int i = 0; i < s.length() - 2; i++) {
            if (s.charAt(i) == 'x') {
                if (s.charAt(i + 1) == '*' && s.charAt(i + 2) != '*') {
                    cnt = cnt.add(BigInteger.valueOf(1));
                }
            }
        }

        //coe和exp
        BigInteger resCoe = BigInteger.valueOf(1);
        for (int i = 0; i < rcdAll.size(); i++) {
            if (signAll[i] == 1) {
                resCoe = resCoe.multiply(new BigInteger(rcdAll.get(i)));
            }
        }

        BigInteger resExp = BigInteger.valueOf(0);
        for (String tmpExp : rcdExp) {
            resExp = resExp.add(new BigInteger(tmpExp));
        }
        resExp = resExp.add(cnt);

        this.coe = resCoe;
        this.exp = resExp;
        //System.out.print("coe:");
        //System.out.println(this.coe);
        //System.out.print("exp:");
        //System.out.println(this.exp);
    }

    public void updSetTerm(BigInteger coe,BigInteger exp) {
        this.coe = coe;
        this.exp = exp;
    }

    //求导
    public String diffTerm() {
        BigInteger def1 = BigInteger.valueOf(1);
        BigInteger def0 = BigInteger.valueOf(0);
        BigInteger defn1 = BigInteger.valueOf(-1);

        String coeStr = String.valueOf(coe);
        String expStr = String.valueOf(exp);
        //必要的符号判断 +:--/++/n+/+n/nn  -:+-/-+/-n/n-
        if ((coeStr.charAt(0) == '-' && expStr.charAt(0) != '-') ||
            (coeStr.charAt(0) != '-' && expStr.charAt(0) == '-')) { //负数(自带负号)
            if (coe.multiply(exp).equals(defn1)) { //系数上-1*可以写为-
                if (exp.add(defn1).equals(def1)) { //指数上**1可以不写
                    return "-x";
                } else if (exp.add(defn1).equals(def0)) { //指数上**0可以不写
                    return "-1";
                } else { return "-x**" + exp.add(defn1); }
            } else {
                if (exp.add(defn1).equals(def1)) { //指数上**1可以不写
                    return coe.multiply(exp) + "*x";
                } else if (exp.add(defn1).equals(def0)) { //指数上**0可以不写
                    return String.valueOf(coe.multiply(exp));
                } else { return coe.multiply(exp) + "*x**" + exp.add(defn1); } //正常输出
            }
        } else { //正数
            if (coe.multiply(exp).equals(def1)) { //系数上1*可以不写
                if (exp.add(defn1).equals(def1)) { //指数上**1可以不写
                    return "+x";
                } else if (exp.add(defn1).equals(def0)) { //指数上**0可以不写
                    return "+1";
                } else { return "+x**" + exp.add(defn1); }
            } else {
                if (exp.add(defn1).equals(def1)) { //指数上**1可以不写
                    return "+" + coe.multiply(exp) + "*x";
                } else if (exp.add(defn1).equals(def0)) { //指数上**0可以不写
                    return "+" + coe.multiply(exp);
                } else { return "+" + coe.multiply(exp) + "*x**" + exp.add(defn1); }
            }
        }
    }

}
