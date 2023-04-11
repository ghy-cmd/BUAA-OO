package task3;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class booklist {
    private Scanner scanner = new Scanner(System.in);
    ArrayList<Bookset> booksArrayList = new ArrayList<>(16);

    public double getExprice() {
        double max = 0;
        for (Bookset item : booksArrayList) {
            double temp = item.getPrice();
            if (temp > max)
                max = temp;
        }
        return max;
    }

    public BigDecimal getTotalprice() {
        BigDecimal total = BigDecimal.valueOf(0);
        for (Bookset item : booksArrayList) {
            BigDecimal num = BigDecimal.valueOf(item.getNum());
            BigDecimal price = BigDecimal.valueOf(item.getPrice());
            BigDecimal perprice = num.multiply(price);
            total = total.add(perprice);
        }
        return total;
    }

    public BigInteger getTotalnum() {
        BigInteger total = BigInteger.ZERO;
        for (Bookset item : booksArrayList) {
            BigInteger num = BigInteger.valueOf(item.getNum());
            total = total.add(num);
        }
        return total;
    }

    public int getTypenum() {
        int total = 0;
        int a[] = new int[7];
        int i;
        for (i = 0; i <= 6; i++) {
            a[i] = 0;
        }
        for (Bookset item : booksArrayList) {
            if (item.getTypeName().equals("Other")) {
                a[0] = 1;
            } else if (item.getTypeName().equals("OtherA")) {
                a[1] = 1;
            } else if (item.getTypeName().equals("Novel")) {
                a[2] = 1;
            } else if (item.getTypeName().equals("Poetry")) {
                a[3] = 1;
            } else if (item.getTypeName().equals("OtherS")) {
                a[4] = 1;
            } else if (item.getTypeName().equals("Math")) {
                a[5] = 1;
            } else if (item.getTypeName().equals("Computer")) {
                a[6] = 1;
            }
        }
        for (i = 0; i <= 6; i++) {
            if (a[i] != 0) {
                total++;
            }
        }
        return total;
    }
}
