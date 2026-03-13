/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pawllu.acceso;

import com.pawllu.datos.ClienteDAO;
import com.pawllu.entidades.Cliente;
import java.io.File;

import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author TDAVI
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        List<String> lista = Conexion.cargarArchivo(new File("Archivos/cliente.txt"));
//        for(String dato : lista){
//            StringTokenizer st = new StringTokenizer(dato, ",");
//            System.out.println(st.nextToken()+","+st.nextToken()+","+st.nextToken());
//        }
        ClienteDAO dao = new ClienteDAO();
//List<Cliente> lista = dao.listar();

//        dao.insertar(new Cliente(2, "Rudy"));
        dao.actualizar(new Cliente(3, "Rudy"));
    }

}
