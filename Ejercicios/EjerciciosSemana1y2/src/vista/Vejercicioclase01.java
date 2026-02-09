package vista;

import javax.swing.*;
import java.awt.*;


public class Vejercicioclase01 extends JFrame {
    
   
    private JPanel contentPane;
    private JLabel lblNewLabel;
    private JTextField T1;
    private JTextField T2;
    private JButton Bagregar;
    private JButton Bsalir;
    private JTextArea Tr;
    private JScrollPane scrollPane;

    public Vejercicioclase01() {
        setTitle("Ejercicio Clase 01 - Gestión de Personas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(245, 245, 245));
        setContentPane(contentPane);
        
        lblNewLabel = new JLabel("REGISTRO DE PERSONAS");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
        lblNewLabel.setForeground(new Color(52, 73, 94));
        lblNewLabel.setBounds(130, 20, 250, 25);
        contentPane.add(lblNewLabel);
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNombre.setBounds(50, 70, 80, 25);
        contentPane.add(lblNombre);
        
        T1 = new JTextField();
        T1.setBounds(140, 70, 200, 25);
        T1.setFont(new Font("Arial", Font.PLAIN, 12));
        contentPane.add(T1);
        
        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(new Font("Arial", Font.PLAIN, 12));
        lblApellido.setBounds(50, 110, 80, 25);
        contentPane.add(lblApellido);
        
        T2 = new JTextField();
        T2.setBounds(140, 110, 200, 25);
        T2.setFont(new Font("Arial", Font.PLAIN, 12));
        contentPane.add(T2);
        
        Bagregar = new JButton("Agregar");
        Bagregar.setBounds(100, 150, 120, 30);
        Bagregar.setBackground(new Color(46, 204, 113));
        Bagregar.setForeground(Color.WHITE);
        Bagregar.setFont(new Font("Arial", Font.BOLD, 12));
        contentPane.add(Bagregar);
        
        Bsalir = new JButton("Salir");
        Bsalir.setBounds(250, 150, 120, 30);
        Bsalir.setBackground(new Color(231, 76, 60));
        Bsalir.setForeground(Color.WHITE);
        Bsalir.setFont(new Font("Arial", Font.BOLD, 12));
        contentPane.add(Bsalir);
        
        Tr = new JTextArea();
        Tr.setEditable(false);
        Tr.setFont(new Font("Monospaced", Font.PLAIN, 11));
        scrollPane = new JScrollPane(Tr);
        scrollPane.setBounds(50, 200, 390, 130);
        contentPane.add(scrollPane);
    }
    
    

    public String getT1() {
        return T1.getText();
    }
    

    public String getT2() {
        return T2.getText();
    }
    
    
    public JButton bagregar() {
        return Bagregar;
    }
    
 
    public JButton bsalir() {
        return Bsalir;
    }
    
    
    public void setT1(String texto) {
        T1.setText(texto);
    }
    
   
    public void setT2(String texto) {
        T2.setText(texto);
    }
    
    
    public void setTr(String texto) {
        Tr.setText(texto);
    }
    
    public void limparText() {
        T1.setText("");
        T2.setText("");
    }
}
