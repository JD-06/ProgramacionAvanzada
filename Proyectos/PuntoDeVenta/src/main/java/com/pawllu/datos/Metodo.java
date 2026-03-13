package com.pawllu.datos;

import java.util.List;

public class Metodo<T> {

    private final List<T> a;

    public Metodo(List<T> a) {
        this.a = a;
    }

    public void agregarRegistro(T p) {
        this.a.add(p);
    }

    public void modificarRegistro(int i, T p) {
        this.a.set(i, p);
    }

    public void eliminarRegistro(int i) {
        this.a.remove(i);
    }

    public T obtenerRegistro(int i) {
        return (T) a.get(i);
    }

    public int cantidadRegistro() {
        return this.a.size();
    }

}
