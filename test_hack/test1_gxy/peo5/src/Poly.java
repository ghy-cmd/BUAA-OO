import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Poly {
    private final List<Trim> trims;
    
    //打印一个poly
    public Poly(List<Trim> trims) {
        this.trims = trims;
    }
    
    public Poly() {
        trims = new ArrayList<>();
    }
    
    public List<Trim> getTrims() {
        return trims;
    }
    
    public String printPoly() {
        String result = "";
        int i;
        for (i = 0; i < trims.size(); i++) {
            if (!trims.get(i).getCeo().equals(new BigInteger("0"))) {
                break;
            }
        }
        //如果有不为0的，调换位置，把一个+的调到第一个，可以省去一个+
        if (i >= 0 && i < trims.size()) {
            Trim trimFirst = trims.get(i);
            if (trimFirst.getCeo().compareTo(new BigInteger("0")) < 0) {
                //遍历后面的，找一个正的和i换位置
                int j;
                for (j = i + 1; j < trims.size(); j++) {
                    Trim trimToSwap = trims.get(j);
                    if (trimToSwap.getCeo().compareTo(new BigInteger("0")) > 0) {
                        Collections.swap(trims, i, j);
                        break;
                    }
                }
            }
            for (Trim trim : trims) {
                //对于每一个项，打印的最好方法是按照ceo打印，如果>0,直接添一个加号，然后打印，如果==0，不打印，跳到下一个，如果<0，直接打印
                //如果是第一个非零项且>0，加号直接省了
                if (trim.getCeo().compareTo(new BigInteger("0")) > 0) {
                    if (trims.indexOf(trim) == i) {
                        result = result + trim.printTrim();
                    } else {
                        result = result + "+" + trim.printTrim();
                    }
                } else if (trim.getCeo().compareTo(new BigInteger("0")) < 0) {
                    result = result + trim.printTrim();
                }
            }
            return result;
        }
        //全为0,直接不用输出
        else {
            return "0";
        }
    }
}
