package Vista;

import Controlador.ActualizadorVistas;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private JTabbedPane panelPestanas;
    
    public VentanaPrincipal() {
        iniciarComponentes();
        setTitle("Smart Punto de Venta - Tienda de Abarrotes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 750);
        setLocationRelativeTo(null);
        
        // Registrar esta ventana en el actualizador
        ActualizadorVistas.setVentanaPrincipal(this);
    }
    
    private void iniciarComponentes() {
        panelPestanas = new JTabbedPane();
        panelPestanas.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        panelPestanas.addTab("🏪 INFORMACIÓN", new VistaInformacionEmpresa());
        panelPestanas.addTab("🛒 PUNTO DE VENTA", new VistaPuntoVenta());
        panelPestanas.addTab("📦 PRODUCTOS", new VistaProductos());
        panelPestanas.addTab("📊 INVENTARIO", new VistaInventario());
        panelPestanas.addTab("🏢 PROVEEDORES", new VistaProveedores());
        panelPestanas.addTab("📈 REPORTES", new VistaReportes());
        
        add(panelPestanas);
    }
}