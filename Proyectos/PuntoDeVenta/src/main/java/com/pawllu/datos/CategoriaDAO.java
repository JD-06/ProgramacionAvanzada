package com.pawllu.datos;

import com.pawllu.acceso.Acceso;
import com.pawllu.datos.interfaces.metodosDao;
import com.pawllu.entidades.Categoria;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class CategoriaDAO implements metodosDao<Categoria> {

    private final List<Categoria> lista;
    Metodo<Categoria> metodos;
    private final String ruta = "categoria.txt";
    private boolean resp;
    private Categoria categoria;

    public CategoriaDAO() {
        lista = new ArrayList<>();
        metodos = new Metodo<>(lista);
        cargarLista();

    }

    private void cargarLista() {
        Categoria categ;
        for (String dato : Acceso.cargarArchivo(ruta)) {
            StringTokenizer st = new StringTokenizer((String) dato, ",");
            categ = new Categoria(Long.parseLong(st.nextToken()), st.nextToken(), st.nextToken());
            metodos.agregarRegistro(categ);
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

    public List seleccionar() {//Lo usamos en Producto
        List<Categoria> registros = new ArrayList<>();
        try {
            for (String dato : Acceso.cargarArchivo(ruta)) {
                StringTokenizer st = new StringTokenizer((String) dato, ",");
                registros.add(new Categoria(Long.parseLong(st.nextToken()), st.nextToken(), st.nextToken()));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar Categoria: " + e.getMessage());
        } finally {

        }
        return registros;
    }

    @Override
    public Categoria getObjeto(long codigo) {
        Categoria categ = null;
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            categ = metodos.obtenerRegistro(i);
            if (categ.getId() == codigo) {
                categ = new Categoria(categ.getId(), categ.getNombre(), categ.getDescripcion());
                return categ;
            }
        }
        return categ;
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
    public List<Categoria> listar() {
        List<Categoria> registros = new ArrayList<>();
        try {
            Acceso.cargarArchivo(ruta).stream().map(dato -> new StringTokenizer((String) dato, ",")).forEachOrdered(st -> {
                registros.add(new Categoria(Long.parseLong(st.nextToken()), st.nextToken(), st.nextToken()));
            });
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al listar Categoria: " + e.getMessage());
        } finally {

        }
        return registros;
    }

    @Override
    public boolean insertar(Categoria obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            obj = new Categoria(obj.getId(), obj.getNombre(), obj.getDescripcion());
            metodos.agregarRegistro(obj);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                categoria = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(categoria.getId() + "," + categoria.getNombre() + "," + categoria.getDescripcion()));
            }
            pw.close();
            resp = true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar Categoria: " + ex.getMessage());
        }
        return resp;
    }
//

    @Override
    public boolean actualizar(Categoria obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            obj = new Categoria(obj.getId(), obj.getNombre(), obj.getDescripcion());
            long idCategoria = obj.getId();
            int codigo = buscaCodigo(idCategoria);
            if (codigo == -1) {
                metodos.agregarRegistro(obj);
            } else {
                metodos.modificarRegistro(codigo, obj);
            }
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                categoria = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(categoria.getId() + "," + categoria.getNombre() + "," + categoria.getDescripcion()));
            }
            pw.close();
            resp = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return resp;
    }

}
