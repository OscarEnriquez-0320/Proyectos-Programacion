package principal.java.Modelo;

public class Salchichoneria extends ProductoAbstract {

    public Salchichoneria() {
        super();
    }

    public Salchichoneria(String id, String nombre, String descripcion,
                          double precioCompra, double precioVenta, int stock, int stockMinimo,
                          String rutaImagen) {
        super(id, nombre, descripcion, "Salchichonería", precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
    }

    @Override
    public String getTipo() {
        return "Salchichonería";
    }
}