package Controlador;
import java.util.EventListener;

public interface FormListener extends EventListener {
    public void formEventOccurred(Modelo.FormEvent e);
}