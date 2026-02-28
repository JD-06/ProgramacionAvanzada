package view;

import model.StorageType;

import javax.swing.ButtonGroup;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class MenuPrincipalFrame extends JFrame {
    public final JDesktopPane desktopPane;
    public final JMenuItem miCategorias;
    public final JMenuItem miInsumos;
    public final JMenuItem miObras;
    public final JMenuItem miSalir;
    public final JRadioButtonMenuItem miTxt;
    public final JRadioButtonMenuItem miXml;
    private final JMenu menuOperacion;
    private final JMenu menuConfiguracion;

    public MenuPrincipalFrame() {
        setTitle("PA_a2251330007_practica2");
        setSize(1050, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.desktopPane = new JDesktopPane();
        setContentPane(this.desktopPane);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        this.menuOperacion = new JMenu("Operacion");
        this.miCategorias = new JMenuItem("Categorias");
        this.miInsumos = new JMenuItem("Insumos");
        this.miObras = new JMenuItem("Obras");
        this.menuOperacion.add(this.miCategorias);
        this.menuOperacion.add(this.miInsumos);
        this.menuOperacion.add(this.miObras);

        this.menuConfiguracion = new JMenu("Configuracion");
        this.miTxt = new JRadioButtonMenuItem("TXT");
        this.miXml = new JRadioButtonMenuItem("XML");
        ButtonGroup group = new ButtonGroup();
        group.add(this.miTxt);
        group.add(this.miXml);
        this.menuConfiguracion.add(this.miTxt);
        this.menuConfiguracion.add(this.miXml);

        JMenu menuSalir = new JMenu("Salir");
        this.miSalir = new JMenuItem("Cerrar");
        menuSalir.add(this.miSalir);

        menuBar.add(this.menuOperacion);
        menuBar.add(this.menuConfiguracion);
        menuBar.add(menuSalir);
    }

    public void setMainActionsEnabled(boolean enabled) {
        this.menuOperacion.setEnabled(enabled);
        this.menuConfiguracion.setEnabled(enabled);
    }

    public void applyStorageType(StorageType storageType) {
        if (storageType == StorageType.XML) {
            this.miXml.setSelected(true);
        } else {
            this.miTxt.setSelected(true);
        }
    }
}
