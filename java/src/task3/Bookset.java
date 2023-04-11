package task3;

public class Bookset {
    private String name;
    private double price;
    private long num;
    private String typeName;

    public Bookset(String name, double price, long num, String typeName) {
        this.name = name;
        this.price = price;
        this.num = num;
        this.typeName = typeName;
    }

    public String getName() {
        return name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

}
