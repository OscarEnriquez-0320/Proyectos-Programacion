package Main;

import Vista.VistaPrincipal;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
     
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    
                   
                    VistaPrincipal ventana = new VistaPrincipal();
                    ventana.setVisible(true);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                        "Error al iniciar la aplicación: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}