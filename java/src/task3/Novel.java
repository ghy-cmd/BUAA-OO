package task3;

public class Novel extends OtherA {
    private boolean finish;

    public Novel(String name, double price, long num, String typeName, long age, boolean finish) {
        super(name, price, num, typeName, age);
        this.finish = finish;
    }

    public boolean isFinish() {
        return finish;
    }
}
