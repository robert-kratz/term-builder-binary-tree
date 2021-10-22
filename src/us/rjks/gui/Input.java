package us.rjks.gui;

import us.rjks.lib.TreeBuilder;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

/**
 * Copyright Ⓒ Robert J. Kratz 2021
 * Created: 22.10.2021 / 15:13
 * Contact: https://link.rjks.us/support
 */

public class Input extends JFrame {

    private JPanel main;

    private JTextField input;
    private JButton calculate;
    private JButton draw;
    private JButton close;
    private JLabel label;

    public Canvas canvas;

    public Input() {

        this.setTitle("Binary Calculator by rjks.us");
        this.setSize(600, 70);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.main = new JPanel();
        this.main.setLayout(new FlowLayout());

        this.input = new JTextField("Enter your form here");
        this.input.setMargin(new Insets(5, 5, 5, 5));

        this.label = new JLabel("Robert J. Kratz (rjks.us) © 2021");

        this.calculate = new JButton("Calculate");

        this.draw = new JButton("Draw");

        this.close = new JButton("Close");

        addListeners();

        this.main.add(this.input);
        this.main.add(this.calculate);
        this.main.add(this.draw);
        this.main.add(this.close);

        this.main.add(this.label);

        this.getContentPane().add(main);
    }
    
    public void addListeners() {
        input.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                trigger();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                trigger();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                trigger();
            }

            public void trigger() {
                if (canvas != null) {
                    TreeBuilder tb = new TreeBuilder(input.getText());

                    if(!String.valueOf(input.getText()).equals("NaN")) canvas.refresh(tb.getTree());
                }
            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input.setText("");
                close();
                if (canvas != null) canvas.close();
                canvas = null;
            }
        });

        calculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeBuilder tb = new TreeBuilder(input.getText());
                double result = tb.calculate();

                if(String.valueOf(result).equals("NaN")) {
                    JOptionPane.showMessageDialog(null,
                            "The provided formel is not valid", "Syntax Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "The answer is " + result, "Result found",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        draw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TreeBuilder tb = new TreeBuilder(input.getText());
                double result = tb.calculate();

                if(String.valueOf(result).equals("NaN")) {
                    JOptionPane.showMessageDialog(null,
                            "The provided formel is not valid", "Syntax Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (canvas == null) {
                    canvas = new Canvas();
                }

                canvas.refresh(tb.getTree());
            }
        });
    }

    public void close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
