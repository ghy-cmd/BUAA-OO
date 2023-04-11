public class Multi extends TreeNode {
    public Multi(TreeNode left, TreeNode right) {
        super(left, right);
    }
    
    @Override
    public TreeNode derive() {
        TreeNode leftde = super.getLeft().derive();
        TreeNode rightde = super.getRight().derive();
        TreeNode multi1 = new Multi(leftde, super.getRight());
        TreeNode multi2 = new Multi(super.getLeft(), rightde);
        TreeNode result = new Add(multi1, multi2);
        return result;
    }
    
    @Override
    public String toString() {
        String str1 = super.getLeft().toString();
        String str2 = super.getRight().toString();
        boolean left1 = str1.equals("1")
                || str1.equals("(1)");
        boolean right1 = str2.equals("1")
                || str2.equals("(1)");
        if (str1.equals("0") || str2.equals("0")) {
            return "0";
        }
        if (super.getLeft() instanceof Add && super.getRight() instanceof Add) {
            if (left1) {
                return "(" + str2 + ")";
            }
            if (right1) {
                return "(" + str1 + ")";
            }
            return "(" + str1 + ")" +
                    "*" + "(" + str2 + ")";
        } else if (super.getLeft() instanceof Add) {
            if (left1) {
                return str2;
            }
            if (right1) {
                return "(" + str1 + ")";
            }
            return "(" + str1 + ")" + "*" + str2;
        } else if (super.getRight() instanceof Add) {
            if (left1) {
                return "(" + str2 + ")";
            }
            if (right1) {
                return  str1;
            }
            return str1 + "*" + "(" + str2 + ")";
        } else {
            if (left1) {
                return str2;
            }
            if (right1) {
                return str1;
            }
            return str1 + "*" + str2;
        }
    }
}
