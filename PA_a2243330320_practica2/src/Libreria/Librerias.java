package Libreria;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Clase con métodos utilitarios para toda la aplicación
 * Incluye manejo de imágenes, menús y otros helpers
 */
public class Librerias {
    
    /**
     * Método para habilitar/deshabilitar todos los menús de una barra
     * @param barra JMenuBar a modificar
     * @param accion true para habilitar, false para deshabilitar
     * @return La misma barra de menú modificada
     */
    public JMenuBar menuspadre(JMenuBar barra, boolean accion) {
        if (barra == null) return barra;
        
        JMenuBar barraaux = barra;
        int cantmenus = barraaux.getMenuCount();
        
        for (int pos = 0; pos < cantmenus; pos++) {
            JMenu menu = barraaux.getMenu(pos);
            if (menu != null) {
                menu.setEnabled(accion);
                
                // También deshabilitar/habilitar los items del menú
                for (int i = 0; i < menu.getItemCount(); i++) {
                    JMenuItem item = menu.getItem(i);
                    if (item != null) {
                        item.setEnabled(accion);
                    }
                }
            }
        }
        return barraaux;
    }
    
    /**
     * Método para cargar una imagen en un JLabel ajustándola al tamaño del label
     * @param archivo Ruta completa de la imagen
     * @param ancho Ancho deseado
     * @param alto Alto deseado
     * @return Icon con la imagen escalada
     */
    public Icon EtiquetaImagen(String archivo, int ancho, int alto) {
        try {
            File file = new File(archivo);
            if (!file.exists()) {
                System.out.println("Archivo de imagen no encontrado: " + archivo);
                // Retornar un ícono vacío o null
                return null;
            }
            
            ImageIcon iconoOriginal = new ImageIcon(archivo);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        } catch (Exception e) {
            System.err.println("Error al cargar imagen: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Versión sobrecargada que mantiene la proporción de la imagen
     * @param archivo Ruta de la imagen
     * @param maxAncho Ancho máximo
     * @param maxAlto Alto máximo
     * @return Icon con la imagen escalada manteniendo proporción
     */
    public Icon EtiquetaImagenProporcional(String archivo, int maxAncho, int maxAlto) {
        try {
            File file = new File(archivo);
            if (!file.exists()) {
                return null;
            }
            
            ImageIcon iconoOriginal = new ImageIcon(archivo);
            int anchoOriginal = iconoOriginal.getIconWidth();
            int altoOriginal = iconoOriginal.getIconHeight();
            
            // Calcular escala manteniendo proporción
            double escalaAncho = (double) maxAncho / anchoOriginal;
            double escalaAlto = (double) maxAlto / altoOriginal;
            double escala = Math.min(escalaAncho, escalaAlto);
            
            int nuevoAncho = (int) (anchoOriginal * escala);
            int nuevoAlto = (int) (altoOriginal * escala);
            
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        } catch (Exception e) {
            System.err.println("Error al cargar imagen: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Muestra un mensaje de confirmación personalizado
     * @param padre Componente padre
     * @param mensaje Mensaje a mostrar
     * @return true si el usuario confirma, false en caso contrario
     */
    public boolean confirmar(Component padre, String mensaje) {
        int resultado = JOptionPane.showConfirmDialog(
            padre, 
            mensaje, 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return resultado == JOptionPane.YES_OPTION;
    }
    
    /**
     * Muestra un mensaje de error
     * @param padre Componente padre
     * @param mensaje Mensaje de error
     */
    public void mostrarError(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(
            padre,
            mensaje,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Muestra un mensaje informativo
     * @param padre Componente padre
     * @param mensaje Mensaje informativo
     */
    public void mostrarInformacion(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(
            padre,
            mensaje,
            "Información",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Centra una ventana en la pantalla
     * @param ventana Ventana a centrar
     */
    public void centrarVentana(Window ventana) {
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventanaSize = ventana.getSize();
        
        int x = (pantalla.width - ventanaSize.width) / 2;
        int y = (pantalla.height - ventanaSize.height) / 2;
        
        ventana.setLocation(x, y);
    }
    
    /**
     * Valida si una cadena es un número entero
     * @param cadena Cadena a validar
     * @return true si es un número entero válido
     */
    public boolean esNumero(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Genera un ID automático basado en el tamaño de una lista
     * @param tamanio Tamaño actual de la lista
     * @param prefijo Prefijo para el ID (ej. "CAT", "INS", "OBR")
     * @return ID generado (ej. "CAT001")
     */
    public String generarId(int tamanio, String prefijo) {
        int numero = tamanio + 1;
        String formato = String.format("%03d", numero); // 001, 002, etc.
        return prefijo + formato;
    }
}