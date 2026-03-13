package com.pawllu.negocio;

import com.pawllu.datos.ClienteDAO;
import com.pawllu.datos.DireccionClienteDAO;
import com.pawllu.entidades.Cliente;
import com.pawllu.entidades.DireccionCliente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class DireccionClienteControl {

    private final DireccionClienteDAO DATOS;
    private final ClienteDAO DATOSCLI;
    private final DireccionCliente obj;
    private DefaultTableModel modeloTabla;

    public DireccionClienteControl() {
        DATOS = new DireccionClienteDAO();
        DATOSCLI = new ClienteDAO();
        obj = new DireccionCliente();
    }

    public DefaultTableModel listar() {
        String[] titulos = {"RUT","Cliente", "Calle", "Numero", "Ciudad", "Comuna"};//columnas
        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[6];
        List<DireccionCliente> lista = DATOS.listar();
        for (DireccionCliente item : lista) {//recorrer la lista
            registro[0] = Long.toString(item.getClienteId().getRut());
            registro[1] = item.getClienteId().getNombre();
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
            return "Este cliente ya tiene una dirección: máximo 1 dirección por cliente";

        } else {
            obj.setClienteId(new Cliente(rut));
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

    public String actualizar(Long rut, String calle, String numero, String ciudad, String comuna) {

        if (DATOS.buscaCodigo(rut) != -1) {
            obj.setClienteId(new Cliente(rut));
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
            return "Cliente incorrecto";
        }

    }

    public DefaultComboBoxModel seleccionar() {
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Cliente> lista = new ArrayList<>();
        lista = DATOSCLI.seleccionar();
        for (Cliente item : lista) {
            items.addElement(new Cliente(item.getRut(), item.getNombre()));
        }
        return items;
    }

}
