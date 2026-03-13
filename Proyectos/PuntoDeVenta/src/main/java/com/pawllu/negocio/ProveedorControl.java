package com.pawllu.negocio;

import com.pawllu.datos.ProveedorDAO;
import com.pawllu.entidades.Proveedor;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ProveedorControl {

    private final ProveedorDAO DATOS;
    private final Proveedor obj;
    private DefaultTableModel modeloTabla;

    public ProveedorControl() {
        DATOS = new ProveedorDAO();
        obj = new Proveedor();
    }

    public DefaultTableModel listar() {
        String[] titulos = {"ID", "Nombre", "Telefono", "Pagina Web"};//columnas
        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[4];
        List<Proveedor> lista = DATOS.listar();
        for (Proveedor item : lista) {//recorrer la lista
            registro[0] = Long.toString(item.getRut());
            registro[1] = item.getNombre();
            registro[2] = item.getTelefono();
            registro[3] = item.getPagina_web();
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public String insertar(Long rut, String nombre, String telefono, String paginaWeb) {
        if (DATOS.buscaCodigo(rut) != -1) {
            return "Ya existe un proveedor con ese RUT";
        } else if (DATOS.buscaNombre(nombre) != -1) {//no sera -1 entonces existe
            return "Ya existe un proveedor con ese nombre";
        } else {
            obj.setRut(rut);
            obj.setNombre(nombre);
            obj.setTelefono(telefono);
            obj.setPagina_web(paginaWeb);

            if (DATOS.insertar(obj)) {
                return "OK";
            } else {
                return "Error en el registro";
            }
        }
    }

    public String actualizar(Long rut, String nombre, String nombreAnterior, String telefono, String paginaWeb) {
        if (nombre.equals(nombreAnterior)) {
            obj.setRut(rut);
            obj.setNombre(nombre);
            obj.setTelefono(telefono);
            obj.setPagina_web(paginaWeb);

            if (DATOS.actualizar(obj)) {
                return "OK";
            } else {
                return "Error en la actualizacion";
            }
        } else {
            if (DATOS.buscaNombre(nombre) != -1) {
                return "El nombre ya existe";
            } else {
                obj.setRut(rut);
                obj.setNombre(nombre);
                obj.setTelefono(telefono);
                obj.setPagina_web(paginaWeb);

                if (DATOS.actualizar(obj)) {
                    return "OK";
                } else {
                    return "Error en la actualizacion";
                }
            }
        }
    }

}
