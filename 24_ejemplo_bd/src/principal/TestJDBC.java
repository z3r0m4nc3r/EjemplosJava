package principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDBC {
	static String cadenaConexion="jdbc:mysql://localhost:3306/miscontactos?serverTimezone=UTC";
	static String user="root";
	static String password="root";
	static final String driver="com.mysql.cj.jdbc.Driver";
	
	static { 
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
	
	}
	
	public static void main(String[] args) {
		
		
		try (Connection con = DriverManager.getConnection(cadenaConexion, user, password)){
			
			//Instrucciones sql
			String sql = "insert into contactos(nombre,email,edad) values('superman','clark@metropolis.com','32')";
			
			Statement st = con.createStatement();
			st.execute(sql);
			
			
		}  catch (SQLException e) {
			
			e.printStackTrace();
		}
		

	}

}
