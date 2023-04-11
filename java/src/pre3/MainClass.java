package pre3;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        int num = 0;
        String[] username = new String[10000];
        String[] ousername = new String[10000];
        String[] domain = new String[10000];
        String[] time = new String[10000];
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if (s.equals("END_OF_INFORMATION")) {
                break;
            }
            String str = s.replace(",", " ");
            String[] a = str.split("\\s+");
            for (int i = num; i < num + a.length; i++) {
                String[] b = a[i - num].split("@");
                ousername[i] = b[0];
                username[i] = bsChange(b[0]);
                String[] c = b[1].split("-", 2);
                domain[i] = c[0];
                time[i] = c[1];
            }
            num = num + a.length;
        }
        while (scanner.hasNextLine()) {
            String seekType = scanner.next();
            String seekUsername = scanner.next();
            int number = researchUsername(seekUsername, username, num);
            if (number == num) {
                System.out.println("no username exists");
            } else {
                String[] seekTime = time[number].split("-|:");
                switch (seekType) {
                    case ("qdtype"):
                        String[] qdtype = domain[number].split("\\.");
                        System.out.println(qdtype[0]);
                        break;
                    case ("qyear"):
                        System.out.println(seekTime[0]);
                        break;
                    case ("qmonth"):
                        System.out.println(seekTime[1]);
                        break;
                    case ("qday"):
                        System.out.println(seekTime[2]);
                        break;
                    case ("qhour"):
                        if (seekTime.length < 4) {
                            System.out.println("null");
                        } else {
                            System.out.println(seekTime[3]);
                        }
                        break;
                    case ("qminute"):
                        if (seekTime.length < 5) {
                            System.out.println("null");
                        } else {
                            System.out.println(seekTime[4]);
                        }
                        break;
                    case ("qsec"):
                        if (seekTime.length < 6) {
                            System.out.println("null");
                        } else {
                            System.out.println(seekTime[5]);
                        }
                        break;
                    case ("qutype"):
                        match(ousername[number]);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static String bsChange(String a) {
        char[] b = a.toCharArray();
        for (int i = 0; i < b.length; i++) {
            if (b[i] >= 'A' && b[i] <= 'Z') {
                b[i] = (char) (b[i] - ('A' - 'a'));
            }
        }
        return new String(b);
    }

    public static int researchUsername(String username, String[] lib, int num) {
        int i;
        int temp = 0;
        for (i = 0; i < num; i++) {
            if (username.equals(lib[i])) {
                temp = 1;
                break;
            }
        }
        if (temp == 1) {
            return i;
        } else {
            return num;
        }
    }

    public static void match(String username) {
        String a = new String();
        int typeNum = 0;
        String regexA = "(a){2,3}?(b){2,4}?(a){2,4}?(c){2,4}?";
        Pattern regexAp = Pattern.compile(regexA);
        Matcher regexApm = regexAp.matcher(username);
        if (regexApm.find()) {
            a += "A";
            typeNum++;
        }
        String regexB = "(a){2,3}?(ba)*?(bc){2,4}?";
        Pattern regexBp = Pattern.compile(regexB);
        Matcher regexBpm = regexBp.matcher(username);
        if (regexBpm.find()) {
            a += "B";
            typeNum++;
        }
        String regexC = "(a){2,3}?(ba)*?(bc){2,4}?";
        Pattern regexCp = Pattern.compile(regexC);
        Matcher regexCpm = regexCp.matcher(bsChange(username));
        if (regexCpm.find()) {
            a += "C";
            typeNum++;
        }
        String regexD1 = "^(a){0,3}?(b)+?(c){2,3}?";
        Pattern regexDp1 = Pattern.compile(regexD1);
        Matcher regexDpm1 = regexDp1.matcher(username);
        String regexD2 = "(b){1,2}?(a){1,2}?(c){0,3}?$";
        Pattern regexDp2 = Pattern.compile(regexD2);
        Matcher regexDpm2 = regexDp2.matcher(bsChange(username));
        if (regexDpm1.find() && regexDpm2.find()) {
            a += "D";
            typeNum++;
        }
        String regexE = "a.*b.*b.*c.*b.*c.*c";
        Pattern regexEp = Pattern.compile(regexE);
        Matcher regexEpm = regexEp.matcher(username);
        if (regexEpm.find()) {
            a += "E";
            typeNum++;
        }
        System.out.print(typeNum);
        if (typeNum != 0) {
            System.out.print(" " + a);
        }
        System.out.print("\n");
    }
}

