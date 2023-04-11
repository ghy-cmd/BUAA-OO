import java.util.List;

public class Add {
    private final Poly left;
    private final Trim right;
    
    public Add(Poly left, Trim right) {
        this.left = left;
        this.right = right;
    }
    
    public Add() {
        left = new Poly();
        right = new Trim();
    }
    
    public Poly getLeft() {
        return left;
    }
    
    public Trim getRight() {
        return right;
    }
    
    public Poly getValue() {
        //方法对于right来说，遍历left，然后检测和right exp相同的，进行合并，如果没找到，就添加到list中
        List<Trim> trims = left.getTrims();
        int flag = 0;//没有合并
        for (Trim trim : trims) {
            if (trim.getExp().equals(right.getExp())) {
                //直接set trim的ceo
                trim.setCeo(trim.getCeo().add(right.getCeo()));
                flag = 1;//合并完成
                break;
            }
        }
        if (flag == 0) {
            //没匹配到，在trims尾部填上right
            trims.add(right);
        }
        return new Poly(trims);
    }
}
