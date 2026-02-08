package ejemplos.tablemodel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorTableModel {
    private TableModelModelo modelo;
    private TableModelVista vista;

    public ControladorTableModel(TableModelModelo modelo, TableModelVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        inicializar();
        inicializarEventos();
    }

    private void inicializar() {
        actualizarTabla();
        actualizarEstadisticas();
    }

    private void inicializarEventos() {

        vista.getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEstudiante();
            }
        });

        vista.getBtnModificar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarEstudiante();
            }
        });

        vista.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEstudiante();
            }
        });

        vista.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEstudiante();
            }
        });

        vista.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.limpiarBusqueda();
                actualizarTabla();
                actualizarEstadisticas();
            }
        });
    }

    private void agregarEstudiante() {
        String[] carreras = modelo.getCarrerasDisponibles();
        Estudiante nuevoEstudiante = vista.mostrarDialogoAgregar(carreras);

        if (nuevoEstudiante != null) {
            modelo.agregarEstudiante(nuevoEstudiante);
            actualizarTabla();
            actualizarEstadisticas();
            vista.mostrarInfo("Estudiante agregado correctamente");
        }
    }

    private void modificarEstudiante() {
        int filaSeleccionada = vista.getFilaSeleccionada();

        if (filaSeleccionada == -1) {
            vista.mostrarError("Por favor, selecciona un estudiante para modificar");
            return;
        }

        Estudiante estudianteActual = modelo.obtenerEstudiante(filaSeleccionada);
        String[] carreras = modelo.getCarrerasDisponibles();
        Estudiante estudianteModificado = vista.mostrarDialogoModificar(estudianteActual, carreras);

        if (estudianteModificado != null) {
            modelo.modificarEstudiante(filaSeleccionada, estudianteModificado);
            actualizarTabla();
            actualizarEstadisticas();
            vista.mostrarInfo("Estudiante modificado correctamente");
        }
    }

    private void eliminarEstudiante() {
        int filaSeleccionada = vista.getFilaSeleccionada();

        if (filaSeleccionada == -1) {
            vista.mostrarError("Por favor, selecciona un estudiante para eliminar");
            return;
        }

        Estudiante estudiante = modelo.obtenerEstudiante(filaSeleccionada);
        String nombreCompleto = estudiante.getNombre() + " " + estudiante.getApellido();

        if (vista.confirmarEliminacion(nombreCompleto)) {
            modelo.eliminarEstudiante(filaSeleccionada);
            actualizarTabla();
            actualizarEstadisticas();
            vista.mostrarInfo("Estudiante eliminado correctamente");
        }
    }

    private void buscarEstudiante() {
        String textoBusqueda = vista.getTextoBusqueda();

        if (textoBusqueda.isEmpty()) {
            vista.mostrarError("Ingresa un nombre para buscar");
            return;
        }

        List<Estudiante> resultados = modelo.buscarPorNombre(textoBusqueda);

        if (resultados.isEmpty()) {
            vista.mostrarInfo("No se encontraron estudiantes con ese nombre");
            vista.limpiarBusqueda();
            actualizarTabla();
        } else {
            vista.cargarEstudiantes(resultados);
            vista.actualizarEstadisticas(resultados.size(), calcularPromedioLista(resultados));
        }
    }

    private double calcularPromedioLista(List<Estudiante> lista) {
        if (lista.isEmpty()) {
            return 0.0;
        }

        double suma = 0.0;
        for (Estudiante est : lista) {
            suma += est.getPromedio();
        }

        return suma / lista.size();
    }

    private void actualizarTabla() {
        List<Estudiante> estudiantes = modelo.obtenerTodosEstudiantes();
        vista.cargarEstudiantes(estudiantes);
    }

    private void actualizarEstadisticas() {
        int total = modelo.getCantidadEstudiantes();
        double promedio = modelo.calcularPromedioGeneral();
        vista.actualizarEstadisticas(total, promedio);
    }

    public void iniciar() {
        vista.setVisible(true);
    }
}
