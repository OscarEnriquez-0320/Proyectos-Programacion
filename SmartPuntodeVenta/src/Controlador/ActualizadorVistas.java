package Controlador;

import Vista.*;
import javax.swing.*;
import java.awt.*;

public class ActualizadorVistas {
    
    private static VentanaPrincipal ventanaPrincipal;
    
    public static void setVentanaPrincipal(VentanaPrincipal ventana) {
        ventanaPrincipal = ventana;
    }
    
    public static void refrescarTodasLasVistas() {
        if (ventanaPrincipal == null) return;
        
        // Refrescar cada pestaña
        Component[] componentes = ventanaPrincipal.getContentPane().getComponents();
        for (Component comp : componentes) {
            if (comp instanceof JTabbedPane) {
                JTabbedPane tabPane = (JTabbedPane) comp;
                for (int i = 0; i < tabPane.getTabCount(); i++) {
                    Component tab = tabPane.getComponentAt(i);
                    refrescarVista(tab);
                }
            }
        }
    }
    
    private static void refrescarVista(Component vista) {
        if (vista instanceof VistaProductos) {
            ((VistaProductos) vista).recargarDatos();
        } else if (vista instanceof VistaInventario) {
            ((VistaInventario) vista).recargarDatos();
        } else if (vista instanceof VistaReportes) {
            ((VistaReportes) vista).recargarDatos();
        } else if (vista instanceof VistaPuntoVenta) {
            ((VistaPuntoVenta) vista).recargarProductos();
        } else if (vista instanceof VistaProveedores) {
            ((VistaProveedores) vista).recargarDatos();
        }
    }
}