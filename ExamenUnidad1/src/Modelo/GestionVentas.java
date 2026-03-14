package Modelo;

import java.util.ArrayList;
import java.util.Iterator;

public class GestionVentas {
    private ArrayList<Venta> listaVentas;
    private int correlativoTicket;

    public GestionVentas() {
        this.listaVentas = new ArrayList<>();
        this.correlativoTicket = 1;
    }

    public void agregarVenta(Venta venta) {
        listaVentas.add(venta);
    }

    public ArrayList<Venta> getListaVentas() {
        return listaVentas;
    }

    public String generarNuevoTicket() {
        String ticket = String.format("%06d", correlativoTicket);
        correlativoTicket++;
        return ticket;
    }

    public void setCorrelativoDesdeArchivo(int ultimo) {
        this.correlativoTicket = ultimo + 1;
    }

    public double calcularTotalVentasDia(String fecha) {
        double total = 0;
        for (Venta v : listaVentas) {
            if (v.getFecha().equals(fecha)) {
                total += v.getSubtotal();
            }
        }
        return total;
    }
}