package principal.java.Modelo;

public class Abarrotes extends ProductoAbstract {

    public Abarrotes() {
        super();
    }

    public Abarrotes(String id, String nombre, String descripcion,
                     double precioCompra, double precioVenta, int stock, int stockMinimo,
                     String rutaImagen) {
        super(id, nombre, descripcion, "Abarrotes", precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
    }

    @Override
    public String getTipo() {
        return "Abarrotes";
    }
}