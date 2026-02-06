package Proyecto1;

import javax.swing.*;
import java.awt.*;

public class VistaLogin extends JFrame {


    private JPanel panelFondo;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblPassword;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancelar;
    private JProgressBar progressBar;

    public VistaLogin() {
        initComponents();
        configurarVentana();
    }

    private void initComponents() {

        panelFondo = new JPanel();
        lblTitulo = new JLabel("Acceso al Sistema");
        lblUsuario = new JLabel("Usuario:");
        lblPassword = new JLabel("Contraseña:");
        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Ingresar");
        btnCancelar = new JButton("Salir");
        progressBar = new JProgressBar();


        panelFondo.setLayout(null);


        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(100, 20, 200, 30);
        
        lblUsuario.setBounds(50, 70, 80, 25);
        txtUsuario.setBounds(140, 70, 150, 25);
        
        lblPassword.setBounds(50, 110, 80, 25);
        txtPassword.setBounds(140, 110, 150, 25);
        
        btnLogin.setBounds(50, 160, 100, 30);
        btnCancelar.setBounds(190, 160, 100, 30);
        
        progressBar.setBounds(20, 210, 300, 15);
        progressBar.setStringPainted(true); 


        panelFondo.add(lblTitulo);
        panelFondo.add(lblUsuario);
        panelFondo.add(txtUsuario);
        panelFondo.add(lblPassword);
        panelFondo.add(txtPassword);
        panelFondo.add(btnLogin);
        panelFondo.add(btnCancelar);
        panelFondo.add(progressBar);


        this.add(panelFondo);
    }

    private void configurarVentana() {
        this.setTitle("Login");
        this.setSize(360, 280);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); 
        this.setResizable(false);
    }


    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }
}