package vista;

import javax.swing.*;
import java.awt.*;


public class VPractica01_Tarea02 extends JFrame {
    
    private JPanel contentPane;
    private JMenuBar menuBar;
    private JMenu mGestionPresupuesto;
    private JMenu mGestionObra;
    private JMenu mGestionInsumos;
    private JMenu mReportes;
    private JMenu mSalida;
    private JMenuItem miPresupuestacion;
    private JMenuItem miObras;
    private JMenuItem miInsumos;
    private JMenuItem miConfiguracion;
    private JMenuItem miReportes;
    private JMenuItem miSalida;
    private JDesktopPane escritorio;

    public VPractica01_Tarea02() {
        setTitle("Sistema de Gestión de Obras y Presupuestos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 50, 1000, 700);
        
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(52, 73, 94));
        setJMenuBar(menuBar);
        
        mGestionPresupuesto = new JMenu("Gestión de Presupuesto");
        mGestionPresupuesto.setForeground(Color.WHITE);
        mGestionPresupuesto.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mGestionPresupuesto);
        
        miPresupuestacion = new JMenuItem("Presupuestación");
        miPresupuestacion.setFont(new Font("Arial", Font.PLAIN, 11));
        mGestionPresupuesto.add(miPresupuestacion);
        
        mGestionObra = new JMenu("Gestión de Obra");
        mGestionObra.setForeground(Color.WHITE);
        mGestionObra.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mGestionObra);
        
        miObras = new JMenuItem("Obras");
        miObras.setFont(new Font("Arial", Font.PLAIN, 11));
        mGestionObra.add(miObras);
        
        miConfiguracion = new JMenuItem("Configuración");
        miConfiguracion.setFont(new Font("Arial", Font.PLAIN, 11));
        mGestionObra.add(miConfiguracion);
        
        mGestionInsumos = new JMenu("Gestión de Insumos");
        mGestionInsumos.setForeground(Color.WHITE);
        mGestionInsumos.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mGestionInsumos);
        
        miInsumos = new JMenuItem("Insumos");
        miInsumos.setFont(new Font("Arial", Font.PLAIN, 11));
        mGestionInsumos.add(miInsumos);
        
        mReportes = new JMenu("Reportes");
        mReportes.setForeground(Color.WHITE);
        mReportes.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mReportes);
        
        miReportes = new JMenuItem("Generar Reportes");
        miReportes.setFont(new Font("Arial", Font.PLAIN, 11));
        mReportes.add(miReportes);
        
        mSalida = new JMenu("Salida");
        mSalida.setForeground(Color.WHITE);
        mSalida.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mSalida);
        
        miSalida = new JMenuItem("Salir del Sistema");
        miSalida.setFont(new Font("Arial", Font.PLAIN, 11));
        mSalida.add(miSalida);
        
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);
        
        escritorio = new JDesktopPane();
        escritorio.setBackground(new Color(236, 240, 241));
        contentPane.add(escritorio, BorderLayout.CENTER);
        
        JPanel panelInfo = new JPanel();
        panelInfo.setBackground(new Color(52, 73, 94));
        panelInfo.setPreferredSize(new Dimension(0, 30));
        
        JLabel lblInfo = new JLabel("Sistema de Gestión - Usuario: Administrador");
        lblInfo.setForeground(Color.WHITE);
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        panelInfo.add(lblInfo);
        
        contentPane.add(panelInfo, BorderLayout.SOUTH);
    }
    
    
    public JMenuItem getMiPresupuestacion() {
        return miPresupuestacion;
    }
    
    public JMenuItem getMiObras() {
        return miObras;
    }
    
    public JMenuItem getMiInsumos() {
        return miInsumos;
    }
    
    public JMenuItem getMiConfiguracion() {
        return miConfiguracion;
    }
    
    public JMenuItem getMiReportes() {
        return miReportes;
    }
    
    public JMenuItem getMiSalida() {
        return miSalida;
    }
    
    
    public JDesktopPane getEscritorio() {
        return escritorio;
    }
    
    
    public void setEstadoMenus(boolean estado) {
        mGestionPresupuesto.setEnabled(estado);
        mGestionObra.setEnabled(estado);
        mGestionInsumos.setEnabled(estado);
        mReportes.setEnabled(estado);
        mSalida.setEnabled(estado);
    }
}
