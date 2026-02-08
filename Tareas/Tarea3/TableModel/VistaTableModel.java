package ejemplos.tablemodel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VistaTableModel extends JFrame {
    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JTextField txtBuscar;
    private JLabel lblEstadisticas;

    public VistaTableModel() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Grilla/TableModel - MVC");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSuperior.setBackground(new Color(63, 81, 181));

        JLabel lblTitulo = new JLabel("📊 SISTEMA DE GESTIÓN DE ESTUDIANTES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusqueda.setOpaque(false);
        JLabel lblBuscar = new JLabel("🔍 Buscar:");
        lblBuscar.setForeground(Color.WHITE);
        txtBuscar = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        btnActualizar = new JButton("🔄 Actualizar");

        estilizarBotonBusqueda(btnBuscar);
        estilizarBotonBusqueda(btnActualizar);

        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnActualizar);

        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(panelBusqueda, BorderLayout.EAST);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        String[] columnas = {"ID", "Nombre", "Apellido", "Edad", "Carrera", "Promedio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEstudiantes = new JTable(modeloTabla);
        tablaEstudiantes.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaEstudiantes.setRowHeight(25);
        tablaEstudiantes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaEstudiantes.getTableHeader().setBackground(new Color(63, 81, 181));
        tablaEstudiantes.getTableHeader().setForeground(Color.WHITE);
        tablaEstudiantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaEstudiantes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaEstudiantes.getColumnModel().getColumn(1).setPreferredWidth(120);
        tablaEstudiantes.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablaEstudiantes.getColumnModel().getColumn(3).setPreferredWidth(60);
        tablaEstudiantes.getColumnModel().getColumn(4).setPreferredWidth(200);
        tablaEstudiantes.getColumnModel().getColumn(5).setPreferredWidth(80);

        JScrollPane scrollTabla = new JScrollPane(tablaEstudiantes);
        panelCentral.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnAgregar = crearBoton("➕ Agregar", new Color(76, 175, 80));
        btnModificar = crearBoton("✏️ Modificar", new Color(33, 150, 243));
        btnEliminar = crearBoton("🗑️ Eliminar", new Color(244, 67, 54));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);

        lblEstadisticas = new JLabel("Total de estudiantes: 0 | Promedio general: 0.0");
        lblEstadisticas.setFont(new Font("Arial", Font.BOLD, 12));
        lblEstadisticas.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        panelInferior.add(panelBotones, BorderLayout.CENTER);
        panelInferior.add(lblEstadisticas, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(150, 35));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void estilizarBotonBusqueda(JButton boton) {
        boton.setBackground(Color.WHITE);
        boton.setForeground(new Color(63, 81, 181));
        boton.setFont(new Font("Arial", Font.BOLD, 11));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void cargarEstudiantes(List<Estudiante> estudiantes) {
        modeloTabla.setRowCount(0);

        for (Estudiante est : estudiantes) {
            Object[] fila = {
                est.getId(),
                est.getNombre(),
                est.getApellido(),
                est.getEdad(),
                est.getCarrera(),
                String.format("%.2f", est.getPromedio())
            };
            modeloTabla.addRow(fila);
        }
    }

    public void actualizarEstadisticas(int total, double promedio) {
        lblEstadisticas.setText(String.format(
            "Total de estudiantes: %d | Promedio general: %.2f", total, promedio));
    }

    public Estudiante mostrarDialogoAgregar(String[] carreras) {
        return mostrarDialogoEstudiante("Agregar Estudiante", null, carreras);
    }

    public Estudiante mostrarDialogoModificar(Estudiante estudiante, String[] carreras) {
        return mostrarDialogoEstudiante("Modificar Estudiante", estudiante, carreras);
    }

    private Estudiante mostrarDialogoEstudiante(String titulo, Estudiante estudiante, String[] carreras) {
        JTextField txtNombre = new JTextField(15);
        JTextField txtApellido = new JTextField(15);
        JTextField txtEdad = new JTextField(15);
        JComboBox<String> cmbCarrera = new JComboBox<>(carreras);
        JTextField txtPromedio = new JTextField(15);

        if (estudiante != null) {
            txtNombre.setText(estudiante.getNombre());
            txtApellido.setText(estudiante.getApellido());
            txtEdad.setText(String.valueOf(estudiante.getEdad()));
            cmbCarrera.setSelectedItem(estudiante.getCarrera());
            txtPromedio.setText(String.valueOf(estudiante.getPromedio()));
        }

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("Edad:"));
        panel.add(txtEdad);
        panel.add(new JLabel("Carrera:"));
        panel.add(cmbCarrera);
        panel.add(new JLabel("Promedio:"));
        panel.add(txtPromedio);

        int resultado = JOptionPane.showConfirmDialog(this, panel, titulo,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                int edad = Integer.parseInt(txtEdad.getText().trim());
                String carrera = (String) cmbCarrera.getSelectedItem();
                double promedio = Double.parseDouble(txtPromedio.getText().trim());

                if (nombre.isEmpty() || apellido.isEmpty()) {
                    mostrarError("El nombre y apellido no pueden estar vacíos");
                    return null;
                }

                if (edad < 15 || edad > 100) {
                    mostrarError("La edad debe estar entre 15 y 100 años");
                    return null;
                }

                if (promedio < 0 || promedio > 10) {
                    mostrarError("El promedio debe estar entre 0 y 10");
                    return null;
                }

                return new Estudiante(0, nombre, apellido, edad, carrera, promedio);

            } catch (NumberFormatException ex) {
                mostrarError("Por favor, ingresa valores numéricos válidos");
                return null;
            }
        }

        return null;
    }

    public int getFilaSeleccionada() {
        return tablaEstudiantes.getSelectedRow();
    }

    public String getTextoBusqueda() {
        return txtBuscar.getText().trim();
    }

    public void limpiarBusqueda() {
        txtBuscar.setText("");
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean confirmarEliminacion(String nombreEstudiante) {
        int respuesta = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de eliminar al estudiante " + nombreEstudiante + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        return respuesta == JOptionPane.YES_OPTION;
    }

    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnModificar() { return btnModificar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnActualizar() { return btnActualizar; }
}
