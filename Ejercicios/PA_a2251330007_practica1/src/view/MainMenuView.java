package view;

import javax.swing.*;

public class MainMenuView extends JFrame {
    public JDesktopPane desktopPane;
    public JMenuItem m1_01, m1_02, m1_03, m1_01b, m2_a, m2_b, m2_b2, m2_c;
    public JMenuItem m3_a, m3_b, m3_c, m3_d;

    public MainMenuView() {
        setTitle("Menu Principal - Practicas MVC");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuP1 = new JMenu("Vistas y Componentes");
        m1_01 = new JMenuItem("Practica01_01 (JFrame)");
        m1_02 = new JMenuItem("Practica01_02 (JWindow)");
        m1_03 = new JMenuItem("Practica01_03 (JDialog)");
        m1_01b = new JMenuItem("Practica01_01_b (Contenedores)");
        m2_a = new JMenuItem("Practica02_a (Boton)");
        m2_b = new JMenuItem("Practica02_b (Eventos)");
        m2_b2 = new JMenuItem("Practica02_b2 (Nemonicos)");
        m2_c = new JMenuItem("Practica02_c (Componentes)");
        
        menuP1.add(m1_01); menuP1.add(m1_02); menuP1.add(m1_03); menuP1.add(m1_01b);
        menuP1.addSeparator();
        menuP1.add(m2_a); menuP1.add(m2_b); menuP1.add(m2_b2); menuP1.add(m2_c);

        JMenu menuP3 = new JMenu("MVC y Datos");
        m3_a = new JMenuItem("Practica03_a (Insumos)");
        m3_b = new JMenuItem("Practica03_b (Categorias)");
        m3_c = new JMenuItem("Practica03_c (Archivos)");
        m3_d = new JMenuItem("Practica03_d (Persistencia Completa)");
        
        menuP3.add(m3_a); menuP3.add(m3_b); menuP3.add(m3_c); menuP3.add(m3_d);

        menuBar.add(menuP1);
        menuBar.add(menuP3);
        setJMenuBar(menuBar);
    }
}

