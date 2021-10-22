package us.rjks.lib;

import java.util.ArrayList;

/**
 * Copyright Ⓒ Robert J. Kratz 2021
 * Created: 16.10.2021 / 14:58
 * Contact: https://link.rjks.us/support
 */

public class TreeBuilder {

    private String formel;
    private BinaryTree<Token> tree;

    public TreeBuilder(String formel) {
        this.formel = formel;
        this.tree = new BinaryTree<>();

        transform(); //<-- Starts translation
    }

    public ArrayList<Token> transform() {
        ArrayList<Token> tokens = new ArrayList<>();
        String[] array = formel.split("");

        this.tree = new BinaryTree<>(); //prevents duplicate tree

        for (int i = 0; i < array.length; i++) {
            Token token;
            try {
                System.out.println(array[i].equalsIgnoreCase("+") || array[i].equalsIgnoreCase("-") || array[i].equalsIgnoreCase("*") || array[i].equalsIgnoreCase("/"));
                if(array[i].equalsIgnoreCase("+") || array[i].equalsIgnoreCase("-") || array[i].equalsIgnoreCase("*") || array[i].equalsIgnoreCase("/")) {
                    token = new Token(formel.charAt(i));
                } else {
                    String tmp = processFollowing(array, i);
                    token = new Token(Double.valueOf(tmp));
                }
            } catch (Exception exception) {
                return null; //Invalid input due of error
            }
            recBuild(this.tree, token);
            tokens.add(token);
        }
        return tokens;
    }

    private String processFollowing(String[] array, int start) {
        String res = "";
        for (int a = start; a < array.length; a++) {
            if(array[a] != null && !array[a].equalsIgnoreCase("+") && !array[a].equalsIgnoreCase("-") && !array[a].equalsIgnoreCase("*") && !array[a].equalsIgnoreCase("/")) {
                res = res + array[a];
            } else {
                return res;
            }
        }
        return res;
    }

    private void recBuild(BinaryTree<Token> res, Token active) {
        if(res.isEmpty()) {
            res.setContent(active); return;
        }

        Token content = res.getContent();
        if (active.isOperand() && !content.isOperand() ) {
            res.setContent(active);
            if (res.getLeftTree() != null && !res.getLeftTree().isEmpty()) recBuild(res.getLeftTree(), content);
            else res.setLeftTree(new BinaryTree<Token>(content));

            return;
        }
        if (!active.isOperand() && content.isOperand() ) {
            if (res.getRightTree() != null && !res.getRightTree().isEmpty() ) recBuild(res.getRightTree(), active);
            else res.setRightTree(new BinaryTree<Token>(active));

            return;
        }
        if (active.isOperand() && (active.getOperand()=='*' || active.getOperand()=='/') && content.isOperand() && (content.getOperand()=='+' || content.getOperand()=='-') ) {
            if (res.getRightTree() != null && !res.getRightTree().isEmpty()) recBuild(res.getRightTree(), active);
            else res.setRightTree(new BinaryTree<Token>(active));

            return;
        }
        if (active.isOperand() && (active.getOperand()=='+' || active.getOperand()=='-') && content.isOperand() && (content.getOperand()=='*' || content.getOperand()=='/') ) {

            res.setContent(active);

            BinaryTree<Token> nlt = new BinaryTree<Token>(content);
            nlt.setLeftTree(res.getLeftTree() );
            nlt.setRightTree(res.getRightTree() );

            res.setLeftTree( nlt );
            res.setRightTree(new BinaryTree<Token>());

            return;
        }
        if ((active.isOperand() && (active.getOperand() == '+' || active.getOperand() == '-') && content.isOperand() && (content.getOperand() == '+' || content.getOperand() == '-')) ||
                (active.isOperand() && (active.getOperand() == '*' || active.getOperand() == '/') && content.isOperand() && (content.getOperand()=='*' || content.getOperand() == '/'))) {

            res.setContent(active);
            BinaryTree<Token> tmp = new BinaryTree<Token>(content);
            tmp.setLeftTree(res.getLeftTree());
            tmp.setRightTree(res.getRightTree());

            res.setLeftTree(tmp);
            res.setRightTree(new BinaryTree<Token>());
            return;
        }

    }

    public double calculate() {
        return new Calculation(tree).calc();
    }

    public BinaryTree<Token> getTree() {
        return tree;
    }

    public String getFormel() {
        return formel;
    }
}
