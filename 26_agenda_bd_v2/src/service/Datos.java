package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Datos  {
	static String driver="com.mysql.cj.jdbc.Driver";
	static String cadenaConexion="jdbc:mysql://localhost:3307/miscontactos?serverTimezone=UTC";
	static String user="root";
	static String password="root";
	static {
		//carga del driver
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(cadenaConexion, user, password);
	}

}
