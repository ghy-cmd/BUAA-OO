public class Add extends TreeNode {
    public Add(TreeNode left, TreeNode right) {
        super(left, right);
    }
    
    @Override
    public TreeNode derive() {
        TreeNode result = new Add(super.getLeft().derive(), super.getRight().derive());
        return result;
    }
    
    @Override
    public String toString() {
        if (super.getLeft().toString().equals("0") && super.getRight().toString().equals("0")) {
            return "0";
        }
        if (super.getLeft().toString().equals("0")) {
            return super.getRight().toString();
        }
        if (super.getRight().toString().equals("0")) {
            return super.getLeft().toString();
        }
        return super.getLeft().toString() + "+" + super.getRight().toString();
    }
}
