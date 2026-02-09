package modelo;

import java.util.Objects;


public class Cpersona {
    private int id;
    private String nombre;
    private String apellido;

    public Cpersona() {
        this.id = 0;
        this.nombre = "";
        this.apellido = "";
    }
    

    public Cpersona(int id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
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
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
  
    @Override
    public String toString() {
        return "ID: " + id + " - " + nombre + " " + apellido;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Cpersona)) return false;
        Cpersona other = (Cpersona) obj;
        return this.id == other.id;
    }
    
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
