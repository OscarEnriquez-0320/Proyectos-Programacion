package Modelo;
import java.util.EventObject;

public class FormEvent extends EventObject {
    private String name;
    private String occupation;
    private int ageCategory;
    private String empCat;
    private String taxtId;
    private boolean usCitizen;
    private String gender;

    public FormEvent(Object source) {
        super(source);
    }
    
    public FormEvent(Object source, String name, String occupation, int AgeCat, 
                    String empCat, String taxtId, boolean usCitizen, String gender) {
        super(source);
        this.name = name;
        this.occupation = occupation;
        this.ageCategory = AgeCat;
        this.empCat = empCat;
        this.taxtId = taxtId;
        this.usCitizen = usCitizen;
        this.gender = gender;
    }
    
    public String getGender() { return gender; }
    public String getTaxtId() { return taxtId; }
    public boolean isUsCitizen() { return usCitizen; }
    public String getName() { return name; }
    public String getOccupation() { return occupation; }
    public int getAgeCategory() { return ageCategory; }
    public String getEmploymentCategory() { return empCat; }
}