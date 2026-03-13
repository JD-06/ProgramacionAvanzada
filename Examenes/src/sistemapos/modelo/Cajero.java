package sistemapos.modelo;

public class Cajero {

    private int id;
    private String nombre;

    public Cajero(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public String toCsv() {
        return id + "," + nombre.replace(",", ";");
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre;
    }
}
