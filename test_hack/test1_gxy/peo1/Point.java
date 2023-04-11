import java.math.BigInteger;
//import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Point {
    private BigInteger coefficient;
    private BigInteger index;

    public Point(BigInteger coefficient, BigInteger index) {
        this.coefficient = coefficient;
        this.index = index;
    }

    public Point(String input) {
        if (input.equals("x")) {
            this.coefficient = new BigInteger("1");
            this.index = new BigInteger("1");
        } else {
            Pattern p1 = Pattern.compile("x\\*\\*(?<index>[+-]?[\\d]+)");
            Pattern p2 = Pattern.compile("[+-]?[\\d]+");
            Matcher m1 = p1.matcher(input);
            Matcher m2 = p2.matcher(input);
            if (m1.matches()) {
                this.coefficient = new BigInteger("1");
                this.index = new BigInteger(m1.group("index"));
            } else if (m2.matches()) {
                this.coefficient = new BigInteger(m2.group(0));
                this.index = new BigInteger("0");
            }
        }
    }

    public BigInteger getCoefficient() {
        return coefficient;
    }

    public BigInteger getIndex() {
        return index;
    }

    public void mulPoint(Point another) {
        this.coefficient = this.coefficient.multiply(another.coefficient);
        this.index = this.index.add(another.index);
    }

    public void diff() {
        if (this.index.equals(BigInteger.valueOf(0))) {
            this.coefficient = BigInteger.valueOf(0);
        } else {
            this.coefficient = this.coefficient.multiply(this.index);
            this.index = this.index.subtract(BigInteger.valueOf(1));
        }
    }

    public String toString() {
        String pattern = (coefficient.compareTo(BigInteger.valueOf(0)) < 0) ? "" : "+";
        if (coefficient.equals(BigInteger.valueOf(0))) {
            return "";
        } else if (index.equals(BigInteger.valueOf(0))) {
            return pattern + coefficient;
        } else if (coefficient.equals(BigInteger.valueOf(1))
                && index.equals(BigInteger.valueOf(1))) {
            return "+x";
        } else if (coefficient.equals(BigInteger.valueOf(1))
                && !index.equals(BigInteger.valueOf(1))) {
            return "+x**" + index;
        } else if (coefficient.equals(BigInteger.valueOf(-1))
                && index.equals(BigInteger.valueOf(1))) {
            return "-x";
        } else if (coefficient.equals(BigInteger.valueOf(-1))
                && !index.equals(BigInteger.valueOf(1))) {
            return "-x**" + index;
        } else if (index.equals(BigInteger.valueOf(1))) {
            return pattern + coefficient + "*x";
        } else if (index.equals(BigInteger.valueOf(2))) {
            return pattern + coefficient + "*x*x";
        } else {
            return pattern + coefficient + "*x**" + index;
        }
    }

    /*public static void main(String[] args) {//已测试
        Scanner scanner = new Scanner(System.in);
        Point point1 = new Point("1");
        while(scanner.hasNext()) {
            String input = scanner.nextLine();
            Point point = new Point(input);
            System.out.println(point.getCoefficient() +" "+ point.getIndex());
            point1.MulPoint(point);
            System.out.println(point1.getCoefficient() +" "+ point1.getIndex());
        }
    }*/
}
