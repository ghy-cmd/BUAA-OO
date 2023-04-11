public class Factory {
    public static Expression factor(String str) {
        Expression temp;
        String string = str;
        String symbol = "";
        if (str.charAt(0) == '+' || str.charAt(0) == '-') {
            string = str.substring(1);
            symbol = String.valueOf(str.charAt(0));
        }
        switch (string.charAt(0)) {
            case 's':
                temp = new Sin(string, symbol);
                return temp;
            case 'c':
                temp = new Cos(string, symbol);
                return temp;
            case 'x':
                temp = new Power(string, symbol);
                return temp;
            case '(':
                temp = new Expression(string.substring(1, string.length() - 1), symbol);
                temp.addBrackets();
                return temp;
            default:
                temp = new Constant(string, symbol);
                return temp;
        }
    }
}
