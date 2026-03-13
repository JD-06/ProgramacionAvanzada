package com.pawllu.datos;

import com.pawllu.acceso.Acceso;
import com.pawllu.datos.interfaces.metodosDao;
import com.pawllu.entidades.Cliente;
import com.pawllu.entidades.DireccionCliente;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class DireccionClienteDAO implements metodosDao<DireccionCliente> {

    private final List<DireccionCliente> lista;
    Metodo<DireccionCliente> metodos;
    private final String ruta = "direccionCliente.txt";
    private boolean resp;
    private final ClienteDAO DATOS;
    private DireccionCliente direccion;

    public DireccionClienteDAO() {
        lista = new ArrayList<>();
        metodos = new Metodo<>(lista);
        DATOS = new ClienteDAO();
        cargarLista();
    }

    private void cargarLista() {
        DireccionCliente direc;
        Cliente cliente;
        long rut;
        for (String dato : Acceso.cargarArchivo(ruta)) {
            direc = new DireccionCliente();
            StringTokenizer st = new StringTokenizer((String) dato, ",");
            rut = Long.parseLong(st.nextToken());
            direc.setCalle(st.nextToken());
            direc.setNumero(st.nextToken());//numero direccion
            direc.setCiudad(st.nextToken());
            direc.setComuna(st.nextToken());

            cliente = DATOS.getObjeto(rut);
            direc.setClienteId(cliente);

            metodos.agregarRegistro(direc);
        }

    }

    @Override
    public int buscaCodigo(long codigo) {
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            if (codigo == metodos.obtenerRegistro(i).getClienteId().getRut()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public List<DireccionCliente> listar() {
        List<DireccionCliente> registros = new ArrayList<>();
        DireccionCliente direc;
        Cliente cliente;
        long rut;
        try {
            for (String dato : Acceso.cargarArchivo(ruta)) {
                direc = new DireccionCliente();
                StringTokenizer st = new StringTokenizer((String) dato, ",");
                rut = Long.parseLong(st.nextToken());
                direc.setCalle(st.nextToken());
                direc.setNumero(st.nextToken());
                direc.setCiudad(st.nextToken());
                direc.setComuna(st.nextToken());

                cliente = DATOS.getObjeto(rut);
                direc.setClienteId(cliente);
                registros.add(direc);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al listar DireccionCliente: " + e.getMessage());
        } finally {

        }
        return registros;
    }

    @Override
    public boolean insertar(DireccionCliente obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            obj = new DireccionCliente(obj.getClienteId(), obj.getCalle(), obj.getNumero(), obj.getCiudad(), obj.getComuna());
            metodos.agregarRegistro(obj);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                direccion = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(direccion.getClienteId().getRut() + "," + direccion.getCalle() + "," + direccion.getNumero() + "," + direccion.getCiudad() + "," + direccion.getComuna()));
            }
            pw.close();
            resp = true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar DireccionCliente: " + ex.getMessage());
        }
        return resp;
    }

    @Override
    public boolean actualizar(DireccionCliente obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            obj = new DireccionCliente(obj.getClienteId(), obj.getCalle(), obj.getNumero(), obj.getCiudad(), obj.getComuna());
            int codigo = buscaCodigo(obj.getClienteId().getRut());
            if (codigo == -1) {
                metodos.agregarRegistro(obj);
            } else {
                metodos.modificarRegistro(codigo, obj);
            }
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);

            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                direccion = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(direccion.getClienteId().getRut() + "," + direccion.getCalle() + "," + direccion.getNumero() + "," + direccion.getCiudad() + "," + direccion.getComuna()));
            }
            pw.close();
            resp = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return resp;
    }

    @Override
    public DireccionCliente getObjeto(long codigo) {
        return null;
    }

}
