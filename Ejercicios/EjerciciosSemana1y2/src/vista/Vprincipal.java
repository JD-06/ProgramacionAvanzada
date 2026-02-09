package vista;

import javax.swing.*;
import java.awt.*;


public class Vprincipal extends JFrame {
    
    private JPanel contentPane;
    private JMenuBar menuBar;
    private JMenu mGestionPresupuesto;
    private JMenu mGestionObra;
    private JMenu mReportes;
    private JMenu msalida;
    private JMenuItem mipresupuestacion;
    private JMenuItem miobras;
    private JMenuItem miconfiguracion;
    private JMenuItem misalida;
    private JDesktopPane escritorio;
    

    public Vprincipal() {
        setTitle("Sistema de Presupuesto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(52, 73, 94));
        setJMenuBar(menuBar);
        
        mGestionPresupuesto = new JMenu("Gestión de Presupuesto");
        mGestionPresupuesto.setForeground(Color.WHITE);
        mGestionPresupuesto.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mGestionPresupuesto);
        
        mipresupuestacion = new JMenuItem("Presupuestación");
        mipresupuestacion.setFont(new Font("Arial", Font.PLAIN, 11));
        mGestionPresupuesto.add(mipresupuestacion);
        
        mGestionObra = new JMenu("Gestión de Obra");
        mGestionObra.setForeground(Color.WHITE);
        mGestionObra.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mGestionObra);
        
        miobras = new JMenuItem("Obras");
        miobras.setFont(new Font("Arial", Font.PLAIN, 11));
        mGestionObra.add(miobras);
        
        miconfiguracion = new JMenuItem("Configuración");
        miconfiguracion.setFont(new Font("Arial", Font.PLAIN, 11));
        mGestionObra.add(miconfiguracion);
        
        mReportes = new JMenu("Reportes");
        mReportes.setForeground(Color.WHITE);
        mReportes.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mReportes);
        
        msalida = new JMenu("Salida");
        msalida.setForeground(Color.WHITE);
        msalida.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(msalida);
        
        misalida = new JMenuItem("Salir");
        misalida.setFont(new Font("Arial", Font.PLAIN, 11));
        msalida.add(misalida);
        
        contentPane = new JPanel();
        contentPane.setLayout(new CardLayout());
        setContentPane(contentPane);
        
        escritorio = new JDesktopPane();
        escritorio.setBackground(new Color(220, 220, 220));
        contentPane.add(escritorio, "escritorio");
        
        JLabel lblBienvenida = new JLabel("Sistema de Gestión de Presupuestos");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        lblBienvenida.setForeground(new Color(100, 100, 100));
        lblBienvenida.setBounds(250, 250, 500, 40);
        escritorio.add(lblBienvenida);
    }
    
    

    public JMenuItem getMipresupuestacion() {
        return mipresupuestacion;
    }
    

    public JMenuItem getMiobras() {
        return miobras;
    }
    

    public JMenuItem getMiconfiguracion() {
        return miconfiguracion;
    }
    

    public JMenuItem getMisalida() {
        return misalida;
    }
    

    public JDesktopPane getEscritorio() {
        return escritorio;
    }
    

    public void setEstadoMenus(boolean estado) {
        mGestionPresupuesto.setEnabled(estado);
        mGestionObra.setEnabled(estado);
        mReportes.setEnabled(estado);
        msalida.setEnabled(estado);
    }
}
