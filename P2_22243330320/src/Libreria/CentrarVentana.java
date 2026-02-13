package Libreria;

import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;

public class CentrarVentana {
    
    /**
     * Centra un JInternalFrame dentro del JDesktopPane
     * @param frame JInternalFrame a centrar
     * @param desktop JDesktopPane contenedor
     */
    public static void centrar(JInternalFrame frame, JDesktopPane desktop) {
        int desktopWidth = desktop.getWidth();
        int desktopHeight = desktop.getHeight();
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        
        int x = (desktopWidth - frameWidth) / 2;
        int y = (desktopHeight - frameHeight) / 2;
        
        // Si el desktop aún no tiene tamaño, usar valores por defecto
        if (x < 0) x = 50;
        if (y < 0) y = 50;
        
        frame.setLocation(x, y);
    }
    
    /**
     * Maximiza un JInternalFrame
     * @param frame JInternalFrame a maximizar
     */
    public static void maximizar(JInternalFrame frame) {
        try {
            frame.setMaximum(true);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Centra y maximiza un JInternalFrame
     * @param frame JInternalFrame a centrar y maximizar
     * @param desktop JDesktopPane contenedor
     */
    public static void centrarYMaximizar(JInternalFrame frame, JDesktopPane desktop) {
        centrar(frame, desktop);
        maximizar(frame);
    }
}