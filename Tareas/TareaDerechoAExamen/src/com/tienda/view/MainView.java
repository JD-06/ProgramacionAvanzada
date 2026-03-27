package com.tienda.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainView extends JFrame {

    // ── Paleta ──────────────────────────────────────
    public static final Color C_SIDEBAR  = new Color(0x1B2631);
    public static final Color C_ACCENT   = new Color(0x27AE60);
    public static final Color C_HOVER    = new Color(0x2ECC71);
    public static final Color C_TEXT     = new Color(0xECF0F1);
    public static final Color C_BG       = new Color(0xF4F6F7);
    public static final Color C_HEADER   = new Color(0x2C3E50);

    private JPanel sidebar;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JLabel lblModulo;
    private String activeModule = "";

    // Botones de navegación
    public JButton btnProductos   = navBtn("  Productos",   "📦");
    public JButton btnInventario  = navBtn("  Inventario",  "📋");
    public JButton btnProveedores = navBtn("  Proveedores", "🚚");
    public JButton btnPOS         = navBtn("  Punto de Venta", "🛒");
    public JButton btnReportes    = navBtn("  Reportes",    "📊");

    public MainView() {
        setTitle("Tienda de Abarrotes — Sistema POS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 780);
        setMinimumSize(new Dimension(1000, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));

        buildSidebar();
        buildContent();

        // Resalta el primer botón activo
        setActiveBtn(btnProductos);
    }

    // ── Sidebar ─────────────────────────────────────

    private void buildSidebar() {
        sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(C_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(220, 0));

        // Logo / título
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(C_ACCENT);
        logoPanel.setPreferredSize(new Dimension(220, 80));
        logoPanel.setBorder(new EmptyBorder(10, 15, 10, 10));

        JLabel lblTitle = new JLabel("<html><b>ABARROTES</b><br><small>Sistema POS</small></html>");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        logoPanel.add(lblTitle, BorderLayout.CENTER);
        sidebar.add(logoPanel, BorderLayout.NORTH);

        // Botones de módulo
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(C_SIDEBAR);
        navPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        navPanel.add(Box.createVerticalStrut(8));
        navPanel.add(btnProductos);
        navPanel.add(Box.createVerticalStrut(4));
        navPanel.add(btnInventario);
        navPanel.add(Box.createVerticalStrut(4));
        navPanel.add(btnProveedores);
        navPanel.add(Box.createVerticalStrut(4));
        navPanel.add(btnPOS);
        navPanel.add(Box.createVerticalStrut(4));
        navPanel.add(btnReportes);
        navPanel.add(Box.createVerticalGlue());

        sidebar.add(navPanel, BorderLayout.CENTER);

        // Info inferior
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(C_SIDEBAR);
        bottomPanel.setBorder(new EmptyBorder(8, 15, 8, 10));
        JLabel lblVer = new JLabel("v1.0  |  MXN");
        lblVer.setFont(new Font("Arial", Font.PLAIN, 11));
        lblVer.setForeground(new Color(0x7F8C8D));
        bottomPanel.add(lblVer, BorderLayout.WEST);
        sidebar.add(bottomPanel, BorderLayout.SOUTH);

        add(sidebar, BorderLayout.WEST);
    }

    // ── Content area ────────────────────────────────

    private void buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 0));
        wrapper.setBackground(C_BG);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(C_HEADER);
        header.setPreferredSize(new Dimension(0, 56));
        header.setBorder(new EmptyBorder(8, 20, 8, 20));

        lblModulo = new JLabel("Selecciona un módulo");
        lblModulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblModulo.setForeground(Color.WHITE);
        header.add(lblModulo, BorderLayout.WEST);

        // Reloj en el header
        JLabel lblClock = new JLabel();
        lblClock.setFont(new Font("Monospaced", Font.PLAIN, 13));
        lblClock.setForeground(new Color(0xBDC3C7));
        header.add(lblClock, BorderLayout.EAST);
        Timer clockTimer = new Timer(1000, e ->
            lblClock.setText(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss"))));
        clockTimer.start();

        wrapper.add(header, BorderLayout.NORTH);

        // Panel con CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(C_BG);
        wrapper.add(contentPanel, BorderLayout.CENTER);

        add(wrapper, BorderLayout.CENTER);
    }

    // ── API pública ─────────────────────────────────

    public void addPanel(JPanel panel, String name) {
        contentPanel.add(panel, name);
    }

    public void showPanel(String name) {
        cardLayout.show(contentPanel, name);
        activeModule = name;
        lblModulo.setText(labelForModule(name));
    }

    public void setActiveBtn(JButton btn) {
        for (JButton b : new JButton[]{btnProductos, btnInventario,
                btnProveedores, btnPOS, btnReportes}) {
            b.setBackground(C_SIDEBAR);
            b.setForeground(C_TEXT);
        }
        btn.setBackground(C_ACCENT);
        btn.setForeground(Color.WHITE);
    }

    // ── Helpers ─────────────────────────────────────

    private static JButton navBtn(String text, String icon) {
        JButton btn = new JButton(icon + text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(C_TEXT);
        btn.setBackground(C_SIDEBAR);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(12, 20, 12, 10));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                if (!btn.getBackground().equals(C_ACCENT))
                    btn.setBackground(new Color(0x2C3E50));
            }
            @Override public void mouseExited(MouseEvent e) {
                if (!btn.getBackground().equals(C_ACCENT))
                    btn.setBackground(C_SIDEBAR);
            }
        });
        return btn;
    }

    private String labelForModule(String name) {
        switch (name) {
            case "PRODUCTOS":   return "📦  Productos";
            case "INVENTARIO":  return "📋  Inventario";
            case "PROVEEDORES": return "🚚  Proveedores";
            case "POS":         return "🛒  Punto de Venta";
            case "REPORTES":    return "📊  Reportes";
            default:            return name;
        }
    }
}
