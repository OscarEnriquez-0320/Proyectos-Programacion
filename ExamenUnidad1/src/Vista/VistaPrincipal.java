package Vista;

import Controlador.InventarioControlador;
import Controlador.ProductoControlador;
import Controlador.PuntoVentaControlador;
import Modelo.GestionInventario;
import Modelo.GestionProducto;
import datos.ArchivoInventario;
import datos.ArchivoProducto;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipal extends JFrame {
    private JDesktopPane desktopPane;
    private GestionProducto modeloProductos;
    private GestionInventario modeloInventario;

    public VistaPrincipal() {
        setTitle("Sistema de Gestión - Tienda de Abarrotes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);


        inicializarModelos();

       
        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        
        crearMenu();
    }

    private void inicializarModelos() {
      
        modeloProductos = new GestionProducto();
        modeloProductos.getLista().addAll(ArchivoProducto.importarCSV());
        if (modeloProductos.getLista().isEmpty()) {
            modeloProductos.cargarDatosIniciales();
            ArchivoProducto.exportarCSV(modeloProductos.getLista());
        }

       
        modeloInventario = new GestionInventario();
        modeloInventario.getLista().addAll(ArchivoInventario.importarCSV());
        
       
        if (modeloInventario.getLista().isEmpty()) {
            modeloInventario.sincronizarConProductos(modeloProductos.getLista());
            ArchivoInventario.exportarCSV(modeloInventario.getLista());
        }
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);
        
      
        JMenu menuCatalogos = new JMenu("Catálogos");
        
        JMenuItem itemProductos = new JMenuItem("Productos");
        itemProductos.addActionListener(e -> abrirVistaProductos());
        menuCatalogos.add(itemProductos);
        
        JMenuItem itemInventario = new JMenuItem("Inventario");
        itemInventario.addActionListener(e -> abrirVistaInventario());
        menuCatalogos.add(itemInventario);
        
        JMenuItem itemPuntoVenta = new JMenuItem("Punto de Venta");
        itemPuntoVenta.addActionListener(e -> abrirVistaPuntoVenta());
        menuCatalogos.add(itemPuntoVenta);
        
      
        
        menuBar.add(menuArchivo);
        menuBar.add(menuCatalogos);
      
        
        setJMenuBar(menuBar);
        
    }
    

    private void abrirVistaProductos() {
      
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof VistaProductos) {
                try {
                    frame.setSelected(true);
                } catch (java.beans.PropertyVetoException e) {
                    e.printStackTrace();
                }
                return;
            }
        }

      
        VistaProductos vista = new VistaProductos();
        
        
        new ProductoControlador(vista, modeloProductos);
        
        desktopPane.add(vista);
        vista.setVisible(true);
    }

    private void abrirVistaInventario() {
        
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof VistaInventario) {
                try {
                    frame.setSelected(true);
                } catch (java.beans.PropertyVetoException e) {
                    e.printStackTrace();
                }
                return;
            }
        }

        
        VistaInventario vista = new VistaInventario();
        
       
        new InventarioControlador(vista, modeloInventario, modeloProductos);
        
        desktopPane.add(vista);
        vista.setVisible(true);
    }

    private void abrirVistaPuntoVenta() {
       
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof VistaPuntoVenta) {
                try {
                    frame.setSelected(true);
                } catch (java.beans.PropertyVetoException e) {
                    e.printStackTrace();
                }
                return;
            }
        }

        
        VistaPuntoVenta vista = new VistaPuntoVenta();
        
       
         new PuntoVentaControlador(vista, modeloInventario, modeloProductos);
        
        desktopPane.add(vista);
        vista.setVisible(true);
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
               
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                
                UIManager.put("InternalFrame.activeTitleBackground", new Color(0, 120, 215));
                UIManager.put("InternalFrame.activeTitleForeground", Color.WHITE);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            VistaPrincipal ventana = new VistaPrincipal();
            ventana.setVisible(true);
        });
    }
}