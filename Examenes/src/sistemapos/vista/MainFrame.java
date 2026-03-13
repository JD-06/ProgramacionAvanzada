package sistemapos.vista;

import sistemapos.modelo.GestorCajeros;
import sistemapos.modelo.GestorClientes;
import sistemapos.modelo.GestorCompras;
import sistemapos.modelo.GestorProductos;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class MainFrame extends JFrame {

    private JDesktopPane desktop;
    private GestorProductos gestor;
    private GestorClientes gestorClientes;
    private GestorCajeros gestorCajeros;
    private GestorCompras gestorCompras;
    private ProductosFrame productosFrame;
    private InventarioFrame inventarioFrame;
    private PuntoDeVentaFrame puntoDeVentaFrame;
    private ClientesFrame clientesFrame;
    private CajerosFrame cajerosFrame;

    public MainFrame() {
        gestor = new GestorProductos();
        gestorClientes = new GestorClientes();
        gestorCajeros = new GestorCajeros();
        gestorCompras = new GestorCompras();
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

        JMenu menuPersonas = new JMenu("Personas");
        JMenuItem miClientes = new JMenuItem("Socios");
        JMenuItem miCajeros = new JMenuItem("Cajeros");
        menuPersonas.add(miClientes);
        menuPersonas.add(miCajeros);

        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem miSalir = new JMenuItem("Salir");
        menuSistema.add(miSalir);

        menuBar.add(menuArchivo);
        menuBar.add(menuVistas);
        menuBar.add(menuPersonas);
        menuBar.add(menuSistema);
        setJMenuBar(menuBar);

        miAbrirCsv.addActionListener((ActionEvent e) -> abrirArchivoCsv());
        miProductos.addActionListener((ActionEvent e) -> abrirProductos());
        miInventario.addActionListener((ActionEvent e) -> abrirInventario());
        miPuntoVenta.addActionListener((ActionEvent e) -> abrirPuntoDeVenta());
        miClientes.addActionListener((ActionEvent e) -> abrirClientes());
        miCajeros.addActionListener((ActionEvent e) -> abrirCajeros());
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

    private void refrescarSeleccionVenta() {
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
            puntoDeVentaFrame.recargarDatos();
            activarVentana(puntoDeVentaFrame);
            return;
        }
        puntoDeVentaFrame = new PuntoDeVentaFrame(gestor, gestorClientes, gestorCajeros, gestorCompras);
        desktop.add(puntoDeVentaFrame);
        puntoDeVentaFrame.setVisible(true);
        activarVentana(puntoDeVentaFrame);
    }

    private void abrirClientes() {
        if (estaAbierto(clientesFrame)) {
            clientesFrame.recargarDatos();
            activarVentana(clientesFrame);
            return;
        }
        clientesFrame = new ClientesFrame(gestorClientes, this::refrescarSeleccionVenta);
        desktop.add(clientesFrame);
        clientesFrame.setVisible(true);
        activarVentana(clientesFrame);
    }

    private void abrirCajeros() {
        if (estaAbierto(cajerosFrame)) {
            cajerosFrame.recargarDatos();
            activarVentana(cajerosFrame);
            return;
        }
        cajerosFrame = new CajerosFrame(gestorCajeros, this::refrescarSeleccionVenta);
        desktop.add(cajerosFrame);
        cajerosFrame.setVisible(true);
        activarVentana(cajerosFrame);
    }
}
