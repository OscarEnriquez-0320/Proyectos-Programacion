package BasedeDatos;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
	
	private static final String URL = "jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;databaseName=AbarrotesFelipe;encrypt=false;trustServerCertificate=true;";
	private static final String USUARIO = "pruebausuario_pos";
	private static final String CLAVE = "admin123";
	//credenciales para inicar sesion
	//admin
	//1234
	
    public static Connection obtenerConexion() {
        Connection con = null;
        try {
           
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(URL, USUARIO, CLAVE);
            System.out.println("Conexión exitosa.");
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: No se encontró el Driver JDBC. Revisa tus librerías.");
        } catch (SQLException e) {
            System.out.println("ERROR DE SQL: " + e.getMessage());
            System.out.println("Código de error: " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("ERROR GENERAL: " + e.getMessage());
        }
        return con;
    }
    
}