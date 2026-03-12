package negocio;

import datos.ClienteDAO;
import datos.DetalleVentaDAO;
import datos.ProductoDAO;
import datos.VentaDAO;
import entidades.Cliente;
import entidades.DetalleVenta;
import entidades.Producto;
import entidades.Venta;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class VentaCONTROL {

    private final VentaDAO DATOS;
    private final ClienteDAO DATOSCLI;
    private final ProductoDAO DATOSPROD;
    private final DetalleVentaDAO DATOS_DET;
    private final Venta obj;
    private DefaultTableModel modeloTabla;

    public VentaCONTROL() {
        DATOS = new VentaDAO();
        DATOSCLI = new ClienteDAO();
        DATOSPROD = new ProductoDAO();
        DATOS_DET = new DetalleVentaDAO();
        obj = new Venta();
    }

    public DefaultTableModel listar() {
        String[] titulos = {"ID", "Fecha", "Descuento", "ID Cliente", "Cliente", "Total", "Estado"};//columnas
        this.modeloTabla = new DefaultTableModel(null, titulos);

        Object[] registro = new Object[7];
        List<Venta> lista = DATOS.listar();
        for (Venta item : lista) {//recorrer la lista
            registro[0] = Integer.toString(item.getId());
            registro[1] = item.getFecha();
            registro[2] = Double.toString(item.getDescuento());
            registro[3] = Integer.toString(item.getClienteId().getRut());
            registro[4] = item.getClienteId().getNombre();
            registro[5] = Double.toString(item.getTotal());
            if (item.isEstado()) {
                registro[6] = "Activo";
            } else {
                registro[6] = "Anulado";
            }

            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public DefaultTableModel listarDetalle(int idVenta) {
        String[] titulos = {"Id Producto", "Producto", "Stock", "Cantidad", "Precio", "SubTotal"};//columnas
        this.modeloTabla = new DefaultTableModel(null, titulos);

        Object[] registro = new Object[6];
        List<DetalleVenta> lista = DATOS_DET.listarDetalles(idVenta);
        for (DetalleVenta item : lista) {//recorrer la lista
            registro[0] = Integer.toString(item.getId());
            registro[1] = item.getProductoId().getNombre();
            registro[2] = Integer.toString(item.getProductoId().getStock());
            registro[3] = Integer.toString(item.getCantidad());
            registro[4] = Double.toString(item.getPrecio());
            registro[5] = Double.toString(item.getCantidad() * item.getPrecio());

            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public String insertar(Integer id, String fecha, double descuento, int idCliente, double total, DefaultTableModel modeloDetalles) {
        if (DATOS.buscaCodigo(id) != -1) {
            return "El codigo ingresado ya existe";

        } else {
            obj.setId(id);
            obj.setFecha(fecha);
            obj.setDescuento(descuento);
            obj.setClienteId(new Cliente(idCliente));
            obj.setTotal(total - descuento);
            obj.setEstado(true);

            List<DetalleVenta> listaDetalles = new ArrayList();
            long idProducto;
            int cantidad;
            double precio;

            for (int i = 0; i < modeloDetalles.getRowCount(); i++) {
                idProducto = Long.parseLong(String.valueOf(modeloDetalles.getValueAt(i, 0)));
                //nombre producto 1
                //stock 2
                cantidad = Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 3)));
                precio = Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 4)));
                // DetalleVenta(int id, double precio, int cantidad, int idVenta, Producto productoId)

                listaDetalles.add(new DetalleVenta(claveGenerado(), precio, cantidad, id, new Producto(idProducto)));
            }

            obj.setDetalles(listaDetalles);

            if (DATOS.insertar(obj)) {
                return "OK";
            } else {
                return "Error en el registro";
            }
        }

    }

    private int claveGenerado() {
        int min = 1000;
        int max = 9999;

        Random random = new Random();

        int value = random.nextInt(max + min) + min;
        return value;
    }

    public String anular(Integer id, String fecha, double descuento, int idCliente, double total) {

        obj.setId(id);
        obj.setFecha(fecha);
        obj.setDescuento(descuento);
        obj.setClienteId(new Cliente(idCliente));
        obj.setTotal(total - descuento);
        obj.setEstado(false);

        if (DATOS.anular(obj)) {
            return "OK";
        } else {
            return "Error en la actualizacion";
        }

    }

    public DefaultComboBoxModel seleccionarCliente() {
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Cliente> lista = new ArrayList<>();
        lista = DATOSCLI.seleccionar();
        for (Cliente item : lista) {
            items.addElement(new Cliente(item.getRut(), item.getNombre()));
        }
        return items;
    }

    public DefaultComboBoxModel seleccionarProducto() {
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Producto> lista = new ArrayList<>();
        lista = DATOSPROD.seleccionar();
        for (Producto item : lista) {
            items.addElement(new Producto(item.getId(), item.getNombre(), item.getPrecio(), item.getStock()));
        }
        return items;
    }

}
