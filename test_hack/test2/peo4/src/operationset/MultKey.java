package operationset;

import java.math.BigInteger;
import java.util.Objects;

public class MultKey {
    private BigInteger powExp;
    private BigInteger sinExp;
    private BigInteger cosExp;
    
    public MultKey(MultSet m) {
        powExp = m.getFinalPowFunc().getExp();
        sinExp = m.getFinalSin().getExp();
        cosExp = m.getFinalCos().getExp();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MultKey multKey = (MultKey) o;
        return powExp.equals(multKey.powExp) &&
                sinExp.equals(multKey.sinExp) &&
                cosExp.equals(multKey.cosExp);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(powExp, sinExp, cosExp);
    }
}
