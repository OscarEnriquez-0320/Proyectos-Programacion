
import Controlador.Cvprincipal;

public class Ejecutable {
    public static void main(String[] args) {
        try {
            Cvprincipal controlador = new Cvprincipal();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}