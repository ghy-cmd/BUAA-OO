package strdeal;

import factor.Constant;
import factor.Factor;
import factor.Factory;
import operationset.AddSet;
import operationset.MultSet;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionAnalyse {
    
    public String toNormal(String str) {
        String ret = "";
        ret = ret.concat(str);
        ret = ret.replaceAll("\\s+", "");
        
        ret = ret.replaceAll("\\+-", "-");
        ret = ret.replaceAll("-\\+", "-");
        ret = ret.replaceAll("\\+\\+", "+");
        ret = ret.replaceAll("--", "+");
        
        ret = ret.replaceAll("\\+\\+", "+");
        ret = ret.replaceAll("\\+-", "-");
        ret = ret.replaceAll("-\\+", "-");
        
        //ret = ret.replaceAll("-", "+-");
        
        ret = ret.replaceAll("-x", "-1*x");
        ret = ret.replaceAll("-sin", "-1*sin");
        ret = ret.replaceAll("-cos", "-1*cos");
        ret = ret.replaceAll("-\\(", "-1*(");
        return ret;
    }
    
    private HashMap<String, Pattern> pattMap = new HashMap<>();
    
    public ExpressionAnalyse() {
        pattMap.put("Constant", Patterns.CONSTANT);
        pattMap.put("PowFunc", Patterns.POWFUNC);
        pattMap.put("Sin", Patterns.SIN);
        pattMap.put("Cos", Patterns.COS);
    }
    
    public AddSet createAddSet(StringBuilder str) {
        AddSet newAdd = new AddSet();
        MultSet tempMult = new MultSet();
        boolean isMulSignBefore = true;
        while (true) {
            boolean hasFind = false;
            if (str.length() == 0 || str.charAt(0) == ')') {
                tempMult.merge();
                tempMult.openParenthese();
                newAdd.includeAddSet(tempMult.getFinalAddSet());
                if (str.length() != 0) {
                    str.deleteCharAt(0);
                }
                break;
            }
            if (isMulSignBefore) {
                for (String facType : pattMap.keySet()) {
                    Pattern patt = pattMap.get(facType);
                    Matcher m = patt.matcher(str.toString());
                    if (m.find()) {
                        hasFind = true;
                        Factor newFact = Factory.get(facType, m.group(0));
                        tempMult.add(newFact);
                        str.delete(0, m.end());
                        break;
                    }
                }
                isMulSignBefore = false;
            }
            if (hasFind) {
                continue;
            }
            if (str.charAt(0) == '*') {
                str.deleteCharAt(0);
                isMulSignBefore = true;
                continue;
            } else if (str.charAt(0) == '+' || str.charAt(0) == '-') {
                tempMult.merge();
                tempMult.openParenthese();
                newAdd.includeAddSet(tempMult.getFinalAddSet());
                tempMult = new MultSet();
                isMulSignBefore = true;
                if (str.charAt(0) == '-') {
                    tempMult.add(new Constant(BigInteger.valueOf(-1)));
                }
                str.deleteCharAt(0);
                continue;
            }
            else if (str.charAt(0) == '(') {
                str.deleteCharAt(0);
                AddSet parentheseInMult = createAddSet(str);
                tempMult.add(parentheseInMult);
                continue;
            }
        }
        return newAdd;
    }
    

    
}
