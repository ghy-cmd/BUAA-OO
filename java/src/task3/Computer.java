package task3;

public class Computer extends OtherS {
    private String major;

    public Computer(String name, double price, long num, String typeName, long year, String major) {
        super(name, price, num, typeName, year);
        this.major = major;
    }

    public String getMajor() {
        return major;
    }
}
