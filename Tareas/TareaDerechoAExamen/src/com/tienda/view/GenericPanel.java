package com.tienda.view;

import javax.swing.*;
import java.awt.*;

public class GenericPanel extends JPanel {
    public GenericPanel(String title) {
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Módulo: " + title, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 24));
        add(lbl, BorderLayout.CENTER);
        
        add(new JLabel("Este es un módulo de " + title.toLowerCase()), BorderLayout.SOUTH);
    }
}