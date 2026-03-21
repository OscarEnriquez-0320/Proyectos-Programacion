package principal.java.Modelo;

public class Panaderia extends ProductoAbstract {

    public Panaderia() {
        super();
    }

    public Panaderia(String id, String nombre, String descripcion,
                     double precioCompra, double precioVenta, int stock, int stockMinimo,
                     String rutaImagen) {
        super(id, nombre, descripcion, "Panadería", precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
    }

    @Override
    public String getTipo() {
        return "Panadería";
    }
}