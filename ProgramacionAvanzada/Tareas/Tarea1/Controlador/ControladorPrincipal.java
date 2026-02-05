package Controlador;

import Modelo.FormEvent;
import Vista.TextPanel;
import Vista.FormPanel;
import Vista.Toolbar;

public class ControladorPrincipal {
    private TextPanel textPanel;
    private FormPanel formPanel;
    private Toolbar toolbar;
    
    public void setVistas(TextPanel textPanel, FormPanel formPanel, Toolbar toolbar) {
        this.textPanel = textPanel;
        this.formPanel = formPanel;
        this.toolbar = toolbar;
        
        configurarListeners();
    }
    
    private void configurarListeners() {
        // Listener para el formulario
        if (formPanel != null) {
            formPanel.setFormListener(new FormListener() {
                @Override
                public void formEventOccurred(FormEvent e) {
                    if (textPanel != null) {
                        String name = e.getName();
                        String occupation = e.getOccupation();
                        int ageCat = e.getAgeCategory();
                        String empCat = e.getEmploymentCategory();
                        
                        textPanel.appendText(name + ": " + occupation + ":" + ageCat + "," + empCat + "\n");
                        System.out.println(e.getGender());
                    }
                }
            });
        }
        
        // Listeners para la toolbar
        if (toolbar != null && textPanel != null) {
            toolbar.setStringListener(new StringListener() {
                @Override
                public void textEmitted(String text) {
                    textPanel.appendText(text);
                }
            });
        }
    }
}