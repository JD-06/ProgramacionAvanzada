package controlador;

import vista.VInternalGestionInsumos;
import modelo.MpracticaTarea01;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class CInternalGestionInsumos implements ActionListener {
    

    private VInternalGestionInsumos vista;
    private MpracticaTarea01 modelo;
    

    public CInternalGestionInsumos(VInternalGestionInsumos vista) {
        this.vista = vista;
        this.modelo = new MpracticaTarea01();
        

        vista.getCmbCategoria().setModel(modelo.getModeloCategoria());
        vista.getListInsumos().setModel(modelo.getModeloLista());

        vista.getBtnAgregar().addActionListener(this);
        vista.getBtnEliminar().addActionListener(this);
        vista.getBtnCerrar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        

        if (e.getSource() == vista.getBtnAgregar()) {
            String nombreInsumo = vista.getTxtInsumo().trim();
            String categoria = (String) vista.getCmbCategoria().getSelectedItem();
            
            if (nombreInsumo.isEmpty()) {
                JOptionPane.showMessageDialog(
                    vista,
                    "Ingrese el nombre del insumo",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            modelo.agregarInsumo(nombreInsumo, categoria);
            vista.limpiarCampos();
            
            JOptionPane.showMessageDialog(
                vista,
                "Insumo agregado correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        
        if (e.getSource() == vista.getBtnEliminar()) {
            int indice = vista.getListInsumos().getSelectedIndex();
            
            if (indice < 0) {
                JOptionPane.showMessageDialog(
                    vista,
                    "Seleccione un insumo",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            int conf = JOptionPane.showConfirmDialog(
                vista,
                "¿Eliminar este insumo?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
            );
            
            if (conf == 0) {
                modelo.eliminarInsumo(indice);
                JOptionPane.showMessageDialog(
                    vista,
                    "Insumo eliminado",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
        

        if (e.getSource() == vista.getBtnCerrar()) {
            vista.dispose();
        }
    }
}
