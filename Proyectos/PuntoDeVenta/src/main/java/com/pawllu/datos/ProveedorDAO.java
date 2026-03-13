package com.pawllu.datos;

import com.pawllu.acceso.Acceso;
import com.pawllu.datos.interfaces.metodosDao;
import com.pawllu.entidades.Proveedor;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class ProveedorDAO implements metodosDao<Proveedor> {

    private final List<Proveedor> lista;
    Metodo<Proveedor> metodos;
    private final String ruta = "proveedor.txt";
    private boolean resp;
    private Proveedor proveedor;

    public ProveedorDAO() {
        lista = new ArrayList<>();
        metodos = new Metodo<>(lista);
        cargarLista();
    }

    private void cargarLista() {
        Proveedor proveedor;
        for (String dato : Acceso.cargarArchivo(ruta)) {
            StringTokenizer st = new StringTokenizer((String) dato, ",");
            proveedor = new Proveedor(Long.parseLong(st.nextToken()), st.nextToken(), st.nextToken(), st.nextToken());
            metodos.agregarRegistro(proveedor);
        }

    }

    public int buscaNombre(String nombre) {//busca por nombre de proveedor (opcional)
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            if (metodos.obtenerRegistro(i).getNombre().equalsIgnoreCase(nombre)) {
                return i;
            }
        }
        return -1;
    }

    public List seleccionar() {//Lo usamos en Direccion del proveedor y en productos
        List<Proveedor> registros = new ArrayList<>();
        try {
            for (String dato : Acceso.cargarArchivo(ruta)) {
                StringTokenizer st = new StringTokenizer((String) dato, ",");
                registros.add(new Proveedor(Long.parseLong(st.nextToken()), st.nextToken(), st.nextToken(), st.nextToken()));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar Proveedor: " + e.getMessage());
        } finally {

        }
        return registros;
    }

    @Override
    public List<Proveedor> listar() {
        List<Proveedor> registros = new ArrayList<>();
        try {
            for (String dato : Acceso.cargarArchivo(ruta)) {
                StringTokenizer st = new StringTokenizer((String) dato, ",");
                registros.add(new Proveedor(Long.parseLong(st.nextToken()), st.nextToken(), st.nextToken(), st.nextToken()));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al listar Proveedor: " + e.getMessage());
        } finally {

        }
        return registros;
    }

    @Override
    public boolean insertar(Proveedor obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            obj = new Proveedor(obj.getRut(), obj.getNombre(), obj.getTelefono(), obj.getPagina_web());
            metodos.agregarRegistro(obj);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                proveedor = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(proveedor.getRut() + "," + proveedor.getNombre() + "," + proveedor.getTelefono() + "," + proveedor.getPagina_web()));
            }
            pw.close();
            resp = true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar Proveedor: " + ex.getMessage());
        }
        return resp;
    }

    @Override
    public boolean actualizar(Proveedor obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            obj = new Proveedor(obj.getRut(), obj.getNombre(), obj.getTelefono(), obj.getPagina_web());
            int codigo = buscaCodigo(obj.getRut());
            if (codigo == -1) {//Si ese proveedor no existe lo podemos agregar
                metodos.agregarRegistro(obj);
            } else {//Si existe entonces modificamos
                metodos.modificarRegistro(codigo, obj);
            }
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                proveedor = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(proveedor.getRut() + "," + proveedor.getNombre() + "," + proveedor.getTelefono() + "," + proveedor.getPagina_web()));
            }
            pw.close();
            resp = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return resp;
    }

    @Override
    public int buscaCodigo(long codigo) {//si existe retorna posición en la lista
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            if (codigo == metodos.obtenerRegistro(i).getRut()) {
                return i;
            }
        }
        return -1;//No retorna ninguna posición
    }

    @Override
    public Proveedor getObjeto(long codigo) {//codigo a buscar
        Proveedor proveedor = null;
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {//recorremos toda la lista de proveedores
            proveedor = metodos.obtenerRegistro(i);//obtiene un proveedor segun la posición i en la lista
            if (proveedor.getRut() == codigo) {//comparamos ese rut con el rut del parametro
                proveedor = new Proveedor(proveedor.getRut(), proveedor.getNombre(), proveedor.getTelefono(), proveedor.getPagina_web());
                return proveedor;
            }
        }
        return proveedor;//retorna null
    }

}
