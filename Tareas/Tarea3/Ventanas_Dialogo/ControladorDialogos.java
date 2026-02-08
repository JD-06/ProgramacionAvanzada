package ejemplos.dialogos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorDialogos {
    private DialogosModelo modelo;
    private DialogosVista vista;

    public ControladorDialogos(DialogosModelo modelo, DialogosVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        inicializarEventos();
    }

    private void inicializarEventos() {

        vista.getBtnMensajeSimple().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.mostrarMensajeSimple("Este es un mensaje simple");
                modelo.agregarAccion("Mostrado: Mensaje Simple");
                actualizarVista();
            }
        });

        vista.getBtnMensajeInfo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.mostrarMensajeInfo("Esta es una información importante");
                modelo.agregarAccion("Mostrado: Mensaje de Información");
                actualizarVista();
            }
        });

        vista.getBtnMensajeAdvertencia().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.mostrarMensajeAdvertencia("¡Atención! Esta es una advertencia");
                modelo.agregarAccion("Mostrado: Mensaje de Advertencia");
                actualizarVista();
            }
        });

        vista.getBtnMensajeError().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.mostrarMensajeError("Ha ocurrido un error");
                modelo.agregarAccion("Mostrado: Mensaje de Error");
                actualizarVista();
            }
        });

        vista.getBtnMensajePregunta().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = vista.mostrarMensajePregunta("¿Deseas continuar?");
                String resultado = "";
                switch (respuesta) {
                    case JOptionPane.YES_OPTION:
                        resultado = "Usuario seleccionó: Sí";
                        break;
                    case JOptionPane.NO_OPTION:
                        resultado = "Usuario seleccionó: No";
                        break;
                    case JOptionPane.CANCEL_OPTION:
                        resultado = "Usuario seleccionó: Cancelar";
                        break;
                    default:
                        resultado = "Diálogo cerrado";
                }
                modelo.agregarAccion("Pregunta - " + resultado);
                actualizarVista();
            }
        });

        vista.getBtnDialogoInput().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = vista.mostrarDialogoInput("Ingresa tu nombre:", "");
                if (nombre != null && !nombre.trim().isEmpty()) {
                    modelo.setNombreUsuario(nombre);
                    vista.setEstado("Nombre guardado: " + nombre);
                } else {
                    modelo.agregarAccion("Diálogo de entrada cancelado");
                }
                actualizarVista();
            }
        });

        vista.getBtnDialogoOpcion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] colores = {"Rojo", "Azul", "Verde", "Amarillo", "Naranja", "Morado"};
                String color = vista.mostrarDialogoOpcion("Selecciona tu color favorito:", colores);
                if (color != null) {
                    modelo.setColorFavorito(color);
                    vista.setEstado("Color favorito: " + color);
                } else {
                    modelo.agregarAccion("Selección de color cancelada");
                }
                actualizarVista();
            }
        });

        vista.getBtnDialogoConfirmacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = vista.mostrarDialogoConfirmacion(
                    "¿Deseas limpiar todos los datos guardados?");

                if (respuesta == JOptionPane.OK_OPTION) {
                    modelo.limpiarDatos();
                    vista.setEstado("Datos limpiados");
                    vista.mostrarMensajeInfo("Datos limpiados correctamente");
                } else {
                    modelo.agregarAccion("Limpieza de datos cancelada");
                }
                actualizarVista();
            }
        });

        vista.getBtnDialogoPersonalizado().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] datos = vista.mostrarDialogoPersonalizado();
                if (datos != null) {
                    String nombre = (String) datos[0];
                    String edadTexto = (String) datos[1];
                    String color = (String) datos[2];

                    if (!nombre.trim().isEmpty()) {
                        modelo.setNombreUsuario(nombre);
                    }

                    if (modelo.validarEdad(edadTexto)) {
                        modelo.setEdad(Integer.parseInt(edadTexto));
                    } else {
                        vista.mostrarMensajeAdvertencia("Edad inválida. No se guardó.");
                    }

                    modelo.setColorFavorito(color);
                    vista.setEstado("Datos personalizados guardados");
                } else {
                    modelo.agregarAccion("Diálogo personalizado cancelado");
                }
                actualizarVista();
            }
        });

        vista.getBtnMostrarResumen().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resumen = modelo.obtenerResumen();
                vista.mostrarMensajeInfo(resumen);
                modelo.agregarAccion("Resumen mostrado");
                actualizarVista();
            }
        });
    }

    private void actualizarVista() {
        vista.actualizarHistorial(modelo.getHistorialAcciones());
    }

    public void iniciar() {
        vista.setVisible(true);
    }
}
