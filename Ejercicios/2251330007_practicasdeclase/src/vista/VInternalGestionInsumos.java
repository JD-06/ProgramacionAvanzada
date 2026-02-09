package vista;

import javax.swing.*;
import java.awt.*;
import modelo.Insumo;


public class VInternalGestionInsumos extends JInternalFrame {

    private JPanel contentPane;
    private JTextField txtInsumo;
    private JComboBox<String> cmbCategoria;
    private JList<Insumo> listInsumos;
    private JScrollPane scrollPane;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnCerrar;
    

    public VInternalGestionInsumos() {

        super("Gestión de Insumos", true, true, true, true);
        setBounds(0, 0, 550, 450);
        

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);

        JLabel lblTitulo = new JLabel("MÓDULO DE INSUMOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(41, 128, 185));
        lblTitulo.setBounds(180, 15, 200, 25);
        contentPane.add(lblTitulo);
    
        JLabel lblInsumo = new JLabel("Insumo:");
        lblInsumo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInsumo.setBounds(30, 60, 100, 25);
        contentPane.add(lblInsumo);
        
        txtInsumo = new JTextField();
        txtInsumo.setBounds(140, 60, 250, 25);
        txtInsumo.setFont(new Font("Arial", Font.PLAIN, 12));
        contentPane.add(txtInsumo);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setFont(new Font("Arial", Font.PLAIN, 12));
        lblCategoria.setBounds(30, 100, 100, 25);
        contentPane.add(lblCategoria);
        
        cmbCategoria = new JComboBox<>();
        cmbCategoria.setBounds(140, 100, 250, 25);
        cmbCategoria.setFont(new Font("Arial", Font.PLAIN, 11));
        contentPane.add(cmbCategoria);

        JLabel lblLista = new JLabel("Insumos Registrados:");
        lblLista.setFont(new Font("Arial", Font.BOLD, 11));
        lblLista.setBounds(30, 150, 150, 20);
        contentPane.add(lblLista);
        
        listInsumos = new JList<>();
        listInsumos.setFont(new Font("Monospaced", Font.PLAIN, 11));
        scrollPane = new JScrollPane(listInsumos);
        scrollPane.setBounds(30, 175, 480, 170);
        contentPane.add(scrollPane);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(new Color(46, 204, 113));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 11));
        btnAgregar.setBounds(80, 370, 120, 30);
        contentPane.add(btnAgregar);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 11));
        btnEliminar.setBounds(220, 370, 120, 30);
        contentPane.add(btnEliminar);
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(149, 165, 166));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 11));
        btnCerrar.setBounds(360, 370, 120, 30);
        contentPane.add(btnCerrar);
    }
    

    
    public String getTxtInsumo() {
        return txtInsumo.getText();
    }
    
    public JComboBox<String> getCmbCategoria() {
        return cmbCategoria;
    }
    
    public JList<Insumo> getListInsumos() {
        return listInsumos;
    }
    
    public JButton getBtnAgregar() {
        return btnAgregar;
    }
    
    public JButton getBtnEliminar() {
        return btnEliminar;
    }
    
    public JButton getBtnCerrar() {
        return btnCerrar;
    }
    
    public void limpiarCampos() {
        txtInsumo.setText("");
        cmbCategoria.setSelectedIndex(0);
    }
}
