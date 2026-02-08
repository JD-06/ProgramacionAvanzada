package ejemplos.calculadoramenu;

import javax.swing.*;
import java.awt.*;

public class VistaCalculadora extends JFrame {
    private JTextField txtDisplay;
    private JTextField txtOperando1;
    private JTextField txtOperando2;
    private JComboBox<String> cmbOperacion;
    private JButton btnCalcular;
    private JButton btnLimpiar;
    private JTextArea txtHistorial;

    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenu menuCalculadora;
    private JMenu menuCientifico;
    private JMenu menuAyuda;

    private JMenuItem itemNuevo;
    private JMenuItem itemVerHistorial;
    private JMenuItem itemLimpiarHistorial;
    private JMenuItem itemSalir;

    private JMenuItem itemModoBasico;
    private JMenuItem itemModoCientifico;

    private JMenuItem itemSeno;
    private JMenuItem itemCoseno;
    private JMenuItem itemTangente;
    private JMenuItem itemRaiz;
    private JMenuItem itemLogaritmo;

    private JMenuItem itemAcercaDe;

    private JPanel panelCientifico;
    private JTextField txtAngulo;
    private JButton btnSeno, btnCoseno, btnTangente, btnRaiz, btnLog;

    public VistaCalculadora() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Calculadora con JMenuBar - MVC");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        crearMenuBar();

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelDisplay = new JPanel(new BorderLayout(5, 5));
        panelDisplay.setBorder(BorderFactory.createTitledBorder("Resultado"));

        txtDisplay = new JTextField();
        txtDisplay.setFont(new Font("Digital-7", Font.BOLD, 32));
        txtDisplay.setEditable(false);
        txtDisplay.setHorizontalAlignment(JTextField.RIGHT);
        txtDisplay.setText("0");
        txtDisplay.setBackground(new Color(220, 255, 220));
        panelDisplay.add(txtDisplay, BorderLayout.CENTER);

        JPanel panelOperacion = new JPanel(new GridLayout(4, 2, 10, 10));
        panelOperacion.setBorder(BorderFactory.createTitledBorder("Operación"));

        panelOperacion.add(new JLabel("Operando 1:"));
        txtOperando1 = new JTextField();
        txtOperando1.setFont(new Font("Arial", Font.PLAIN, 16));
        panelOperacion.add(txtOperando1);

        panelOperacion.add(new JLabel("Operación:"));
        cmbOperacion = new JComboBox<>(new String[]{"+", "-", "×", "÷", "^"});
        cmbOperacion.setFont(new Font("Arial", Font.BOLD, 16));
        panelOperacion.add(cmbOperacion);

        panelOperacion.add(new JLabel("Operando 2:"));
        txtOperando2 = new JTextField();
        txtOperando2.setFont(new Font("Arial", Font.PLAIN, 16));
        panelOperacion.add(txtOperando2);

