package Swing4;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class MainFrame extends JFrame {

    private JTextArea textArea;
    private JButton btn;

    public MainFrame() {
        super("Hello World");

        setLayout(new BorderLayout());

        textArea = new JTextArea();
        btn = new JButton("Click me");

        btn.addActionListener(new ActionListener() {
            

            @Override
            public void actionPerformed(ActionEvent arg0) {

                textArea.append("hello\n");
            }
        });


        add(textArea, BorderLayout.CENTER);
        add(btn, BorderLayout.SOUTH);

        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}