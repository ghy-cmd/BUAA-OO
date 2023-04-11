public class TreeNode {
    private final TreeNode left;
    
    public TreeNode getLeft() {
        return left;
    }
    
    public TreeNode getRight() {
        return right;
    }
    
    private final TreeNode right;
    
    public TreeNode(TreeNode left, TreeNode right) {
        this.left = left;
        this.right = right;
    }
    
    public TreeNode() {
        left = null;
        right = null;
    }
    
    //求导作为对节点的一种操作，建一个树，返回新建树的树根
    public TreeNode derive() {
        return new TreeNode();
    }
    
    public String toString() {
        return "";
    }
}
