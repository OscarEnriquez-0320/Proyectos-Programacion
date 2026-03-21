package principal.java.Modelo;

public class Carnes extends ProductoAbstract {

    public Carnes() {
        super();
    }

    public Carnes(String id, String nombre, String descripcion,
                  double precioCompra, double precioVenta, int stock, int stockMinimo,
                  String rutaImagen) {
        super(id, nombre, descripcion, "Carnes", precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
    }

    @Override
    public String getTipo() {
        return "Carnes";
    }
}