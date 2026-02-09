package modelo;

import java.util.Objects;


public class Insumo {
    private int id;
    private String insumo;
    private String categoria;

    public Insumo() {
        this.id = 0;
        this.insumo = "";
        this.categoria = "";
    }

    public Insumo(int id, String insumo, String categoria) {
        this.id = id;
        this.insumo = insumo;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getInsumo() {
        return insumo;
    }
    
    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    

    @Override
    public String toString() {
        return String.format("%03d - %s [%s]", id, insumo, categoria);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Insumo)) return false;
        Insumo other = (Insumo) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
