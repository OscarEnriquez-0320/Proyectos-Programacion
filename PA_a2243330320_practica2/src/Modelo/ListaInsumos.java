package Modelo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Clase que maneja una colección de Insumos/Productos
 */
public class ListaInsumos {
    private ArrayList<Insumo> insumos;
    
    public ListaInsumos() {
        this.insumos = new ArrayList<>();
    }
    
    /**
     * Agrega un insumo si no existe
     * @param insumo Insumo a agregar
     * @return true si se agregó, false si ya existe
     */
    public boolean agregarInsumo(Insumo insumo) {
        if (buscarInsumo(insumo.getIdProducto()) == null) {
            insumos.add(insumo);
            return true;
        }
        return false;
    }
    
    /**
     * Busca un insumo por su ID
     * @param id ID a buscar
     * @return Insumo encontrado o null
     */
    public Insumo buscarInsumo(String id) {
        for (Insumo ins : insumos) {
            if (ins.getIdProducto().equals(id)) {
                return ins;
            }
        }
        return null;
    }
    
    /**
     * Elimina un insumo por su ID
     * @param id ID del insumo a eliminar
     * @return true si se eliminó, false si no existe
     */
    public boolean eliminarInsumoPorId(String id) {
        Insumo ins = buscarInsumo(id);
        if (ins != null) {
            insumos.remove(ins);
            return true;
        }
        return false;
    }
    
    /**
     * Carga insumos desde un ArrayList de String[] (proveniente de archivo)
     * @param insumosString ArrayList con los datos del archivo
     */
    public void cargarInsumo(ArrayList<String[]> insumosString) {
        if (insumosString != null) {
            for (String[] insumoString : insumosString) {
                if (insumoString.length >= 3) {
                    String id = insumoString[0];
                    String insumo = insumoString[1];
                    String idCategoria = insumoString[2];
                    Insumo nodo = new Insumo(id, insumo, idCategoria);
                    this.agregarInsumo(nodo);
                }
            }
            System.out.println("Cargados " + insumos.size() + " insumos");
        }
    }
    
    /**
     * Genera el modelo para JTable (como en la práctica)
     * @param listac Lista de categorías para obtener los nombres
     * @return DefaultTableModel listo para JTable
     */
    public DefaultTableModel getmodelo(ListaCategorias listac) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna celda editable
            }
        };
        
        modelo.addColumn("Id");
        modelo.addColumn("Insumo");
        modelo.addColumn("Categoria");
        
        for (Insumo nodo : this.insumos) {
            String[] info = new String[3];
            info[0] = nodo.getIdProducto().trim();
            info[1] = nodo.getProducto().trim();
            info[2] = listac.buscarCategoria(nodo.getIdCategoria().trim());
            modelo.addRow(info);
        }
        return modelo;
    }
    
    /**
     * Genera un modelo para JTable de insumos (versión simplificada)
     * @return DefaultTableModel
     */
    public DefaultTableModel getModeloTabla() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        modelo.addColumn("ID");
        modelo.addColumn("Insumo");
        modelo.addColumn("ID Categoría");
        
        for (Insumo ins : insumos) {
            String[] fila = {ins.getIdProducto(), ins.getProducto(), ins.getIdCategoria()};
            modelo.addRow(fila);
        }
        
        return modelo;
    }
    
    /**
     * Obtiene un array con los IDs de los insumos (para JOptionPane)
     * @return Array de IDs
     */
    public Object[] idinsumos() {
        Object[] ids = new Object[insumos.size()];
        for (int i = 0; i < insumos.size(); i++) {
            ids[i] = insumos.get(i).getIdProducto();
        }
        return ids;
    }
    
    /**
     * Convierte toda la lista a formato para guardar en archivo
     * @return String con todos los datos
     */
    public String toArchivo() {
        StringBuilder sb = new StringBuilder();
        for (Insumo ins : insumos) {
            sb.append(ins.toLinea()).append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Insumo ins : insumos) {
            sb.append(ins.toString()).append("\n");
        }
        return sb.toString();
    }
    
    public int size() {
        return insumos.size();
    }
    
    public ArrayList<Insumo> getInsumos() {
        return insumos;
    }
}