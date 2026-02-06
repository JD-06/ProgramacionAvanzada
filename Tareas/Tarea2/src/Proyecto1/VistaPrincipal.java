package Proyecto1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaPrincipal extends JFrame {

 
    private JPanel panelDatos;
    private JLabel lblId, lblNombre, lblGenero, lblDepartamento, lblFecha, lblImagen;
    private JTextField txtId, txtNombre, txtFecha;
    private JRadioButton rbtnMasculino, rbtnFemenino;
    private ButtonGroup grupoGenero;
    private JComboBox<String> cmbDepartamento;
    private JButton btnSubirImagen;
    private JDesktopPane desktopPaneFoto; 


    private JPanel panelBotones;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar, btnImprimir;


    private JPanel panelTabla;
    private JLabel lblBuscar;
    private JTextField txtBuscar;
    private JTable tablaDatos;
    private JScrollPane scrollTabla;
    private DefaultTableModel modeloTabla;

    public VistaPrincipal() {
        initComponents();
        configurarVentana();
    }

    private void initComponents() {
        
        this.setLayout(null); 

   
        panelDatos = new JPanel();
        panelDatos.setLayout(null);
        panelDatos.setBorder(BorderFactory.createTitledBorder("Información del Usuario"));
        panelDatos.setBounds(10, 10, 350, 450);

        lblId = new JLabel("ID:");
        lblId.setBounds(20, 30, 80, 25);
        txtId = new JTextField();
        txtId.setBounds(100, 30, 150, 25);
        txtId.setEditable(false); 

        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 70, 80, 25);
        txtNombre = new JTextField();
        txtNombre.setBounds(100, 70, 200, 25);

        lblGenero = new JLabel("Género:");
        lblGenero.setBounds(20, 110, 80, 25);
        rbtnMasculino = new JRadioButton("Masculino");
        rbtnMasculino.setBounds(100, 110, 90, 25);
        rbtnFemenino = new JRadioButton("Femenino");
        rbtnFemenino.setBounds(190, 110, 90, 25);
        grupoGenero = new ButtonGroup();
        grupoGenero.add(rbtnMasculino);
        grupoGenero.add(rbtnFemenino);

        lblDepartamento = new JLabel("Depto:");
        lblDepartamento.setBounds(20, 150, 80, 25);
        cmbDepartamento = new JComboBox<>(new String[]{"Seleccionar", "IT", "RRHH", "Ventas", "Admin"});
        cmbDepartamento.setBounds(100, 150, 200, 25);

        lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(20, 190, 80, 25);
        txtFecha = new JTextField();
        txtFecha.setBounds(100, 190, 200, 25);

        desktopPaneFoto = new JDesktopPane(); 
        desktopPaneFoto.setBounds(100, 230, 150, 150);
        desktopPaneFoto.setBackground(Color.LIGHT_GRAY);
        
        lblImagen = new JLabel();
        lblImagen.setBounds(0, 0, 150, 150);
        lblImagen.setHorizontalAlignment(JLabel.CENTER);
        lblImagen.setText("FOTO");
        desktopPaneFoto.add(lblImagen);

        btnSubirImagen = new JButton("Subir Imagen");
        btnSubirImagen.setBounds(100, 390, 150, 30);

  
        panelDatos.add(lblId); panelDatos.add(txtId);
        panelDatos.add(lblNombre); panelDatos.add(txtNombre);
        panelDatos.add(lblGenero);
        panelDatos.add(rbtnMasculino); panelDatos.add(rbtnFemenino);
        panelDatos.add(lblDepartamento); panelDatos.add(cmbDepartamento);
        panelDatos.add(lblFecha); panelDatos.add(txtFecha);
        panelDatos.add(desktopPaneFoto);
        panelDatos.add(btnSubirImagen);

        this.add(panelDatos);


        panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEtchedBorder());
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBounds(10, 470, 860, 60);

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnImprimir = new JButton("Imprimir");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnImprimir);

        this.add(panelBotones);


        panelTabla = new JPanel();
        panelTabla.setLayout(null);
        panelTabla.setBounds(370, 10, 500, 450);

        lblBuscar = new JLabel("Buscar Usuario:");
        lblBuscar.setBounds(10, 20, 100, 25);
        txtBuscar = new JTextField();
        txtBuscar.setBounds(110, 20, 200, 25);


        String[] columnas = {"ID", "Nombre", "Género", "Depto", "Fecha"};
        modeloTabla = new DefaultTableModel(null, columnas);
        tablaDatos = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tablaDatos);
        scrollTabla.setBounds(10, 60, 480, 380);

        panelTabla.add(lblBuscar);
        panelTabla.add(txtBuscar);
        panelTabla.add(scrollTabla);

        this.add(panelTabla);
    }

    private void configurarVentana() {
        this.setTitle("Sistema de Gestión de Usuarios");
        this.setSize(900, 580);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }



    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtFecha() { return txtFecha; }
    public JTextField getTxtBuscar() { return txtBuscar; }


    public JRadioButton getRbtnMasculino() { return rbtnMasculino; }
    public JRadioButton getRbtnFemenino() { return rbtnFemenino; }
    public ButtonGroup getGrupoGenero() { return grupoGenero; }
    public JComboBox<String> getCmbDepartamento() { return cmbDepartamento; }


    public JButton getBtnSubirImagen() { return btnSubirImagen; }
    public JLabel getLblImagen() { return lblImagen; }


    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnImprimir() { return btnImprimir; }

    public JTable getTablaDatos() { return tablaDatos; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}