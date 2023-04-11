package testfirst.factors;

import java.util.regex.Pattern;

public class Constant {
    private final String dintstyle = "(([+\\-])?)(0)*([0-9]+)";//常数因子
    private final Pattern dintregex = Pattern.compile(dintstyle);
    private String a;
}
