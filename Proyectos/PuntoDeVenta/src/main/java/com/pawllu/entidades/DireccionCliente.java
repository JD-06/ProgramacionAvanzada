package com.pawllu.entidades;

public class DireccionCliente {

    private Cliente clienteId;
    private String calle;
    private String numero;
    private String ciudad;
    private String comuna;

    public DireccionCliente() {
    }

    public DireccionCliente(Cliente clienteId, String calle, String numero, String ciudad, String comuna) {
        this.clienteId = clienteId;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.comuna = comuna;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
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
