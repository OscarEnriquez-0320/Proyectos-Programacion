package principal.java.Modelo;

public class Bebidas extends ProductoAbstract {

    public Bebidas() {
        super();
    }

    public Bebidas(String id, String nombre, String descripcion,
                   double precioCompra, double precioVenta, int stock, int stockMinimo,
                   String rutaImagen) {
        super(id, nombre, descripcion, "Bebidas", precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
    }

    @Override
    public String getTipo() {
        return "Bebidas";
    }
}