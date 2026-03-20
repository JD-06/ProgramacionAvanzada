package sistemapos.controlador;

import sistemapos.exportacion.ExportadorExcel;
import sistemapos.exportacion.ExportadorJSON;
import sistemapos.modelo.GestorProductos;
import sistemapos.modelo.Producto;
import sistemapos.vista.ProductosFrame;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProductosController {

    private final ProductosFrame  vista;
    private final GestorProductos gestor;
    private int idSeleccionado = -1;

    public ProductosController(ProductosFrame vista, GestorProductos gestor) {
        this.vista  = vista;
        this.gestor = gestor;
        cargarTabla(gestor.getLista());
        registrarEventos();
    }

    public void recargarDatos() {
        cargarTabla(gestor.getLista());
        limpiarFormulario();
    }

    private void registrarEventos() {
        // Seleccion en tabla → cargar formulario
        vista.tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && vista.tabla.getSelectedRow() >= 0) {
                int fila = vista.tabla.getSelectedRow();
                int id   = (int) vista.modeloTabla.getValueAt(fila, 0);
                Producto p = gestor.buscarPorId(id);
                if (p != null) cargarEnFormulario(p);
            }
        });

        vista.btnGuardar.addActionListener(e -> guardar());
        vista.btnLimpiar.addActionListener(e -> limpiarFormulario());
        vista.btnBuscar.addActionListener(e -> buscarEnCatalogo());
        vista.btnMostrarTodos.addActionListener(e -> cargarTabla(gestor.getLista()));

        // Imagen
        vista.btnSeleccionarImagen.addActionListener(e -> seleccionarImagen());

        // Exportar CSV (original)
        vista.btnExportar.addActionListener(e ->
            JOptionPane.showMessageDialog(vista,
                "CSV guardado en: " + gestor.getRutaArchivo() +
                "\nRegistros: " + gestor.getLista().size(),
                "Exportar", JOptionPane.INFORMATION_MESSAGE)
        );

        // Exportar JSON
        vista.btnExportarJSON.addActionListener(e -> {
            String ruta = ExportadorJSON.exportarProductos(gestor.getLista());
            JOptionPane.showMessageDialog(vista,
                "JSON exportado:\n" + ruta,
                "Exportar JSON", JOptionPane.INFORMATION_MESSAGE);
        });

        // Exportar Excel - lista completa
        vista.btnExportarExcel.addActionListener(e -> {
            try {
                String ruta = ExportadorExcel.exportarProductos(gestor.getLista());
                JOptionPane.showMessageDialog(vista,
                    "Excel exportado:\n" + ruta,
                    "Exportar Excel", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(vista,
                    "Error al exportar Excel:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Exportar Excel - por categoria
        vista.btnExportarExcelCat.addActionListener(e -> {
            try {
                String ruta = ExportadorExcel.exportarProductosPorCategoria(gestor.getLista());
                JOptionPane.showMessageDialog(vista,
                    "Excel por categoria exportado:\n" + ruta,
                    "Exportar Excel", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(vista,
                    "Error al exportar Excel:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // =========================================================
    // GUARDAR (insertar o actualizar)
    // =========================================================

    private void guardar() {
        try {
            String codigo = vista.txtCodigo.getText().trim();
            String nombre = vista.txtNombre.getText().trim();
            if (codigo.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                    "Codigo y Nombre son obligatorios.", "Validacion", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double precCom = Double.parseDouble(vista.txtPrecioCompra.getText().trim());
            double precVen = Double.parseDouble(vista.txtPrecioVenta.getText().trim());
            int    stock   = Integer.parseInt(vista.txtStock.getText().trim());
            int    stockMin= Integer.parseInt(vista.txtStockMin.getText().trim());
            String cat     = (String) vista.cmbCategoria.getSelectedItem();
            String tipo    = (String) vista.cmbTipo.getSelectedItem();
            String desc    = vista.txtDescripcion.getText().trim();
            String estado  = vista.rbActivo.isSelected() ? "Activo" : "Desactivado";
            String imagen  = vista.txtImagenRuta.getText().trim();

            // Crear la subclase correcta segun el tipo elegido
            Producto p = Producto.crear(tipo, idSeleccionado, codigo, nombre, desc,
                                        cat, precCom, precVen, stock, stockMin, estado);
            p.setImagenRuta(imagen);

            if (idSeleccionado == -1) {
                if (gestor.existeCodigo(codigo, -1)) {
                    JOptionPane.showMessageDialog(vista, "El codigo ya existe.", "Duplicado", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                gestor.insertar(p);
                JOptionPane.showMessageDialog(vista, "Producto guardado correctamente.");
            } else {
                if (gestor.existeCodigo(codigo, idSeleccionado)) {
                    JOptionPane.showMessageDialog(vista, "El codigo ya existe en otro producto.", "Duplicado", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                gestor.actualizar(p);
                JOptionPane.showMessageDialog(vista, "Producto modificado correctamente.");
            }

            cargarTabla(gestor.getLista());
            limpiarFormulario();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista,
                "Los campos numericos solo deben contener numeros.",
                "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================
    // BUSCAR
    // =========================================================

    private void buscarEnCatalogo() {
        String filtro  = (String) vista.cmbFiltro.getSelectedItem();
        String termino = JOptionPane.showInputDialog(vista, "Buscar por " + filtro + ":");
        if (termino == null || termino.isBlank()) return;

        ArrayList<Producto> resultado = new ArrayList<>();
        for (Producto p : gestor.getLista()) {
            boolean coincide = false;
            switch (filtro) {
                case "Nombre":    coincide = p.getNombre().toLowerCase().contains(termino.toLowerCase());     break;
                case "Categoria": coincide = p.getCategoria().toLowerCase().contains(termino.toLowerCase()); break;
                case "Tipo":      coincide = p.getTipoProducto().toLowerCase().contains(termino.toLowerCase()); break;
                case "Estado":    coincide = p.getEstado().equalsIgnoreCase(termino);                        break;
            }
            if (coincide) resultado.add(p);
        }
        cargarTabla(resultado);
    }

    // =========================================================
    // TABLA
    // =========================================================

    public void cargarTabla(ArrayList<Producto> lista) {
        vista.modeloTabla.setRowCount(0);
        for (Producto p : lista) {
            vista.modeloTabla.addRow(new Object[]{
                p.getId(), p.getCodigo(), p.getNombre(),
                p.getCategoria(), p.getTipoProducto(), p.getStock(),
                String.format("$%.2f", p.getPrecioVenta()), p.getEstado()
            });
        }
    }

    // =========================================================
    // FORMULARIO
    // =========================================================

    private void cargarEnFormulario(Producto p) {
        idSeleccionado = p.getId();
        vista.txtId.setText(String.valueOf(p.getId()));
        vista.txtCodigo.setText(p.getCodigo());
        vista.txtNombre.setText(p.getNombre());
        vista.txtDescripcion.setText(p.getDescripcion());
        vista.cmbCategoria.setSelectedItem(p.getCategoria());
        vista.cmbTipo.setSelectedItem(p.getTipoProducto());
        vista.txtPrecioCompra.setText(String.valueOf(p.getPrecioCompra()));
        vista.txtPrecioVenta.setText(String.valueOf(p.getPrecioVenta()));
        vista.txtStock.setText(String.valueOf(p.getStock()));
        vista.txtStockMin.setText(String.valueOf(p.getStockMinimo()));
        vista.rbActivo.setSelected(p.getEstado().equalsIgnoreCase("Activo"));
        vista.rbDesactivado.setSelected(!p.getEstado().equalsIgnoreCase("Activo"));
        vista.txtImagenRuta.setText(p.getImagenRuta() != null ? p.getImagenRuta() : "");
        actualizarPreview(p.getImagenRuta());
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
        vista.cmbTipo.setSelectedIndex(0);
        vista.rbActivo.setSelected(true);
        vista.txtImagenRuta.setText("");
        vista.lblImagenPreview.setIcon(null);
        vista.lblImagenPreview.setText("Sin imagen");
        vista.tabla.clearSelection();
    }

    // =========================================================
    // IMAGEN
    // =========================================================

    private void seleccionarImagen() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Seleccionar imagen del producto");
        fc.setFileFilter(new FileNameExtensionFilter(
            "Imagenes (jpg, png, gif, bmp)", "jpg", "jpeg", "png", "gif", "bmp"));
        if (fc.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
            String ruta = fc.getSelectedFile().getAbsolutePath();
            vista.txtImagenRuta.setText(ruta);
            actualizarPreview(ruta);
        }
    }

    private void actualizarPreview(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            vista.lblImagenPreview.setIcon(null);
            vista.lblImagenPreview.setText("Sin imagen");
            return;
        }
        try {
            ImageIcon icon = new ImageIcon(ruta);
            if (icon.getIconWidth() <= 0) {
                vista.lblImagenPreview.setText("Imagen no encontrada");
                return;
            }
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            vista.lblImagenPreview.setIcon(new ImageIcon(img));
            vista.lblImagenPreview.setText("");
        } catch (Exception e) {
            vista.lblImagenPreview.setIcon(null);
            vista.lblImagenPreview.setText("Error al cargar");
        }
    }
}
