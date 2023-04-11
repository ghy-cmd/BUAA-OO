import java.util.ArrayList;

public class PolyCompute {
    private ArrayList<String> input = new ArrayList<>();
    private Poly poly = new Poly();

    public PolyCompute(ArrayList<String> in) {
        this.input = in;
    }

    public void compute() {
        for (String i : input) {
            poly.addTerm(new Term(i));
        }
        poly.printPoly();
    }
}
