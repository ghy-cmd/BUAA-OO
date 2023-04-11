package task3;

public class Peotry extends OtherA {
    private String author;

    public Peotry(String name, double price, long num, String typeName, long age, String author) {
        super(name, price, num, typeName, age);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}
