import java.math.BigInteger;

public class Tree {
    private Node root = new Node(null);
    
    public Node getRoot() {
        return root;
    }
    
    public void setRoot(Node node) {
        this.root = node;
        return;
    }
    
    public Node createNode(String str) {
        int i = -1;
        int leftCount = 0;
        int rightCount = 0;
        while (i < str.length() - 1) {
            i++;
            if (str.charAt(i) == '(') {
                leftCount++;
            } else if (str.charAt(i) == ')') {
                rightCount++;
            }
            if (str.charAt(i) == '+' && leftCount == rightCount) {
                Node left = createNode(str.substring(i + 1));
                Node right = createNode(str.substring(0, i));
                Node now = addType("+", left, right);
                return now;
            }
        }
        i = -1;
        leftCount = 0;
        rightCount = 0;
        int leftFirst = 0;
        int rightLast = 0;
        while (i < str.length() - 1) {
            i++;
            if (str.charAt(i) == '(') {
                leftCount++;
            } else if (str.charAt(i) == ')') {
                rightCount++;
                rightLast = i + 0;
            }
            if (str.charAt(i) == '*' && leftCount == rightCount) {
                Node left = createNode(str.substring(i + 1));
                Node right = createNode(str.substring(0, i));
                Node now = addType("*", left, right);
                return now;
            }
        }
        for (int j = 0; j < str.length(); j++) {
            if (str.charAt(j) == '(') {
                leftFirst = j + 0;
                break;
            }
        }
        if (leftCount > 0 && rightCount > 0) {
            Node left = addType("1", null, null);
            Node right = createNode(str.substring(leftFirst + 1, rightLast));
            Node now = addType("*", left, right);
            return now;
        } else if (str.equals("")) {
            Node now = addType("0", null, null);
            return now;
        } else {
            Node now = addType(str, null, null);
            return now;
        }
    }
    
    public Node addType(String c, Node left, Node right) {
        Node now = new Node(c);
        now.setLeft(left);
        now.setRight(right);
        return now;
    }
    
    public Node derivative(Node node) {
        String value = node.getValue();
        if (value.equals("y")) {
            node.setDer("z");
            node.setCom("y");
            return node;
        } else if (value.equals("z")) {
            node.setDer("-1*y");
            node.setCom("z");
            return node;
        } else if (value.equals("x")) {
            node.setDer("1");
            node.setCom("x");
            return node;
        } else if (value.matches(".*x\\^.+")) {
            int index = value.indexOf("^", 0);
            String strA = value.substring(0, index);
            String strB = value.substring(index + 1);
            BigInteger mi = new BigInteger(strB);
            if (mi.equals(BigInteger.valueOf(1))) {
                node.setDer("1");
                node.setCom("x");
                return node;
            } else if (mi.equals(BigInteger.valueOf(0))) {
                node.setDer("0");
                node.setCom("1");
                return node;
            } else {
                BigInteger xishu = mi.add(BigInteger.valueOf(0));
                mi = mi.subtract(BigInteger.valueOf(1));
                String ans = xishu.toString() + "*x^" + mi.toString();
                node.setDer(ans);
                node.setCom(value);
                return node;
            }
        } else if (value.matches(".*y\\^.+")) {
            int index = value.indexOf("^", 0);
            String strA = value.substring(0, index);
            String strB = value.substring(index + 1);
            BigInteger mi = new BigInteger(strB);
            if (mi.equals(BigInteger.valueOf(1))) {
                node.setDer("z");
                node.setCom("y");
                return node;
            } else if (mi.equals(BigInteger.valueOf(0))) {
                node.setDer("0");
                node.setCom("1");
                return node;
            } else {
                BigInteger xishu = mi.add(BigInteger.valueOf(0));
                mi = mi.subtract(BigInteger.valueOf(1));
                String ans = xishu.toString() + "*z" + "*y^" + mi.toString();
                node.setDer(ans);
                node.setCom(value);
                return node;
            }
        } else {
            return derivative2(node);
        }
    }
    
    public Node derivative2(Node node) {
        String value = node.getValue();
        if (value.matches(".*z\\^.+")) {
            int index = value.indexOf("^", 0);
            String strA = value.substring(0, index);
            String strB = value.substring(index + 1);
            BigInteger mi = new BigInteger(strB);
            if (mi.equals(BigInteger.valueOf(1))) {
                node.setDer("-1*y");
                node.setCom("z");
                return node;
            } else if (mi.equals(BigInteger.valueOf(0))) {
                node.setDer("0");
                node.setCom("1");
                return node;
            } else {
                BigInteger xishu = mi.add(BigInteger.valueOf(0));
                mi = mi.subtract(BigInteger.valueOf(1));
                String ans = xishu.toString() + "*-1*y" + "*z^" + mi.toString();
                node.setDer(ans);
                node.setCom(value);
                return node;
            }
        } else if (value.matches(".*\\d.*")) {
            node.setDer("0");
            node.setCom(value);
            return node;
        } else if (value.equals("+")) {
            node.setLeft(derivative(node.getLeft()));
            node.setRight(derivative(node.getRight()));
            node.setDer("(" + node.getLeft().getDer() + "+" + node.getRight().getDer() + ")");
            node.setCom("(" + node.getLeft().getCom() + "+" + node.getRight().getCom() + ")");
            return node;
        } else if (value.equals("*")) {
            node.setLeft(derivative(node.getLeft()));
            node.setRight(derivative(node.getRight()));
            node.setDer("(" + node.getLeft().getDer() + "*" + node.getRight().getCom() +
                    "+"
                    + node.getLeft().getCom() + "*" + node.getRight().getDer() + ")");
            node.setCom(node.getLeft().getCom() + "*" + node.getRight().getCom());
            return node;
        } else {
            return null;
        }
    }
}
