package negocio;

import datos.CategoriaDAO;
import entidades.Categoria;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class CategoriaControl {

    private final CategoriaDAO DATOS;
    private final Categoria obj;
    private DefaultTableModel modeloTabla;

    public CategoriaControl() {
        DATOS = new CategoriaDAO();
        obj = new Categoria();
    }

    public DefaultTableModel listar() {
        String[] titulos = {"ID", "Nombre", "Descripcion"};//columnas
        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[3];
        List<Categoria> lista = DATOS.listar();
        for (Categoria item : lista) {//recorrer la lista
            registro[0] = Long.toString(item.getId());
            registro[1] = item.getNombre();
            registro[2] = item.getDescripcion();
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public String insertar(Integer id, String nombre, String descripcion) {
        if (DATOS.buscaCodigo(id) != -1) {
            return "El codigo ingresado ya existe";

        } else if (DATOS.buscaNombre(nombre) != -1) {
            return "El nombre ya existe";
        } else {
            long idLong = (int) id;
            obj.setId(idLong);
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);

            if (DATOS.insertar(obj)) {
                return "OK";
            } else {
                return "Error en el registro";
            }
        }

    }

    public String actualizar(Integer id, String nombre, String nombreAnterior, String descripcion) {

        if (nombre.equals(nombreAnterior)) {
            long idLong = (int) id;
            obj.setId(idLong);
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);

            if (DATOS.actualizar(obj)) {
                return "OK";
            } else {
                return "Error en la actualizacion";
            }
        } else {
            if (DATOS.buscaNombre(nombre) != -1) {
                return "El nombre ya existe";
            } else {
                long idLong = (int) id;
                obj.setId(idLong);
                obj.setNombre(nombre);
                obj.setDescripcion(descripcion);

                if (DATOS.actualizar(obj)) {
                    return "OK";
                } else {
                    return "Error en la actualizacion";
                }
            }
        }
    }

}
