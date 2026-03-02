import java.io.File;

public class VerificarImagenes {
    public static void main(String[] args) {
        String ruta = System.getProperty("user.dir") + "\\Imagenes\\000.png";
        File archivo = new File(ruta);
        
        if (archivo.exists()) {
            System.out.println("✅ ¡Perfecto! La carpeta Imagenes y el archivo 000.png existen");
            System.out.println("Ubicación: " + archivo.getAbsolutePath());
        } else {
            System.out.println("❌ No se encuentra la imagen. Revisa que:");
            System.out.println("1. La carpeta se llame exactamente 'Imagenes'");
            System.out.println("2. Esté en: " + System.getProperty("user.dir"));
            System.out.println("3. El archivo 000.png esté dentro");
        }
    }
}