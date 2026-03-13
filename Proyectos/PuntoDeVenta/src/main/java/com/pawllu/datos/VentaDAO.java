package com.pawllu.datos;

import com.pawllu.acceso.Acceso;
import com.pawllu.datos.interfaces.metodosDao;
import com.pawllu.entidades.Cliente;
import com.pawllu.entidades.DetalleVenta;
import com.pawllu.entidades.Venta;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class VentaDAO implements metodosDao<Venta> {

    private final List<Venta> lista;

    Metodo<Venta> metodos;

    private final String ruta = "venta.txt";
    private boolean resp;
    private Venta venta;
    private final ClienteDAO DATOS;
    private final DetalleVentaDAO DATOS_DET;

    public VentaDAO() {
        lista = new ArrayList<>();
        metodos = new Metodo<>(lista);
        DATOS_DET = new DetalleVentaDAO();
        DATOS = new ClienteDAO();

        cargarLista();

    }

    private void cargarLista() {
        Venta ven;
        for (String dato : Acceso.cargarArchivo(ruta)) {
            StringTokenizer st = new StringTokenizer((String) dato, ",");
            ven = new Venta();

            ven.setId(Long.parseLong(st.nextToken()));
            ven.setFecha(st.nextToken());
            ven.setDescuento(Double.parseDouble(st.nextToken()));

            long idCliente = Long.parseLong(st.nextToken());
            Cliente cliente = DATOS.getObjeto(idCliente);
            if (cliente != null) {
                ven.setClienteId(cliente);
            } else {
                System.out.println("CLIENTE ES NULL");
            }

            ven.setTotal(Double.parseDouble(st.nextToken()));
            ven.setEstado(Boolean.parseBoolean(st.nextToken()));

            metodos.agregarRegistro(ven);
        }

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
    public List<Venta> listar() {
        List<Venta> registros = new ArrayList<>();
        Venta vent;
        try {
            for (String dato : Acceso.cargarArchivo(ruta)) {
                StringTokenizer st = new StringTokenizer(dato, ",");

                vent = new Venta();
                vent.setId(Long.parseLong(st.nextToken()));
                vent.setFecha(st.nextToken());
                vent.setDescuento(Double.parseDouble(st.nextToken()));

                long idCliente = Long.parseLong(st.nextToken());
                Cliente cliente = DATOS.getObjeto(idCliente);
                vent.setClienteId(cliente);

                vent.setTotal(Double.parseDouble(st.nextToken()));
                vent.setEstado(Boolean.parseBoolean(st.nextToken()));

                registros.add(vent);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al listar Ventas: " + e.getMessage());
        }
        return registros;
    }

    @Override
    public boolean insertar(Venta obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            obj = new Venta(obj.getId(), obj.getFecha(), obj.getDescuento(), obj.getClienteId(), obj.getTotal(), true, obj.getDetalles());
            metodos.agregarRegistro(obj);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                venta = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(venta.getId() + "," + venta.getFecha() + "," + venta.getDescuento()
                        + "," + venta.getClienteId().getRut() + "," + venta.getTotal() + "," + venta.isEstado()));
            }
            pw.close();

            if (DATOS_DET.insertarDetalles(obj.getDetalles())) {
                resp = true;
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar Venta: " + ex.getMessage());
        }
        return resp;
    }

    public boolean anular(Venta obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            obj = new Venta(obj.getId(), obj.getFecha(), obj.getDescuento(), obj.getClienteId(), obj.getTotal(), false);
            int codigo = buscaCodigo(obj.getId());
            if (codigo == -1) {
                return false;
            } else {
                metodos.modificarRegistro(codigo, obj);
            }
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                venta = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(venta.getId() + "," + venta.getFecha() + "," + venta.getDescuento()
                        + "," + venta.getClienteId().getRut() + "," + venta.getTotal() + "," + venta.isEstado()));
            }
            pw.close();
            resp = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return resp;
    }

    @Override
    public boolean actualizar(Venta obj) {//No se utiliza
        return false;
    }

    @Override
    public Venta getObjeto(long codigo) {
        Venta vent = null;
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            vent = metodos.obtenerRegistro(i);
            if (vent.getId() == codigo) {
                vent = new Venta(vent.getId(), vent.getFecha(), vent.getDescuento(), vent.getClienteId(), vent.getTotal(), vent.isEstado());
                return vent;
            }
        }
        return vent;
    }

}
