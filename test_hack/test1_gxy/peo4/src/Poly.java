import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

public class Poly {
    private Map<BigInteger,PolyItem> polyItemHashMap = new TreeMap<>();

    public void addPolyItem(PolyItem newPolyItem,String op) {
        if (op.equals("+")) {
            if (this.polyItemHashMap.containsKey(newPolyItem.getIndex())) {
                PolyItem polyItem = this.polyItemHashMap.get(newPolyItem.getIndex());
                polyItem.setCoeff(polyItem.getCoeff().add(newPolyItem.getCoeff()));
                if (polyItem.getCoeff().equals(new BigInteger("0"))) {
                    this.polyItemHashMap.remove(polyItem.getIndex(),polyItem);
                }
            }
            else {
                this.polyItemHashMap.put(newPolyItem.getIndex(),newPolyItem);
            }
        }
        else if (op.equals("-")) {
            if (this.polyItemHashMap.containsKey(newPolyItem.getIndex())) {
                PolyItem polyItem = this.polyItemHashMap.get(newPolyItem.getIndex());
                polyItem.setCoeff(polyItem.getCoeff().subtract(newPolyItem.getCoeff()));
                if (polyItem.getCoeff().equals(new BigInteger("0"))) {
                    this.polyItemHashMap.remove(polyItem.getIndex(),polyItem);
                }
            }
            else {
                BigInteger newCoeff = newPolyItem.getCoeff().negate();
                newPolyItem.setCoeff(newCoeff);
                this.polyItemHashMap.put(newPolyItem.getIndex(),newPolyItem);
            }
        }
        else {
            System.out.println("invalid op");
        }
    }

    public Poly derivation() {
        Poly derPoly = new Poly();
        for (PolyItem polyItem:this.polyItemHashMap.values()) {
            PolyItem derPolyItem = polyItem.derivation();
            if (derPolyItem != null) {
                derPoly.addPolyItem(derPolyItem,"+");
            }
        }
        return derPoly;
    }

    @Override
    public String toString() {
        String str = "";
        for (BigInteger key:this.polyItemHashMap.keySet()) {
            PolyItem term = (PolyItem) this.polyItemHashMap.get(key);
            if (term.getCoeff().compareTo(new BigInteger("0")) > 0) {
                str = str + "+" + term.toString();
            }
            else {
                str = str + term.toString();
            }
        }
        if (str.length() == 0) {
            str = str + "0";
        }
        if (str.charAt(0) == '+') {
            str = str.substring(1);
        }
        return str;
    }
}
