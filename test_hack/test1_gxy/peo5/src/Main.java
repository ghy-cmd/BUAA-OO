import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String readin = scanner.nextLine();
        List<String> signs = new ArrayList<>();
        List<String> trims = new ArrayList<>();
        Pattern pattern = Pattern.compile("(([+-]?x(\\*{2}[+-]?\\d+){0,1})|([+-]?[+-]?\\d+))(\\*((x(\\*{2}[+-]?\\d+){0,1})|([+-]?\\d+)))*");
        String expression = readin.replace(" ", "");
        expression = expression.replace("\t", "");
        if (expression.charAt(0) == '-') {
            signs.add(expression.substring(0, 1));
            expression = expression.substring(1);
        } else {
            signs.add("+");
        }
        Matcher matcher = pattern.matcher(expression);
        
        while (matcher.find()) {
            trims.add(matcher.group());
            if (matcher.end() + 1 < expression.length()) {
                String sign = expression.substring(matcher.end(), matcher.end() + 1);
                signs.add(sign);
            }
            if (matcher.end() + 1 < expression.length()) {
                matcher.region(matcher.end() + 1, expression.length());
            }
        }
        output(trims, signs);
    }
    
    public static void output(List<String> trims, List<String> signs) {
        Add adder = new Add();
        int i;
        for (i = 0; i < trims.size(); i++) {
            String trimtoDeal = trims.get(i);
            String signtoAdd = signs.get(i);
            Trim trimtoAdd = trimDeal(trimtoDeal);
            if (signtoAdd.equals("-")) {
                trimtoAdd.setCeo(trimtoAdd.getCeo().multiply(new BigInteger("-1")));
            }
            adder = new Add(adder.getValue(), trimtoAdd);
        }
        System.out.println(adder.getValue().printPoly());
    }
    
    public static Trim trimDeal(String trim) {
        List<Trim> trimList = new ArrayList<>();
        String regex1 = "((([+-])x(\\*{2}([+-]?\\d+)){0,1}))";
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(trim);
        String trimExpHead = trim;
        //处理开头有符号的特殊形式
        //首先是省略1且后面跟的是变量因子
        if (matcher1.lookingAt()) {
            Pattern pattern2 = Pattern.compile("((([+-]x(\\*{2}[+-]?\\d+){0,1})))(.*)");
            Matcher matcher2 = pattern2.matcher(trim);
            if (matcher2.find()) {
                if (matcher2.group(5) != null) {
                    trimExpHead = matcher2.group(5);
                }
            }
            if (matcher1.group(3).equals("-")) {
                if (matcher1.group(5) == null) {
                    trimList.add(new Trim(new BigInteger("-1"), new BigInteger("1")));
                } else {
                    trimList.add(new Trim(new BigInteger("-1"), new BigInteger(matcher1.group(5))));
                }
            } else {
                if (matcher1.group(5) == null) {
                    trimList.add(new Trim(new BigInteger("1"), new BigInteger("1")));
                } else {
                    trimList.add(new Trim(new BigInteger("1"), new BigInteger(matcher1.group(5))));
                }
            }
            
        }
        //然后是省略1且后面跟的是常数因子
        Pattern pattern = Pattern.compile("(([+-])([+-]\\d+))");
        Matcher matcher = pattern.matcher(trim);
        if (matcher.lookingAt()) {
            Pattern pattern5 = Pattern.compile("((([+-])([+-]\\d+)))(.*)");
            Matcher matcher5 = pattern5.matcher(trim);
            if (matcher5.find()) {
                if (matcher5.group(5) != null) {
                    trimExpHead = matcher5.group(5);
                }
            }
            if (matcher.group(2).equals("-")) {
                trimList.add(new Trim(new BigInteger("-1").multiply(new BigInteger(matcher.group(3))), new BigInteger("0")));
                
            } else {
                trimList.add(new Trim(new BigInteger("1").multiply((new BigInteger((matcher.group(3))))), new BigInteger("0")));
            }
            
        }
        Pattern pattern3 = Pattern.compile("(x(\\*{2}[+-]?\\d+){0,1})|([+-]?\\d+)");
        Matcher matcher3 = pattern3.matcher(trimExpHead);
        while (matcher3.find()) {
            String thisTrim = matcher3.group();
            if (thisTrim.matches("(x(\\*{2}[+-]?\\d+){0,1})")) {
                //如果为x，特殊处理
                if (thisTrim.matches("x")) {
                    trimList.add(new Trim(new BigInteger("1"), new BigInteger("1")));
                } else {
                    Pattern pattern4 = Pattern.compile("(x(\\*{2}([+-]?\\d+)))");
                    Matcher matcher4 = pattern4.matcher(thisTrim);
                    if (matcher4.find()) {
                        trimList.add(new Trim(new BigInteger("1"), new BigInteger(matcher4.group(3))));
                    }
                }
            } else {
                trimList.add(new Trim(new BigInteger(matcher3.group()), new BigInteger("0")));
            }
        }
        //项内乘法化简,项前加一个1*1不影响结果
        Multi multiplyer = new Multi(new Trim(new BigInteger("1"), new BigInteger("0")), new Trim(new BigInteger("1"), new BigInteger("0")));
        for (Trim trimToMult : trimList) {
            multiplyer = new Multi(multiplyer.getValue(), trimToMult);
            
        }
        return multiplyer.getValue().derive();
        
    }
}
