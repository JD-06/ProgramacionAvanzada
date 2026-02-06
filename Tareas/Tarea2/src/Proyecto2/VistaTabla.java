package Proyecto2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaTabla extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtEdad;
    private JButton btnAgregar;

    public VistaTabla() {
        setTitle("Ejemplo Tabla MVC");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel();
        txtNombre = new JTextField(10);
        txtEdad = new JTextField(5);
        btnAgregar = new JButton("Agregar a Tabla");
        
        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Edad:"));
        panelForm.add(txtEdad);
        panelForm.add(btnAgregar);

        add(panelForm, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Edad");
        
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    // Getters
    public DefaultTableModel getModeloTabla() { return modeloTabla; } 
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtEdad() { return txtEdad; }
    public JButton getBtnAgregar() { return btnAgregar; }
}