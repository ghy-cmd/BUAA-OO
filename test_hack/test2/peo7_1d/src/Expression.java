import java.util.ArrayList;

public class Expression {
    private  ArrayList<Item> items = new ArrayList<>();
    private String expression;
    private String symbol;

    public Expression(String str, String symbol) {
        this.expression = str;
        if (symbol.equals("+")) {
            this.symbol = "";
        }
        else {
            this.symbol = symbol;
        }
        int flag = 0;
        int tag = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                flag++;
            }
            if (str.charAt(i) == ')') {
                flag--;
            }
            if ((str.charAt(i) == '+' || str.charAt(i) == '-') && i != 0) {
                if (flag == 0 && str.charAt(i - 1) != '*' && str.charAt(i - 1) != '^') {
                    String substring = str.substring(tag, i);
                    Item temp = new Item(substring);
                    items.add(temp);
                    tag = i;
                }
            }
        }
        String substring = str.substring(tag);
        Item temp = new Item(substring);
        items.add(temp);
    }

    public Expression() {

    }

    public Tuple getTuple() {
        return null;
    }

    public Tuple getDtuple() {
        return null;
    }

    public void addBrackets() {
        this.expression = symbol + "(" + this.expression + ")";
    }

    public String toString() {
        return expression;
    }

    public String dToString() {
        StringBuffer stringBuffer = new StringBuffer();
        int flag = 0;
        for (int i = 0; i < items.size(); i++) {
            String temp = items.get(i).dToString();
            if (!stringBuffer.toString().equals("") && !temp.equals("")) {
                stringBuffer.append("+" + temp);
                flag = 1;
            }
            else {
                stringBuffer.append(temp);
            }
        }
        String expression = new String();
        if (flag == 1) {
            expression = symbol + "(" + stringBuffer.toString() + ")";
        }
        else {
            expression = symbol + stringBuffer.toString();
        }
        return expression;
    }

}