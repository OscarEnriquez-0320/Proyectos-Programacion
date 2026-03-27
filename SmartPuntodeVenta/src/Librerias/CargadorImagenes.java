package Librerias;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CargadorImagenes {
    
    private static final String CARPETA_IMAGENES = "imagenes_productos";
    private static Map<String, ImageIcon> cacheImagenes = new HashMap<>();
    
    static {
        // Crear carpeta si no existe
        File dir = new File(CARPETA_IMAGENES);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        // Cargar todas las imágenes al caché
        cargarTodasImagenesAlCache();
    }
    
    private static void cargarTodasImagenesAlCache() {
        File carpeta = new File(CARPETA_IMAGENES);
        File[] archivos = carpeta.listFiles((dir, name) -> 
            name.toLowerCase().endsWith(".png") || 
            name.toLowerCase().endsWith(".jpg") || 
            name.toLowerCase().endsWith(".jpeg") || 
            name.toLowerCase().endsWith(".gif"));
        
        if (archivos != null) {
            for (File archivo : archivos) {
                String nombre = archivo.getName();
                // Quitar extensión
                String nombreSinExtension = nombre.substring(0, nombre.lastIndexOf("."));
                // Normalizar nombre (reemplazar guiones bajos por espacios)
                String nombreNormalizado = nombreSinExtension.replace("_", " ");
                
                try {
                    BufferedImage img = ImageIO.read(archivo);
                    if (img != null) {
                        cacheImagenes.put(nombreNormalizado.toLowerCase(), new ImageIcon(img));
                        System.out.println("Imagen cargada: " + nombreNormalizado);
                    }
                } catch (IOException e) {
                    System.err.println("Error cargando imagen: " + archivo.getName());
                }
            }
        }
        System.out.println("Total imágenes cargadas: " + cacheImagenes.size());
    }
    
    // Cargar imagen de producto buscando por nombre
    public static ImageIcon cargarImagenProducto(String nombreProducto, int ancho, int alto) {
        if (nombreProducto == null || nombreProducto.isEmpty()) {
            return crearImagenPorDefecto("?", ancho, alto);
        }
        
        String nombreBusqueda = nombreProducto.toLowerCase().trim();
        
        // Buscar coincidencia exacta
        if (cacheImagenes.containsKey(nombreBusqueda)) {
            return escalarImagen(cacheImagenes.get(nombreBusqueda), ancho, alto);
        }
        
        // Buscar coincidencia parcial (ej: "arroz" en "arroz blanco")
        for (Map.Entry<String, ImageIcon> entry : cacheImagenes.entrySet()) {
            if (nombreBusqueda.contains(entry.getKey()) || entry.getKey().contains(nombreBusqueda)) {
                return escalarImagen(entry.getValue(), ancho, alto);
            }
        }
        
        // Si no encuentra, crear imagen por defecto con primera letra
        String primeraLetra = nombreProducto.length() > 0 ? nombreProducto.substring(0, 1).toUpperCase() : "?";
        return crearImagenPorDefecto(primeraLetra, ancho, alto);
    }
    
    // Cargar imagen desde archivo seleccionado
    public static ImageIcon cargarImagenDesdeArchivo(File archivo, int ancho, int alto) {
        try {
            BufferedImage img = ImageIO.read(archivo);
            if (img != null) {
                // Guardar copia en carpeta de imágenes
                String nombreDestino = archivo.getName();
                String rutaDestino = CARPETA_IMAGENES + "/" + nombreDestino;
                
                if (!archivo.getAbsolutePath().contains(CARPETA_IMAGENES)) {
                    java.nio.file.Files.copy(archivo.toPath(), new File(rutaDestino).toPath(),
                                             java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }
                
                Image escalada = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                return new ImageIcon(escalada);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar imagen desde archivo: " + e.getMessage());
        }
        return null;
    }
    
    // Guardar imagen asociada a un producto
    public static String guardarImagenProducto(String nombreProducto, File archivoImagen) {
        try {
            String nombreLimpio = nombreProducto.toLowerCase().replace(" ", "_").replace("ñ", "n");
            String extension = "";
            String nombreArchivo = archivoImagen.getName();
            int puntoIndex = nombreArchivo.lastIndexOf(".");
            if (puntoIndex > 0) {
                extension = nombreArchivo.substring(puntoIndex);
            }
            
            String rutaDestino = CARPETA_IMAGENES + "/" + nombreLimpio + extension;
            java.nio.file.Files.copy(archivoImagen.toPath(), new File(rutaDestino).toPath(),
                                     java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            
            // Recargar caché
            recargarImagenes();
            
            return rutaDestino;
        } catch (Exception e) {
            System.err.println("Error al guardar imagen: " + e.getMessage());
            return null;
        }
    }
    
    // ELIMINAR IMAGEN DE PRODUCTO (método que faltaba)
    public static boolean eliminarImagenProducto(String nombreProducto) {
        if (nombreProducto == null || nombreProducto.isEmpty()) {
            return false;
        }
        
        String nombreLimpio = nombreProducto.toLowerCase().replace(" ", "_").replace("ñ", "n");
        String[] extensiones = {".jpg", ".jpeg", ".png", ".gif"};
        
        for (String ext : extensiones) {
            String ruta = CARPETA_IMAGENES + "/" + nombreLimpio + ext;
            File archivo = new File(ruta);
            if (archivo.exists()) {
                boolean eliminado = archivo.delete();
                if (eliminado) {
                    // Eliminar del caché también
                    String clave = nombreProducto.toLowerCase().trim();
                    cacheImagenes.remove(clave);
                    System.out.println("Imagen eliminada: " + ruta);
                    return true;
                }
            }
        }
        return false;
    }
    
    // Obtener ruta de imagen por nombre de producto
    public static String obtenerRutaImagen(String nombreProducto) {
        String nombreLimpio = nombreProducto.toLowerCase().replace(" ", "_").replace("ñ", "n");
        String[] extensiones = {".jpg", ".jpeg", ".png", ".gif"};
        
        for (String ext : extensiones) {
            String ruta = CARPETA_IMAGENES + "/" + nombreLimpio + ext;
            if (new File(ruta).exists()) {
                return ruta;
            }
        }
        return null;
    }
    
    // Escalar imagen
    private static ImageIcon escalarImagen(ImageIcon icono, int ancho, int alto) {
        if (icono == null) return null;
        Image img = icono.getImage();
        Image escalada = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(escalada);
    }
    
    // Crear imagen por defecto
    private static ImageIcon crearImagenPorDefecto(String letra, int ancho, int alto) {
        BufferedImage img = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        // Fondo
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRect(0, 0, ancho, alto);
        
        // Borde
        g2d.setColor(new Color(200, 200, 200));
        g2d.drawRect(0, 0, ancho - 1, alto - 1);
        
        // Letra
        g2d.setColor(new Color(100, 100, 100));
        int fontSize = Math.min(ancho / 2, 30);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        FontMetrics fm = g2d.getFontMetrics();
        int x = (ancho - fm.stringWidth(letra)) / 2;
        int y = (alto + fm.getAscent()) / 2 - 5;
        g2d.drawString(letra, x, y);
        
        g2d.dispose();
        return new ImageIcon(img);
    }
    
    // Recargar caché de imágenes
    public static void recargarImagenes() {
        cacheImagenes.clear();
        cargarTodasImagenesAlCache();
    }
    
    // Agregar una imagen manualmente
    public static void agregarImagen(String nombreProducto, String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            BufferedImage img = ImageIO.read(archivo);
            if (img != null) {
                String nombreNormalizado = nombreProducto.toLowerCase().trim();
                cacheImagenes.put(nombreNormalizado, new ImageIcon(img));
                
                // Copiar a carpeta de imágenes
                String nombreDestino = nombreProducto.toLowerCase().replace(" ", "_") + 
                                       rutaArchivo.substring(rutaArchivo.lastIndexOf("."));
                String rutaDestino = CARPETA_IMAGENES + "/" + nombreDestino;
                java.nio.file.Files.copy(archivo.toPath(), new File(rutaDestino).toPath(),
                                         java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            System.err.println("Error al agregar imagen: " + e.getMessage());
        }
    }
    
    // Escalar imagen pública
    public static ImageIcon escalarImagenPublic(ImageIcon icono, int ancho, int alto) {
        return escalarImagen(icono, ancho, alto);
    }
}