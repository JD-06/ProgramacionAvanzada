package com.pawllu.entidades;

public class Telefono {

    private Long id;
    private String numero;
    private Cliente clienteId;

    public Telefono() {
    }

    public Telefono(Long id, String numero) {
        this.id = id;
        this.numero = numero;
    }

    public Telefono(Long id, String numero, Cliente clienteId) {
        this.id = id;
        this.numero = numero;
        this.clienteId = clienteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

}
