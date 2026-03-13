package sistemapos.modelo;

public class Cliente {

    private int id;
    private String nombre;
    private boolean socio;

    public Cliente(int id, String nombre, boolean socio) {
        this.id = id;
        this.nombre = nombre;
        this.socio = socio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isSocio() {
        return socio;
    }

    public void setSocio(boolean socio) {
        this.socio = socio;
    }

    public String toCsv() {
        return id + "," + nombre.replace(",", ";") + "," + socio;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre + (socio ? " (Socio)" : "");
    }
}
