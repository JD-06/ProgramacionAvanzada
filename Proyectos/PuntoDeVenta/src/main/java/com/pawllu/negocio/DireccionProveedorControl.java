package com.pawllu.negocio;

import com.pawllu.datos.DireccionProveedorDAO;
import com.pawllu.datos.ProveedorDAO;
import com.pawllu.entidades.DireccionProveedor;
import com.pawllu.entidades.Proveedor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class DireccionProveedorControl {

    private final DireccionProveedorDAO DATOS;
    private final ProveedorDAO DATOSPROV;
    private final DireccionProveedor obj;
    private DefaultTableModel modeloTabla;

    public DireccionProveedorControl() {
        DATOS = new DireccionProveedorDAO();
        DATOSPROV = new ProveedorDAO();
        obj = new DireccionProveedor();
    }

    public DefaultTableModel listar() {
        String[] titulos = {"RUT", "Proveedor", "Calle", "Numero", "Ciudad", "Comuna"};//columnas
        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[6];
        List<DireccionProveedor> lista = DATOS.listar();
        for (DireccionProveedor item : lista) {//recorrer la lista
            registro[0] = Long.toString(item.getProveedorRut().getRut());
            registro[1] = item.getProveedorRut().getNombre();
            registro[2] = item.getCalle();
            registro[3] = item.getNumero();
            registro[4] = item.getCiudad();
            registro[5] = item.getComuna();
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public String insertar(Long rut, String calle, String numero, String ciudad, String comuna) {
        if (DATOS.buscaCodigo(rut) != -1) {
            return "Este proveedor ya tiene una dirección: máximo 1 dirección por proveedor";

        } else {
            obj.setProveedorRut(new Proveedor(rut));
            obj.setCalle(calle);
            obj.setNumero(numero);
            obj.setCiudad(ciudad);
            obj.setComuna(comuna);

            if (DATOS.insertar(obj)) {
                return "OK";
            } else {
                return "Error en el registro";
            }
        }
    }

    public String actualizar(Long rut, Long rutAnterior, String calle, String numero, String ciudad, String comuna) {

        if (rut.equals(rutAnterior)) {
            obj.setProveedorRut(new Proveedor(rut));
            obj.setCalle(calle);
            obj.setNumero(numero);
            obj.setCiudad(ciudad);
            obj.setComuna(comuna);

            if (DATOS.actualizar(obj)) {
                return "OK";
            } else {
                return "Error en la actualizacion";
            }
        } else {
            if (DATOS.buscaCodigo(rut) != -1) {
                return "Este proveedor ya esta registrado en la base de datos";
            } else {
                obj.setProveedorRut(new Proveedor(rut));
                obj.setCalle(calle);
                obj.setNumero(numero);
                obj.setCiudad(ciudad);
                obj.setComuna(comuna);

                if (DATOS.actualizar(obj)) {
                    return "OK";
                } else {
                    return "Error en la actualizacion";
                }
            }
        }

    }

    public DefaultComboBoxModel seleccionar() {
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Proveedor> lista = new ArrayList<>();
        lista = DATOSPROV.seleccionar();
        for (Proveedor item : lista) {
            items.addElement(new Proveedor(item.getRut(), item.getNombre()));
        }
        return items;
    }

}
