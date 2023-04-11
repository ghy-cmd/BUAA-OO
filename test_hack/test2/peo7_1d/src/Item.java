import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Item {
    private ArrayList<Expression> factors = new ArrayList<>();

    public Item(String str) {
        int flag = 0;
        int tag = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                flag++;
            }
            if (str.charAt(i) == ')') {
                flag--;
            }
            if (str.charAt(i) == '*' && i != tag) {
                if (flag == 0) {
                    String substring = str.substring(tag, i);
                    Expression temp = Factory.factor(substring);
                    factors.add(temp);
                    tag = i + 1;
                } } }
        String substring = str.substring(tag);
        Expression temp = Factory.factor(substring);
        factors.add(temp);
    }

    public String dToString() {
        StringBuffer stringBuffer = new StringBuffer();
        BigInteger zero = BigInteger.ZERO;
        for (int i = 0; i < factors.size(); i++) {
            StringBuffer temp = new StringBuffer();
            ArrayList<Tuple> tuples = new ArrayList<>();
            Tuple tuple = new Tuple(zero, zero, zero, zero);
            int flag = 0;                       //因子中是否有表达式因子
            int flag1 = 0;
            if (factors.get(i).dToString().equals("0") ||
                    factors.get(i).dToString().equals("")) { continue; }          //如果某个因子的导数=0，则不输出
            if (!factors.get(i).getClass().getName().equals("Expression")) {
                tuples.add(factors.get(i).getDtuple());      //非表达式因子类存入tuple
            } else {
                flag = 1;
                if (!factors.get(i).dToString().equals("")) {
                    Pattern pattern = Pattern.compile("[+-]{0,3}\\d*(\\*)?(sin\\(x\\)(\\*\\*[+-]?" +
                            "\\d*)?)?(\\*)?(cos\\(x\\)(\\*\\*[+-]?\\d*)?)?" +
                            "(\\*)?(x\\*x|x(\\*\\*[+-]\\d*)?)?");
                    Matcher m = pattern.matcher(factors.get(i).dToString());
                    if (m.matches()) {
                        temp.append(factors.get(i).dToString());
                    } else {
                        temp.append("(" + factors.get(i).dToString() + ")"); } } }
            for (int j = 0; j < factors.size(); j++) {
                if (i == j) {
                    continue;
                }
                if (factors.get(j).toString().equals("0")) {
                    temp.delete(0, temp.length());
                    flag1 = 1;
                    break;
                }
                String add;
                add = factors.get(j).toString();
                if (!factors.get(j).getClass().getName().equals("Expression")) {  //非表达式因字存入tuple中
                    tuples.add(factors.get(j).getTuple()); } else {
                    flag = 1;                                           //含有表达式
                    if (add.charAt(0) == '+') {
                        add = add.substring(1);
                    } else {
                        if (add.charAt(0) == '-') {
                            add = "(" + add + ")"; } }
                    if (temp.toString().equals("")) {           //表达式因子的导数进入字符串缓存区
                        temp.append(add);
                    } else {
                        temp.append("*" + add); } } }
            if (tuples.size() != 0) {
                tuple = (Tuple) tuples.get(0).clone(); }
            for (int k = 1; k < tuples.size(); k++) {
                tuple.combo(tuples.get(k)); }
            String simplify = flag1 == 1 ? "" : tuple.toString(flag);
            StringBuffer smp = new StringBuffer();
            smp.append(simplify);
            smp.append(temp);
            if (!stringBuffer.toString().equals("") && !smp.toString().equals("")) {
                stringBuffer.append("+" + smp);
            } else {
                stringBuffer.append(smp); } }
        return stringBuffer.toString(); }
}
