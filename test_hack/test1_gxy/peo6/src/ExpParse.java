public class ExpParse {
    private String line;

    public ExpParse(String s) { this.line = s; }

    public String preParse() {
        String s1 = line.replaceAll("[ |\t]","");
        String s2 = s1.replaceAll("\\+\\+-|\\+-\\+|-\\+\\+|---","-");
        String s3 = s2.replaceAll("\\+\\+\\+|\\+--|-\\+-|--\\+","+");
        String s4 = s3.replaceAll("-\\+|\\+-","-");
        String s5 = s4.replaceAll("\\+\\+|--","+");
        String str = s5;
        if (s5.charAt(0) == '-') { str = "-1*" + s5.substring(1); }
        if (s5.charAt(0) == '+') { str = s5.substring(1); }
        str = str + '+';
        return str;
    }
}
