package homeworkthird2;

import java.util.ArrayList;

public class Object {
    private ArrayList<String> origin;
    private ArrayList<String> derivative;
    private int num;//为了在递归中传递参数的类

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setDerivative(ArrayList<String> derivative) {
        this.derivative = derivative;
    }

    public ArrayList<String> getDerivative() {
        return derivative;
    }

    public void setOrigin(ArrayList<String> origin) {
        this.origin = origin;
    }

    public ArrayList<String> getOrigin() {
        return origin;
    }
}
