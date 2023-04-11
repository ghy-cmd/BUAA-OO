import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = readLine(scanner);
        String newLine = normalize(line);
        // System.out.println(newLine);
        ArrayList<String> items = catchItem(newLine);
        ArrayList<FactorMul> allfactors = getFactor(items);
        for (int i = 0; i < allfactors.size(); i++) {
            if (i != 0 && allfactors.get(i).getOp() > 0) {
                System.out.print("+");
            }
            FactorMul p = allfactors.get(i).der();
            //System.out.println("result");
            p.print();
        }
        //System.out.println("size is "+ allfactors.size());
    }
    
    public static String readLine(Scanner s) {
        return s.nextLine();
    }
    
    public static String normalize(String l) {
        String l1 = l.replaceAll("[\t ]", "");
        l1 = l1.replace("sin(x)", "s");
        l1 = l1.replace("cos(x)", "c");
        l1 = l1.replace("*+", "*");
        l1 = l1.replace("**", "^");
        for (int i = 0; i <= 1; i++) {
            l1 = l1.replaceAll("(\\+\\+)|(--)", "+");
            l1 = l1.replaceAll("(\\+-)|(-\\+)", "-");
        }
        return l1;
    }
    
    public static ArrayList<String> catchItem(String l) {
        int match = 0;
        StringBuilder s = new StringBuilder(l);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                match++;
            } else if (s.charAt(i) == ')') {
                match--;
            }
            if (i >= 1) {
                if (match == 0 && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
                    if (s.charAt(i) == '+') {
                        s.replace(i, i + 1, " ");
                    } else {
                        boolean k = (s.charAt(i) == '-');
                        if (k && s.charAt(i - 1) != '^' && s.charAt(i - 1) != '*') {
                            s.replace(i, i + 1, "&");
                        }
                    }
                }
            }
        }
        String s15 = s.toString();
        s15 = s15.replace("&", " -");
        String[] s1 = s15.split(" ");
        //System.out.println(s1[i]);
        return new ArrayList<>(Arrays.asList(s1));
    }
    
    public static ArrayList<FactorMul> getFactor(ArrayList<String> s) {
        s.removeIf(String::isEmpty);
        // System.out.println("getFactor: "+s);
        ArrayList<FactorMul> factorMuls = new ArrayList<>();
        for (String i : s) {
            int match = 0;
            StringBuilder k = new StringBuilder(i);
            for (int j = 0; j < i.length(); j++) {
                if (i.charAt(j) == '(') {
                    match++;
                } else if (i.charAt(j) == ')') {
                    match--;
                }
                if (match == 0 && (i.charAt(j) == '*')) {
                    k.replace(j, j + 1, " ");
                }
            }
            String[] ksplit = k.toString().split(" ");
            //System.out.println(ksplit);
            FactorMul l;
            l = new FactorMul(1);
            l.line(ksplit);
            factorMuls.add(l);
        }
        return factorMuls;
    }
}
