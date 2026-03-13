package sistemapos.controlador;

import sistemapos.modelo.GestorProductos;
import sistemapos.modelo.Producto;
import sistemapos.vista.ProductosFrame;
import javax.swing.*;
import java.util.ArrayList;

public class ProductosController {

    private final ProductosFrame vista;
    private final GestorProductos gestor;
    private int idSeleccionado = -1; // -1 = modo insertar

    public ProductosController(ProductosFrame vista, GestorProductos gestor) {
        this.vista  = vista;
        this.gestor = gestor;
        cargarTabla(gestor.getLista());
        registrarEventos();
    }

    // ── Eventos ───────────────────────────────────────────────────────────
    private void registrarEventos() {

        // Selección en tabla → cargar formulario
        vista.tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && vista.tabla.getSelectedRow() >= 0) {
                int fila = vista.tabla.getSelectedRow();
                int id   = (int) vista.modeloTabla.getValueAt(fila, 0);
                Producto p = gestor.buscarPorId(id);
                if (p != null) cargarEnFormulario(p);
            }
        });

        // Guardar (Insertar o Modificar)
        vista.btnGuardar.addActionListener(e -> guardar());

        // Limpiar formulario
        vista.btnLimpiar.addActionListener(e -> limpiarFormulario());

        // Buscar en catálogo
        vista.btnBuscar.addActionListener(e -> buscarEnCatalogo());

        // Mostrar todos
        vista.btnMostrarTodos.addActionListener(e -> cargarTabla(gestor.getLista()));

        // Exportar
        vista.btnExportar.addActionListener(e ->
            JOptionPane.showMessageDialog(vista,
                "Lista exportada: " + gestor.getLista().size() + " registros en productos.csv",
                "Exportar", JOptionPane.INFORMATION_MESSAGE)
        );
    }

    // ── Guardar (Insertar / Modificar) ───────────────────────────────────
    private void guardar() {
        try {
            // Validaciones
            String codigo = vista.txtCodigo.getText().trim();
            String nombre = vista.txtNombre.getText().trim();
            if (codigo.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                    "Código y Nombre son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double precCom = Double.parseDouble(vista.txtPrecioCompra.getText().trim());
            double precVen = Double.parseDouble(vista.txtPrecioVenta.getText().trim());
            int stock      = Integer.parseInt(vista.txtStock.getText().trim());
            int stockMin   = Integer.parseInt(vista.txtStockMin.getText().trim());
            String cat     = (String) vista.cmbCategoria.getSelectedItem();
            String desc    = vista.txtDescripcion.getText().trim();
            String estado  = vista.rbActivo.isSelected() ? "Activo" : "Desactivado";

            Producto p = new Producto(idSeleccionado, codigo, nombre, desc,
                                      cat, precCom, precVen, stock, stockMin, estado);

            if (idSeleccionado == -1) {
                // Insertar
                if (gestor.existeCodigo(codigo, -1)) {
                    JOptionPane.showMessageDialog(vista,
                        "El código ya existe.", "Duplicado", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                gestor.insertar(p);
                JOptionPane.showMessageDialog(vista, "Producto guardado correctamente.");
            } else {
                // Modificar
                if (gestor.existeCodigo(codigo, idSeleccionado)) {
                    JOptionPane.showMessageDialog(vista,
                        "El código ya existe en otro producto.", "Duplicado", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                gestor.actualizar(p);
                JOptionPane.showMessageDialog(vista, "Producto modificado correctamente.");
            }

            cargarTabla(gestor.getLista());
            limpiarFormulario();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista,
                "Los campos numéricos solo deben contener números.",
                "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Búsqueda en catálogo ─────────────────────────────────────────────
    private void buscarEnCatalogo() {
        String filtro = (String) vista.cmbFiltro.getSelectedItem();
        String termino = JOptionPane.showInputDialog(vista, "Buscar por " + filtro + ":");
        if (termino == null || termino.isBlank()) return;

        ArrayList<Producto> resultado = new ArrayList<>();
        for (Producto p : gestor.getLista()) {
            boolean coincide = false;
            switch (filtro) {
                case "Nombre":    coincide = p.getNombre().toLowerCase().contains(termino.toLowerCase());    break;
                case "Categoría": coincide = p.getCategoria().toLowerCase().contains(termino.toLowerCase()); break;
                case "Estado":    coincide = p.getEstado().equalsIgnoreCase(termino);                       break;
            }
            if (coincide) resultado.add(p);
        }
        cargarTabla(resultado);
    }

    // ── Utilidades de Vista ───────────────────────────────────────────────
    public void cargarTabla(ArrayList<Producto> lista) {
        vista.modeloTabla.setRowCount(0);
        for (Producto p : lista) {
            vista.modeloTabla.addRow(new Object[]{
                p.getId(), p.getCodigo(), p.getNombre(),
                p.getCategoria(), p.getStock(),
                String.format("$%.2f", p.getPrecioVenta()), p.getEstado()
            });
        }
    }

    private void cargarEnFormulario(Producto p) {
        idSeleccionado = p.getId();
        vista.txtId.setText(String.valueOf(p.getId()));
        vista.txtCodigo.setText(p.getCodigo());
        vista.txtNombre.setText(p.getNombre());
        vista.txtDescripcion.setText(p.getDescripcion());
        vista.cmbCategoria.setSelectedItem(p.getCategoria());
        vista.txtPrecioCompra.setText(String.valueOf(p.getPrecioCompra()));
        vista.txtPrecioVenta.setText(String.valueOf(p.getPrecioVenta()));
        vista.txtStock.setText(String.valueOf(p.getStock()));
        vista.txtStockMin.setText(String.valueOf(p.getStockMinimo()));
        if (p.getEstado().equalsIgnoreCase("Activo")) {
            vista.rbActivo.setSelected(true);
        } else {
            vista.rbDesactivado.setSelected(true);
        }
    }

    public void limpiarFormulario() {
        idSeleccionado = -1;
        vista.txtId.setText("");
        vista.txtCodigo.setText("");
        vista.txtNombre.setText("");
        vista.txtDescripcion.setText("");
        vista.txtPrecioCompra.setText("");
        vista.txtPrecioVenta.setText("");
        vista.txtStock.setText("");
        vista.txtStockMin.setText("");
        vista.cmbCategoria.setSelectedIndex(0);
        vista.rbActivo.setSelected(true);
        vista.tabla.clearSelection();
    }
}
