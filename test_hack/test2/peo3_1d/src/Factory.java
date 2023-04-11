public class Factory {
    public static String simplify(String s1) {
        String s2 = s1.replace("+-", "-");
        String s3 = s2.replace("-+", "-");
        String s4 = s3.replace("++", "+");
        String s5 = s4.replace("--", "+");
        return s5;
    }

    public Factory() {

    }

    public static String replace(String t) {
        String s1 = t.replace(" ", "");
        s1 = s1.replace("\t", "");
        s1 = s1.replace("sin(x)", "S");
        s1 = s1.replace("cos(x)", "C");
        s1 = s1.replace("**", "^");
        s1 = s1.replace("-x", "-1*x");
        s1 = s1.replace("-S", "-1*S");
        s1 = s1.replace("-C", "-1*C");
        s1 = s1.replace("-(", "-1*(");
        s1 = s1.replace("+(", "+1*(");
        return s1;
    }

    public Poly generate(String line) {
        String s1 = replace(line);
        String s2 = simplify(s1);
        return new Poly(simplify(s2));
    }
}
