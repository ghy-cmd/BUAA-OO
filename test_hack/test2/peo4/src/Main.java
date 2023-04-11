import operationset.AddSet;
import strdeal.ExpressionAnalyse;

import java.util.Scanner;

public class Main {
    public static void main(String[] argv) {
        ExpressionAnalyse analyser = new ExpressionAnalyse();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            input = analyser.toNormal(input);
            StringBuilder strBuilder = new StringBuilder(input);
            AddSet expression = analyser.createAddSet(strBuilder);
            expression.derive();
            System.out.println(expression.derivedToString());
        }
    }
}
