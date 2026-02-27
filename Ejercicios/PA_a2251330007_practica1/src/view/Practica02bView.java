package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class Practica02bView extends JFrame {
    private JPanel PanelPrincipal;
    public JButton Bsalir;

    public Practica02bView(String titulo) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setTitle(titulo);
        PanelPrincipal = new JPanel();
        PanelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(PanelPrincipal);
        PanelPrincipal.setLayout(null);
        
        Bsalir = new JButton("Salir");
        Bsalir.setBounds(145, 124, 89, 23);
        PanelPrincipal.add(Bsalir);
    }
}
