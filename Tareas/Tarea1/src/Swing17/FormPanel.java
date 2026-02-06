package Swing17;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox; // Importamos Checkbox
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {
    
    private JLabel nameLabel;
    private JLabel occupationLabel;
    private JTextField nameField;
    private JTextField occupationField;
    private JButton okBtn;
    private FormListener formListener;
    private JList ageList;
    private JComboBox empCombo;
    
    // --- NUEVOS COMPONENTES ---
    private JCheckBox citizenCheck;
    private JTextField taxField;
    private JLabel taxLabel;
    
    public FormPanel() {
        Dimension dim = getPreferredSize();
        dim.width = 250;
        setPreferredSize(dim);
        
        nameLabel = new JLabel("Name: ");
        occupationLabel = new JLabel("Occupation: ");
        nameField = new JTextField(10);
        occupationField = new JTextField(10);
        
        ageList = new JList();
        DefaultListModel ageModel = new DefaultListModel();
        ageModel.addElement(new AgeCategory(0, "Under 18"));
        ageModel.addElement(new AgeCategory(1, "18 to 65"));
        ageModel.addElement(new AgeCategory(2, "65 or over"));
        ageList.setModel(ageModel);
        ageList.setPreferredSize(new Dimension(110, 66));
        ageList.setBorder(BorderFactory.createEtchedBorder());
        ageList.setSelectedIndex(1);
        
        empCombo = new JComboBox();
        DefaultComboBoxModel empModel = new DefaultComboBoxModel();
        empModel.addElement("Employed");
        empModel.addElement("Self-employed");
        empModel.addElement("Unemployed");
        empCombo.setModel(empModel);
        empCombo.setSelectedIndex(0);
        empCombo.setEditable(true);
        
        // --- INICIALIZACIÓN DE NUEVOS COMPONENTES ---
        citizenCheck = new JCheckBox();
        taxField = new JTextField(10);
        taxLabel = new JLabel("Tax ID: ");
        
        // Configuramos el estado inicial: Deshabilitados
        taxField.setEnabled(false);
        taxLabel.setEnabled(false);
        
        // --- LÓGICA DE INTERACTIVIDAD ---
        citizenCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Verificamos si está marcado
                boolean isTicked = citizenCheck.isSelected();
                // Habilitamos o deshabilitamos según el estado del check
                taxLabel.setEnabled(isTicked);
                taxField.setEnabled(isTicked);
            }
        });
        
        okBtn = new JButton("OK");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String occupation = occupationField.getText();
                AgeCategory ageCat = (AgeCategory) ageList.getSelectedValue();
                String empCat = (String)empCombo.getSelectedItem();
                
                // Obtenemos los nuevos valores
                String taxId = taxField.getText();
                boolean usCitizen = citizenCheck.isSelected();
                
                // Pasamos todo al evento
                FormEvent ev = new FormEvent(this, name, occupation, ageCat.getId(), empCat, taxId, usCitizen);
                
                if(formListener != null) {
                    formListener.formEventOccurred(ev);
                }
            }
        });
        
        Border innerBorder = BorderFactory.createTitledBorder("Add Person");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        
        layoutComponents();
    }
    
    public void setFormListener(FormListener listener) {
        this.formListener = listener;
    }
    
    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        
        int gridy = 0;
        

        gc.gridy = gridy++; 
        gc.weightx = 1; gc.weighty = 0.1;
        
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE; gc.anchor = GridBagConstraints.LINE_END; gc.insets = new Insets(0, 0, 0, 5);
        add(nameLabel, gc);
        
        gc.gridx = 1; gc.gridy = 0; gc.insets = new Insets(0, 0, 0, 0); gc.anchor = GridBagConstraints.LINE_START;
        add(nameField, gc);
        

        gc.gridy = gridy++;
        gc.weightx = 1; gc.weighty = 0.1;
        
        gc.gridx = 0; gc.anchor = GridBagConstraints.LINE_END; gc.insets = new Insets(0, 0, 0, 5);
        add(occupationLabel, gc);
        
        gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = new Insets(0, 0, 0, 0);
        add(occupationField, gc);

        gc.gridy = gridy++;
        gc.weightx = 1; gc.weighty = 0.2;
        
        gc.gridx = 0; gc.anchor = GridBagConstraints.FIRST_LINE_END; gc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("Age: "), gc);
        
        gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(ageList, gc);
        
 
        gc.gridy = gridy++;
        gc.weightx = 1; gc.weighty = 0.2;
        
        gc.gridx = 0; gc.anchor = GridBagConstraints.FIRST_LINE_END; gc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("Employment: "), gc);
        
        gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(empCombo, gc);

        gc.gridy = gridy++;
        gc.weightx = 1; gc.weighty = 0.2;
        
        gc.gridx = 0; gc.anchor = GridBagConstraints.FIRST_LINE_END; gc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("US Citizen: "), gc);
        
        gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(citizenCheck, gc);
        

        gc.gridy = gridy++;
        gc.weightx = 1; gc.weighty = 0.2;
        
        gc.gridx = 0; gc.anchor = GridBagConstraints.FIRST_LINE_END; gc.insets = new Insets(0, 0, 0, 5);
        add(taxLabel, gc); 
        
        gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(taxField, gc);


        gc.gridy = gridy++;
        gc.weightx = 1; gc.weighty = 2.0;
        
        gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(okBtn, gc);
    }
}
