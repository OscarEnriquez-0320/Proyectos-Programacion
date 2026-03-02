package Libreria;

import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;

public class CentrarVentana {
    
   
    public static void centrar(JInternalFrame frame, JDesktopPane desktop) {
        int desktopWidth = desktop.getWidth();
        int desktopHeight = desktop.getHeight();
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        
        int x = (desktopWidth - frameWidth) / 2;
        int y = (desktopHeight - frameHeight) / 2;
        
       
        if (x < 0) x = 50;
        if (y < 0) y = 50;
        
        frame.setLocation(x, y);
    }
    
   
    public static void maximizar(JInternalFrame frame) {
        try {
            frame.setMaximum(true);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static void centrarYMaximizar(JInternalFrame frame, JDesktopPane desktop) {
        centrar(frame, desktop);
        maximizar(frame);
    }
}