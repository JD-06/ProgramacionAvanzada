package ejemplos.copiartexto;

public class ModeloCopiarTexto {
    private String textoOrigen;
    private String textoDestino;

    public ModeloCopiarTexto() {
        this.textoOrigen = "";
        this.textoDestino = "";
    }

    public void setTextoOrigen(String texto) {
        this.textoOrigen = texto;
    }

    public String getTextoOrigen() {
        return textoOrigen;
    }

    public void copiarTexto() {
        this.textoDestino = this.textoOrigen;
    }

    public void copiarTextoInvertido() {
        this.textoDestino = new StringBuilder(this.textoOrigen).reverse().toString();
    }

    public void copiarTextoMayusculas() {
        this.textoDestino = this.textoOrigen.toUpperCase();
    }

    public void copiarTextoMinusculas() {
        this.textoDestino = this.textoOrigen.toLowerCase();
    }

    public String getTextoDestino() {
        return textoDestino;
    }

    public void limpiar() {
        this.textoOrigen = "";
        this.textoDestino = "";
    }

    public int contarPalabras(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return 0;
        }
        return texto.trim().split("\\s+").length;
    }

    public int contarCaracteres(String texto) {
        return texto != null ? texto.length() : 0;
    }
}
