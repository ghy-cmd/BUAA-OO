import java.math.BigInteger;
import java.util.Map;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        //输入预处理
        ExpParse expParse = new ExpParse(s);
        String str = expParse.preParse();

        //项初步分离
        Expression expression = new Expression();
        int start = 0;
        int end = 0;
        for (int i = 1; i < str.length(); i++) {
            if ((str.charAt(i) == '+' || str.charAt(i) == '-') && str.charAt(i - 1) != '*') {
                end = i;
            }
            if (start != end) { //项预处理
                //开头处可能会省略-1*x为-x
                String sforTerm = str.substring(start,end);
                if (str.substring(start,end).charAt(0) == '-') {
                    sforTerm = "-1*" + sforTerm.substring(1);
                }
                //System.out.println(sForTerm);
                Term term = new Term();
                term.setTerm(sforTerm);
                expression.addExp(term);
            }
            start = end;
        }

        Map<BigInteger, Term> expToTerm = expression.getExpToTerm();
        BigInteger defZero = BigInteger.valueOf(0);
        //删掉系数为0的项
        expToTerm.entrySet().removeIf(entry -> entry.getValue().getCoe().equals(defZero));
        //删掉指数为0的项（求导）
        expToTerm.entrySet().removeIf(entry -> entry.getValue().getExp().equals(defZero));
        //遍历化简后的所有项 //后求导
        if (expToTerm.isEmpty()) {
            System.out.println("0");
        } else {
            String output = "";
            for (Term value : expToTerm.values()) {
                //System.out.println(value.getCoe() + "*x**" + value.getExp());
                Term term = new Term();
                term.updSetTerm(value.getCoe(),value.getExp());
                //term.diffTerm返回的是字符串 正项提前
                if (term.diffTerm().charAt(0) == '+') {
                    output = term.diffTerm() + output;
                } else { output = output + term.diffTerm(); }
            }
            if (output.charAt(0) == '+') { output = output.substring(1); }
            System.out.print(output);//进一步化简
        }

    }
}
