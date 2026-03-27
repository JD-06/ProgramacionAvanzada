package com.tienda.view;

import com.tienda.model.Producto;
import com.tienda.model.Venta;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ReportesPanel extends JPanel {

    // Cards resumen
    private JLabel lblTotalProductos  = bigStat("0");
    private JLabel lblTotalStock      = bigStat("0");
    private JLabel lblValorInventario = bigStat("$0.00");
    private JLabel lblVentasHoy       = bigStat("$0.00");

    // Tabla top productos (por valor)
    private DefaultTableModel modelTop;
    private JTable tablaTop;

    // Tabla stock crítico
    private DefaultTableModel modelCritico;
    private JTable tablaCritico;

    // Tabla ventas recientes
    private DefaultTableModel modelVentas;
    private JTable tablaVentas;

    public JButton btnRefresh = ProductosPanel.secBtn("↺ Actualizar");

    public ReportesPanel() {
        setLayout(new BorderLayout(8, 8));
        setBackground(MainView.C_BG);
        setBorder(new EmptyBorder(12, 12, 12, 12));

        add(buildTopBar(), BorderLayout.NORTH);
        add(buildBody(), BorderLayout.CENTER);
    }

    private JPanel buildTopBar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(MainView.C_BG);

        // 4 cards de resumen
        JPanel cards = new JPanel(new GridLayout(1, 4, 10, 0));
        cards.setBackground(MainView.C_BG);
        cards.add(card("Total Productos",      lblTotalProductos,  new Color(0x3498DB)));
        cards.add(card("Unidades en Stock",    lblTotalStock,      new Color(0x27AE60)));
        cards.add(card("Valor Inventario",     lblValorInventario, new Color(0x8E44AD)));
        cards.add(card("Ventas del Día",       lblVentasHoy,       new Color(0xE67E22)));
        p.add(cards, BorderLayout.CENTER);

        JPanel btn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btn.setBackground(MainView.C_BG);
        btn.add(btnRefresh);
        p.add(btn, BorderLayout.EAST);
        return p;
    }

    private JPanel buildBody() {
        JPanel p = new JPanel(new GridLayout(1, 3, 10, 0));
        p.setBackground(MainView.C_BG);

        // Panel: Top 10 mayor valor
        modelTop = tableModel(new String[]{"Nombre", "Precio Venta", "Stock", "Valor Total"});
        tablaTop = styledTable(modelTop);
        p.add(scrollPanel("🏆  Top Productos (mayor valor)", tablaTop));

        // Panel: Stock crítico
        modelCritico = tableModel(new String[]{"SKU", "Nombre", "Stock"});
        tablaCritico = styledTable(modelCritico);
        p.add(scrollPanel("⚠️  Stock Crítico (≤ 5 unidades)", tablaCritico));

        // Panel: Ventas recientes
        modelVentas = tableModel(new String[]{"Folio", "Fecha", "Total", "Método"});
        tablaVentas = styledTable(modelVentas);
        p.add(scrollPanel("🧾  Ventas Recientes", tablaVentas));

        return p;
    }

    // ── API pública ─────────────────────────────────

    public void setData(List<Producto> productos, List<Venta> ventas) {
        // ── Stats ────────────────────────────────────
        int total = productos.size();
        int stock = productos.stream().mapToInt(Producto::getStock).sum();
        double valor = productos.stream()
            .mapToDouble(p -> p.getPrecioVenta() * p.getStock()).sum();

        // Ventas del día
        String hoy = java.time.LocalDate.now().toString();
        double ventasHoy = ventas.stream()
            .filter(v -> v.getFecha() != null && v.getFecha().startsWith(hoy))
            .mapToDouble(Venta::getTotal).sum();

        lblTotalProductos.setText(String.valueOf(total));
        lblTotalStock.setText(String.valueOf(stock));
        lblValorInventario.setText("$" + String.format("%,.2f", valor));
        lblVentasHoy.setText("$" + String.format("%,.2f", ventasHoy));

        // ── Top por valor ─────────────────────────────
        modelTop.setRowCount(0);
        productos.stream()
            .sorted(Comparator.comparingDouble(p -> -(p.getPrecioVenta() * p.getStock())))
            .limit(10)
            .forEach(p -> modelTop.addRow(new Object[]{
                p.getNombre(),
                String.format("$%.2f", p.getPrecioVenta()),
                p.getStock(),
                String.format("$%.2f", p.getPrecioVenta() * p.getStock())
            }));

        // ── Stock crítico ─────────────────────────────
        modelCritico.setRowCount(0);
        productos.stream()
            .filter(p -> p.getStock() <= 5)
            .sorted(Comparator.comparingInt(Producto::getStock))
            .forEach(p -> modelCritico.addRow(new Object[]{
                p.getSku(), p.getNombre(), p.getStock()
            }));

        // ── Ventas recientes ──────────────────────────
        modelVentas.setRowCount(0);
        List<Venta> sorted = ventas.stream()
            .sorted(Comparator.comparingInt(Venta::getFolio).reversed())
            .limit(50)
            .collect(Collectors.toList());
        for (Venta v : sorted) {
            modelVentas.addRow(new Object[]{
                "#" + v.getFolio(), v.getFecha(),
                String.format("$%.2f", v.getTotal()), v.getMetodoPago()
            });
        }
    }

    // ── Helpers visuales ─────────────────────────────

    private JPanel card(String title, JLabel stat, Color color) {
        JPanel p = new JPanel(new BorderLayout(4, 4));
        p.setBackground(color);
        p.setBorder(new EmptyBorder(14, 16, 14, 16));
        p.setPreferredSize(new Dimension(0, 90));

        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(new Color(0xECF0F1));
        p.add(lbl, BorderLayout.NORTH);
        p.add(stat, BorderLayout.CENTER);
        return p;
    }

    private static JLabel bigStat(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.BOLD, 22));
        l.setForeground(Color.WHITE);
        return l;
    }

    private JPanel scrollPanel(String title, JTable t) {
        JScrollPane scroll = new JScrollPane(t);
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setBackground(Color.WHITE);
        p.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(new Color(0xCED4DA)),
            new EmptyBorder(8, 8, 8, 8)));
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        p.add(lbl, BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    private DefaultTableModel tableModel(String[] cols) {
        return new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
    }

    private JTable styledTable(DefaultTableModel m) {
        JTable t = new JTable(m);
        t.setRowHeight(24);
        t.setFont(new Font("Arial", Font.PLAIN, 12));
        t.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        t.getTableHeader().setBackground(MainView.C_HEADER);
        t.getTableHeader().setForeground(Color.WHITE);
        t.setGridColor(new Color(0xDDE1E7));
        t.setShowVerticalLines(false);
        t.setDefaultRenderer(Object.class, new ProductosPanel.StripedRenderer());
        return t;
    }
}
