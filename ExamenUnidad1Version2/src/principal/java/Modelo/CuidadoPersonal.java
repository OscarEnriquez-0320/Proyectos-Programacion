package principal.java.Modelo;

public class CuidadoPersonal extends ProductoAbstract {

    public CuidadoPersonal() {
        super();
    }

    public CuidadoPersonal(String id, String nombre, String descripcion,
                           double precioCompra, double precioVenta, int stock, int stockMinimo,
                           String rutaImagen) {
        super(id, nombre, descripcion, "Cuidado Personal", precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
    }

    @Override
    public String getTipo() {
        return "Cuidado Personal";
    }
}