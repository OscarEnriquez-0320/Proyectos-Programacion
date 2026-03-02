package ParteFinal;

import Libreria.Librerias;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ACTIVIDAD 4 - Página 30
 * Programa Principal con CardLayout y DesktopPane
 */
public class MenuPrincipal extends JFrame implements ActionListener {
    
    private JMenuBar BarraMenu;
    private JMenu Mcatalogos, Msalir;
    private JMenuItem MICategorias, MIInsumos, MIObras, MISalida;
    
    private JDesktopPane Escritorio;
    private Librerias libreria;
    
    public MenuPrincipal() {
        setTitle("Sistema de Administración - Actividad 4");
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
        
        Mcatalogos = new JMenu("Catálogos");
        BarraMenu.add(Mcatalogos);
        
        MICategorias = new JMenuItem("Categorías");
        MICategorias.addActionListener(this);
        Mcatalogos.add(MICategorias);
        
        MIInsumos = new JMenuItem("Insumos");
        MIInsumos.addActionListener(this);
        Mcatalogos.add(MIInsumos);
        
        MIObras = new JMenuItem("Obras");
        MIObras.addActionListener(this);
        Mcatalogos.add(MIObras);
        
        Msalir = new JMenu("Salir");
        BarraMenu.add(Msalir);
        
        MISalida = new JMenuItem("Salir del sistema");
        MISalida.addActionListener(this);
        Msalir.add(MISalida);
    }
    
    private void abrirInternalFrame(JInternalFrame frame) {
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
        
        Escritorio.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == MICategorias) {
            InternalFrameCategorias frame = new InternalFrameCategorias(this);
            abrirInternalFrame(frame);
            
        } else if (e.getSource() == MIInsumos) {
            InternalFrameInsumos frame = new InternalFrameInsumos(this);
            abrirInternalFrame(frame);
            
        } else if (e.getSource() == MIObras) {
            InternalFrameObras frame = new InternalFrameObras(this);
            abrirInternalFrame(frame);
            
        } else if (e.getSource() == MISalida) {
            int respuesta = JOptionPane.showConfirmDialog(
                this, 
                "¿Está seguro de salir?", 
                "Confirmar", 
                JOptionPane.YES_NO_OPTION
            );
            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal();
        });
    }
}