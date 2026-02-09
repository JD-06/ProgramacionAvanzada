package modelo;

import java.util.ArrayList;


public class Lista {
    private ArrayList<Cpersona> personas;
    private int contadorId;
    

    public Lista() {
        this.personas = new ArrayList<>();
        this.contadorId = 1;
    }

    public void insertar(String nombre, String apellido) {
        Cpersona nuevaPersona = new Cpersona(contadorId, nombre, apellido);
        personas.add(nuevaPersona);
        contadorId++;
    }
    

    public String info() {
        StringBuilder sb = new StringBuilder();
        for (Cpersona p : personas) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }
    

    public Cpersona buscar(int id) {
        for (Cpersona p : personas) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
    

    public boolean eliminar(int id) {
        Cpersona personaAEliminar = buscar(id);
        if (personaAEliminar != null) {
            personas.remove(personaAEliminar);
            return true;
        }
        return false;
    }
    

    public int cantidad() {
        return personas.size();
    }
    

    public boolean estaVacia() {
        return personas.isEmpty();
    }
}
