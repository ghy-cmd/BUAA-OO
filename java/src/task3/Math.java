package task3;

public class Math extends OtherS {
    private long grade;

    public Math(String name, double price, long num, String typeName, long year, long grade) {
        super(name, price, num, typeName, year);
        this.grade = grade;
    }

    public long getGrade() {
        return grade;
    }
}
