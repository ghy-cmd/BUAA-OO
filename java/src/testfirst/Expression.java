package testfirst;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    private final String style =
        "(x)((\\*\\*)?([+\\-])?(0*)([0-9])+)?";//幂函数因子
    private final Pattern regex = Pattern.compile(style);
    private final HashMap<BigInteger, Term> expToTerm = new HashMap<BigInteger, Term>();

    private final String dintstyle = "(([+\\-])?)(0)*([0-9]+)";//常数因子
    private final Pattern dintregex = Pattern.compile(dintstyle);

    private final String xiangstyle =
        "[+\\-]?[+\\-]?([+\\-]?0*[0-9]*)((\\*?x(\\*\\*[+\\-]?0*[0-9]+)?)|(\\*[+\\-]?(0)*[0-9]+)|(\\*?sin\\(x\\)(\\*\\*[+\\-]?0*[0-9]+)?)|(\\*?cos\\(x\\)(\\*\\*[+\\-]?0*[0-9]+)?)|(\\*?\\(.+\\)))*";
    //项
    private final Pattern xiangregex = Pattern.compile(xiangstyle);

    private final String yinzistyle =
        "cos\\(x\\)(\\*\\*[+\\-]?0*[0-9]+)?|sin\\(x\\)(\\*\\*[+\\-]?0*[0-9]+)?|x(\\*\\*[+\\-]?0*[0-9]+)?|(0)*([0-9])+|[+\\-]|\\(.+\\)";
    private final Pattern yinziregex = Pattern.compile(yinzistyle);

    private final String sinx = "sin\\(x\\)(\\*\\*[+\\-]?0*[0-9]+)?";
    private final Pattern sinxstyle = Pattern.compile(sinx);

    private final String cosx = "cos\\(x\\)(\\*\\*[+\\-]?0*[0-9]+)?";
    private final Pattern cosxstyle = Pattern.compile(cosx);

    private final String express = "\\((.+)\\)";
    private final Pattern expressstyle = Pattern.compile(express);

    public Expression(String str) {
        Matcher matcher = xiangregex.matcher(str);
        while (matcher.find()) {
            Matcher matcher1 = yinziregex.matcher(matcher.group());
            while (matcher1.find()) {
                Matcher matcher2 = dintregex.matcher(matcher1.group());//常数
                Matcher matcher3 = regex.matcher(matcher1.group());//幂函数
                Matcher matcher5 = sinxstyle.matcher(matcher1.group());
                Matcher matcher6 = cosxstyle.matcher(matcher1.group());
                Matcher matcher7 = expressstyle.matcher(matcher1.group());
                if (matcher7.find()) {
                    Matcher matcher8 = expressstyle.matcher(matcher7.group(1));
                    while (matcher8.find()) {
                        /*Matcher matcher8 = expressstyle.matcher(matcher8.group(1));*/
                    }
                }
            }
        }
    }
}
