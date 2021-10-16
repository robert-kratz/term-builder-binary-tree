package us.rjks.core;

import us.rjks.lib.BinaryTree;
import us.rjks.lib.Calculation;
import us.rjks.lib.Token;
import us.rjks.lib.TreeBuilder;

/**
 * Copyright Ⓒ Robert J. Kratz 2021
 * Created: 16.10.2021 / 14:40
 * Contact: https://link.rjks.us/support
 */

public class Main {

    public static void main(String[] args) {
        BinaryTree<Token> tree = new BinaryTree<>();

        tree.setContent(new Token('+'));
        tree.setRightTree(new BinaryTree<>(new Token(3)));
        tree.setLeftTree(new BinaryTree<>(new Token('*')));
        tree.getLeftTree().setLeftTree(new BinaryTree<>(new Token(5)));
        tree.getLeftTree().setRightTree(new BinaryTree<>(new Token(2)));

        TreeBuilder tb = new TreeBuilder("2*5+3");
        System.out.println(tb.insertTree(tb.transform()));

        Calculation rechnung = new Calculation(tb.insertTree(tb.transform()));
        //Calculation rechnung = new Calculation(tree);

        rechnung.getFormel();
    }

}
