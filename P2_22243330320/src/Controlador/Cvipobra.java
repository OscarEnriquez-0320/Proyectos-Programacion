package Controlador;

import Vista.VIPObra;
import Vista.Vprincipal;
import Modelo.Obra;
import Modelo.ListaObra;
import Libreria.CentrarVentana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Cvipobra implements ActionListener {
    private VIPObra vista;
    private ListaObra modelo;
    private Vprincipal padre;
    
    public Cvipobra(VIPObra vista, Vprincipal padre) {
        this.vista = vista;
        this.padre = padre;
        this.modelo = new ListaObra();
        
        this.vista.getBtnAgregar().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);
        this.vista.getBtnCerrar().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.getBtnAgregar()) {
            String id = vista.getTxtId().trim();
            String nombre = vista.getTxtNombre().trim();
            String fechaInicio = vista.getTxtFechaInicio().trim();
            String fechaFin = vista.getTxtFechaFin().trim();
            
            if(id.isEmpty() || nombre.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Error: Todos los campos son obligatorios");
                return;
            }
            
            Obra nueva = new Obra(id, nombre, fechaInicio, fechaFin);
            modelo.insertar(nueva);
            vista.setModeloLista(modelo.getModeloLista());
            vista.limpiarCampos();
            JOptionPane.showMessageDialog(vista, "Obra agregada correctamente");
        }
        else if(e.getSource() == vista.getBtnEliminar()) {
            int indice = vista.getIndiceSeleccionado();
            
            if(indice < 0) {
                JOptionPane.showMessageDialog(vista, "Seleccione una obra para eliminar");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(vista, 
                "¿Eliminar obra?", "Confirmar", JOptionPane.YES_NO_OPTION);
                
            if(confirm == 0) {
                modelo.eliminar(indice);
                vista.setModeloLista(modelo.getModeloLista());
                JOptionPane.showMessageDialog(vista, "Obra eliminada");
            }
        }
        else if(e.getSource() == vista.getBtnCerrar()) {
            vista.dispose();
            padre.setEstadoMenus(true);
        }
    }
}