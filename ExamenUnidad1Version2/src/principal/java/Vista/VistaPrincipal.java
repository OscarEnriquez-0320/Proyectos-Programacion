package principal.java.Vista;

import principal.java.Controlador.*;
import principal.java.Modelo.*;
import principal.java.Reportes.ReporteExcel;
import principal.java.datos.ArchivoInventario;
import principal.java.datos.ArchivoProductoJSON;

import javax.swing.*;

import Vista.VistaInventario;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class VistaPrincipal extends JFrame {
    private JDesktopPane desktopPane;
    private GestionProducto modeloProductos;
    private GestionInventario modeloInventario;
    private GestionVentas modeloVentas;

    public VistaPrincipal() {
        setTitle("Surti-Tienda® | Tienda de Abarrotes en Línea");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 750);
        setLocationRelativeTo(null);

        inicializarModelos();
        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);
        crearMenu();
    }

    private void inicializarModelos() {
        modeloProductos = new GestionProducto();
        modeloProductos.getLista().addAll(ArchivoProductoJSON.importarJSON());
        if (modeloProductos.getLista().isEmpty()) {
            modeloProductos.cargarDatosIniciales();
            ArchivoProductoJSON.exportarJSON(modeloProductos.getLista());
        }

        modeloInventario = new GestionInventario();
        modeloInventario.getLista().addAll(ArchivoInventario.importarCSV());
        if (modeloInventario.getLista().isEmpty()) {
            modeloInventario.sincronizarConProductos(modeloProductos.getLista());
            ArchivoInventario.exportarCSV(modeloInventario.getLista());
        }

        modeloVentas = new GestionVentas();
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
        JMenuItem itemInventario = new JMenuItem("Inventario");
        itemInventario.addActionListener(e -> abrirVistaInventario());
        JMenuItem itemPuntoVenta = new JMenuItem("Punto de Venta");
        itemPuntoVenta.addActionListener(e -> abrirVistaPuntoVenta());
        menuCatalogos.add(itemProductos);
        menuCatalogos.add(itemInventario);
        menuCatalogos.add(itemPuntoVenta);

        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemReporteGeneral = new JMenuItem("Listado General de Productos");
        itemReporteGeneral.addActionListener(e -> generarReporteGeneral());
        JMenuItem itemReportePorCategoria = new JMenuItem("Listado por Categoría");
        itemReportePorCategoria.addActionListener(e -> generarReportePorCategoria());
        menuReportes.add(itemReporteGeneral);
        menuReportes.add(itemReportePorCategoria);

        menuBar.add(menuArchivo);
        menuBar.add(menuCatalogos);
        menuBar.add(menuReportes);

        setJMenuBar(menuBar);
    }

    private void abrirVistaProductos() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof VistaProductos) {
                try { frame.setSelected(true); } catch (Exception e) {}
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
                try { frame.setSelected(true); } catch (Exception e) {}
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
                try { frame.setSelected(true); } catch (Exception e) {}
                return;
            }
        }
        VistaPuntoVenta vista = new VistaPuntoVenta();
        new PuntoVentaControlador(vista, modeloInventario, modeloProductos);
        desktopPane.add(vista);
        vista.setVisible(true);
    }

    private void generarReporteGeneral() {
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("productos_general.xlsx"));
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                ReporteExcel.generarReporteGeneral(modeloProductos, fc.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Reporte generado correctamente");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void generarReportePorCategoria() {
        String[] categorias = {"Abarrotes", "Bebidas", "Lácteos", "Frutas y Verduras", 
                               "Carnes", "Salchichonería", "Panadería", "Limpieza", 
                               "Cuidado Personal", "Snacks", "Mascotas"};
        String categoria = (String) JOptionPane.showInputDialog(this, 
            "Seleccione categoría:", "Reporte por Categoría",
            JOptionPane.QUESTION_MESSAGE, null, categorias, categorias[0]);
        
        if (categoria != null) {
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File("productos_" + categoria + ".xlsx"));
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    ReporteExcel.generarReportePorCategoria(modeloProductos, categoria, fc.getSelectedFile().getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "Reporte generado correctamente");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {}
            new VistaPrincipal().setVisible(true);
        });
    }
}