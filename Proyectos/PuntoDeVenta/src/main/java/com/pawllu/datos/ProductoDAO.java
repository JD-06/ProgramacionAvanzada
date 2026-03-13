package com.pawllu.datos;

import com.pawllu.acceso.Acceso;
import com.pawllu.datos.interfaces.metodosDao;
import com.pawllu.entidades.Categoria;
import com.pawllu.entidades.Producto;
import com.pawllu.entidades.Proveedor;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class ProductoDAO implements metodosDao<Producto> {

    private final List<Producto> lista;
    Metodo<Producto> metodos;
    private final String ruta = "producto.txt";
    private boolean resp;
    private Producto producto;
    private CategoriaDAO DATOS;
    private ProveedorDAO DATOS_PROV;

    public ProductoDAO() {
        lista = new ArrayList<>();
        metodos = new Metodo<>(lista);
        DATOS = new CategoriaDAO();
        DATOS_PROV = new ProveedorDAO();
        cargarLista();

    }

    private void cargarLista() {
        Producto producto;
        for (String dato : Acceso.cargarArchivo(ruta)) {
            StringTokenizer st = new StringTokenizer((String) dato, ",");

            producto = new Producto();
            producto.setId(Long.parseLong(st.nextToken()));
            producto.setNombre(st.nextToken());
            producto.setPrecio(Double.parseDouble(st.nextToken()));
            producto.setStock(Integer.parseInt(st.nextToken()));

            long idCategoria = Long.parseLong(st.nextToken());
            Categoria categoria = DATOS.getObjeto(idCategoria);

            long idProveedor = Long.parseLong(st.nextToken());
            Proveedor proveedor = DATOS_PROV.getObjeto(idProveedor);

            producto.setCategoriaId(categoria);
            producto.setProveedorId(proveedor);

            metodos.agregarRegistro(producto);
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

    public List seleccionar() {//Lo usamos en ventas
        List<Producto> registros = new ArrayList<>();
        try {
            for (String dato : Acceso.cargarArchivo(ruta)) {
                StringTokenizer st = new StringTokenizer((String) dato, ",");
                registros.add(new Producto(Long.parseLong(st.nextToken()), st.nextToken(), Double.parseDouble(st.nextToken()), Integer.parseInt(st.nextToken())));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar Producto: " + e.getMessage());
        } finally {

        }
        return registros;
    }

    @Override
    public Producto getObjeto(long codigo) {
        Producto producto = null;
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            producto = metodos.obtenerRegistro(i);
            if (producto.getId() == codigo) {
                producto = new Producto(producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getStock(), producto.getCategoriaId(), producto.getProveedorId());
                return producto;
            }
        }
        return producto;
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
    public List<Producto> listar() {
        List<Producto> registros = new ArrayList<>();
        try {
            Acceso.cargarArchivo(ruta).stream().map(dato -> new StringTokenizer((String) dato, ",")).forEachOrdered(st -> {
                registros.add(new Producto(Long.parseLong(st.nextToken()), st.nextToken(), Double.parseDouble(st.nextToken()), Integer.parseInt(st.nextToken()),
                        DATOS.getObjeto(Long.parseLong(st.nextToken())), DATOS_PROV.getObjeto(Long.parseLong(st.nextToken()))));
            });
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al listar Producto: " + e.getMessage());
        } finally {

        }
        return registros;
    }

    @Override
    public boolean insertar(Producto obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            obj = new Producto(obj.getId(), obj.getNombre(), obj.getPrecio(), obj.getStock(), obj.getCategoriaId(), obj.getProveedorId());
            metodos.agregarRegistro(obj);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                producto = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(producto.getId() + "," + producto.getNombre() + "," + producto.getPrecio() + "," + producto.getStock() + "," + producto.getCategoriaId().getId()
                        + "," + producto.getProveedorId().getRut()));
            }
            pw.close();
            resp = true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar Producto: " + ex.getMessage());
        }
        return resp;
    }
//

    public boolean actualizarStock(long id, int cantidad) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            int codigo = buscaCodigo(id);
            if (codigo != -1) {
                producto = metodos.obtenerRegistro(codigo);
                producto.setStock(producto.getStock() - cantidad);
                metodos.modificarRegistro(codigo, producto);

                fw = new FileWriter("Archivos/" + ruta);
                pw = new PrintWriter(fw);
                for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                    Producto p = metodos.obtenerRegistro(i);
                    pw.println(String.valueOf(p.getId() + "," + p.getNombre() + "," + p.getPrecio() + "," + p.getStock() + "," + p.getCategoriaId().getId()
                            + "," + p.getProveedorId().getRut()));
                }
                pw.close();
                resp = true;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar stock: " + e.getMessage());
        }
        return resp;
    }

    @Override
    public boolean actualizar(Producto obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            obj = new Producto(obj.getId(), obj.getNombre(), obj.getPrecio(), obj.getStock(), obj.getCategoriaId(), obj.getProveedorId());
            long idLong = obj.getId();
            int codigo = buscaCodigo(idLong);
            if (codigo == -1) {
                metodos.agregarRegistro(obj);
            } else {
                metodos.modificarRegistro(codigo, obj);
            }
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                producto = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(producto.getId() + "," + producto.getNombre() + "," + producto.getPrecio() + "," + producto.getStock() + "," + producto.getCategoriaId().getId()
                        + "," + producto.getProveedorId().getRut()));
            }
            pw.close();
            resp = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return resp;
    }

}
