import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolyFactory {
    private static Pattern patternPolyFactor = Pattern.compile(
            "((\\s*x\\s*(\\*\\*\\s*([+-]?\\d+))?\\s*)|(\\s*([+-]?\\d+)\\s*))");
    //"((((\\s*([+-]?\\d+)\\s*\\*)|\\s*\\+|\\s*\\-)?\\s*" +
    //                    "x\\s*(\\*\\*\\s*([+-]?\\d+))?\\s*)|(\\s*([+-]?\\d+)\\s*))"
    private static Pattern patternOpAndFactor = Pattern.compile(
            "(\\s*[+-]\\s*)" + patternPolyFactor);
    private static Pattern patternMultFactor = Pattern.compile(
            "(\\s*\\*\\s*)" + patternPolyFactor);
    private static Pattern patternPolyItem = Pattern.compile(
            "(\\s*[+-]\\s*)??" + patternOpAndFactor + "(" + patternMultFactor + ")*");

    public static Poly setPolyClass(String string) {
        Poly poly = new Poly();
        String str = string.trim();
        if (!(str.charAt(0) == '+' || str.charAt(0) == '-')) { str = "+" + str; }
        Matcher matcherOpAndFactor = patternOpAndFactor.matcher(str);
        Matcher matcherMultFactor = patternMultFactor.matcher(str);
        Matcher matcherPolyItem = patternPolyItem.matcher(str);
        while (matcherPolyItem.find()) {
            matcherOpAndFactor.find(matcherPolyItem.start());
            String op;
            if (matcherPolyItem.group(1) != null) {
                if ((matcherPolyItem.group(2).trim()).equals(matcherPolyItem.group(1).trim())) {
                    op = "+";
                }
                else { op = "-"; }
            }
            else { op = matcherOpAndFactor.group(1).trim(); }
            BigInteger coeff;
            BigInteger index;
            if (matcherOpAndFactor.group(3) != null) {
                coeff = new BigInteger("1");
                if (matcherOpAndFactor.group(4) != null) {
                    index = new BigInteger(matcherOpAndFactor.group(5));
                }
                else { index = new BigInteger("1"); }
            }
            else {
                coeff = new BigInteger(matcherOpAndFactor.group(7));
                index = new BigInteger("0");
            }
            PolyItem polyItem = new PolyItem(coeff,index);
            if (matcherOpAndFactor.end() != matcherPolyItem.end()) {
                int end = matcherOpAndFactor.end();
                while (matcherMultFactor.find(end)) {
                    if (matcherMultFactor.group(3) != null) {
                        coeff = new BigInteger("1");
                        if (matcherMultFactor.group(4) != null) {
                            index = new BigInteger(matcherMultFactor.group(5));
                        }
                        else {
                            index = new BigInteger("1");
                        }
                    }
                    else {
                        coeff = new BigInteger(matcherMultFactor.group(7));
                        index = new BigInteger("0");
                    }
                    coeff = coeff.multiply(polyItem.getCoeff());
                    index = index.add(polyItem.getIndex());
                    polyItem.setCoeff(coeff);
                    polyItem.setIndex(index);
                    if (matcherMultFactor.end() == matcherPolyItem.end()) { break; }
                    end = matcherMultFactor.end();
                }
            }
            poly.addPolyItem(polyItem,op);
        }
        return poly;
    }
}
