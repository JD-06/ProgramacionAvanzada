package ejemplos.holamundo;

public class ModeloHolaMundo {
    private String mensaje;

    public ModeloHolaMundo() {
        this.mensaje = "¡Hola Mundo con Swing!";
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String generarSaludo(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return mensaje;
        }
        return "¡Hola " + nombre + "!";
    }
}
