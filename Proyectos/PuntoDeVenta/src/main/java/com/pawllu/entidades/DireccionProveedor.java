package com.pawllu.entidades;

public class DireccionProveedor {

    private Proveedor proveedorRut;
    private String calle;
    private String numero;
    private String comuna;
    private String ciudad;

    public DireccionProveedor() {
    }

    public DireccionProveedor(Proveedor proveedorRut, String calle, String numero, String comuna, String ciudad) {
        this.proveedorRut = proveedorRut;
        this.calle = calle;
        this.numero = numero;
        this.comuna = comuna;
        this.ciudad = ciudad;
    }

    public Proveedor getProveedorRut() {
        return proveedorRut;
    }

    public void setProveedorRut(Proveedor proveedorRut) {
        this.proveedorRut = proveedorRut;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

}
