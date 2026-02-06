package Swing19;

import java.util.EventObject;

public class FormEvent extends EventObject {
    
    private String name;
    private String occupation;
    private int ageCategory;
    private String empCat;
    private String taxId;
    private boolean usCitizen;
    private String gender; 

    public FormEvent(Object source, String name, String occupation, int ageCategory, 
                     String empCat, String taxId, boolean usCitizen, String gender) {
        super(source);
        this.name = name;
        this.occupation = occupation;
        this.ageCategory = ageCategory;
        this.empCat = empCat;
        this.taxId = taxId;
        this.usCitizen = usCitizen;
        this.gender = gender;
    }

    // Getters...
    public String getName() { return name; }
    public String getOccupation() { return occupation; }
    public int getAgeCategory() { return ageCategory; }
    public String getEmploymentCategory() { return empCat; }
    public String getTaxId() { return taxId; }
    public boolean isUsCitizen() { return usCitizen; }
    
    public String getGender() {
        return gender;
    }
}