package task3;

import java.util.Scanner;

public class MainClass {
    @SuppressWarnings("checkstyle:NeedBraces")
    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int nn = n;
        booklist lib[] = new booklist[10000];
        for (int i = 1; i <= n; i++) {
            lib[i] = new booklist();
        }
        int i;
        for (i = 0; i < m; i++) {
            int type = scanner.nextInt();
            switch (type) {
                case (1):
                    int attribute = scanner.nextInt();
                    String name = scanner.next();
                    while (scanner.hasNext() && !scanner.hasNext("[1-9]+")) {
                        scanner.next();
                    }
                    if (lib[attribute].booksArrayList.size() == 0) {
                        System.out.println("Oh, no! This is empty.");
                    } else {
                        int temp = 0;
                        for (Bookset item : lib[attribute].booksArrayList) {
                            if (name.equals(item.getName())) {
                                temp = 1;
                                if (item.getTypeName().equals("Other")) {
                                    System.out.println(item.getName() + " " + item.getPrice() + " " + item.getNum());
                                } else if (item.getTypeName().equals("OtherA")) {
                                    OtherA a = (OtherA) item;
                                    System.out.println(item.getName() + " " + item.getPrice() + " " + item.getNum() + " " + a.getAge());
                                } else if (item.getTypeName().equals("Novel")) {
                                    Novel a = (Novel) item;
                                    System.out.println(a.getName() + " " + a.getPrice() + " " + a.getNum() + " " + a.getAge() + " " + a.isFinish());
                                } else if (item.getTypeName().equals("Poetry")) {
                                    Peotry a = (Peotry) item;
                                    System.out.println(a.getName() + " " + a.getPrice() + " " + a.getNum() + " " + a.getAge() + " " + a.getAuthor());
                                } else if (item.getTypeName().equals("OtherS")) {
                                    OtherS a = (OtherS) item;
                                    System.out.println(a.getName() + " " + a.getPrice() + " " + a.getNum() + " " + a.getYear());
                                } else if (item.getTypeName().equals("Math")) {
                                    Math a = (Math) item;
                                    System.out.println(a.getName() + " " + a.getPrice() + " " + a.getNum() + " " + a.getYear() + " " + a.getGrade());
                                } else if (item.getTypeName().equals("Computer")) {
                                    Computer a = (Computer) item;
                                    System.out.println(a.getName() + " " + a.getPrice() + " " + a.getNum() + " " + a.getYear() + " " + a.getMajor());
                                }
                            }
                        }
                        if (temp == 0) {
                            System.out.println("Oh, no! We don't have " + name + ".");
                        }
                    }
                    break;
                case (2):
                    attribute = scanner.nextInt();
                    while (scanner.hasNext() && !scanner.hasNext("[1-9]+")) {
                        scanner.next();
                    }
                    if (lib[attribute].booksArrayList.size() == 0) {
                        System.out.println("Oh, no! This is empty.");
                    } else {
                        System.out.println(lib[attribute].getTypenum());
                    }
                    break;
                case (3):
                    attribute = scanner.nextInt();
                    while (scanner.hasNext() && !scanner.hasNext("[1-9]+")) {
                        scanner.next();
                    }
                    if (lib[attribute].booksArrayList.size() == 0) {
                        System.out.println("Oh, no! This is empty.");
                    } else {
                        System.out.println(lib[attribute].getTotalnum());
                    }
                    break;
                case (4):
                    attribute = scanner.nextInt();
                    String belong = scanner.next();
                    int temp = 0;
                    name = scanner.next();
                    if (belong.equals("Other")) {
                        double price = scanner.nextDouble();
                        long num = scanner.nextLong();
                        Other book = new Other(name, price, num, belong);
                        for (Bookset item : lib[attribute].booksArrayList) {
                            if (name.equals(item.getName())) {
                                temp = 1;
                            }
                        }
                        if (temp == 1) {
                            System.out.println("Oh, no! The " + name + " exist.");
                        } else {
                            lib[attribute].booksArrayList.add(book);
                        }
                    } else if (belong.equals("OtherA")) {
                        double price = scanner.nextDouble();
                        long num = scanner.nextLong();
                        long age = scanner.nextLong();
                        OtherA book = new OtherA(name, price, num, belong, age);
                        for (Bookset item : lib[attribute].booksArrayList) {
                            if (name.equals(item.getName())) {
                                temp = 1;
                            }
                        }
                        if (temp == 1) {
                            System.out.println("Oh, no! The " + name + " exist.");
                        } else {
                            lib[attribute].booksArrayList.add(book);
                        }
                    } else if (belong.equals("Novel")) {
                        double price = scanner.nextDouble();
                        long num = scanner.nextLong();
                        long age = scanner.nextLong();
                        boolean finish = scanner.nextBoolean();
                        Novel book = new Novel(name, price, num, belong, age, finish);
                        for (Bookset item : lib[attribute].booksArrayList) {
                            if (name.equals(item.getName())) {
                                temp = 1;
                            }
                        }
                        if (temp == 1) {
                            System.out.println("Oh, no! The " + name + " exist.");
                        } else {
                            lib[attribute].booksArrayList.add(book);
                        }
                    } else if (belong.equals("Poetry")) {
                        double price = scanner.nextDouble();
                        long num = scanner.nextLong();
                        long age = scanner.nextLong();
                        String author = scanner.next();
                        Peotry book = new Peotry(name, price, num, belong, age, author);
                        for (Bookset item : lib[attribute].booksArrayList) {
                            if (name.equals(item.getName())) {
                                temp = 1;
                            }
                        }
                        if (temp == 1) {
                            System.out.println("Oh, no! The " + name + " exist.");
                        } else {
                            lib[attribute].booksArrayList.add(book);
                        }
                    } else if (belong.equals("OtherS")) {
                        double price = scanner.nextDouble();
                        long num = scanner.nextLong();
                        long year = scanner.nextLong();
                        OtherS book = new OtherS(name, price, num, belong, year);
                        for (Bookset item : lib[attribute].booksArrayList) {
                            if (name.equals(item.getName())) {
                                temp = 1;
                            }
                        }
                        if (temp == 1) {
                            System.out.println("Oh, no! The " + name + " exist.");
                        } else {
                            lib[attribute].booksArrayList.add(book);
                        }
                    } else if (belong.equals("Math")) {
                        double price = scanner.nextDouble();
                        long num = scanner.nextLong();
                        long year = scanner.nextLong();
                        long grade = scanner.nextLong();
                        Math book = new Math(name, price, num, belong, year, grade);
                        for (Bookset item : lib[attribute].booksArrayList) {
                            if (name.equals(item.getName())) {
                                temp = 1;
                            }
                        }
                        if (temp == 1) {
                            System.out.println("Oh, no! The " + name + " exist.");
                        } else {
                            lib[attribute].booksArrayList.add(book);
                        }
                    } else if (belong.equals("Computer")) {
                        double price = scanner.nextDouble();
                        long num = scanner.nextLong();
                        long year = scanner.nextLong();
                        String major = scanner.next();
                        Computer book = new Computer(name, price, num, belong, year, major);
                        for (Bookset item : lib[attribute].booksArrayList) {
                            if (name.equals(item.getName())) {
                                temp = 1;
                            }
                        }
                        if (temp == 1) {
                            System.out.println("Oh, no! The " + name + " exist.");
                        } else {
                            lib[attribute].booksArrayList.add(book);
                        }
                    }
                    break;
                case (5):
                    attribute = scanner.nextInt();
                    name = scanner.next();
                    while (scanner.hasNext() && !scanner.hasNext("[1-9]+")) {
                        scanner.next();
                    }
                    int k = 0;
                    temp = 0;
                    for (Bookset item : lib[attribute].booksArrayList) {
                        if (name.equals(item.getName())) {
                            temp = 1;
                            break;
                        }
                        k++;
                    }
                    if (temp == 1) {
                        lib[attribute].booksArrayList.remove(k);
                        System.out.println(lib[attribute].getTotalnum());
                    } else {
                        System.out.println("mei you wo zhen mei you.");
                    }
                    break;
                case (6):
                    attribute = scanner.nextInt();
                    int attribute2 = scanner.nextInt();
                    booklist andlist = new booklist();
                    booklist list1 = new booklist();
                    booklist list2 = new booklist();
                    list1.booksArrayList.addAll(lib[attribute].booksArrayList);
                    list2.booksArrayList.addAll(lib[attribute2].booksArrayList);
                    temp = 0;
                    int zero = 0;
                    if (lib[attribute].booksArrayList.size() == 0) {
                        zero = 1;
                    } else if (lib[attribute2].booksArrayList.size() == 0) {
                        zero = 2;
                    } else {
                        for (Bookset item : lib[attribute].booksArrayList) {
                            for (Bookset item2 : list2.booksArrayList) {
                                if (item.getTypeName().equals(item2.getTypeName())) {
                                    if (item.getTypeName().equals("Other")) {
                                        Other a = (Other) item;
                                        Other b = (Other) item2;
                                        if (a.getName().equals(b.getName())) {
                                            if (String.valueOf(a.getPrice()).equals(String.valueOf(b.getPrice()))) {
                                                Other book = new Other(a.getName(), a.getPrice(), a.getNum() + b.getNum(), a.getTypeName());
                                                andlist.booksArrayList.add(book);
                                                list1.booksArrayList.remove(item);
                                                list2.booksArrayList.remove(item2);
                                                break;
                                            } else {
                                                temp = 1;
                                                break;
                                            }
                                        }
                                    } else if (item.getTypeName().equals("OtherA")) {
                                        OtherA a = (OtherA) item;
                                        OtherA b = (OtherA) item2;
                                        if (a.getName().equals(b.getName())) {
                                            if (String.valueOf(a.getPrice()).equals(String.valueOf(b.getPrice())) && a.getAge() == b.getAge()) {
                                                OtherA book = new OtherA(a.getName(), a.getPrice(), a.getNum() + b.getNum(), a.getTypeName(), a.getAge());
                                                andlist.booksArrayList.add(book);
                                                list1.booksArrayList.remove(item);
                                                list2.booksArrayList.remove(item2);
                                                break;
                                            } else {
                                                temp = 1;
                                                break;
                                            }
                                        }
                                    } else if (item.getTypeName().equals("Novel")) {
                                        Novel a = (Novel) item;
                                        Novel b = (Novel) item2;
                                        if (a.getName().equals(b.getName())) {
                                            if (String.valueOf(a.getPrice()).equals(String.valueOf(b.getPrice())) && a.getAge() == b.getAge() && String.valueOf(a.isFinish()).equals(String.valueOf(b.isFinish()))) {
                                                Novel book = new Novel(a.getName(), a.getPrice(), a.getNum() + b.getNum(), a.getTypeName(), a.getAge(), a.isFinish());
                                                andlist.booksArrayList.add(book);
                                                list1.booksArrayList.remove(item);
                                                list2.booksArrayList.remove(item2);
                                                break;
                                            } else {
                                                temp = 1;
                                                break;
                                            }
                                        }
                                    } else if (item.getTypeName().equals("Poetry")) {
                                        Peotry a = (Peotry) item;
                                        Peotry b = (Peotry) item2;
                                        if (a.getName().equals(b.getName())) {
                                            if (String.valueOf(a.getPrice()).equals(String.valueOf(b.getPrice())) && a.getAge() == b.getAge() && a.getAuthor().equals(b.getAuthor())) {
                                                Peotry book = new Peotry(a.getName(), a.getPrice(), a.getNum() + b.getNum(), a.getTypeName(), a.getAge(), a.getAuthor());
                                                andlist.booksArrayList.add(book);
                                                list1.booksArrayList.remove(item);
                                                list2.booksArrayList.remove(item2);
                                                break;
                                            } else {
                                                temp = 1;
                                                break;
                                            }
                                        }
                                    } else if (item.getTypeName().equals("OtherS")) {
                                        OtherS a = (OtherS) item;
                                        OtherS b = (OtherS) item2;
                                        if (a.getName().equals(b.getName())) {
                                            if (String.valueOf(a.getPrice()).equals(String.valueOf(b.getPrice())) && a.getYear() == b.getYear()) {
                                                OtherS book = new OtherS(a.getName(), a.getPrice(), a.getNum() + b.getNum(), a.getTypeName(), a.getYear());
                                                andlist.booksArrayList.add(book);
                                                list1.booksArrayList.remove(item);
                                                list2.booksArrayList.remove(item2);
                                                break;
                                            } else {
                                                temp = 1;
                                                break;
                                            }
                                        }
                                    } else if (item.getTypeName().equals("Math")) {
                                        Math a = (Math) item;
                                        Math b = (Math) item2;
                                        if (a.getName().equals(b.getName())) {
                                            if (String.valueOf(a.getPrice()).equals(String.valueOf(b.getPrice())) && a.getYear() == b.getYear() && a.getGrade() == b.getGrade()) {
                                                Math book = new Math(a.getName(), a.getPrice(), a.getNum() + b.getNum(), a.getTypeName(), a.getYear(), a.getGrade());
                                                andlist.booksArrayList.add(book);
                                                list1.booksArrayList.remove(item);
                                                list2.booksArrayList.remove(item2);
                                                break;
                                            } else {
                                                temp = 1;
                                                break;
                                            }
                                        }
                                    } else if (item.getTypeName().equals("Computer")) {
                                        Computer a = (Computer) item;
                                        Computer b = (Computer) item2;
                                        if (a.getName().equals(b.getName())) {
                                            if (String.valueOf(a.getPrice()).equals(String.valueOf(b.getPrice())) && a.getYear() == b.getYear() && a.getMajor().equals(b.getMajor())) {
                                                Computer book = new Computer(a.getName(), a.getPrice(), a.getNum() + b.getNum(), a.getTypeName(), a.getYear(), a.getMajor());
                                                andlist.booksArrayList.add(book);
                                                list1.booksArrayList.remove(item);
                                                list2.booksArrayList.remove(item2);
                                                break;
                                            } else {
                                                temp = 1;
                                                break;
                                            }
                                        }
                                    }
                                } else if (item.getName().equals(item2.getName())) {
                                    temp = 1;
                                    break;
                                }
                                if (temp == 1) {
                                    break;
                                }
                            }
                        }
                    }
                    if (temp == 1) {
                        System.out.println("Oh, no. We fail!");
                    } else if (zero == 0) {
                        nn++;
                        lib[nn] = new booklist();
                        lib[nn].booksArrayList.addAll(andlist.booksArrayList);
                        lib[nn].booksArrayList.addAll(list1.booksArrayList);
                        lib[nn].booksArrayList.addAll(list2.booksArrayList);
                        System.out.println(nn);
                    } else if (zero == 1) {
                        nn++;
                        lib[nn] = new booklist();
                        lib[nn].booksArrayList.addAll(lib[attribute2].booksArrayList);
                        System.out.println(nn);
                    } else if (zero == 2) {
                        nn++;
                        lib[nn] = new booklist();
                        lib[nn].booksArrayList.addAll(lib[attribute].booksArrayList);
                        System.out.println(nn);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}