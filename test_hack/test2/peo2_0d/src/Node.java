public class Node {
    private String value;
    private String der;
    private String com;
    private Node left;
    private Node right;
    
    public Node(String str) {
        this.value = str;
    }
    
    public void setLeft(Node node) {
        this.left = node;
    }
    
    public void setRight(Node node) {
        this.right = node;
    }
    
    public void setDer(String str) {
        this.der = str;
    }
    
    public void setCom(String str) {
        this.com = str;
    }
    
    public Node getRight() {
        return this.right;
    }
    
    public Node getLeft() {
        return this.left;
    }
    
    public String getDer() {
        return this.der;
    }
    
    public String getCom() {
        return this.com;
    }
    
    public String getValue() {
        return this.value;
    }
}
