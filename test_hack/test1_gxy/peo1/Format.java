import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format {
    private String line;
    private ArrayList<String> terms = new ArrayList<>();

    public Format(String line) {
        this.line = line;
    }

    public void formatLine() {
        line = line.replaceAll("[ \t]", "");
        line = line.replaceAll("\\+-", "-");
        line = line.replaceAll("-\\+", "-");
        line = line.replaceAll("--", "+");
        line = line.replaceAll("\\+\\+", "+");
        line = line.replaceAll("\\+-", "-");
        line = line.replaceAll("-\\+", "-");
        line = line.replaceAll("--", "+");
        line = line.replaceAll("\\+\\+", "+");
        if (line.charAt(0) != '+' && line.charAt(0) != '-') {
            line = "+" + line;
        }
        Pattern pattern = Pattern.compile("[^*][+-]");
        Matcher matcher = pattern.matcher(this.line);
        int start = 0;
        while (matcher.find(start)) {
            terms.add(line.substring(start, matcher.start() + 1));
            start = matcher.end() - 1;
        }
        terms.add(line.substring(start));
    }

    public ArrayList<String> getTerms() {
        return terms;
    }
}
