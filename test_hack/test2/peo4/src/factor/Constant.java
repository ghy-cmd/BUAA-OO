package factor;

import java.math.BigInteger;
import java.util.ArrayList;

public class Constant extends Factor implements Derivable {
    private BigInteger value;
    
    public Constant(BigInteger v) {
        this.value = v;
    }
    
    public ArrayList derive() {
        ArrayList ret = new ArrayList();
        ret.add(new Constant(BigInteger.ZERO));
        return ret;
    }
    
    public BigInteger getValue() {
        return value;
    }
    
    public Constant multiply(Constant c) {
        BigInteger conValue = c.getValue();
        return new Constant(this.value.multiply(conValue));
    }
    
    public String toString() {
        return value.toString();
    }
}
