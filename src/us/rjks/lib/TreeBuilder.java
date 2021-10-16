package us.rjks.lib;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Copyright Ⓒ Robert J. Kratz 2021
 * Created: 16.10.2021 / 14:58
 * Contact: https://link.rjks.us/support
 */

public class TreeBuilder {

    private String formel;

    public TreeBuilder(String formel) {
        this.formel = formel;
    }

    public ArrayList<Token> transform() {
        ArrayList<Token> tokens = new ArrayList<>();
        String[] array = formel.split("");
        for (int i = 0; i < array.length; i++) {
            Token token;
            try {
                if(array[i].equalsIgnoreCase("+") || array[i].equalsIgnoreCase("-") || array[i].equalsIgnoreCase("*") || array[i].equalsIgnoreCase("/")) {
                    token = new Token(formel.charAt(i));
                } else {
                    token = new Token(Double.valueOf(array[i]));
                }
            } catch (Exception exception) {
                return null; //Invalid input due of error
            }

            tokens.add(token);
        }
        return tokens;
    }

    public BinaryTree<Token> build() {
        BinaryTree<Token> tmp = new BinaryTree<>();
        ArrayList<Token> tokens = transform();
        for (int i = 0; i < transform().size(); i++) {
            tmp = recBuild(tmp, tokens.get(i));
        }
        return tmp;
    }

    private BinaryTree<Token> recBuild(BinaryTree<Token> res, Token active) {
        BinaryTree<Token> tree = res;
        if(tree.getLeftTree() != null) return recBuild(tree.getLeftTree(), active);
        if(tree.getRightTree() != null) return recBuild(tree.getRightTree(), active);

        Token knot = tree.getContent();

        System.out.println(knot + " " + active);

        if(tree.isEmpty()) {

            System.out.println(0);
            tree.setContent(active);

        } else if(knot.isNumber() && active.isOperand()) {

            System.out.println(1);

            BinaryTree<Token> tmp = tree;
            BinaryTree<Token> nt = new BinaryTree<>(knot);
            nt.setLeftTree(new BinaryTree<>(active));

            if(!tmp.getLeftTree().isEmpty()) nt.getLeftTree().setLeftTree(tmp.getLeftTree());
            if(!tmp.getRightTree().isEmpty()) nt.getLeftTree().setRightTree(tmp.getRightTree());

            tree = nt;

        } else if(knot.isOperand() && active.isNumber()) {
            System.out.println(2);

            BinaryTree<Token> tmp = tree;

            BinaryTree<Token> nt = new BinaryTree<>(knot);

            nt.setRightTree(new BinaryTree<>(active));

            if(!tmp.getLeftTree().isEmpty()) nt.getRightTree().setLeftTree(tmp.getLeftTree());
            if(!tmp.getRightTree().isEmpty()) nt.getRightTree().setRightTree(tmp.getRightTree());

            tree = nt;

        } else if((knot.isLine() && active.isLine()) || (knot.isPoint() && active.isPoint())) {

            System.out.println(3);

            BinaryTree<Token> tmp = new BinaryTree<>(active);
            tmp.setLeftTree(tree);
            tree = tmp;

        } else if((knot.isOperand() && knot.isPoint()) && (active.isOperand() && active.isLine())) {

            System.out.println(4);

            BinaryTree<Token> tmp = tree;
            tree = new BinaryTree<>(active);
            tree.setLeftTree(tmp);

        } else if((knot.isOperand() && knot.isLine()) && (active.isOperand() && (active.isPoint()))) {

            System.out.println(5);

            BinaryTree<Token> tmp = tree;
            tree = new BinaryTree<>(active);
            tree.setRightTree(tmp);

        } else {
            System.out.println("error while processing: " + active + " with knot " + knot);
        }

        return tree;
    }

    public BinaryTree<Token> insertTree(ArrayList<Token> order) {
        BinaryTree<Token> tree = new BinaryTree<>();

        order.forEach(token -> {
            System.out.println(token);
        });

        System.out.println("\n------------------");

        for (int i = 0; i < order.size(); i++) {
            Token active = order.get(i), knot = tree.getContent();

            if(tree.isEmpty() && active.isNumber()) {

                System.out.println(0);
                tree.setContent(active);

            } else if(knot.isNumber() && active.isOperand()) {

                System.out.println(1);

                BinaryTree<Token> tmp = tree;
                BinaryTree<Token> nt = new BinaryTree<>(knot);
                nt.setLeftTree(new BinaryTree<>(active));

                if(!tmp.getLeftTree().isEmpty()) nt.getLeftTree().setLeftTree(tmp.getLeftTree());
                if(!tmp.getRightTree().isEmpty()) nt.getLeftTree().setRightTree(tmp.getRightTree());

                tree = nt;

                BinaryTree<Token> ra = tree;



            } else if(knot.isOperand() && active.isNumber()) {
                System.out.println(2);

                BinaryTree<Token> tmp = tree;

                BinaryTree<Token> nt = new BinaryTree<>(knot);

                nt.setRightTree(new BinaryTree<>(active));

                if(!tmp.getLeftTree().isEmpty()) nt.getRightTree().setLeftTree(tmp.getLeftTree());
                if(!tmp.getRightTree().isEmpty()) nt.getRightTree().setRightTree(tmp.getRightTree());

                tree = nt;

            } else if((knot.isLine() && active.isLine()) || (knot.isPoint() && active.isPoint())) {

                System.out.println(3);

                BinaryTree<Token> tmp = new BinaryTree<>(active);
                tmp.setLeftTree(tree);
                tree = tmp;

            } else if((knot.isOperand() && knot.isPoint()) && (active.isOperand() && active.isLine())) {

                System.out.println(4);

                BinaryTree<Token> tmp = tree;
                tree = new BinaryTree<>(active);
                tree.setLeftTree(tmp);

            } else if((knot.isOperand() && knot.isLine()) && (active.isOperand() && (active.isPoint()))) {

                System.out.println(5);

                BinaryTree<Token> tmp = tree;
                tree = new BinaryTree<>(active);
                tree.setRightTree(tmp);

            } else {
                System.out.println("error while processing: " + active + " with knot " + knot);
            }
        }
        return tree;
    }
}
