package com.pawllu.negocio;

import com.pawllu.datos.CategoriaDAO;
import com.pawllu.datos.ProductoDAO;
import com.pawllu.datos.ProveedorDAO;
import com.pawllu.entidades.Categoria;
import com.pawllu.entidades.Producto;
import com.pawllu.entidades.Proveedor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class ProductoControl {

    private final ProductoDAO DATOS;
    private final CategoriaDAO DATOS_CAT;
    private final ProveedorDAO DATOS_PROV;
    private final Producto obj;
    private DefaultTableModel modeloTabla;

    public ProductoControl() {
        DATOS = new ProductoDAO();
        DATOS_CAT = new CategoriaDAO();
        DATOS_PROV = new ProveedorDAO();
        obj = new Producto();
    }

    public DefaultTableModel listar() {
        String[] titulos = {"ID", "Nombre", "Precio", "Stock", "Categoria ID", "Categoria", "Proveedor ID", "Proveedor"};//columnas
        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[8];
        List<Producto> lista = DATOS.listar();
        for (Producto item : lista) {//recorrer la lista
            registro[0] = Long.toString(item.getId());
            registro[1] = item.getNombre();
            registro[2] = Double.toString(item.getPrecio());
            registro[3] = Integer.toString(item.getStock());
            registro[4] = Long.toString(item.getCategoriaId().getId());
            registro[5] = item.getCategoriaId().getNombre();
            registro[6] = Long.toString(item.getProveedorId().getRut());
            registro[7] = item.getProveedorId().getNombre();
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public String insertar(Long id, String nombre, double precio, int stock, long idCategoria, Long idProveedor) {
        if (DATOS.buscaCodigo(id) != -1) {
            return "El codigo ingresado ya existe";

        } else if (DATOS.buscaNombre(nombre) != -1) {
            return "El nombre ya existe";
        } else {
            obj.setId(id);
            obj.setNombre(nombre);
            obj.setPrecio(precio);
            obj.setStock(stock);
            obj.setCategoriaId(new Categoria(idCategoria));
            obj.setProveedorId(new Proveedor(idProveedor));

            if (DATOS.insertar(obj)) {
                return "OK";
            } else {
                return "Error en el registro";
            }
        }

    }

    public String actualizar(Long id, String nombre, String nombreAnterior, double precio, int stock, long idCategoria, Long idProveedor) {

        if (nombre.equals(nombreAnterior)) {
            obj.setId(id);
            obj.setNombre(nombre);
            obj.setPrecio(precio);
            obj.setStock(stock);
            obj.setCategoriaId(new Categoria(idCategoria));
            obj.setProveedorId(new Proveedor(idProveedor));

            if (DATOS.actualizar(obj)) {
                return "OK";
            } else {
                return "Error en la actualizacion";
            }
        } else {
            if (DATOS.buscaNombre(nombre) != -1) {
                return "El nombre ya existe";
            } else {
                obj.setId(id);
                obj.setNombre(nombre);
                obj.setPrecio(precio);
                obj.setStock(stock);
                obj.setCategoriaId(new Categoria(idCategoria));
                obj.setProveedorId(new Proveedor(idProveedor));

                if (DATOS.actualizar(obj)) {
                    return "OK";
                } else {
                    return "Error en la actualizacion";
                }
            }
        }
    }

    public DefaultComboBoxModel seleccionarCategoria() {
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Categoria> lista = new ArrayList<>();
        lista = DATOS_CAT.seleccionar();
        for (Categoria item : lista) {
            items.addElement(new Categoria(item.getId(), item.getNombre()));
        }
        return items;
    }

    public DefaultComboBoxModel seleccionarProveedor() {
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Proveedor> lista = new ArrayList<>();
        lista = DATOS_PROV.seleccionar();
        for (Proveedor item : lista) {
            items.addElement(new Proveedor(item.getRut(), item.getNombre()));
        }
        return items;
    }

}
