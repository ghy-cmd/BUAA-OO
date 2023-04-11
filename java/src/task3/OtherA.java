package task3;

public class OtherA extends Bookset {
    private long age;


    public OtherA(String name, double price, long num, String typeName, long age) {
        super(name, price, num, typeName);
        this.age = age;
    }

    public long getAge() {
        return age;
    }
}