        btnCalcular = new JButton("=  Calcular");
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 14));
        btnCalcular.setBackground(new Color(76, 175, 80));
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFocusPainted(false);

        btnLimpiar = new JButton("C  Limpiar");
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 14));
        btnLimpiar.setBackground(new Color(244, 67, 54));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFocusPainted(false);

        panelOperacion.add(btnCalcular);
        panelOperacion.add(btnLimpiar);

        panelCientifico = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCientifico.setBorder(BorderFactory.createTitledBorder("Funciones Científicas"));
        panelCientifico.setVisible(false);

        panelCientifico.add(new JLabel("Valor/Ángulo:"));
        txtAngulo = new JTextField();
        txtAngulo.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCientifico.add(txtAngulo);

        btnSeno = new JButton("sin");
        btnCoseno = new JButton("cos");
        btnTangente = new JButton("tan");
        btnRaiz = new JButton("√");
        btnLog = new JButton("log");

        estilizarBotonCientifico(btnSeno);
        estilizarBotonCientifico(btnCoseno);
        estilizarBotonCientifico(btnTangente);
        estilizarBotonCientifico(btnRaiz);
        estilizarBotonCientifico(btnLog);

        panelCientifico.add(btnSeno);
        panelCientifico.add(btnCoseno);
        panelCientifico.add(btnTangente);
        panelCientifico.add(btnRaiz);
        panelCientifico.add(btnLog);
        panelCientifico.add(new JLabel(""));

        JPanel panelHistorial = new JPanel(new BorderLayout());
        panelHistorial.setBorder(BorderFactory.createTitledBorder("Historial"));

        txtHistorial = new JTextArea(8, 30);
        txtHistorial.setEditable(false);
        txtHistorial.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollHistorial = new JScrollPane(txtHistorial);
        panelHistorial.add(scrollHistorial, BorderLayout.CENTER);

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.add(panelDisplay, BorderLayout.NORTH);
        panelSuperior.add(panelOperacion, BorderLayout.CENTER);
        panelSuperior.add(panelCientifico, BorderLayout.SOUTH);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelHistorial, BorderLayout.CENTER);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private void crearMenuBar() {
        menuBar = new JMenuBar();

        menuArchivo = new JMenu("Archivo");
        menuArchivo.setMnemonic('A');

        itemNuevo = new JMenuItem("Nuevo Cálculo");
        itemNuevo.setAccelerator(KeyStroke.getKeyStroke("control N"));

        itemVerHistorial = new JMenuItem("Ver Historial");
        itemVerHistorial.setAccelerator(KeyStroke.getKeyStroke("control H"));

        itemLimpiarHistorial = new JMenuItem("Limpiar Historial");

        itemSalir = new JMenuItem("Salir");
        itemSalir.setAccelerator(KeyStroke.getKeyStroke("control Q"));

        menuArchivo.add(itemNuevo);
        menuArchivo.addSeparator();
        menuArchivo.add(itemVerHistorial);
        menuArchivo.add(itemLimpiarHistorial);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        menuCalculadora = new JMenu("Calculadora");
        menuCalculadora.setMnemonic('C');

        itemModoBasico = new JMenuItem("Modo Básico");
        itemModoCientifico = new JMenuItem("Modo Científico");

        menuCalculadora.add(itemModoBasico);
        menuCalculadora.add(itemModoCientifico);

        menuCientifico = new JMenu("Científico");
        menuCientifico.setMnemonic('i');
        menuCientifico.setEnabled(false);

        itemSeno = new JMenuItem("Seno");
        itemCoseno = new JMenuItem("Coseno");
        itemTangente = new JMenuItem("Tangente");
        itemRaiz = new JMenuItem("Raíz Cuadrada");
        itemLogaritmo = new JMenuItem("Logaritmo");

        menuCientifico.add(itemSeno);
        menuCientifico.add(itemCoseno);
        menuCientifico.add(itemTangente);
        menuCientifico.addSeparator();
        menuCientifico.add(itemRaiz);
        menuCientifico.add(itemLogaritmo);

        menuAyuda = new JMenu("Ayuda");
        menuAyuda.setMnemonic('y');

        itemAcercaDe = new JMenuItem("Acerca de");
        menuAyuda.add(itemAcercaDe);

        menuBar.add(menuArchivo);
        menuBar.add(menuCalculadora);
        menuBar.add(menuCientifico);
        menuBar.add(menuAyuda);

        setJMenuBar(menuBar);
    }

    private void estilizarBotonCientifico(JButton btn) {
        btn.setBackground(new Color(33, 150, 243));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
    }

    public double getOperando1() throws NumberFormatException {
        return Double.parseDouble(txtOperando1.getText());
    }

    public double getOperando2() throws NumberFormatException {
        return Double.parseDouble(txtOperando2.getText());
    }

    public String getOperacion() {
        return (String) cmbOperacion.getSelectedItem();
    }

    public double getAngulo() throws NumberFormatException {
        return Double.parseDouble(txtAngulo.getText());
    }

    public void setResultado(double resultado) {
        txtDisplay.setText(String.format("%.4f", resultado));
    }

    public void mostrarHistorial(java.util.List<String> historial) {
        StringBuilder sb = new StringBuilder();
        for (String entrada : historial) {
            sb.append(entrada).append("\n");
        }
        txtHistorial.setText(sb.toString());
    }

    public void limpiar() {
        txtOperando1.setText("");
        txtOperando2.setText("");
        txtAngulo.setText("");
        txtDisplay.setText("0");
    }

    public void limpiarHistorial() {
        txtHistorial.setText("");
    }

    public void mostrarModoCientifico(boolean mostrar) {
        panelCientifico.setVisible(mostrar);
        menuCientifico.setEnabled(mostrar);
        pack();
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarAcercaDe() {
        JOptionPane.showMessageDialog(this,
            "Calculadora con JMenuBar\n" +
            "Versión 1.0\n" +
            "Desarrollada con Java Swing\n" +
            "Patrón MVC",
            "Acerca de",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public JButton getBtnCalcular() { return btnCalcular; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnSeno() { return btnSeno; }
    public JButton getBtnCoseno() { return btnCoseno; }
    public JButton getBtnTangente() { return btnTangente; }
    public JButton getBtnRaiz() { return btnRaiz; }
    public JButton getBtnLog() { return btnLog; }

    public JMenuItem getItemNuevo() { return itemNuevo; }
    public JMenuItem getItemVerHistorial() { return itemVerHistorial; }
    public JMenuItem getItemLimpiarHistorial() { return itemLimpiarHistorial; }
    public JMenuItem getItemSalir() { return itemSalir; }
    public JMenuItem getItemModoBasico() { return itemModoBasico; }
    public JMenuItem getItemModoCientifico() { return itemModoCientifico; }
    public JMenuItem getItemSeno() { return itemSeno; }
    public JMenuItem getItemCoseno() { return itemCoseno; }
    public JMenuItem getItemTangente() { return itemTangente; }
    public JMenuItem getItemRaiz() { return itemRaiz; }
    public JMenuItem getItemLogaritmo() { return itemLogaritmo; }
    public JMenuItem getItemAcercaDe() { return itemAcercaDe; }
}
