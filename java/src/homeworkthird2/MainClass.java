package homeworkthird2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    private static int error = 0;

    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        StringBuilder stringBuilder = new StringBuilder(string);
        Object yjh = MainClass.Derivative(stringBuilder);
        if (error == 1 || yjh.getNum() == 0) {
            System.out.println("WRONG FORMAT!");
        } else {
            String b = "";
            for (int i = 0; i < yjh.getOrigin().size(); i++) {
                if (i == 0) {
                    b += yjh.getDerivative().get(i);
                } else {
                    b += "+" + yjh.getDerivative().get(i);
                }
            }
            System.out.println(b);
        }
    }

    public static Object Derivative(StringBuilder stringBuilder) {
        Object result = new Object();
        ArrayList<String> items = new ArrayList<>();
        ArrayList<String> itemDerivatives = new ArrayList<>();
        int peace = 1;
        int num = 0;
        ArrayList<String> item = new ArrayList<>();
        ArrayList<String> itemDerivative = new ArrayList<>();
        String powerfuction = "^x(\\s|\\t)*(\\*\\*(\\s|\\t)*[+\\-]?0*(50|[1-4]?[0-9]))?";
        Pattern patternPowerfuction = Pattern.compile(powerfuction);
        String constant = "^([+\\-])?0*([0-9]+)";
        Pattern patternConstant = Pattern.compile(constant);
        String exp = "([+\\-])?0*([0-9]+)";
        Pattern patternExp = Pattern.compile(exp);
        String sinx = "^sin(\\s|\\t)*\\(";
        Pattern patternsinx = Pattern.compile(sinx);
        String cosx = "^cos(\\s|\\t)*\\(";
        Pattern patterncosx = Pattern.compile(cosx);
        String expplus = "^(\\s|\\t)*\\*\\*(\\s|\\t)*([+\\-])?(0*)(50|[1-4]?[0-9])";
        Pattern patternexpplus = Pattern.compile(expplus);
        String zf = "^[+\\-](\\s|\\t)*[+\\-]?(\\s|\\t)*";
        Pattern patternzf = Pattern.compile(zf);
        while (true) {
            Matcher powerfuctionmatcher = patternPowerfuction.matcher(stringBuilder);
            Matcher matcherConstant = patternConstant.matcher(stringBuilder);
            Matcher matchersinx = patternsinx.matcher(stringBuilder);
            Matcher matchercosx = patterncosx.matcher(stringBuilder);
            Matcher matcherzf = patternzf.matcher(stringBuilder);
            if (error == 1) {
                break;
            } else if (stringBuilder.length() == 0) {
                break;
            } else if (powerfuctionmatcher.find()) {
                if (peace == 0) {
                    error = 1;
                    break;
                }
                Matcher matcherExp = patternExp.matcher(powerfuctionmatcher.group());
                String b;
                if (matcherExp.find()) {
                    BigInteger a = new BigInteger(matcherExp.group());
                    b = a.toString() + "*x**" + a.add(BigInteger.valueOf(-1)).toString();
                } else {
                    b = "1";
                }
                item.add(powerfuctionmatcher.group());
                itemDerivative.add(b);
                stringBuilder.delete(0, powerfuctionmatcher.group().length());
                peace = 0;
                num++;
            } else if (matcherConstant.find()) {
                if (peace == 0 &&
                    (stringBuilder.charAt(0) == '+' || stringBuilder.charAt(0) == '-')) {
                    String a = "";
                    String b = "";
                    if (item.size() == 1) {
                        a += item.get(0);
                        b += itemDerivative.get(0);
                    } else {
                        for (int i = 0; i < item.size(); i++) {
                            if (i == 0) {
                                a += item.get(i);
                                b += itemDerivative.get(i);
                            } else {
                                a += "*(" + item.get(i) + ")";
                                b += "+(" + itemDerivative.get(i) + ")";
                            }
                            for (int j = 0; j < item.size(); j++) {
                                if (i != j) {
                                    b += "*(" + item.get(j) + ")";
                                }
                            }
                        }
                    }
                    items.add(a);
                    itemDerivatives.add(b);
                    item.clear();
                    itemDerivative.clear();
                } else if (peace == 0) {
                    error = 1;
                    break;
                }
                item.add(matcherConstant.group());
                itemDerivative.add("0");
                stringBuilder.delete(0, matcherConstant.group().length());
                peace = 0;
                num++;
            } else if (stringBuilder.charAt(0) == '*') {
                if (peace == 1) {
                    error = 1;
                    break;
                } else {
                    peace = 1;
                }
                stringBuilder.delete(0, 1);
            } else if (matchersinx.find()) {
                if (peace == 0) {
                    error = 1;
                    break;
                }
                String sinxitem = matchersinx.group();
                stringBuilder.delete(0, matchersinx.group().length());
                Object object = Derivative(stringBuilder);
                String a = "";
                String b = "";
                for (int i = 0; i < object.getOrigin().size(); i++) {
                    if (i == 0) {
                        a += object.getOrigin().get(i);
                        b += object.getDerivative().get(i);
                    } else {
                        a += "+" + object.getOrigin().get(i);
                        b += "+" + object.getDerivative().get(i);
                    }
                }
                sinxitem += a + ")";
                if (error == 1 || object.getNum() != 1) {
                    error = 1;
                    break;
                }
                String sinxd = "(" + b + ")*cos(" + a + ")";
                Matcher matcherexpplus = patternexpplus.matcher(stringBuilder);
                if (matcherexpplus.find()) {
                    Matcher matcherexp = patternExp.matcher(matcherexpplus.group());
                    matcherexp.find();
                    BigInteger c = new BigInteger(matcherexp.group());
                    item.add(sinxitem + matcherexpplus.group());
                    itemDerivative
                        .add(c.toString() + "*" + sinxitem + "**" +
                            c.add(BigInteger.valueOf(-1)).toString() + "*" + sinxd);
                    stringBuilder.delete(0, matcherexpplus.group().length());
                } else {
                    item.add(sinxitem);
                    itemDerivative.add(sinxd);
                }
                peace = 0;
                num++;
            } else if (matchercosx.find()) {
                if (peace == 0) {
                    error = 1;
                    break;
                }
                String cosxitem = matchercosx.group();
                stringBuilder.delete(0, matchercosx.group().length());
                Object object = Derivative(stringBuilder);
                String a = "";
                String b = "";
                for (int i = 0; i < object.getOrigin().size(); i++) {
                    if (i == 0) {
                        a += object.getOrigin().get(i);
                        b += object.getDerivative().get(i);
                    } else {
                        a += "+" + object.getOrigin().get(i);
                        b += "+" + object.getDerivative().get(i);
                    }
                }
                cosxitem += a + ")";
                if (error == 1 || object.getNum() != 1) {
                    error = 1;
                    break;
                }
                String cosxd = "(" + b + ")*-1*sin(" + a + ")";
                Matcher matcherexpplus = patternexpplus.matcher(stringBuilder);
                if (matcherexpplus.find()) {
                    Matcher matcherexp = patternExp.matcher(matcherexpplus.group());
                    matcherexp.find();
                    BigInteger c = new BigInteger(matcherexp.group());
                    item.add(cosxitem + matcherexpplus.group());
                    itemDerivative
                        .add(c.toString() + "*" + cosxitem + "**" +
                            c.add(BigInteger.valueOf(-1)).toString() + "*" + cosxd);
                    stringBuilder.delete(0, matcherexpplus.group().length());
                } else {
                    item.add(cosxitem);
                    itemDerivative.add(cosxd);
                }
                peace = 0;
                num++;
            } else if (stringBuilder.charAt(0) == '(') {
                if (peace == 0) {
                    error = 1;
                    break;
                }
                stringBuilder.delete(0, 1);
                Object object = Derivative(stringBuilder);
                String a = "";
                String b = "";
                for (int i = 0; i < object.getOrigin().size(); i++) {
                    if (i == 0) {
                        a += object.getOrigin().get(i);
                        b += object.getDerivative().get(i);
                    } else {
                        a += "+" + object.getOrigin().get(i);
                        b += "+" + object.getDerivative().get(i);
                    }
                }
                item.add(a);
                itemDerivative.add(b);
                peace = 0;
                num++;
            } else if (stringBuilder.charAt(0) == ')') {
                if (peace == 1 | num == 0) {
                    error = 1;
                    break;
                }
                String a = "";
                String b = "";
                if (item.size() == 1) {
                    a += item.get(0);
                    b += itemDerivative.get(0);
                } else {
                    for (int i = 0; i < item.size(); i++) {
                        if (i == 0) {
                            a += item.get(i);
                            b += itemDerivative.get(i);
                        } else {
                            a += "*(" + item.get(i) + ")";
                            b += "+(" + itemDerivative.get(i) + ")";
                        }
                        for (int j = 0; j < item.size(); j++) {
                            if (i != j) {
                                b += "*(" + item.get(j) + ")";
                            }
                        }
                    }
                }
                items.add(a);
                itemDerivatives.add(b);
                result.setNum(num);
                result.setOrigin(items);
                result.setDerivative(itemDerivatives);
                stringBuilder.delete(0, 1);
                return result;
            } else if (stringBuilder.charAt(0) == ' ') {
                stringBuilder.delete(0, 1);
            } else if (matcherzf.find()) {
                if (peace == 1 && (items.size() != 0 || item.size() != 0)) {
                    error = 1;
                    break;
                }
                if (peace == 0) {
                    String a = "";
                    String b = "";
                    if (item.size() == 1) {
                        a += item.get(0);
                        b += itemDerivative.get(0);
                    } else {
                        for (int i = 0; i < item.size(); i++) {
                            if (i == 0) {
                                a += item.get(i);
                                b += itemDerivative.get(i);
                            } else {
                                a += "*(" + item.get(i) + ")";
                                b += "+(" + itemDerivative.get(i) + ")";
                            }
                            for (int j = 0; j < item.size(); j++) {
                                if (i != j) {
                                    b += "*(" + item.get(j) + ")";
                                }
                            }
                        }
                    }

                    items.add(a);
                    itemDerivatives.add(b);
                    item.clear();
                    itemDerivative.clear();
                }
                BigInteger c = BigInteger.ONE;
                for (int i = 0; i < matcherzf.group().length(); i++) {
                    if (matcherzf.group().charAt(i) == '-') {
                        c = c.multiply(BigInteger.valueOf(-1));
                    }
                }
                item.add(c.toString());
                itemDerivative.add("0");
                stringBuilder.delete(0, matcherzf.group().length());
                peace = 1;
                num++;
            } else {
                error = 1;
                break;
            }
        }
        if (peace == 1 | num == 0) {
            error = 1;
        }
        String a = "";
        String b = "";
        if (item.size() == 1) {
            a += item.get(0);
            b += itemDerivative.get(0);
        } else {
            for (int i = 0; i < item.size(); i++) {

                if (i == 0) {
                    a += item.get(i);
                    b += itemDerivative.get(i);
                } else {
                    a += "*(" + item.get(i) + ")";
                    b += "+(" + itemDerivative.get(i) + ")";
                }
                for (int j = 0; j < item.size(); j++) {
                    if (i != j) {
                        b += "*(" + item.get(j) + ")";
                    }
                }
            }
        }

        items.add(a);
        itemDerivatives.add(b);
        result.setNum(num);
        result.setOrigin(items);
        result.setDerivative(itemDerivatives);
        return result;
    }
}
