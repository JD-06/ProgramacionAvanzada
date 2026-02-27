package view;

import javax.swing.JWindow;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class Practica01_02 extends JWindow {
    public static void main(String[] args) {
        Practica01_02 window = new Practica01_02();
        window.setVisible(true);
    }

    public Practica01_02() {
        setBounds(100, 100, 450, 300);
        getContentPane().setBackground(Color.CYAN);
        JLabel label = new JLabel("Esta es una JWindow", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        getContentPane().add(label);
    }
}
