package acceso;

import datos.ClienteDAO;
import datos.Conexion;
import entidades.Cliente;
import java.io.File;

import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author TDAVI
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        List<String> lista = Conexion.cargarArchivo(new File("Archivos/cliente.txt"));
        for(String dato : lista){
            StringTokenizer st = new StringTokenizer(dato, ",");
            // Solo pedimos datos si realmente existen
            while (st.hasMoreTokens()) {
                System.out.print(st.nextToken());
                if(st.hasMoreTokens()) System.out.print(","); // Agrega la coma solo si viene otro dato
            }
            System.out.println(); // Salto de línea al terminar cada registro
        }   
             
        ClienteDAO dao = new ClienteDAO();
List<Cliente> listaclientes = dao.listar();

        dao.insertar(new Cliente(2, "Rudy"));
        dao.actualizar(new Cliente(3, "Rudy"));
    }

}
