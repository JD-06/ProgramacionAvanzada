package main;

import controller.AppService;
import view.Practica03_c;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class Practica05 extends JFrame {
    private final JDesktopPane escritorio;
    private final JMenuItem itemCategorias;
    private final JMenuItem itemSalir;
    private final AppService service;

    public Practica05() {
        this.service = new AppService();

        setTitle("Parte2 - Practica05");
        setSize(900, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.escritorio = new JDesktopPane();
        setContentPane(this.escritorio);

        JMenuBar barra = new JMenuBar();
        JMenu menuConfiguracion = new JMenu("Configuracion");
        this.itemCategorias = new JMenuItem("Categorias");
        menuConfiguracion.add(this.itemCategorias);

        JMenu menuSalir = new JMenu("Salir");
        this.itemSalir = new JMenuItem("Cerrar");
        menuSalir.add(this.itemSalir);

        barra.add(menuConfiguracion);
        barra.add(menuSalir);
        setJMenuBar(barra);

        this.itemCategorias.addActionListener(e -> abrirCategorias());
        this.itemSalir.addActionListener(e -> dispose());
    }

    private void abrirCategorias() {
        Practica03_c child = new Practica03_c(this.service);
        this.escritorio.add(child);
        this.itemCategorias.setEnabled(false);
        child.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                itemCategorias.setEnabled(true);
            }
        });
        child.setVisible(true);
    }

    public static void main(String[] args) {
        new Practica05().setVisible(true);
    }
}
