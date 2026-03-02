package Modelo;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Clase que maneja una colección de Categorías
 */
public class ListaCategorias {
    private ArrayList<Categoria> categorias;
    
    public ListaCategorias() {
        this.categorias = new ArrayList<>();
    }
    
    /**
     * Agrega una categoría si no existe
     * @param categoria Categoría a agregar
     * @return true si se agregó, false si ya existe
     */
    public boolean agregarCategoria(Categoria categoria) {
        if (buscarCategoriaPorId(categoria.getIdCategoria()) == null) {
            categorias.add(categoria);
            return true;
        }
        return false;
    }
    
    /**
     * Busca una categoría por su ID
     * @param idCategoria ID a buscar
     * @return Categoría encontrada o null
     */
    public Categoria buscarCategoriaPorId(String idCategoria) {
        for (Categoria cat : categorias) {
            if (cat.getIdCategoria().equals(idCategoria)) {
                return cat;
            }
        }
        return null;
    }
    
    /**
     * Busca y devuelve el NOMBRE de una categoría por su ID
     * @param idCategoria ID a buscar
     * @return Nombre de la categoría o "Desconocida"
     */
    public String buscarCategoria(String idCategoria) {
        Categoria cat = buscarCategoriaPorId(idCategoria);
        return (cat != null) ? cat.getNombreCategoria() : "Desconocida";
    }
    
    /**
     * Elimina una categoría por su ID
     * @param idCategoria ID de la categoría a eliminar
     * @return true si se eliminó, false si no existe
     */
    public boolean eliminarCategoria(String idCategoria) {
        Categoria cat = buscarCategoriaPorId(idCategoria);
        if (cat != null) {
            categorias.remove(cat);
            return true;
        }
        return false;
    }
    
    /**
     * Carga categorías desde un ArrayList de String[] (proveniente de archivo)
     * @param categoriasString ArrayList con los datos del archivo
     */
    public void cargarCategorias(ArrayList<String[]> categoriasString) {
        if (categoriasString != null) {
            for (String[] categoriaString : categoriasString) {
                if (categoriaString.length >= 2) {
                    String idCategoria = categoriaString[0];
                    String nombreCategoria = categoriaString[1];
                    Categoria categoria = new Categoria(idCategoria, nombreCategoria);
                    this.agregarCategoria(categoria);
                }
            }
            System.out.println("Cargadas " + categorias.size() + " categorías");
        }
    }
    
    /**
     * Genera un DefaultListModel para usar en JList
     * @return DefaultListModel con todas las categorías
     */
    public DefaultListModel<Categoria> generarModeloCategorias() {
        DefaultListModel<Categoria> modelo = new DefaultListModel<>();
        for (Categoria cat : categorias) {
            modelo.addElement(cat);
        }
        return modelo;
    }
    
    /**
     * Genera un modelo para JTable (para la actividad de Categorías)
     * @return DefaultTableModel con los datos de categorías
     */
    public javax.swing.table.DefaultTableModel getModeloTabla() {
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        
        for (Categoria cat : categorias) {
            String[] fila = {cat.getIdCategoria(), cat.getNombreCategoria()};
            modelo.addRow(fila);
        }
        
        return modelo;
    }
    
    /**
     * Obtiene un array con los nombres de las categorías (para JOptionPane)
     * @return Array de nombres
     */
    public Object[] getNombresCategorias() {
        Object[] nombres = new Object[categorias.size()];
        for (int i = 0; i < categorias.size(); i++) {
            nombres[i] = categorias.get(i).getNombreCategoria();
        }
        return nombres;
    }
    
    /**
     * Obtiene un array con los IDs de las categorías (para JOptionPane)
     * @return Array de IDs
     */
    public Object[] getIdCategorias() {
        Object[] ids = new Object[categorias.size()];
        for (int i = 0; i < categorias.size(); i++) {
            ids[i] = categorias.get(i).getIdCategoria();
        }
        return ids;
    }
    
    /**
     * Convierte toda la lista a formato para guardar en archivo
     * @return String con todos los datos
     */
    public String toArchivo() {
        StringBuilder sb = new StringBuilder();
        for (Categoria cat : categorias) {
            sb.append(cat.toLinea()).append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Categoria cat : categorias) {
            sb.append(cat.toString()).append("\n");
        }
        return sb.toString();
    }
    
    public int size() {
        return categorias.size();
    }
    
    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }
}