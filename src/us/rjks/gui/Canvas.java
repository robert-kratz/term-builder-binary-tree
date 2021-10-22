package us.rjks.gui;

import javafx.scene.canvas.GraphicsContext;
import us.rjks.lib.BinaryTree;
import us.rjks.lib.Token;
import us.rjks.lib.TreeBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * Copyright Ⓒ Robert J. Kratz 2021
 * Created: 22.10.2021 / 15:54
 * Contact: https://link.rjks.us/support
 */

public class Canvas extends JFrame {

    private JPanel main;

    private BinaryTree<Token> tokenBinaryTree;

    private int width = 800, height = 600;

    public Canvas() {
        this.main = new JPanel();
        this.tokenBinaryTree = new BinaryTree<>();

        this.setTitle("Binary Calculator by rjks.us");
        this.setSize(this.width, this.height);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (tokenBinaryTree == null) return;

        update((Graphics2D) this.getGraphics(), tokenBinaryTree, this.width/2, 200, 0, null, false);
    }

    public void refresh(BinaryTree<Token> token) {
        this.tokenBinaryTree = token;
        paint(getGraphics());
    }

    private void paintComponent(Graphics2D graphics2D, String text, int x, int y, LP lp) {
        if(lp != null) {
            graphics2D.drawLine(lp.getX(), lp.getY() + 60, (x-1), (y +20));
        }

        graphics2D.drawString(text + " ", (x - 2), (y + 45));
        graphics2D.drawOval((x - 20), (y + 20),40,40);
    }

    private int getHeight(int layer) {
        return (50 + (70*layer));
    }

    private boolean isEndpoint(BinaryTree<Token> tree) {
        return (tree.getRightTree() == null && tree.getLeftTree() == null);
    }

    private void update(Graphics2D g2d, BinaryTree<Token> tree, int x, int width, int layer, LP lastPos, boolean animation) {
        if(tree.isEmpty()) return;

        paintComponent(g2d, tree.getContent().toString(), x-10, getHeight(layer), lastPos);

        if (animation) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {}
        }

        LP lp = new LP(x-10, getHeight(layer));

        if(tree.getLeftTree() != null && !isEndpoint(tree.getLeftTree())) {
            update(g2d, tree.getLeftTree(), x - width, width/2, layer + 1, lp, animation);
        }
        if(tree.getRightTree() != null && !isEndpoint(tree.getRightTree())) {
            update(g2d, tree.getRightTree(), x + width, width/2, layer + 1, lp, animation);
        }
    }

    public void clear() {
        this.getGraphics().clearRect(0, 0, getWidth(), getHeight());
    }

    public void close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public class LP {

        public int x, y;

        public LP(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
