package com.pawllu.acceso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

public class Acceso {

    public static synchronized List<String> cargarArchivo(String ruta) {
        List<String> lista = null;
        FileReader fi;
        BufferedReader bu;
        String linea;
        File carpeta = new File("Archivos");
        File archivo = new File("Archivos/" + ruta);
        try {
            if (!carpeta.exists()) {
                FileUtils.forceMkdir(carpeta);
            }else if(!archivo.exists()){
                archivo.createNewFile();
            }
            lista = new ArrayList<>();
            fi = new FileReader("Archivos/"+ruta);
            bu = new BufferedReader(fi);
            while ((linea = bu.readLine()) != null) {
                lista.add(linea);
            }
            bu.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar archivo de texto: " + archivo.getName() + " ->" + ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return lista;
    }

}
