package Controlador;

import Vista.VIPInsumo;
import Vista.Vprincipal;
import Modelo.Insumo;
import Modelo.ListaInsumo;
import Libreria.CentrarVentana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Cvipinsumo implements ActionListener {
    private VIPInsumo vista;
    private ListaInsumo modelo;
    private Vprincipal padre;
    
   
    public Cvipinsumo(VIPInsumo vista, Vprincipal padre) {
        this.vista = vista;
        this.padre = padre;
        this.modelo = new ListaInsumo();
        
        this.vista.setModeloCombo(modelo.getModeloCategorias());
        this.vista.getBtnAgregar().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);
        this.vista.getBtnCerrar().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.getBtnAgregar()) {
            String id = vista.getTxtId().trim();
            String insumo = vista.getTxtInsumo().trim();
            String categoria = vista.getCmbCategoria().getSelectedItem().toString();
            
            if(id.isEmpty() || insumo.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Error: ID e Insumo son obligatorios");
                return;
            }
            
            Insumo nuevo = new Insumo(id, insumo, categoria);
            modelo.insertar(nuevo);
            vista.setModeloLista(modelo.getModeloLista());
            vista.limpiarCampos();
            JOptionPane.showMessageDialog(vista, "Insumo agregado correctamente");
        }
        else if(e.getSource() == vista.getBtnEliminar()) {
            int indice = vista.getIndiceSeleccionado();
            
            if(indice < 0) {
                JOptionPane.showMessageDialog(vista, "Seleccione un insumo para eliminar");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(vista, 
                "¿Eliminar insumo?", "Confirmar", JOptionPane.YES_NO_OPTION);
                
            if(confirm == 0) {
                modelo.eliminar(indice);
                vista.setModeloLista(modelo.getModeloLista());
                JOptionPane.showMessageDialog(vista, "Insumo eliminado");
            }
        }
        else if(e.getSource() == vista.getBtnCerrar()) {
            vista.dispose();
            padre.setEstadoMenus(true);  
        }
    }
}