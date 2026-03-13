package com.pawllu.entidades;

import java.util.Objects;

public class Proveedor {

    private Long rut;
    private String nombre;
    private String telefono;
    private String pagina_web;

    public Proveedor() {
    }

    public Proveedor(Long rut) {
        this.rut = rut;
    }

    public Proveedor(Long rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
    }

    public Proveedor(Long rut, String nombre, String telefono, String pagina_web) {
        this.rut = rut;
        this.nombre = nombre;
        this.telefono = telefono;
        this.pagina_web = pagina_web;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Long getRut() {
        return rut;
    }

    public void setRut(Long rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPagina_web() {
        return pagina_web;
    }

    public void setPagina_web(String pagina_web) {
        this.pagina_web = pagina_web;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.rut);
        hash = 29 * hash + Objects.hashCode(this.nombre);
        hash = 29 * hash + Objects.hashCode(this.telefono);
        hash = 29 * hash + Objects.hashCode(this.pagina_web);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Proveedor other = (Proveedor) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.pagina_web, other.pagina_web)) {
            return false;
        }
        if (!Objects.equals(this.rut, other.rut)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

}
