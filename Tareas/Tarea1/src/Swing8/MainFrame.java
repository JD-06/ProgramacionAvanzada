package Swing8;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
    
    private TextPanel textPanel;
    private Toolbar toolbar;
    
    public MainFrame() {
        super("Hello World");
        
        setLayout(new BorderLayout());
        
        toolbar = new Toolbar();
        textPanel = new TextPanel();
        
        // --- AQUÍ ESTÁ EL CAMBIO IMPORTANTE ---
        // MainFrame crea una clase anónima que implementa StringListener.
        // Cuando la Toolbar emita texto, este código se ejecutará.
        toolbar.setStringListener(new StringListener() {
            @Override
            public void textEmitted(String text) {
                // El MainFrame recibe el texto y se lo pasa al TextPanel
                textPanel.appendText(text);
            }
        });
        
        add(toolbar, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
        
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}