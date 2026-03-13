package com.pawllu.datos.interfaces;

import java.util.List;

public interface metodosDao<T> {

    public List<T> listar();

    public boolean insertar(T obj);

    public boolean actualizar(T obj);

    public int buscaCodigo(long codigo);

    public T getObjeto(long codigo);

}
