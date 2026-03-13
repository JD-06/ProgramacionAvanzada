package com.pawllu.datos;

import com.pawllu.acceso.Acceso;
import com.pawllu.datos.interfaces.metodosDao;
import com.pawllu.entidades.DireccionProveedor;
import com.pawllu.entidades.Proveedor;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class DireccionProveedorDAO implements metodosDao<DireccionProveedor> {

    private final List<DireccionProveedor> lista;
    Metodo<DireccionProveedor> metodos;
    private final String ruta = "direccionProveedor.txt";
    private boolean resp;
    private final ProveedorDAO DATOS;
    private DireccionProveedor direccion;

    public DireccionProveedorDAO() {
        lista = new ArrayList<>();
        metodos = new Metodo<>(lista);
        DATOS = new ProveedorDAO();
        cargarLista();
    }

    private void cargarLista() {
        DireccionProveedor direc;
        Proveedor proveedor;
        long rut;
        for (String dato : Acceso.cargarArchivo(ruta)) {
            direc = new DireccionProveedor();
            StringTokenizer st = new StringTokenizer((String) dato, ",");
            rut = Long.parseLong(st.nextToken());
            direc.setCalle(st.nextToken());
            direc.setNumero(st.nextToken());//numero direccion
            direc.setCiudad(st.nextToken());
            direc.setComuna(st.nextToken());

            proveedor = DATOS.getObjeto(rut);
            direc.setProveedorRut(proveedor);

            metodos.agregarRegistro(direc);
        }

    }

    @Override
    public int buscaCodigo(long codigo) {
        System.out.println("codigo a buscar: " + codigo);
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            System.out.println("-> " + metodos.obtenerRegistro(i).getProveedorRut().getRut());
            if (codigo == metodos.obtenerRegistro(i).getProveedorRut().getRut()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public List<DireccionProveedor> listar() {
        List<DireccionProveedor> registros = new ArrayList<>();
        DireccionProveedor direc;
        Proveedor proveedor;
        long rut;
        try {
            for (String dato : Acceso.cargarArchivo(ruta)) {
                direc = new DireccionProveedor();
                StringTokenizer st = new StringTokenizer((String) dato, ",");
                rut = Long.parseLong(st.nextToken());
                direc.setCalle(st.nextToken());
                direc.setNumero(st.nextToken());
                direc.setCiudad(st.nextToken());
                direc.setComuna(st.nextToken());

                proveedor = DATOS.getObjeto(rut);
                direc.setProveedorRut(proveedor);
                registros.add(direc);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al listar DireccionProveedor: " + e.getMessage());
        } finally {

        }
        return registros;
    }

    @Override
    public boolean insertar(DireccionProveedor obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            obj = new DireccionProveedor(obj.getProveedorRut(), obj.getCalle(), obj.getNumero(), obj.getCiudad(), obj.getComuna());
            metodos.agregarRegistro(obj);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                direccion = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(direccion.getProveedorRut().getRut() + "," + direccion.getCalle() + "," + direccion.getNumero() + "," + direccion.getCiudad() + "," + direccion.getComuna()));
            }
            pw.close();
            resp = true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar DireccionProveedor: " + ex.getMessage());
        }
        return resp;
    }

    @Override
    public boolean actualizar(DireccionProveedor obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            obj = new DireccionProveedor(obj.getProveedorRut(), obj.getCalle(), obj.getNumero(), obj.getCiudad(), obj.getComuna());
            int codigo = buscaCodigo(obj.getProveedorRut().getRut());
            if (codigo == -1) {
                metodos.agregarRegistro(obj);
            } else {
                metodos.modificarRegistro(codigo, obj);
            }
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);

            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                direccion = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(direccion.getProveedorRut().getRut() + "," + direccion.getCalle() + "," + direccion.getNumero() + "," + direccion.getCiudad() + "," + direccion.getComuna()));
            }
            pw.close();
            resp = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return resp;
    }

    @Override
    public DireccionProveedor getObjeto(long codigo) {
        return null;
    }

}
