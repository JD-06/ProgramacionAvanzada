package view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Practica01_03 extends JDialog {
    public static void main(String[] args) {
        try {
            Practica01_03 dialog = new Practica01_03();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Practica01_03() {
        setTitle("Dialog Practica01_03");
        setBounds(100, 100, 300, 200);
        setModal(true);
        JLabel label = new JLabel("Esta es una JDialog Modal", SwingConstants.CENTER);
        getContentPane().add(label);
    }
}
