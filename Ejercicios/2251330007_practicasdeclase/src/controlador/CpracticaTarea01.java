package controlador;

import vista.VPractica01_Tarea01;
import modelo.MpracticaTarea01;
import modelo.Insumo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class CpracticaTarea01 implements ActionListener {

    private VPractica01_Tarea01 vista;
    private MpracticaTarea01 modelo;

    public CpracticaTarea01() {
        vista = new VPractica01_Tarea01();
        modelo = new MpracticaTarea01();
        

        vista.getCmbCategoria().setModel(modelo.getModeloCategoria());
        vista.getListInsumos().setModel(modelo.getModeloLista());
        
        vista.setVisible(true);
        

        vista.getBtnAgregar().addActionListener(this);
        vista.getBtnEliminar().addActionListener(this);
        vista.getBtnActualizar().addActionListener(this);
        vista.getBtnSalir().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.getBtnAgregar()) {

            String nombreInsumo = vista.getTxtInsumo().trim();
            String categoria = (String) vista.getCmbCategoria().getSelectedItem();

            if (nombreInsumo.isEmpty()) {
                JOptionPane.showMessageDialog(
                    vista,
                    "Por favor, ingrese el nombre del insumo",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            modelo.agregarInsumo(nombreInsumo, categoria);
            vista.limpiarCampos();

            JOptionPane.showMessageDialog(
                vista,
                "Insumo agregado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
        }

        if (e.getSource() == vista.getBtnEliminar()) {
            int indiceSeleccionado = vista.getListInsumos().getSelectedIndex();

            if (indiceSeleccionado < 0) {
                JOptionPane.showMessageDialog(
                    vista,
                    "Seleccione un insumo para eliminar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de eliminar este insumo?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacion == 0) {
                modelo.eliminarInsumo(indiceSeleccionado);
                JOptionPane.showMessageDialog(
                    vista,
                    "Insumo eliminado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }

        if (e.getSource() == vista.getBtnActualizar()) {
            int indiceSeleccionado = vista.getListInsumos().getSelectedIndex();

            if (indiceSeleccionado < 0) {
                JOptionPane.showMessageDialog(
                    vista,
                    "Seleccione un insumo para actualizar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            String nombreInsumo = vista.getTxtInsumo().trim();
            String categoria = (String) vista.getCmbCategoria().getSelectedItem();

            if (nombreInsumo.isEmpty()) {
                JOptionPane.showMessageDialog(
                    vista,
                    "Por favor, ingrese el nuevo nombre del insumo",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Insumo insumoSeleccionado = modelo.obtenerInsumoPorIndice(indiceSeleccionado);
            boolean actualizado = modelo.getListaInsumos().actualizar(
                insumoSeleccionado.getId(),
                nombreInsumo,
                categoria
            );
            
            if (actualizado) {

                modelo.agregarInsumo("temp", "Materiales");
                modelo.eliminarInsumo(modelo.getListaInsumos().cantidad() - 1);
                
                vista.limpiarCampos();
                JOptionPane.showMessageDialog(
                    vista,
                    "Insumo actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }

        if (e.getSource() == vista.getBtnSalir()) {
            int opcion = JOptionPane.showConfirmDialog(
                vista,
                "¿Desea salir del sistema?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION
            );
            
            if (opcion == 0) {
                vista.dispose();
                System.exit(0);
            }
        }
    }
}
