package principal.java.Modelo;

import java.util.ArrayList;
import java.util.Iterator;

public class GestionProducto {
    private ArrayList<ProductoAbstract> listaProductos;

    public GestionProducto() {
        this.listaProductos = new ArrayList<>();
    }

    public boolean insertar(ProductoAbstract producto) {
        if (!existe(producto.getId())) {
            return listaProductos.add(producto);
        }
        return false;
    }

    public ProductoAbstract buscar(String id) {
        Iterator<ProductoAbstract> it = listaProductos.iterator();
        while (it.hasNext()) {
            ProductoAbstract p = it.next();
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public boolean actualizar(ProductoAbstract productoActualizado) {
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getId().equals(productoActualizado.getId())) {
                listaProductos.set(i, productoActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(String id) {
        Iterator<ProductoAbstract> it = listaProductos.iterator();
        while (it.hasNext()) {
            ProductoAbstract p = it.next();
            if (p.getId().equals(id)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public boolean existe(String id) {
        return buscar(id) != null;
    }

    public ArrayList<ProductoAbstract> getLista() {
        return listaProductos;
    }

    public ArrayList<ProductoAbstract> getActivos() {
        ArrayList<ProductoAbstract> activos = new ArrayList<>();
        for (ProductoAbstract p : listaProductos) {
            if (p.isActivo()) {
                activos.add(p);
            }
        }
        return activos;
    }

    public void cargarDatosIniciales() {
        // ABARROTES
        listaProductos.add(new Abarrotes("001", "Arroz 1kg", "Arroz blanco de grano largo", 30.0, 35.0, 50, 10, "Imagenes/arroz.png"));
        listaProductos.add(new Abarrotes("002", "Azúcar 1kg", "Azúcar refinada", 20.0, 25.0, 40, 10, "Imagenes/azucar.png"));
        listaProductos.add(new Abarrotes("003", "Harina 1kg", "Harina de trigo", 23.0, 28.0, 30, 10, "Imagenes/harina.png"));
        listaProductos.add(new Abarrotes("004", "Aceite 1L", "Aceite vegetal", 45.0, 50.0, 25, 5, "Imagenes/aceite.png"));
        listaProductos.add(new Abarrotes("005", "Frijol 1kg", "Frijol negro", 25.0, 30.0, 35, 8, "Imagenes/frijol.png"));

        // BEBIDAS
        listaProductos.add(new Bebidas("006", "Agua 1L", "Agua purificada", 8.0, 12.0, 100, 20, "Imagenes/agua.png"));
        listaProductos.add(new Bebidas("007", "Refresco 600ml", "Refresco cola", 12.0, 18.0, 80, 15, "Imagenes/refresco.png"));
        listaProductos.add(new Bebidas("008", "Jugo de Naranja", "Jugo natural", 15.0, 22.0, 40, 10, "Imagenes/jugo.png"));
        listaProductos.add(new Bebidas("009", "Café 250g", "Café molido", 45.0, 60.0, 30, 5, "Imagenes/cafe.png"));
        listaProductos.add(new Bebidas("010", "Té de Manzanilla", "Té en bolsas", 25.0, 35.0, 45, 8, "Imagenes/te.png"));

        // LÁCTEOS
        listaProductos.add(new Lacteos("011", "Leche 1L", "Leche pasteurizada", 18.0, 25.0, 30, 8, "Imagenes/leche.png"));
        listaProductos.add(new Lacteos("012", "Huevo 12pz", "Huevo blanco", 35.0, 45.0, 60, 15, "Imagenes/huevo.png"));
        listaProductos.add(new Lacteos("013", "Yogurt 1L", "Yogurt natural", 20.0, 28.0, 25, 5, "Imagenes/yogurt.png"));
        listaProductos.add(new Lacteos("014", "Mantequilla 500g", "Mantequilla sin sal", 40.0, 55.0, 20, 5, "Imagenes/mantequilla.png"));
        listaProductos.add(new Lacteos("015", "Crema 500g", "Crema ácida", 35.0, 48.0, 20, 5, "Imagenes/crema.png"));

        // FRUTAS Y VERDURAS
        listaProductos.add(new FrutasVerduras("016", "Manzana kg", "Manzana roja", 25.0, 35.0, 40, 10, "Imagenes/manzana.png"));
        listaProductos.add(new FrutasVerduras("017", "Plátano kg", "Plátano tabasco", 15.0, 22.0, 50, 12, "Imagenes/platano.png"));
        listaProductos.add(new FrutasVerduras("018", "Tomate kg", "Tomate saladet", 18.0, 25.0, 35, 8, "Imagenes/tomate.png"));
        listaProductos.add(new FrutasVerduras("019", "Cebolla kg", "Cebolla blanca", 12.0, 18.0, 45, 10, "Imagenes/cebolla.png"));
        listaProductos.add(new FrutasVerduras("020", "Lechuga", "Lechuga orejona", 10.0, 15.0, 30, 8, "Imagenes/lechuga.png"));

        // CARNES
        listaProductos.add(new Carnes("021", "Pechuga de Pollo kg", "Pechuga sin hueso", 65.0, 85.0, 25, 5, "Imagenes/pollo.png"));
        listaProductos.add(new Carnes("022", "Carne de Res kg", "Carne para bistec", 110.0, 150.0, 20, 4, "Imagenes/res.png"));
        listaProductos.add(new Carnes("023", "Carne de Cerdo kg", "Pierna de cerdo", 90.0, 120.0, 20, 4, "Imagenes/cerdo.png"));
        listaProductos.add(new Carnes("024", "Pescado kg", "Filete de pescado", 80.0, 110.0, 15, 3, "Imagenes/pescado.png"));
        listaProductos.add(new Carnes("025", "Camarón kg", "Camarón mediano", 180.0, 250.0, 10, 2, "Imagenes/camaron.png"));

        // SALCHICHONERÍA
        listaProductos.add(new Salchichoneria("026", "Jamón de Pavo kg", "Jamón rebanado", 85.0, 120.0, 20, 5, "Imagenes/jamon.png"));
        listaProductos.add(new Salchichoneria("027", "Salchichas pkt", "Salchichas de pavo", 45.0, 65.0, 30, 8, "Imagenes/salchichas.png"));
        listaProductos.add(new Salchichoneria("028", "Tocino kg", "Tocino ahumado", 110.0, 150.0, 15, 3, "Imagenes/tocino.png"));
        listaProductos.add(new Salchichoneria("029", "Queso Oaxaca kg", "Queso para fundir", 90.0, 130.0, 18, 4, "Imagenes/queso.png"));
        listaProductos.add(new Salchichoneria("030", "Chorizo kg", "Chorizo rojo", 65.0, 90.0, 20, 5, "Imagenes/chorizo.png"));

        // PANADERÍA
        listaProductos.add(new Panaderia("031", "Pan de Caja", "Pan blanco de caja", 25.0, 35.0, 30, 8, "Imagenes/pancaja.png"));
        listaProductos.add(new Panaderia("032", "Bolillo pz", "Bolillo tradicional", 2.0, 3.5, 100, 20, "Imagenes/bolillo.png"));
        listaProductos.add(new Panaderia("033", "Tortilla de Maíz kg", "Tortilla recién hecha", 12.0, 18.0, 80, 15, "Imagenes/tortilla.png"));
        listaProductos.add(new Panaderia("034", "Pan Dulce pz", "Pan dulce surtido", 5.0, 8.0, 60, 12, "Imagenes/pandulce.png"));
        listaProductos.add(new Panaderia("035", "Tortilla de Harina pz", "Tortilla de harina grande", 8.0, 12.0, 50, 10, "Imagenes/tortillaharina.png"));

        // LIMPIEZA
        listaProductos.add(new Limpieza("036", "Detergente 1kg", "Detergente en polvo", 35.0, 48.0, 40, 10, "Imagenes/detergente.png"));
        listaProductos.add(new Limpieza("037", "Suavizante 1L", "Suavizante de telas", 40.0, 55.0, 35, 8, "Imagenes/suavizante.png"));
        listaProductos.add(new Limpieza("038", "Cloro 1L", "Cloro desinfectante", 15.0, 22.0, 50, 12, "Imagenes/cloro.png"));
        listaProductos.add(new Limpieza("039", "Papel Higiénico", "Papel de 4 rollos", 25.0, 35.0, 60, 15, "Imagenes/papel.png"));
        listaProductos.add(new Limpieza("040", "Trapeador", "Trapeador de microfibra", 45.0, 65.0, 20, 5, "Imagenes/trapeador.png"));

        // CUIDADO PERSONAL
        listaProductos.add(new CuidadoPersonal("041", "Shampoo 500ml", "Shampoo para cabello", 35.0, 48.0, 40, 10, "Imagenes/shampoo.png"));
        listaProductos.add(new CuidadoPersonal("042", "Jabón de Tocador", "Jabón de tocador", 12.0, 18.0, 80, 15, "Imagenes/jabon.png"));
        listaProductos.add(new CuidadoPersonal("043", "Pasta Dental", "Pasta dental 90ml", 18.0, 25.0, 60, 12, "Imagenes/pasta.png"));
        listaProductos.add(new CuidadoPersonal("044", "Desodorante", "Desodorante en barra", 30.0, 42.0, 45, 10, "Imagenes/desodorante.png"));
        listaProductos.add(new CuidadoPersonal("045", "Crema Hidratante", "Crema para manos", 25.0, 35.0, 35, 8, "Imagenes/crema_corporal.png"));

        // SNACKS
        listaProductos.add(new Snacks("046", "Papas Fritas", "Papas fritas 100g", 12.0, 18.0, 60, 15, "Imagenes/papas.png"));
        listaProductos.add(new Snacks("047", "Galletas", "Galletas surtidas", 10.0, 15.0, 70, 15, "Imagenes/galletas.png"));
        listaProductos.add(new Snacks("048", "Chocolate", "Chocolate con leche", 15.0, 22.0, 50, 12, "Imagenes/chocolate.png"));
        listaProductos.add(new Snacks("049", "Dulces", "Dulces surtidos", 8.0, 12.0, 80, 20, "Imagenes/dulces.png"));
        listaProductos.add(new Snacks("050", "Botanas", "Botanas de maíz", 10.0, 15.0, 65, 15, "Imagenes/botanas.png"));

        // MASCOTAS
        listaProductos.add(new Mascotas("051", "Alimento Perro 1kg", "Alimento balanceado", 35.0, 48.0, 40, 10, "Imagenes/alimentoperro.png"));
        listaProductos.add(new Mascotas("052", "Alimento Gato 1kg", "Alimento para gatos", 40.0, 55.0, 35, 8, "Imagenes/alimentogato.png"));
        listaProductos.add(new Mascotas("053", "Correa", "Correa para perro", 50.0, 70.0, 25, 5, "Imagenes/correa.png"));
        listaProductos.add(new Mascotas("054", "Plato", "Plato de acero", 25.0, 35.0, 30, 8, "Imagenes/plato.png"));
        listaProductos.add(new Mascotas("055", "Juguete", "Juguete para mascota", 30.0, 45.0, 40, 10, "Imagenes/juguete.png"));
    }}