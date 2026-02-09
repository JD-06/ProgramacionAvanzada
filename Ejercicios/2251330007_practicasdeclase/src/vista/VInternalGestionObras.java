package vista;

import javax.swing.*;
import java.awt.*;


public class VInternalGestionObras extends JInternalFrame {
    

    private JPanel contentPane;
    private JTextField txtNombreObra;
    private JTextField txtUbicacion;
    private JComboBox<String> cmbEstado;
    private JList<String> listObras;
    private JScrollPane scrollPane;
    private JButton btnRegistrar;
    private JButton btnEliminar;
    private JButton btnCerrar;
    

    public VInternalGestionObras() {
        super("Gestión de Obras", true, true, true, true);
        setBounds(0, 0, 600, 500);
        
        // Panel principal
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        
        JLabel lblTitulo = new JLabel("MÓDULO DE OBRAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(230, 126, 34));
        lblTitulo.setBounds(210, 15, 200, 25);
        contentPane.add(lblTitulo);
        
        JLabel lblNombre = new JLabel("Nombre de Obra:");
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNombre.setBounds(40, 70, 120, 25);
        contentPane.add(lblNombre);
        
        txtNombreObra = new JTextField();
        txtNombreObra.setBounds(170, 70, 300, 25);
        txtNombreObra.setFont(new Font("Arial", Font.PLAIN, 12));
        contentPane.add(txtNombreObra);
        
        JLabel lblUbicacion = new JLabel("Ubicación:");
        lblUbicacion.setFont(new Font("Arial", Font.PLAIN, 12));
        lblUbicacion.setBounds(40, 110, 120, 25);
        contentPane.add(lblUbicacion);
        
        txtUbicacion = new JTextField();
        txtUbicacion.setBounds(170, 110, 300, 25);
        txtUbicacion.setFont(new Font("Arial", Font.PLAIN, 12));
        contentPane.add(txtUbicacion);
        
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Arial", Font.PLAIN, 12));
        lblEstado.setBounds(40, 150, 120, 25);
        contentPane.add(lblEstado);
        
        cmbEstado = new JComboBox<>(new String[]{
            "En Planificación",
            "En Ejecución",
            "Pausada",
            "Finalizada"
        });
        cmbEstado.setBounds(170, 150, 300, 25);
        cmbEstado.setFont(new Font("Arial", Font.PLAIN, 11));
        contentPane.add(cmbEstado);
        
        JLabel lblLista = new JLabel("Obras Registradas:");
        lblLista.setFont(new Font("Arial", Font.BOLD, 11));
        lblLista.setBounds(40, 200, 150, 20);
        contentPane.add(lblLista);
        
        listObras = new JList<>();
        listObras.setFont(new Font("Monospaced", Font.PLAIN, 11));
        scrollPane = new JScrollPane(listObras);
        scrollPane.setBounds(40, 225, 510, 160);
        contentPane.add(scrollPane);
        
        btnRegistrar = new JButton("Registrar Obra");
        btnRegistrar.setBackground(new Color(52, 152, 219));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 11));
        btnRegistrar.setBounds(90, 410, 140, 35);
        contentPane.add(btnRegistrar);
        
        btnEliminar = new JButton("Eliminar Obra");
        btnEliminar.setBackground(new Color(192, 57, 43));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 11));
        btnEliminar.setBounds(250, 410, 140, 35);
        contentPane.add(btnEliminar);
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(127, 140, 141));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 11));
        btnCerrar.setBounds(410, 410, 140, 35);
        contentPane.add(btnCerrar);
    }
    
    
    public String getTxtNombreObra() {
        return txtNombreObra.getText();
    }
    
    public String getTxtUbicacion() {
        return txtUbicacion.getText();
    }
    
    public JComboBox<String> getCmbEstado() {
        return cmbEstado;
    }
    
    public JList<String> getListObras() {
        return listObras;
    }
    
    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }
    
    public JButton getBtnEliminar() {
        return btnEliminar;
    }
    
    public JButton getBtnCerrar() {
        return btnCerrar;
    }
    
    public void limpiarCampos() {
        txtNombreObra.setText("");
        txtUbicacion.setText("");
        cmbEstado.setSelectedIndex(0);
    }
}
