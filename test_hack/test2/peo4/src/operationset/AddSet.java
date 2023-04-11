package operationset;

import factor.Constant;
import java.util.ArrayList;
import java.util.HashMap;

public class AddSet {
    private HashMap<MultKey, MultSet> derivedMultMap = new HashMap<>();
    private HashMap<MultKey, MultSet> multSets = new HashMap<>();
    
    public AddSet() {
    
    }
    
    /*public AddSet(int i) {
        multSets.add(new MultSet());
    }*/
    
    public ArrayList<MultSet> getMultSets() {
        ArrayList<MultSet> ret = new ArrayList<>();
        for (MultSet m : multSets.values()) {
            ret.add(m);
        }
        return ret;
    }
    
    /*public void addToDerived(MultSet m) {
        BigInteger key = m.getFinalPowFunc().getExp();
        ArrayList<MultSet> pushmInto = derivedMultMap.get(key);
        if (pushmInto == null) {
            pushmInto = new ArrayList<>();
            pushmInto.add(m);
            derivedMultMap.put(key, pushmInto);
        } else {
            pushmInto.add(m);
        }
    }*/
    
    public void mergeToMap(HashMap<MultKey, MultSet> map, MultSet m) {
        MultKey key = new MultKey(m);
        MultSet oldMult = map.get(key);
        if (oldMult == null) {
            map.put(key, m);
            return;
        }
        MultSet newM = new MultSet();
        newM.setFinalSin(m.getFinalSin());
        newM.setFinalCos(m.getFinalCos());
        newM.setFinalPowFunc(m.getFinalPowFunc());
        newM.setFinalConst(new Constant(m.getFinalConst().getValue().add(
                oldMult.getFinalConst().getValue())));
        map.put(key, newM);
    }
    
    public void includeAddSet(AddSet a) {
        ArrayList<MultSet> toAdd = a.getMultSets();
        for (MultSet m : toAdd) {
            this.add(m);
        }
    }
    
    public void add(MultSet m) {
        mergeToMap(multSets, m);
    }
    
    public void derive() {
        ArrayList<MultSet> derivedMultSet;
        for (MultSet m : multSets.values()) {
            derivedMultSet = m.derive();
            for (MultSet m2 : derivedMultSet) {
                mergeToMap(derivedMultMap, m2);
            }
        }
    }
    
    public String derivedToString() {
        boolean isNegaFirst = true;
        StringBuilder stringBuilder = new StringBuilder();
        String str;
        for (MultSet m : derivedMultMap.values()) {
            str = m.toString();
            if (str.length() == 0) {
                continue;
            }
            if (str.charAt(0) == '-') {
                stringBuilder.append(str);
            } else {
                if (isNegaFirst) {
                    stringBuilder.insert(0, str);
                    isNegaFirst = false;
                } else {
                    stringBuilder.append('+').append(str);
                }
            }
        }
        if (stringBuilder.length() == 0) {
            stringBuilder.append(0);
        }
        return stringBuilder.toString();
    }
}
