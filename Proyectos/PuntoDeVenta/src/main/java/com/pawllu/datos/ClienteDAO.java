package com.pawllu.datos;

import com.pawllu.acceso.Acceso;
import com.pawllu.datos.interfaces.metodosDao;
import com.pawllu.entidades.Cliente;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class ClienteDAO implements metodosDao<Cliente> {

    private final List<Cliente> lista;
    Metodo<Cliente> metodos;
    private final String ruta = "cliente.txt";
    private boolean resp;
    private Cliente cliente;

    public ClienteDAO() {
        lista = new ArrayList<>();
        metodos = new Metodo<>(lista);
        cargarLista();

    }

    private void cargarLista() {
        Cliente cli;
        for (String dato : Acceso.cargarArchivo(ruta)) {
            StringTokenizer st = new StringTokenizer((String) dato, ",");
            cli = new Cliente(Long.parseLong(st.nextToken()), st.nextToken());
            metodos.agregarRegistro(cli);
        }

    }

    public int buscaNombre(String nombre) {
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            if (metodos.obtenerRegistro(i).getNombre().equalsIgnoreCase(nombre)) {
                return i;
            }
        }
        return -1;
    }

    public List seleccionar() {//Lo usamos en Direccion y Telefono del Cliente
        List<Cliente> registros = new ArrayList<>();
        try {
            for (String dato : Acceso.cargarArchivo(ruta)) {
                StringTokenizer st = new StringTokenizer((String) dato, ",");
                registros.add(new Cliente(Long.parseLong(st.nextToken()), st.nextToken()));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar Cliente: " + e.getMessage());
        } finally {

        }
        return registros;
    }

    @Override
    public Cliente getObjeto(long codigo) {
        Cliente cliente = null;
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            cliente = metodos.obtenerRegistro(i);
            if (cliente.getRut() == codigo) {
                cliente = new Cliente(cliente.getRut(), cliente.getNombre());
                return cliente;
            }
        }
        return cliente;
    }

    @Override
    public int buscaCodigo(long codigo) {
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            if (codigo == metodos.obtenerRegistro(i).getRut()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> registros = new ArrayList<>();
        try {
            Acceso.cargarArchivo(ruta).stream().map(dato -> new StringTokenizer((String) dato, ",")).forEachOrdered(st -> {
                registros.add(new Cliente(Long.parseLong(st.nextToken()), st.nextToken()));
            });
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al listar Cliente: " + e.getMessage());
        } finally {

        }
        return registros;
    }

    @Override
    public boolean insertar(Cliente obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/"+ruta);
            pw = new PrintWriter(fw);
            obj = new Cliente(obj.getRut(), obj.getNombre());
            metodos.agregarRegistro(obj);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                cliente = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(cliente.getRut() + "," + cliente.getNombre()));
            }
            pw.close();
            resp = true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar Cliente: " + ex.getMessage());
        }
        return resp;
    }
//

    @Override
    public boolean actualizar(Cliente obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            obj = new Cliente(obj.getRut(), obj.getNombre());
            int codigo = buscaCodigo(obj.getRut());
            if (codigo == -1) {
                metodos.agregarRegistro(obj);
            } else {
                metodos.modificarRegistro(codigo, obj);
            }
            fw = new FileWriter("Archivos/"+ruta);
            pw = new PrintWriter(fw);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                cliente = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(cliente.getRut() + "," + cliente.getNombre()));
            }
            pw.close();
            resp = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return resp;
    }

}
