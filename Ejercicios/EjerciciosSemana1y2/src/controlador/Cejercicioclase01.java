package controlador;

import vista.Vejercicioclase01;
import modelo.Lista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Cejercicioclase01 {
    
    private Vejercicioclase01 vista;
    private Lista lista;
    

    public Cejercicioclase01() {
        vista = new Vejercicioclase01();
        lista = new Lista();
        
        vista.setVisible(true);
        
        
        vista.bsalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        });
        
        vista.bagregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String nombre = vista.getT1();
                String apellido = vista.getT2();
                
                lista.insertar(nombre, apellido);
                
                vista.limparText();
                
                vista.setTr(lista.info());
            }
        });
    }
}
