import java.util.ArrayList;
//import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Term {
    private String term;
    private ArrayList<Point> points = new ArrayList<>();
    private Point point = new Point("1");
    private char ch;

    public Term(String term) {
        this.term = term.substring(1);
        ch = term.charAt(0);
        Pattern p = Pattern.compile("[^*]\\*[^*]");
        Matcher m = p.matcher(this.term);
        int begin = 0;
        int end;
        while (m.find(begin)) {
            end = m.start() + 1;
            points.add(new Point(this.term.substring(begin, end)));
            begin = m.end() - 1;
        }
        if (!this.term.substring(begin).isEmpty()) {
            points.add(new Point(this.term.substring(begin)));
        }
        for (Point point1 : points) {
            point.mulPoint(point1);
        }
        if (ch == '-') {
            point.mulPoint(new Point("-1"));
        }
        point.diff();
    }

    public Point getPoint() {
        return point;
    }

    /*public static void main(String[] args) {//已测试
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String input = scanner.nextLine();
            Term term = new Term(input);
            System.out.println(term.getPoint().getCoefficient()+"x**"+term.getPoint().getIndex());
        }
    }*/
}
