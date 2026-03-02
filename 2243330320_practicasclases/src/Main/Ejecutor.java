package Main;
import Controlador.Cejercioclase01;

public class Ejecutor {
    public static void main(String[] args) {
        try {
            Cejercioclase01 controlador = new Cejercioclase01();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}