package view;

import javax.swing.*;
import java.awt.*;

public class Practica03bView extends JFrame {
    public JTextField Tid, Tcategoria;
    public JButton Bagregar, Beliminar, Bsalir;
    public JTextArea Tareacategoria;
    private JPanel panelFormulario;

    public Practica03bView(String title) {
        super(title);
        setBounds(0, 0, 390, 370);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        getContentPane().add(panelFormulario, BorderLayout.CENTER);

        JLabel labelId = new JLabel("ID:");
        labelId.setBounds(10, 9, 71, 20);
        Tid = new JTextField(10);
        Tid.setEditable(false);
        Tid.setBounds(91, 9, 147, 20);
        panelFormulario.add(labelId);
        panelFormulario.add(Tid);

        JLabel labelCategoria = new JLabel("Categoria:");
        labelCategoria.setBounds(10, 34, 71, 20);
        Tcategoria = new JTextField(20);
        Tcategoria.setEditable(false);
        Tcategoria.setBounds(91, 35, 147, 20);
        panelFormulario.add(labelCategoria);
        panelFormulario.add(Tcategoria);

        Bagregar = new JButton("Agregar");
        Bagregar.setBounds(20, 104, 111, 20);
        panelFormulario.add(Bagregar);

        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(153, 104, 111, 20);
        panelFormulario.add(Beliminar);

        Bsalir = new JButton("Salir");
        Bsalir.setBounds(274, 104, 79, 20);
        panelFormulario.add(Bsalir);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 132, 357, 179);
        panelFormulario.add(scrollPane);
        Tareacategoria = new JTextArea(10, 40);
        scrollPane.setViewportView(Tareacategoria);
        Tareacategoria.setEditable(false);
    }
}

