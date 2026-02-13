package Modelo;

import java.util.LinkedList;
import javax.swing.DefaultListModel;
import javax.swing.DefaultComboBoxModel;

public class ListaInsumo {
    private LinkedList<Insumo> lista;
    
    public ListaInsumo() {
        lista = new LinkedList<>();
    }
    
    public void insertar(Insumo insumo) {
        lista.add(insumo);
    }
    
    public void eliminar(int indice) {
        if(indice >= 0 && indice < lista.size()) {
            lista.remove(indice);
        }
    }
    
    public Insumo obtener(int indice) {
        if(indice >= 0 && indice < lista.size()) {
            return lista.get(indice);
        }
        return null;
    }
    
    public int tamaño() {
        return lista.size();
    }
    
    public DefaultListModel<String> getModeloLista() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for(Insumo i : lista) {
            model.addElement(i.toString());
        }
        return model;
    }
    
    public DefaultComboBoxModel<String> getModeloCategorias() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Materiales");
        model.addElement("Mano de Obra");
        model.addElement("Herramienta");
        model.addElement("Servicios");
        return model;
    }
}