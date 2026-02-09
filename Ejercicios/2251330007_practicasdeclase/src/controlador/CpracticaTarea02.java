package controlador;

import vista.VPractica01_Tarea02;
import vista.VInternalGestionInsumos;
import vista.VInternalGestionObras;
import utilidades.BibliotecaFrames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class CpracticaTarea02 implements ActionListener {
    

    private VPractica01_Tarea02 ventana;

    public CpracticaTarea02() {
        ventana = new VPractica01_Tarea02();
        ventana.setVisible(true);
        

        ventana.getMiPresupuestacion().addActionListener(this);
        ventana.getMiObras().addActionListener(this);
        ventana.getMiInsumos().addActionListener(this);
        ventana.getMiConfiguracion().addActionListener(this);
        ventana.getMiReportes().addActionListener(this);
        ventana.getMiSalida().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ventana.setEstadoMenus(false);

        if (e.getSource() == ventana.getMiInsumos()) {

            if (!BibliotecaFrames.estaAbierta(
                    ventana.getEscritorio(), 
                    VInternalGestionInsumos.class)) {

                VInternalGestionInsumos vistaInsumos = new VInternalGestionInsumos();
                new CInternalGestionInsumos(vistaInsumos);
                

                ventana.getEscritorio().add(vistaInsumos);
                BibliotecaFrames.centrarFrame(vistaInsumos, ventana.getEscritorio());
                vistaInsumos.setVisible(true);
            }
        }

        if (e.getSource() == ventana.getMiObras()) {

            if (!BibliotecaFrames.estaAbierta(
                    ventana.getEscritorio(), 
                    VInternalGestionObras.class)) {

                VInternalGestionObras vistaObras = new VInternalGestionObras();
                new CInternalGestionObras(vistaObras);

                ventana.getEscritorio().add(vistaObras);
                BibliotecaFrames.centrarFrame(vistaObras, ventana.getEscritorio());
                vistaObras.setVisible(true);
            }
        }
        

       
        

        if (e.getSource() == ventana.getMiSalida()) {
            int opcion = JOptionPane.showConfirmDialog(
                ventana.getEscritorio(),
                "¿Desea salir del sistema?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION
            );
            
            if (opcion == 0) {
                ventana.dispose();
                System.exit(0);
            }
        }
        
        ventana.setEstadoMenus(true);
    }
}
