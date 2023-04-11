import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int result = 0;
        int length = 0;
        int last = 0;
        int equal = 0;
        int equalNum = 0;
        for (int i = 0; i < m; i++) {
            int now = scanner.nextInt();
            if (now > last) {
                length++;
                equal = 0;
                equalNum = 0;
            } else if (now < last) {
                if (equal == 1) {
                    length = length - equalNum;
                }
                if (length > result) {
                    result = length;
                }
                length = 1;
                equal = 0;
                equalNum = 0;
            } else if (length >= 2) {
                length++;
                equalNum++;
                equal = 1;
            }
            last = now;
        }
        if (equal == 1) {
            length = length - equalNum;
        }
        if (length > result && length != 1) {
            result = length;
        }
        System.out.println(result);
    }
}
