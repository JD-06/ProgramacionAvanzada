package vista;

import javax.swing.*;
import java.awt.*;
import modelo.Insumo;


public class VPractica01_Tarea01 extends JFrame {
    
    private JPanel contentPane;
    private JTextField txtInsumo;
    private JComboBox<String> cmbCategoria;
    private JList<Insumo> listInsumos;
    private JScrollPane scrollPane;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JButton btnSalir;
    

    public VPractica01_Tarea01() {
        setTitle("Gestión de Insumos - CRUD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        
        // Panel principal
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(240, 248, 255));
        setContentPane(contentPane);
        
        JLabel lblTitulo = new JLabel("GESTIÓN DE INSUMOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(25, 25, 112));
        lblTitulo.setBounds(180, 15, 250, 30);
        contentPane.add(lblTitulo);
        
        JLabel lblInsumo = new JLabel("Nombre del Insumo:");
        lblInsumo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInsumo.setBounds(50, 70, 140, 25);
        contentPane.add(lblInsumo);
        
        txtInsumo = new JTextField();
        txtInsumo.setBounds(200, 70, 250, 25);
        txtInsumo.setFont(new Font("Arial", Font.PLAIN, 12));
        contentPane.add(txtInsumo);
        
        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setFont(new Font("Arial", Font.PLAIN, 12));
        lblCategoria.setBounds(50, 110, 140, 25);
        contentPane.add(lblCategoria);
        
        cmbCategoria = new JComboBox<>();
        cmbCategoria.setBounds(200, 110, 250, 25);
        cmbCategoria.setFont(new Font("Arial", Font.PLAIN, 11));
        contentPane.add(cmbCategoria);
        
        JLabel lblLista = new JLabel("Lista de Insumos:");
        lblLista.setFont(new Font("Arial", Font.BOLD, 12));
        lblLista.setBounds(50, 160, 150, 25);
        contentPane.add(lblLista);
        
        listInsumos = new JList<>();
        listInsumos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listInsumos.setFont(new Font("Monospaced", Font.PLAIN, 11));
        scrollPane = new JScrollPane(listInsumos);
        scrollPane.setBounds(50, 190, 500, 180);
        contentPane.add(scrollPane);
        
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(new Color(46, 139, 87));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 11));
        btnAgregar.setBounds(50, 400, 110, 35);
        contentPane.add(btnAgregar);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(220, 20, 60));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 11));
        btnEliminar.setBounds(180, 400, 110, 35);
        contentPane.add(btnEliminar);
        
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(new Color(30, 144, 255));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 11));
        btnActualizar.setBounds(310, 400, 110, 35);
        contentPane.add(btnActualizar);
        
        btnSalir = new JButton("Salir");
        btnSalir.setBackground(new Color(105, 105, 105));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 11));
        btnSalir.setBounds(440, 400, 110, 35);
        contentPane.add(btnSalir);
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
    
    public JButton getBtnActualizar() {
        return btnActualizar;
    }
    
    public JButton getBtnSalir() {
        return btnSalir;
    }
    
    
    public void limpiarCampos() {
        txtInsumo.setText("");
        cmbCategoria.setSelectedIndex(0);
    }
}
