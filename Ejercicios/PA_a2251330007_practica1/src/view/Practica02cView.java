package view;

import javax.swing.*;

public class Practica02cView extends JFrame {
    public JComboBox<String> comboBox;
    public JButton Bsalir;
    public JTextField campoTexto;
    public JPasswordField campoPassword;
    public JTextArea areaTexto;
    public JFormattedTextField campoFormateado;
    public JSpinner spinner;
    public JSlider slider;

    public Practica02cView() {
        setTitle("Componentes de entrada MVC");
        setSize(442, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel etiqueta1 = new JLabel("JButton:");
        etiqueta1.setBounds(0, 3, 178, 36);
        Bsalir = new JButton("Haz clic para Salir");
        Bsalir.setBounds(188, 3, 172, 36);
        panel.add(etiqueta1);
        panel.add(Bsalir);

        JLabel etiqueta2 = new JLabel("JTextField:");
        etiqueta2.setBounds(0, 49, 148, 36);
        campoTexto = new JTextField();
        campoTexto.setBounds(188, 50, 172, 36);
        panel.add(etiqueta2);
        panel.add(campoTexto);

        JLabel etiqueta3 = new JLabel("JPasswordField:");
        etiqueta3.setBounds(0, 95, 148, 36);
        campoPassword = new JPasswordField();
        campoPassword.setBounds(188, 97, 172, 36);
        panel.add(etiqueta3);
        panel.add(campoPassword);

        JLabel etiqueta4 = new JLabel("JTextArea:");
        etiqueta4.setBounds(0, 141, 148, 36);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(188, 141, 172, 36);
        panel.add(etiqueta4);
        areaTexto = new JTextArea();
        scrollPane.setViewportView(areaTexto);
        panel.add(scrollPane);

        JLabel etiqueta5 = new JLabel("JFormattedTextField:");
        etiqueta5.setBounds(0, 187, 141, 36);
        campoFormateado = new JFormattedTextField();
        campoFormateado.setBounds(188, 187, 172, 36);
        campoFormateado.setValue(12345.67);
        panel.add(etiqueta5);
        panel.add(campoFormateado);

        JLabel etiqueta6 = new JLabel("JSpinner:");
        etiqueta6.setBounds(0, 233, 141, 36);
        spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spinner.setBounds(188, 233, 172, 36);
        panel.add(etiqueta6);
        panel.add(spinner);

        JLabel etiqueta7 = new JLabel("JSlider:");
        etiqueta7.setBounds(0, 279, 148, 36);
        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.setBounds(188, 280, 178, 36);
        slider.setMajorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        panel.add(etiqueta7);
        panel.add(slider);

        JLabel etiqueta8 = new JLabel("JComboBox:");
        etiqueta8.setBounds(0, 325, 148, 36);
        comboBox = new JComboBox<>(new String[]{"Opcion 1", "Opcion 2", "Opcion 3"});
        comboBox.setBounds(188, 325, 172, 36);
        panel.add(etiqueta8);
        panel.add(comboBox);

        getContentPane().add(panel);
    }
}

