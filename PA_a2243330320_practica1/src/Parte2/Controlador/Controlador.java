package Parte2.Controlador;

import javax.swing.JOptionPane;


import Parte2.Modelo.Categoria;
import Parte2.Modelo.Insumo;
import Parte2.Modelo.ListaInsumos;
import Parte2.Vista.Practica03_a;

public class Controlador {

    private Practica03_a vista;
    private ListaInsumos listaInsumos;

    public Controlador(Practica03_a vista, ListaInsumos listaInsumos) {
        this.vista = vista;
        this.listaInsumos = listaInsumos;
    }

   
	public void eliminar() {
        Object[] opciones = listaInsumos.idinsumos();
        String id = (String) JOptionPane.showInputDialog(
            vista,
            "Seleccione una opción:",
            "Eliminación de Insumos",
            JOptionPane.PLAIN_MESSAGE,
            null,
            opciones,
            opciones.length > 0 ? opciones[0] : null
        );

        if (id != null && !id.isEmpty()) {
            if (!listaInsumos.eliminarInsumoPorId(id)) {
                JOptionPane.showMessageDialog(vista, "No existe este id");
            } else {
                vista.actualizarArea(listaInsumos.toString());
            }
        }
    }

    public void altas() {
        if (vista.getBagregar().getText().equals("Agregar")) {
            vista.prepararAlta();
        } else {
            if (esDatosCompletos()) {
                String id = vista.getTid().getText().trim();
                String insumo = vista.getTinsumo().getText().trim();
                String idcategoria = ((Categoria) vista.getComboCategoria().getSelectedItem()).getIdcategoria().trim();

                Insumo nodo = new Insumo(id, insumo, idcategoria);
                if (!listaInsumos.agregarInsumo(nodo)) {
                    JOptionPane.showMessageDialog(vista, "Lo siento, el id " + id + " ya existe. Está asignado a " + listaInsumos.buscarInsumo(id));
                } else {
                    vista.actualizarArea(listaInsumos.toString());
                }
            }
            vista.volverAlInicio();
        }
    }

    private boolean esDatosCompletos() {
        if (vista.getTinsumo().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe ingresar un insumo");
            return false;
        }
        if (vista.getComboCategoria().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar una categoría");
            return false;
        }
        return true;
    }
}