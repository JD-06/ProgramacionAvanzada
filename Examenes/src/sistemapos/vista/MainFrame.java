package sistemapos.vista;

import sistemapos.modelo.GestorProductos;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class MainFrame extends JFrame {

    private JDesktopPane desktop;
    private GestorProductos gestor;
    private ProductosFrame productosFrame;
    private InventarioFrame inventarioFrame;
    private PuntoDeVentaFrame puntoDeVentaFrame;

    public MainFrame() {
        gestor = new GestorProductos();
        initUI();
    }

    private void initUI() {
        setTitle("Sistema de Punto de Venta - MDI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);

        desktop = new JDesktopPane();
        desktop.setBackground(new Color(45, 45, 48));
        setContentPane(desktop);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem miAbrirCsv = new JMenuItem("Abrir CSV");
        menuArchivo.add(miAbrirCsv);

        JMenu menuVistas = new JMenu("Vistas");
        JMenuItem miProductos   = new JMenuItem("Productos");
        JMenuItem miInventario  = new JMenuItem("Inventario");
        JMenuItem miPuntoVenta  = new JMenuItem("Punto de Venta");
        menuVistas.add(miProductos);
        menuVistas.add(miInventario);
        menuVistas.add(miPuntoVenta);

        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem miSalir = new JMenuItem("Salir");
        menuSistema.add(miSalir);

        menuBar.add(menuArchivo);
        menuBar.add(menuVistas);
        menuBar.add(menuSistema);
        setJMenuBar(menuBar);

        miAbrirCsv.addActionListener((ActionEvent e) -> abrirArchivoCsv());
        miProductos.addActionListener((ActionEvent e) -> abrirProductos());
        miInventario.addActionListener((ActionEvent e) -> abrirInventario());
        miPuntoVenta.addActionListener((ActionEvent e) -> abrirPuntoDeVenta());
        miSalir.addActionListener((ActionEvent e) -> System.exit(0));
    }

    private void abrirArchivoCsv() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar archivo CSV");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv"));
        String rutaActual = gestor.getRutaArchivo();
        if (rutaActual != null && !rutaActual.isBlank()) {
            File actual = new File(rutaActual);
            File directorio = actual.getParentFile();
            if (directorio != null && directorio.exists()) {
                chooser.setCurrentDirectory(directorio);
            }
        }
        int opcion = chooser.showOpenDialog(this);
        if (opcion != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File seleccionado = chooser.getSelectedFile();
        gestor.cargarDesdeCSV(seleccionado.getAbsolutePath());
        refrescarVistasAbiertas();
        JOptionPane.showMessageDialog(
            this,
            "Archivo CSV cargado: " + seleccionado.getAbsolutePath(),
            "Carga completada",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void refrescarVistasAbiertas() {
        if (estaAbierto(productosFrame)) {
            productosFrame.recargarDatos();
        }
        if (estaAbierto(inventarioFrame)) {
            inventarioFrame.recargarDatos();
        }
        if (estaAbierto(puntoDeVentaFrame)) {
            puntoDeVentaFrame.recargarDatos();
        }
    }

    private boolean estaAbierto(JInternalFrame frame) {
        return frame != null && frame.isDisplayable() && !frame.isClosed();
    }

    private void activarVentana(JInternalFrame frame) {
        try {
            frame.setIcon(false);
            frame.setSelected(true);
        } catch (Exception ignored) {
        }
    }

    private void abrirProductos() {
        if (estaAbierto(productosFrame)) {
            activarVentana(productosFrame);
            return;
        }
        productosFrame = new ProductosFrame(gestor);
        desktop.add(productosFrame);
        productosFrame.setVisible(true);
        activarVentana(productosFrame);
    }

    private void abrirInventario() {
        if (estaAbierto(inventarioFrame)) {
            activarVentana(inventarioFrame);
            return;
        }
        inventarioFrame = new InventarioFrame(gestor);
        desktop.add(inventarioFrame);
        inventarioFrame.setVisible(true);
        activarVentana(inventarioFrame);
    }

    private void abrirPuntoDeVenta() {
        if (estaAbierto(puntoDeVentaFrame)) {
            activarVentana(puntoDeVentaFrame);
            return;
        }
        puntoDeVentaFrame = new PuntoDeVentaFrame(gestor);
        desktop.add(puntoDeVentaFrame);
        puntoDeVentaFrame.setVisible(true);
        activarVentana(puntoDeVentaFrame);
    }
}
