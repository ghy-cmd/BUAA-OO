import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String str = in.nextLine();
            String str0 = "+" + str;
            String str1 = str0.replaceAll("\\s", "");        //去除空白字符
            String str2 = str1.replaceAll("\\+\\+", "+");
            String str3 = str2.replaceAll("\\+\\-", "-");
            String str4 = str3.replaceAll("\\-\\+", "-");
            String str5 = str4.replaceAll("\\-\\-", "+");
            
            String str6 = str5.replaceAll("\\+\\+", "+");
            String str7 = str6.replaceAll("\\+\\-", "-");
            String str8 = str7.replaceAll("\\-\\+", "-");
            String str9 = str8.replaceAll("\\-\\-", "+");   //将至多四个符号化简成一个符号
            
            String str9A = str9.replaceAll("\\+\\+", "+");
            String str9B = str9A.replaceAll("\\+\\-", "-");
            String str9C = str9B.replaceAll("\\-\\+", "-");
            String str9D = str9C.replaceAll("\\-\\-", "+");
            
            String str10 = str9D.replaceAll("\\-", "+-");
            String str11 = str10.replaceAll("\\*\\+", "*"); //使仅仅项之间的-变为+-
            String str11A = str11.replaceAll("\\(\\+", "(");
            
            String str13 = str11A.replaceAll("\\-x", "-1*x"); //使x变为1*x
            String str13A = str13.replaceAll("\\-\\(", "-1*(");
            String str14 = str13A.replaceAll("x", "x@");
            String str15 = str14.replaceAll("@\\+", "+");//加上次方
            String str16 = str15.replaceAll("@\\-", "-");
            String str17 = str16.replaceAll("\\*\\*", "^");
            String str18 = str17.replaceAll("@\\*", "*");
            String str19 = str18.replaceAll("@", "");        //把**变为^,x变为x^1
            
            String str20 = str19.replaceAll("sin\\(x\\)", "y");
            String str21 = str20.replaceAll("cos\\(x\\)", "z");
            String str22 = str21.replaceAll("\\-y", "-1*y");
            String str23 = str22.replaceAll("\\-z", "-1*z");
            
            Tree nodetree = new Tree();
            nodetree.setRoot(nodetree.createNode(str23));
            nodetree.setRoot(nodetree.derivative(nodetree.getRoot()));
            String strlast = nodetree.getRoot().getDer();
            strlast = strlast.replaceAll("y", "sin(x)").replaceAll("z", "cos(x)");
            strlast = strlast.replaceAll("\\^", "**");
            System.out.println(strlast);
        }
    }
}
