/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pawllu.datos;

import com.pawllu.acceso.Acceso;
import com.pawllu.entidades.DetalleVenta;
import com.pawllu.entidades.Producto;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author TDAVI
 */
public class DetalleVentaDAO {

    private final List<DetalleVenta> listaDetalle;
    Metodo<DetalleVenta> metodoDetalle;
    private final String rutaDetalle = "detalleVenta.txt";
    private DetalleVenta detalle;

    private final ProductoDAO DATOS_PROD;
    private boolean resp = false;

    public DetalleVentaDAO() {
        listaDetalle = new ArrayList<>();
        metodoDetalle = new Metodo<>(listaDetalle);
        detalle = new DetalleVenta();
        DATOS_PROD = new ProductoDAO();
        cargarListaDetalle();
    }

    private void cargarListaDetalle() {
        DetalleVenta detalleVenta;
        for (String dato : Acceso.cargarArchivo(rutaDetalle)) {
            StringTokenizer st = new StringTokenizer((String) dato, ",");
            detalleVenta = new DetalleVenta();

            detalleVenta.setId(Integer.parseInt(st.nextToken()));
            detalleVenta.setPrecio(Double.parseDouble(st.nextToken()));
            detalleVenta.setCantidad(Integer.parseInt(st.nextToken()));

//            int idVenta = Integer.parseInt(st.nextToken());
//            Venta venta = DATOS_VENT.getObjeto(idVenta);
//            detalleVenta.setVentasId(venta);
            detalleVenta.setIdVenta(Integer.parseInt(st.nextToken()));

            int idProducto = Integer.parseInt(st.nextToken());
            Producto producto = DATOS_PROD.getObjeto(idProducto);
            detalleVenta.setProductoId(producto);

            metodoDetalle.agregarRegistro(detalleVenta);
        }

    }

    public List listarDetalles(int idVenta) {
        List<DetalleVenta> listDetalle = new ArrayList<>();
        DetalleVenta vent;
        try {
            for (String dato : Acceso.cargarArchivo(rutaDetalle)) {
                StringTokenizer st = new StringTokenizer(dato, ",");

                vent = new DetalleVenta();
                vent.setId(Integer.parseInt(st.nextToken()));
                vent.setPrecio(Double.parseDouble(st.nextToken()));
                vent.setCantidad(Integer.parseInt(st.nextToken()));

                int idV = Integer.parseInt(st.nextToken());
//                Venta venta = DATOS_VENT.getObjeto(idV);
//                vent.setVentasId(venta);
                vent.setIdVenta(idV);

                int idProducto = Integer.parseInt(st.nextToken());
                Producto producto = DATOS_PROD.getObjeto(idProducto);
                vent.setProductoId(producto);

                if (idV == idVenta) {
                    listDetalle.add(vent);
                }

            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al listar DetalleVentas: " + e.getMessage());
        }

        return listDetalle;
    }

    public boolean insertarDetalles(List<DetalleVenta> detalles) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/" + rutaDetalle);
            pw = new PrintWriter(fw);
            for (DetalleVenta det : detalles) {
                metodoDetalle.agregarRegistro(det);
            }
            for (int i = 0; i < metodoDetalle.cantidadRegistro(); i++) {
                detalle = metodoDetalle.obtenerRegistro(i);
                pw.println(String.valueOf(detalle.getId() + "," + detalle.getPrecio() + "," + detalle.getCantidad() + "," + detalle.getIdVenta() + "," + detalle.getProductoId().getId()));
            }
            pw.close();
            resp = true;

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar Detalles de Venta: " + ex.getMessage());
        }
        return resp;
    }

    public boolean insertar(DetalleVenta obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/" + rutaDetalle);
            pw = new PrintWriter(fw);
            obj = new DetalleVenta(obj.getId(), obj.getPrecio(), obj.getCantidad(), obj.getIdVenta(), obj.getProductoId());
            metodoDetalle.agregarRegistro(obj);
            for (int i = 0; i < metodoDetalle.cantidadRegistro(); i++) {
                detalle = metodoDetalle.obtenerRegistro(i);
                pw.println(String.valueOf(detalle.getId() + "," + detalle.getPrecio() + "," + detalle.getCantidad() + "," + detalle.getIdVenta() + "," + detalle.getProductoId().getId()));
            }
            pw.close();
            resp = true;

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar DetalleVenta: " + ex.getMessage());
        }
        return resp;
    }

}
