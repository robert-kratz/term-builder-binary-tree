package us.rjks.lib;

public class Calculation {

    private BinaryTree<Token> tree;

    public Calculation(BinaryTree<Token> tree) {
        this.tree = tree;
    }

    public void getFormel() {
        inorder(tree);
    }

    public double calc() {
        return postorder(tree);
    }

    private void inorder(BinaryTree<Token> binaryTree) {
        if (binaryTree.getLeftTree() != null) inorder(binaryTree.getLeftTree());
        if (!binaryTree.isEmpty()) System.out.print(binaryTree.getContent().toString());
        if (binaryTree.getRightTree() != null) inorder(binaryTree.getRightTree());
    }

    private double postorder(BinaryTree<Token> binaryTree) {

        double l = Double.NaN, r = Double.NaN;

        if (binaryTree.getLeftTree() != null) l = postorder(binaryTree.getLeftTree());
        if (binaryTree.getRightTree() != null) r = postorder(binaryTree.getRightTree());

        if (binaryTree.getLeftTree() != null && binaryTree.getLeftTree().isEmpty() && binaryTree.getRightTree() != null && binaryTree.getRightTree().isEmpty() ) return binaryTree.getContent().getNumber();

        if (binaryTree.isEmpty()) return Double.NaN;


        switch(binaryTree.getContent().getOperand()) {
            case '*': {
                return l * r;
            }
            case '/': {
                if (r != 0.0) return l / r;
                else return Double.NaN;
            }
            case '+': {
                return l + r;
            }
            case '-': {
                return l - r;
            }
            default: {
                return Double.NaN;
            }
        }
    }

}
