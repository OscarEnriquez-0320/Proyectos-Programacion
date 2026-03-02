package Controlador;

import Vista.Vprincipal;
import Vista.VIPInsumo;
import Vista.VIPObra;
import Libreria.CentrarVentana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Cvprincipal implements ActionListener {
    private Vprincipal padre;
    
    public Cvprincipal() {
        padre = new Vprincipal("Sistema de Gestión - Insumos y Obras");
        padre.setVisible(true);
        
        padre.getMiInsumos().addActionListener(this);
        padre.getMiObras().addActionListener(this);   
        padre.getMiSalida().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == padre.getMiInsumos()) {
            abrirVentanaInsumos();
        }
        else if(e.getSource() == padre.getMiObras()) {  
            abrirVentanaObras();
        }
        else if(e.getSource() == padre.getMiSalida()) {
            int opcion = JOptionPane.showConfirmDialog(padre, 
                "¿Desea salir del programa?", "Salir", 
                JOptionPane.YES_NO_OPTION);
            if(opcion == 0) {
                padre.dispose();
                System.exit(0);
            }
        }
    }
    
    private void abrirVentanaInsumos() {
        padre.setEstadoMenus(false);  
        
        JInternalFrame[] frames = padre.getEscritorio().getAllFrames();
        for(JInternalFrame frame : frames) {
            if(frame instanceof VIPInsumo) {
                try {
                    frame.setSelected(true);
                    padre.setEstadoMenus(true);  
                    return;
                } catch(Exception ex) {
                    return;
                }
            }
        }
        
        VIPInsumo ventana = new VIPInsumo();
        Cvipinsumo controlador = new Cvipinsumo(ventana, padre);  
        
        padre.getEscritorio().add(ventana);
        CentrarVentana.centrar(ventana, padre.getEscritorio());
        ventana.setVisible(true);
    }
    
    private void abrirVentanaObras() {
        padre.setEstadoMenus(false);  
        
        JInternalFrame[] frames = padre.getEscritorio().getAllFrames();
        for(JInternalFrame frame : frames) {
            if(frame instanceof VIPObra) {
                try {
                    frame.setSelected(true);
                    padre.setEstadoMenus(true);  
                    return;
                } catch(Exception ex) {
                    return;
                }
            }
        }
        
        VIPObra ventana = new VIPObra();
        Cvipobra controlador = new Cvipobra(ventana, padre);
        
        padre.getEscritorio().add(ventana);
        CentrarVentana.centrar(ventana, padre.getEscritorio());
        ventana.setVisible(true);
    }
}