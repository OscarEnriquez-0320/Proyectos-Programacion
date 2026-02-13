package Modelo;

import java.util.LinkedList;

public class Lista {
    private LinkedList<Cpersona> lista;
    
    public Lista() {
        lista = new LinkedList<>();
    }
    
    public void insertar(Cpersona persona) {
        lista.add(persona);
    }
    
    public String info() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            sb.append((i + 1)).append(". ").append(lista.get(i).toString()).append("\n");
        }
        return sb.toString();
    }
}