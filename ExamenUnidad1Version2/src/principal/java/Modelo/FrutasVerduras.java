package principal.java.Modelo;

public class FrutasVerduras extends ProductoAbstract {
	public FrutasVerduras() {
		super();
	}

	
	public FrutasVerduras(String id, String nombre, String descripcion,
            double precioCompra, double precioVenta, int stock, int stockMinimo,
            String rutaImagen) {
 super(id, nombre, descripcion, "Bebidas", precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
}
	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return null;
	}

}
