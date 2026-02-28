package model;

public class Insumo {
    private String id;
    private String nombre;
    private String idCategoria;

    public Insumo(String id, String nombre, String idCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.idCategoria = idCategoria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }
}
