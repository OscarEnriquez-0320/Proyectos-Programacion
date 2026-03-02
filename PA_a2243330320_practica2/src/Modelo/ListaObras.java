package Modelo;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ListaObras {
    private ArrayList<Obra> obras;
    
    public ListaObras() {
        obras = new ArrayList<>();
    }
    
    public boolean agregarObra(Obra obra) {
        if (buscarObra(obra.getIdObra()) == null) {
            obras.add(obra);
            return true;
        }
        return false;
    }
    
    public Obra buscarObra(String id) {
        for (Obra o : obras) {
            if (o.getIdObra().equals(id)) {
                return o;
            }
        }
        return null;
    }
    
    public boolean eliminarObra(String id) {
        Obra o = buscarObra(id);
        if (o != null) {
            obras.remove(o);
            return true;
        }
        return false;
    }
    
    /**
     * Carga obras desde ArrayList de String[] (proveniente de Archivotxt)
     */
    public void cargarObras(ArrayList<String[]> datos) {
        if (datos != null) {
            for (String[] d : datos) {
                if (d.length >= 3) {
                    obras.add(new Obra(d[0], d[1], d[2]));
                }
            }
        }
    }
    
    /**
     * Formato para guardar en archivo
     */
    public String toArchivo() {
        StringBuilder sb = new StringBuilder();
        for (Obra o : obras) {
            sb.append(o.toLinea()).append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Genera ID automático: OBR001, OBR002, etc.
     */
    public String generarId() {
        int numero = obras.size() + 1;
        return String.format("OBR%03d", numero); // OBR001, OBR002, etc.
    }
    
    /**
     * Modelo para JTable
     */
    public DefaultTableModel getModeloTabla() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Descripción");
        
        for (Obra o : obras) {
            modelo.addRow(new Object[]{o.getIdObra(), o.getNombre(), o.getDescripcion()});
        }
        
        return modelo;
    }
    
    /**
     * Array de IDs para JOptionPane
     */
    public Object[] getIdObras() {
        Object[] ids = new Object[obras.size()];
        for (int i = 0; i < obras.size(); i++) {
            ids[i] = obras.get(i).getIdObra();
        }
        return ids;
    }
    
    public int size() { return obras.size(); }
    public ArrayList<Obra> getObras() { return obras; }
}