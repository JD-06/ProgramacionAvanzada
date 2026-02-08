package ejemplos.dialogos;

import java.util.ArrayList;
import java.util.List;

public class ModeloDialogos {
    private String nombreUsuario;
    private int edad;
    private String colorFavorito;
    private List<String> historialAcciones;

    public ModeloDialogos() {
        this.nombreUsuario = "";
        this.edad = 0;
        this.colorFavorito = "";
        this.historialAcciones = new ArrayList<>();
    }

    public void setNombreUsuario(String nombre) {
        this.nombreUsuario = nombre;
        historialAcciones.add("Nombre establecido: " + nombre);
    }

    public void setEdad(int edad) {
        this.edad = edad;
        historialAcciones.add("Edad establecida: " + edad);
    }

    public void setColorFavorito(String color) {
        this.colorFavorito = color;
        historialAcciones.add("Color favorito: " + color);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public int getEdad() {
        return edad;
    }

    public String getColorFavorito() {
        return colorFavorito;
    }

    public List<String> getHistorialAcciones() {
        return new ArrayList<>(historialAcciones);
    }

    public void agregarAccion(String accion) {
        historialAcciones.add(accion);
    }

    public void limpiarDatos() {
        nombreUsuario = "";
        edad = 0;
        colorFavorito = "";
        historialAcciones.add("Datos limpiados");
    }

    public String obtenerResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESUMEN DE DATOS ===\n");
        sb.append("Nombre: ").append(nombreUsuario.isEmpty() ? "No establecido" : nombreUsuario).append("\n");
        sb.append("Edad: ").append(edad == 0 ? "No establecida" : edad + " años").append("\n");
        sb.append("Color Favorito: ").append(colorFavorito.isEmpty() ? "No establecido" : colorFavorito).append("\n");
        return sb.toString();
    }

    public boolean validarEdad(String edadTexto) {
        try {
            int e = Integer.parseInt(edadTexto);
            return e > 0 && e < 150;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
