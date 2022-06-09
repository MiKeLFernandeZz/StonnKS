package Modelo;
/**
 * Esta clase conecta Java con MySQL
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class Conector {
	public static final String URL = "jdbc:mysql://localhost:3306/StonnKS";
    public static final String USER = "adm";
    public static final String CLAVE = "admin1234";
     
    public Connection getConexion(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return con;
    }
}
