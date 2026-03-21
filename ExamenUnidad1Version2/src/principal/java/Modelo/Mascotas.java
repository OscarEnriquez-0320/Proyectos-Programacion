package principal.java.Modelo;

public class Mascotas extends ProductoAbstract {

    public Mascotas() {
        super();
    }

    public Mascotas(String id, String nombre, String descripcion,
                    double precioCompra, double precioVenta, int stock, int stockMinimo,
                    String rutaImagen) {
        super(id, nombre, descripcion, "Mascotas", precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
    }

    @Override
    public String getTipo() {
        return "Mascotas";
    }
}