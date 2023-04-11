package task3;

public class OtherS extends Bookset {
    private long year;

    public OtherS(String name, double price, long num, String typeName, long year) {
        super(name, price, num, typeName);
        this.year = year;
    }

    public long getYear() {
        return year;
    }
}
