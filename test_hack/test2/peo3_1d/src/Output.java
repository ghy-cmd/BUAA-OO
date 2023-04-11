import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Output {
    public static String outputSimplify(String t) {
        Factory factory = new Factory();
        String comm = "[+-]{2}";
        String legal = t;
        Pattern pattern = Pattern.compile(comm);
        Matcher matcher = pattern.matcher(legal);
        while (matcher.find()) {
            legal = factory.simplify(legal);
            matcher = pattern.matcher(legal);
        }
        legal = legal.replace("*-x", "*-1*x");
        legal = legal.replace("*-sin(x)", "*-1*sin(x)");
        legal = legal.replace("*-cos(x)", "*-1*cos(x)");
        legal = legal.replace("*-(", "*-1*(");
        legal = legal.replace("*+x", "*x");
        legal = legal.replace("*+sin(x)", "*sin(x)");
        legal = legal.replace("*+cos(x)", "*cos(x)");
        legal = legal.replace("*+(", "*(");
        //System.out.println(legal);
        Poly poly = factory.generate(t);
        return Poly.rmOuterPare(poly.toString());
    }
}
