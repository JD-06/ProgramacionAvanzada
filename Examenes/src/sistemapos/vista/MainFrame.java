package sistemapos.vista;

import sistemapos.modelo.GestorProductos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    private JDesktopPane desktop;
    private GestorProductos gestor;

    public MainFrame() {
        gestor = new GestorProductos();
        initUI();
    }

    private void initUI() {
        setTitle("Sistema de Punto de Venta — MDI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);

        // ── Desktop Pane ─────────────────────────────────────────────────
        desktop = new JDesktopPane();
        desktop.setBackground(new Color(45, 45, 48));
        setContentPane(desktop);

        // ── Barra de Menú ─────────────────────────────────────────────────
        JMenuBar menuBar = new JMenuBar();

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

        menuBar.add(menuVistas);
        menuBar.add(menuSistema);
        setJMenuBar(menuBar);

        // ── Eventos del Menú ──────────────────────────────────────────────
        miProductos.addActionListener((ActionEvent e) -> abrirProductos());
        miInventario.addActionListener((ActionEvent e) -> abrirInventario());
        miPuntoVenta.addActionListener((ActionEvent e) -> abrirPuntoDeVenta());
        miSalir.addActionListener((ActionEvent e) -> System.exit(0));
    }

    private void abrirProductos() {
        ProductosFrame pf = new ProductosFrame(gestor);
        desktop.add(pf);
        pf.setVisible(true);
        try { pf.setSelected(true); } catch (Exception ignored) {}
    }

    private void abrirInventario() {
        InventarioFrame inv = new InventarioFrame(gestor);
        desktop.add(inv);
        inv.setVisible(true);
        try { inv.setSelected(true); } catch (Exception ignored) {}
    }

    private void abrirPuntoDeVenta() {
        PuntoDeVentaFrame pdv = new PuntoDeVentaFrame(gestor);
        desktop.add(pdv);
        pdv.setVisible(true);
        try { pdv.setSelected(true); } catch (Exception ignored) {}
    }
}
