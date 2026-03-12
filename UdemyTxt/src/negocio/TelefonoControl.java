package negocio;

import datos.ClienteDAO;
import datos.TelefonoDAO;
import entidades.Cliente;
import entidades.Telefono;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class TelefonoControl {

    private final TelefonoDAO DATOS;
    private final ClienteDAO DATOSCLI;
    private final Telefono obj;
    private DefaultTableModel modeloTabla;

    public TelefonoControl() {
        DATOS = new TelefonoDAO();
        DATOSCLI = new ClienteDAO();
        obj = new Telefono();
    }

    public DefaultTableModel listar() {
        String[] titulos = {"ID", "Numero", "ID", "Nombre"};//columnas
        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[4];
        List<Telefono> lista = DATOS.listar();
        for (Telefono item : lista) {//recorrer la lista
            registro[0] = Integer.toString(item.getId());
            registro[1] = item.getNumero();
            registro[2] = String.valueOf(item.getClienteId().getRut());
            registro[3] = item.getClienteId().getNombre();
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public String insertar(Integer id, String numero, Integer rut) {
        if (DATOS.buscaCodigo(id) != -1) {
            return "El codigo ingresado ya existe";

        } else if (DATOS.buscaTelefono(numero) != -1) {
            return "El numero de telefono ya existe";
        } else {
            obj.setId(id);
            obj.setNumero(numero);
            obj.setClienteId(new Cliente(rut));

            if (DATOS.insertar(obj)) {
                return "OK";
            } else {
                return "Error en el registro";
            }
        }
    }

    public String actualizar(Integer id, String numero, String numeroAnterior, Integer rut) {

        if (numero.equals(numeroAnterior)) {
            obj.setId(id);
            obj.setNumero(numero);
            obj.setClienteId(new Cliente(rut));

            if (DATOS.actualizar(obj)) {
                return "OK";
            } else {
                return "Error en la actualizacion";
            }
        } else {
            if (DATOS.buscaTelefono(numero) != -1) {
                return "El numero de telefono ya existe";
            } else {
                obj.setId(id);
                obj.setNumero(numero);
                obj.setClienteId(new Cliente(rut));

                if (DATOS.actualizar(obj)) {
                    return "OK";
                } else {
                    return "Error en la actualizacion";
                }
            }
        }
    }

    public DefaultComboBoxModel seleccionar() {
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Cliente> lista = new ArrayList<>();
        lista = DATOSCLI.seleccionar();
        for (Cliente item : lista) {
            items.addElement(new Cliente(item.getRut(), item.getNombre()));
        }
        return items;
    }

}
