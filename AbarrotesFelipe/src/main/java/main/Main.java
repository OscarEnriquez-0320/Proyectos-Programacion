package main;


import javax.swing.*;
import Vista.VistaLogin;

public class Main {
    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
  
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VistaLogin login = new VistaLogin();
                login.setVisible(true);
            }
        });
    }
}