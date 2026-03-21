package principal.java.Modelo;

public class Limpieza extends ProductoAbstract {

    public Limpieza() {
        super();
    }

    public Limpieza(String id, String nombre, String descripcion,
                    double precioCompra, double precioVenta, int stock, int stockMinimo,
                    String rutaImagen) {
        super(id, nombre, descripcion, "Limpieza", precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
    }

    @Override
    public String getTipo() {
        return "Limpieza";
    }
}