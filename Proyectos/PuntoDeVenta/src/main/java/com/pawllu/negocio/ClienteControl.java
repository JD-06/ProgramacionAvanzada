package com.pawllu.negocio;

import com.pawllu.datos.ClienteDAO;
import com.pawllu.entidades.Cliente;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ClienteControl {

    private final ClienteDAO DATOS;
    private final Cliente obj;
    private DefaultTableModel modeloTabla;

    public ClienteControl() {
        DATOS = new ClienteDAO();
        obj = new Cliente();
    }

    public DefaultTableModel listar() {
        String[] titulos = {"ID", "Nombre"};//columnas
        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[2];
        List<Cliente> lista = DATOS.listar();
        for (Cliente item : lista) {//recorrer la lista
            registro[0] = Long.toString(item.getRut());
            registro[1] = item.getNombre();
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public String insertar(Long rut, String nombre) {
        if (DATOS.buscaCodigo(rut) != -1) {
            return "El codigo ingresado ya existe";

        } else if (DATOS.buscaNombre(nombre) != -1) {
            return "El nombre ya existe";
        } else {
            obj.setRut(rut);
            obj.setNombre(nombre);

            if (DATOS.insertar(obj)) {
                return "OK";
            } else {
                return "Error en el registro";
            }
        }

    }

    public String actualizar(Long rut, String nombre, String nombreAnterior) {

        if (nombre.equals(nombreAnterior)) {
            obj.setRut(rut);
            obj.setNombre(nombre);

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

                if (DATOS.actualizar(obj)) {
                    return "OK";
                } else {
                    return "Error en la actualizacion";
                }
            }
        }
    }

}
