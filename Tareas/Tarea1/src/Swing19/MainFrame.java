package Swing19;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;     
import javax.swing.JMenuBar;  
import javax.swing.JMenuItem;  

public class MainFrame extends JFrame {
    
    private TextPanel textPanel;
    private Toolbar toolbar;
    private FormPanel formPanel;
    
    public MainFrame() {
        super("Hello World");
        
        setLayout(new BorderLayout());

        setJMenuBar(createMenuBar());
        
        toolbar = new Toolbar();
        textPanel = new TextPanel();
        formPanel = new FormPanel();
        
        toolbar.setStringListener(new StringListener() {
            @Override
            public void textEmitted(String text) {
                textPanel.appendText(text);
            }
        });
        
        formPanel.setFormListener(new FormListener() {
            @Override
            public void formEventOccurred(FormEvent e) {
                String name = e.getName();
                String occupation = e.getOccupation();
                int ageCat = e.getAgeCategory();
                String empCat = e.getEmploymentCategory();
                String taxId = e.getTaxId();
                boolean isCitizen = e.isUsCitizen();
                String gender = e.getGender();
                
                textPanel.appendText(name + ": " + occupation + ": " + ageCat + ", " + empCat + 
                                     ", " + isCitizen + ", " + taxId + ", " + gender + "\n");
            }
        });
        
        add(toolbar, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
        add(formPanel, BorderLayout.WEST);
        
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    

    private JMenuBar createMenuBar() {
        // 1. La barra
        JMenuBar menuBar = new JMenuBar();
        

        JMenu fileMenu = new JMenu("File");
        
        JMenuItem exportDataItem = new JMenuItem("Export Data...");
        JMenuItem importDataItem = new JMenuItem("Import Data...");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        fileMenu.add(exportDataItem);
        fileMenu.add(importDataItem);
        fileMenu.addSeparator(); 
        fileMenu.add(exitItem);
        

        JMenu windowMenu = new JMenu("Window");
        
       
        JMenu showMenu = new JMenu("Show"); 
        JMenuItem showFormItem = new JMenuItem("Person Form");
        
        showMenu.add(showFormItem);
        windowMenu.add(showMenu);   
        
        menuBar.add(fileMenu);
        menuBar.add(windowMenu);
        
        return menuBar;
    }
}