package vista;

import javax.swing.*;
import java.awt.*;


public class Vejercicioclase02 extends JFrame {
    
 
    private JPanel contentPane;
    private JComboBox<String> comboBox;
    private JList<String> listadesplegable;
    private JScrollPane scrollPane;
    private JTextField Tdato;
    private JButton Bagregar;
    private JButton Beliminar;
    private JButton Bsalir;
    
 
    public Vejercicioclase02() {
     
        setTitle("Ejercicio Clase 02 - Gestión de Equipos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 450);
        

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(236, 240, 241));
        setContentPane(contentPane);
     
        JLabel lblTitulo = new JLabel("GESTIÓN DE EQUIPOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(41, 128, 185));
        lblTitulo.setBounds(150, 20, 200, 25);
        contentPane.add(lblTitulo);

        JLabel lblDato = new JLabel("Equipo:");
        lblDato.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDato.setBounds(50, 70, 80, 25);
        contentPane.add(lblDato);
        
        Tdato = new JTextField();
        Tdato.setBounds(140, 70, 200, 25);
        Tdato.setFont(new Font("Arial", Font.PLAIN, 12));
        contentPane.add(Tdato);

        JLabel lblCombo = new JLabel("ComboBox:");
        lblCombo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblCombo.setBounds(50, 120, 80, 25);
        contentPane.add(lblCombo);
        
        comboBox = new JComboBox<>();
        comboBox.setBounds(140, 120, 200, 25);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 11));
        contentPane.add(comboBox);

        JLabel lblLista = new JLabel("Lista:");
        lblLista.setFont(new Font("Arial", Font.PLAIN, 12));
        lblLista.setBounds(50, 170, 80, 25);
        contentPane.add(lblLista);
        
        listadesplegable = new JList<>();
        listadesplegable.setFont(new Font("Arial", Font.PLAIN, 11));
        scrollPane = new JScrollPane(listadesplegable);
        scrollPane.setBounds(140, 170, 200, 120);
        contentPane.add(scrollPane);
        
        Bagregar = new JButton("Agregar");
        Bagregar.setBounds(50, 320, 120, 30);
        Bagregar.setBackground(new Color(46, 204, 113));
        Bagregar.setForeground(Color.WHITE);
        Bagregar.setFont(new Font("Arial", Font.BOLD, 11));
        contentPane.add(Bagregar);
        
        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(190, 320, 120, 30);
        Beliminar.setForeground(Color.WHITE);
        Beliminar.setFont(new Font("Arial", Font.BOLD, 11));
        contentPane.add(Beliminar);
        
        Bsalir = new JButton("Salir");
        Bsalir.setBounds(330, 320, 120, 30);
        Bsalir.setBackground(new Color(149, 165, 166));
        Bsalir.setForeground(Color.WHITE);
        Bsalir.setFont(new Font("Arial", Font.BOLD, 11));
        contentPane.add(Bsalir);
    }
    

    public JTextField getTdato() {
        return Tdato;
    }
    

    public JComboBox<String> getComboBox() {
        return comboBox;
    }
    

    public JList<String> getListadesplegable() {
        return listadesplegable;
    }
    

    public JButton getBagregar() {
        return Bagregar;
    }

    public JButton getBeliminar() {
        return Beliminar;
    }

    public JButton getBsalir() {
        return Bsalir;
    }

    public void limpiarText() {
        Tdato.setText("");
    }
}
