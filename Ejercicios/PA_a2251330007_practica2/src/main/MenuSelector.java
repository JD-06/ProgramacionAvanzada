package main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class MenuSelector extends JFrame {

    public MenuSelector() {
        setTitle("Selector de Programas - Practica 2");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Menu de Ejecucion", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 10, 10));
        
        JButton btnApp = new JButton("Ejecutar App (Principal)");
        JButton btnP03b = new JButton("Ejecutar Practica 03_b");
        JButton btnP05 = new JButton("Ejecutar Practica 05");
        JButton btnPFinal = new JButton("Ejecutar Practica Final");
        JButton btnPFinal2 = new JButton("Ejecutar Practica Final 2");
        JButton btnSalir = new JButton("Salir");

        btnApp.addActionListener(e -> App.main(null));
        btnP03b.addActionListener(e -> Practica03_b.main(null));
        btnP05.addActionListener(e -> Practica05.main(null));
        btnPFinal.addActionListener(e -> PracticaFinal.main(null));
        btnPFinal2.addActionListener(e -> PracticaFinal2.main(null));
        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(btnApp);
        panelBotones.add(btnP03b);
        panelBotones.add(btnP05);
        panelBotones.add(btnPFinal);
        panelBotones.add(btnPFinal2);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuSelector().setVisible(true);
        });
    }
}
