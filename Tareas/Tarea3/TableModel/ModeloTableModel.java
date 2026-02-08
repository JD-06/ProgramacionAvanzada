package ejemplos.tablemodel;

import java.util.ArrayList;
import java.util.List;

public class ModeloTableModel {
    private List<Estudiante> estudiantes;
    private int siguienteId;

    public ModeloTableModel() {
        this.estudiantes = new ArrayList<>();
        this.siguienteId = 1;
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        agregarEstudiante("Juan", "Pérez", 20, "Ingeniería en Sistemas", 8.5);
        agregarEstudiante("María", "González", 22, "Medicina", 9.2);
        agregarEstudiante("Carlos", "Rodríguez", 21, "Derecho", 7.8);
        agregarEstudiante("Ana", "Martínez", 19, "Arquitectura", 8.9);
        agregarEstudiante("Luis", "López", 23, "Administración", 8.1);
    }

    public void agregarEstudiante(String nombre, String apellido, int edad, String carrera, double promedio) {
        Estudiante estudiante = new Estudiante(siguienteId++, nombre, apellido, edad, carrera, promedio);
        estudiantes.add(estudiante);
    }

    public void agregarEstudiante(Estudiante estudiante) {
        estudiante.setId(siguienteId++);
        estudiantes.add(estudiante);
    }

    public void eliminarEstudiante(int indice) {
        if (indice >= 0 && indice < estudiantes.size()) {
            estudiantes.remove(indice);
        }
    }

    public void modificarEstudiante(int indice, Estudiante estudiante) {
        if (indice >= 0 && indice < estudiantes.size()) {
            Estudiante est = estudiantes.get(indice);
            estudiante.setId(est.getId());
            estudiantes.set(indice, estudiante);
        }
    }

    public Estudiante obtenerEstudiante(int indice) {
        if (indice >= 0 && indice < estudiantes.size()) {
            return estudiantes.get(indice);
        }
        return null;
    }

    public List<Estudiante> obtenerTodosEstudiantes() {
        return new ArrayList<>(estudiantes);
    }

    public int getCantidadEstudiantes() {
        return estudiantes.size();
    }

    public List<Estudiante> buscarPorNombre(String nombre) {
        List<Estudiante> resultados = new ArrayList<>();
        String nombreBusqueda = nombre.toLowerCase();

        for (Estudiante est : estudiantes) {
            if (est.getNombre().toLowerCase().contains(nombreBusqueda) ||
                est.getApellido().toLowerCase().contains(nombreBusqueda)) {
                resultados.add(est);
            }
        }

        return resultados;
    }

    public List<Estudiante> filtrarPorCarrera(String carrera) {
        List<Estudiante> resultados = new ArrayList<>();

        for (Estudiante est : estudiantes) {
            if (est.getCarrera().equalsIgnoreCase(carrera)) {
                resultados.add(est);
            }
        }

        return resultados;
    }

    public double calcularPromedioGeneral() {
        if (estudiantes.isEmpty()) {
            return 0.0;
        }

        double suma = 0.0;
        for (Estudiante est : estudiantes) {
            suma += est.getPromedio();
        }

        return suma / estudiantes.size();
    }

    public String[] getCarrerasDisponibles() {
        return new String[]{
            "Ingeniería en Sistemas",
            "Medicina",
            "Derecho",
            "Arquitectura",
            "Administración",
            "Contaduría",
            "Psicología",
            "Diseño Gráfico"
        };
    }
}
