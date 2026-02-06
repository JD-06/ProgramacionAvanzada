package Proyecto2;

public class ModeloTexto {
    private int contador = 0;
    private String textoGuardado = "";

    public void setTexto(String texto) {
        this.textoGuardado = texto;
    }

    public String getTexto() {
        return textoGuardado;
    }

    public void incrementarContador() {
        contador++;
    }

    public int getContador() {
        return contador;
    }
}