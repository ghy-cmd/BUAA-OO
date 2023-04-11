import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public static void main(String[] args) {
        ArrayList<Poly> allPoly = new ArrayList<>();
        ArrayList<Operator> allOperator = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        str = basicHandle(str);
        
        Pattern polyPattern = Pattern.compile("[0-9x]+");
        Pattern operatorPattern = Pattern.compile("[-+*^]+");
        Matcher polyMatcher = polyPattern.matcher(str);
        Matcher operatorMatcher = operatorPattern.matcher(str);
        allPoly.add(new Poly(new BigInteger("0"), new BigInteger("0")));
        while (polyMatcher.find() && operatorMatcher.find()) {
            String tempStr = polyMatcher.group();
            if (tempStr.equals("x")) {
                allPoly.add(new Poly(new BigInteger("1"), new BigInteger("1")));
            } else {
                allPoly.add(new Poly(new BigInteger(tempStr), new BigInteger("0")));
            }
            allOperator.add(new Operator(operatorMatcher.group()));
        }
        for (int i = allOperator.size() - 1; i >= 0; i--) {
            if (allOperator.get(i).getOpStr().charAt(0) == '^') {
                allPoly.set(i, allPoly.get(i).deal(
                        allPoly.get(i + 1), allOperator.get(i)));
                allPoly.remove(i + 1);
                allOperator.remove(i);
            }
        }
        for (int i = allOperator.size() - 1; i >= 0; i--) {
            if (allOperator.get(i).getOpStr().charAt(0) == '*') {
                allPoly.set(i, allPoly.get(i).deal(
                        allPoly.get(i + 1), allOperator.get(i)));
                allPoly.remove(i + 1);
                allOperator.remove(i);
            }
        }
        for (int i = 1; i < allPoly.size(); i++) {
            if (allOperator.get(i - 1).getOpStr().equals("-")) {
                allPoly.set(i, allPoly.get(i).neg());
                allOperator.set(i - 1, new Operator("+"));
            }
        }
        for (int i = 0; i < allPoly.size(); i++) {
            for (int j = allPoly.size() - 1; j > i; j--) {
                if (allPoly.get(i).getCishu().equals(allPoly.get(j).getCishu())) {
                    allPoly.set(i, allPoly.get(i).deal(
                            allPoly.get(j), allOperator.get(j - 1)));
                    allPoly.remove(j);
                    allOperator.remove(j - 1);
                }
            }
        }
        derivation(allPoly);
        ansPrint(allPoly, allOperator);
    }
    
    public static void derivation(ArrayList<Poly> allPoly) {
        for (int i = 0; i < allPoly.size(); i++) {
            allPoly.set(i, allPoly.get(i).qiudao());
        }
    }
    
    public static void ansPrint(ArrayList<Poly> allPoly,
                                ArrayList<Operator> allOperator) {
        String ansString = "";
        for (int i = 1; i < allPoly.size(); i++) {
            for (int j = i + 1; j < allPoly.size(); j++) {
                if (allPoly.get(i).getXishu().compareTo(allPoly.get(j).getXishu()) < 0) {
                    Poly poly = allPoly.get(i);
                    allPoly.set(i, allPoly.get(j));
                    allPoly.set(j, poly);
                    Operator operator = allOperator.get(i - 1);
                    allOperator.set(i - 1, allOperator.get(j - 1));
                    allOperator.set(j - 1, operator);
                }
            }
        }
        int judge = 0;
        for (int i = 0; i < allPoly.size(); i++) {
            
            
            if (!allPoly.get(i).getXishu().equals(new BigInteger("0"))) {
                
                if (judge == 1 &&
                        allPoly.get(i).getXishu().compareTo(new BigInteger("0")) > 0) {
                    ansString = ansString + allOperator.get(i - 1).getOpStr();
                }
                judge = 1;
                if (allPoly.get(i).getXishu().equals(new BigInteger("-1"))
                        && !allPoly.get(i).getCishu().equals(new BigInteger("0"))) {
                    ansString = ansString + "-";
                } else if (!allPoly.get(i).getXishu().equals(new BigInteger("1"))
                        || (allPoly.get(i).getXishu().equals(new BigInteger("1")) &&
                        allPoly.get(i).getCishu().equals(new BigInteger("0")))) {
                    ansString = ansString + allPoly.get(i).getXishu().toString();
                    if (!allPoly.get(i).getCishu().equals(new BigInteger("0"))) {
                        ansString = ansString + "*";
                    }
                }
                
                if (!allPoly.get(i).getCishu().equals(new BigInteger("0"))) {
                    ansString = ansString + "x";
                    if (!allPoly.get(i).getCishu().equals(new BigInteger("1"))) {
                        ansString = ansString + "**" + allPoly.get(i).getCishu().toString();
                        
                    }
                }
            }
            
        }
        ansString = ansString.replace("++", "+");
        ansString = ansString.replace("+-", "-");
        ansString = ansString.replace("-+", "-");
        ansString = ansString.replace("--", "+");
        
        if (judge == 0) {
            System.out.print("0");
        } else {
            if (ansString.charAt(0) == '+') {
                ansString = ansString.substring(1);
            }
            System.out.print(ansString);
        }
    }
    
    public static String basicHandle(String str) {
        String tempStr = str;
        tempStr = tempStr.replaceAll("[ \t]", "");
        tempStr = tempStr.replace("**", "^");
        tempStr = tempStr.replace("++", "+");
        tempStr = tempStr.replace("+-", "-");
        tempStr = tempStr.replace("-+", "-");
        tempStr = tempStr.replace("--", "+");
        
        tempStr = "+" + tempStr;
        tempStr = tempStr.replace("++", "+");
        tempStr = tempStr.replace("+-", "-");
        tempStr = tempStr.replace("-+", "-");
        tempStr = tempStr.replace("--", "+");
        return tempStr;
    }
}
