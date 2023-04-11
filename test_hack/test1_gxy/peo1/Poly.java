import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
//import java.util.Scanner;

public class Poly {
    private HashMap<BigInteger, BigInteger> terms;

    public Poly() {
        terms = new HashMap<>();
    }

    public void addTerm(Term term) {
        if (this.terms.containsKey(term.getPoint().getIndex())) {
            BigInteger temp = terms.get(
                    term.getPoint().getIndex()).add(term.getPoint().getCoefficient()
            );
            this.terms.put(term.getPoint().getIndex(), temp);
        } else {
            this.terms.put(term.getPoint().getIndex(), term.getPoint().getCoefficient());
        }
    }

    public void printPoly() {
        //删除系数为0的项。
        this.terms.entrySet().removeIf(entry -> entry.getValue().equals(new BigInteger("0")));
        StringBuilder out = new StringBuilder();
        for (Map.Entry<BigInteger, BigInteger> entry : terms.entrySet()) {
            String string = new Point(entry.getValue(), entry.getKey()).toString();
            if (string.charAt(0) == '+') {
                string = string.substring(1);
                out.append(string);
                terms.remove(entry.getKey());
                break;
            }
        }
        for (Map.Entry<BigInteger, BigInteger> entry : terms.entrySet()) {
            String string = new Point(entry.getValue(), entry.getKey()).toString();
            out.append(string);
        }
        if (out.toString().isEmpty()) {
            System.out.println("0");
        } else {
            System.out.println(out);
        }
    }

    /*public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Poly poly = new Poly();
        while (scanner.hasNext()) {
            Term term = new Term(scanner.nextLine());
            poly.addTerm(term);
            poly.printPoly();
        }
    }*/

}

