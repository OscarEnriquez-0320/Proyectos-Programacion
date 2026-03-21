package principal.java.Modelo;

public class Snacks extends ProductoAbstract {

    public Snacks() {
        super();
    }

    public Snacks(String id, String nombre, String descripcion,
                  double precioCompra, double precioVenta, int stock, int stockMinimo,
                  String rutaImagen) {
        super(id, nombre, descripcion, "Snacks", precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
    }

    @Override
    public String getTipo() {
        return "Snacks";
    }
}