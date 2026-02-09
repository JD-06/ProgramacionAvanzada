package controlador;

import vista.VInternalGestionObras;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;


public class CInternalGestionObras implements ActionListener {
    

    private VInternalGestionObras vista;
    private DefaultListModel<String> modeloLista;
    

    public CInternalGestionObras(VInternalGestionObras vista) {
        this.vista = vista;
        this.modeloLista = new DefaultListModel<>();
        

        vista.getListObras().setModel(modeloLista);

        vista.getBtnRegistrar().addActionListener(this);
        vista.getBtnEliminar().addActionListener(this);
        vista.getBtnCerrar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vista.getBtnRegistrar()) {
            String nombre = vista.getTxtNombreObra().trim();
            String ubicacion = vista.getTxtUbicacion().trim();
            String estado = (String) vista.getCmbEstado().getSelectedItem();
            

            if (nombre.isEmpty() || ubicacion.isEmpty()) {
                JOptionPane.showMessageDialog(
                    vista,
                    "Complete todos los campos",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String obra = String.format("%s | %s | %s", nombre, ubicacion, estado);
            modeloLista.addElement(obra);
            vista.limpiarCampos();
            
            JOptionPane.showMessageDialog(
                vista,
                "Obra registrada exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
        }

        if (e.getSource() == vista.getBtnEliminar()) {
            int indice = vista.getListObras().getSelectedIndex();
            
            if (indice < 0) {
                JOptionPane.showMessageDialog(
                    vista,
                    "Seleccione una obra",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            int conf = JOptionPane.showConfirmDialog(
                vista,
                "¿Eliminar esta obra?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
            );
            
            if (conf == 0) {
                modeloLista.removeElementAt(indice);
                JOptionPane.showMessageDialog(
                    vista,
                    "Obra eliminada exitosamente",
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
