package principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestResultados {
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
			
			String sql = "select nombre from contactos where edad>40";
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println(rs.getString("nombre"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		}
	}



