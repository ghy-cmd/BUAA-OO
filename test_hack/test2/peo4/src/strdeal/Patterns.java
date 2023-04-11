package strdeal;

import java.util.regex.Pattern;

public class Patterns {
    private static String Constant = "(^[+-]?[0-9]+)";
    private static String PowFunc = "(^x(\\*\\*[+-]?[0-9]+)?)";
    private static String Sin = "(^sin\\(x\\)(\\*\\*[+-]?[0-9]+)?)";
    private static String Cos = "(^cos\\(x\\)(\\*\\*[+-]?[0-9]+)?)";
    public static final Pattern CONSTANT = Pattern.compile(Constant);
    public static final Pattern POWFUNC = Pattern.compile(PowFunc);
    public static final Pattern SIN = Pattern.compile(Sin);
    public static final Pattern COS = Pattern.compile(Cos);
}
