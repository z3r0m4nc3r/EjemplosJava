package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Contacto;

public class AgendaService {
	
static final String driver="com.mysql.cj.jdbc.Driver";
static String cadenaConexion="jdbc:mysql://localhost:3306/miscontactos?serverTimezone=UTC";
static String user="root";
static String password="root";
	
static { 
	try {
		Class.forName(driver);
	} catch (ClassNotFoundException e) {

		e.printStackTrace();
	}

}

public boolean agregarContacto(Contacto c) {
	
	try (Connection con = DriverManager.getConnection(cadenaConexion, user, password)){
	String sql = "insert into contactos(nombre,email,edad) values(?,?,?)";
	PreparedStatement st = con.prepareStatement(sql);
	st.setString(1, c.getNombre());
	st.setString(2, c.getEmail());
	st.setInt(3, c.getEdad());
	st.execute();
	return true;
	}catch (SQLException e) {
		
		e.printStackTrace();
	}
	return false;
}

}