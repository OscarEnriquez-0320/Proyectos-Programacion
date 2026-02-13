

import Vista.MainFrame;
import Controlador.ControladorPrincipal;
import javax.swing.SwingUtilities;

public class app {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Crear controlador
                ControladorPrincipal controlador = new ControladorPrincipal();
                
                // Crear vista principal (que creará las sub-vistas)
                new MainFrame(controlador);
            }
        });
    }
}