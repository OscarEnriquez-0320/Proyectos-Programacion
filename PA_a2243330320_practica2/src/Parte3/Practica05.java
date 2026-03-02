package Parte3;

import Libreria.Librerias;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Práctica 05 - JFrame con JDesktopPane y menú
 * Ahora llama a:
 * - InternalFrameCategorias (Actividad 2)
 * - InternalFrameObras (Actividad 3)
 */
public class Practica05 extends JFrame implements ActionListener {
    
    private JMenuBar BarraMenu;
    private JMenu Moperacion, Mconfiguracion, Msalir;
    private JMenuItem SMcategorias, SMobras, SMsalida; // Cambié "SMinsumos" por "SMobras"
    
    private JDesktopPane Escritorio;
    private Librerias libreria;
    
    public Practica05() {
        setTitle("Sistema de Administración - Práctica 05");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 700);
        
        libreria = new Librerias();
        
        configurarMenus();
        
        Escritorio = new JDesktopPane();
        Escritorio.setBackground(new Color(240, 240, 240));
        setContentPane(Escritorio);
        
        setVisible(true);
    }
    
    private void configurarMenus() {
        BarraMenu = new JMenuBar();
        this.setJMenuBar(BarraMenu);
        
        Moperacion = new JMenu("Operación");
        BarraMenu.add(Moperacion);
        
        // Menú para Categorías (Actividad 2)
        SMcategorias = new JMenuItem("Categorías");
        Moperacion.add(SMcategorias);
        SMcategorias.addActionListener(this);
        
        // Menú para Obras (Actividad 3)
        SMobras = new JMenuItem("Obras");
        Moperacion.add(SMobras);
        SMobras.addActionListener(this);
        
        Msalir = new JMenu("Salir");
        BarraMenu.add(Msalir);
        
        SMsalida = new JMenuItem("Salir del sistema");
        Msalir.add(SMsalida);
        SMsalida.addActionListener(this);
    }
    
    private void abrirInternalFrame(JInternalFrame frame) {
        // Verificar si ya está abierto
        JInternalFrame[] frames = Escritorio.getAllFrames();
        for (JInternalFrame f : frames) {
            if (f.getClass().equals(frame.getClass())) {
                try {
                    f.setSelected(true);
                    JOptionPane.showMessageDialog(this, "La ventana ya está abierta");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return;
            }
        }
        
        // Si no está abierto, agregarlo
        Escritorio.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SMcategorias) {
            // Abrir InternalFrame de Categorías (Actividad 2)
            InternalFrameCategorias frameCategorias = new InternalFrameCategorias(this);
            abrirInternalFrame(frameCategorias);
            
        } else if (e.getSource() == SMobras) {
            // Abrir InternalFrame de Obras (Actividad 3)
            InternalFrameObras frameObras = new InternalFrameObras(this);
            abrirInternalFrame(frameObras);
            
        } else if (e.getSource() == SMsalida) {
            int respuesta = JOptionPane.showConfirmDialog(
                this, 
                "¿Está seguro de salir del sistema?", 
                "Confirmar salida", 
                JOptionPane.YES_NO_OPTION
            );
            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Practica05();
        });
    }
}