package com.pawllu.datos;

import com.pawllu.acceso.Acceso;
import com.pawllu.datos.interfaces.metodosDao;
import com.pawllu.entidades.Cliente;
import com.pawllu.entidades.Telefono;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class TelefonoDAO implements metodosDao<Telefono> {

    private final List<Telefono> lista;
    Metodo<Telefono> metodos;
    private final String ruta = "telefonos.txt";
    private boolean resp;
    private final ClienteDAO DATOS;
    private Telefono telefono;

    public TelefonoDAO() {
        lista = new ArrayList<>();
        metodos = new Metodo<>(lista);
        DATOS = new ClienteDAO();
        cargarLista();
    }

    private void cargarLista() {
        Telefono tel;
        Cliente cliente;
        long idCli;
        for (String dato : Acceso.cargarArchivo(ruta)) {
            tel = new Telefono();
            StringTokenizer st = new StringTokenizer((String) dato, ",");
            tel.setId(Long.parseLong(st.nextToken()));//id telefono
            tel.setNumero(st.nextToken());//numero telefono
            idCli = Long.parseLong(st.nextToken());//id cliente

            cliente = DATOS.getObjeto(idCli);
            tel.setClienteId(cliente);

            metodos.agregarRegistro(tel);
        }

    }

    public int buscaTelefono(String numero) {
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            if (metodos.obtenerRegistro(i).getNumero().equalsIgnoreCase(numero)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int buscaCodigo(long codigo) {
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            if (codigo == metodos.obtenerRegistro(i).getId()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public List<Telefono> listar() {
        List<Telefono> registros = new ArrayList<>();
        Telefono tel;
        Cliente cliente;
        long idCli;
        try {
            for (String dato : Acceso.cargarArchivo(ruta)) {
                tel = new Telefono();
                StringTokenizer st = new StringTokenizer((String) dato, ",");
                tel.setId(Long.parseLong(st.nextToken()));//id telefono
                tel.setNumero(st.nextToken());//numero telefono
                idCli = Long.parseLong(st.nextToken());//id cliente

                cliente = DATOS.getObjeto(idCli);
                tel.setClienteId(cliente);
                registros.add(tel);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al listar Telefono: " + e.getMessage());
        }
        return registros;
    }

    @Override
    public boolean insertar(Telefono obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/"+ruta);
            pw = new PrintWriter(fw);
            obj = new Telefono(obj.getId(), obj.getNumero(), obj.getClienteId());
            metodos.agregarRegistro(obj);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                telefono = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(telefono.getId() + "," + telefono.getNumero() + "," + (telefono.getClienteId() != null ? telefono.getClienteId().getRut() : "null")));
            }
            pw.close();
            resp = true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar Telefono: " + ex.getMessage());
        }
        return resp;
    }

    @Override
    public boolean actualizar(Telefono obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            obj = new Telefono(obj.getId(), obj.getNumero(), obj.getClienteId());
            int codigo = buscaCodigo(obj.getId());
            if (codigo == -1) {
                metodos.agregarRegistro(obj);
            } else {
                metodos.modificarRegistro(codigo, obj);
            }
            fw = new FileWriter("Archivos/"+ruta);
            pw = new PrintWriter(fw);

            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                telefono = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(telefono.getId() + "," + telefono.getNumero() + "," + (telefono.getClienteId() != null ? telefono.getClienteId().getRut() : "null")));
            }
            pw.close();
            resp = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return resp;
    }

    @Override
    public Telefono getObjeto(long codigo) {
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            if (metodos.obtenerRegistro(i).getId() == codigo) {
                return metodos.obtenerRegistro(i);
            }
        }
        return null;
    }

}
