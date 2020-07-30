package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

public boolean eliminarContacto (String email) {
	
	try (Connection con = DriverManager.getConnection(cadenaConexion, user, password)){
		String sql = "delete from contactos where email=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, email);
		st.executeUpdate();
		return true;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	return false;
	
}

public Contacto buscarContacto (String email) {
	try (Connection con = DriverManager.getConnection(cadenaConexion, user, password)){
		
		String sql = "select * from contactos where email=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, email);
		ResultSet rs = st.executeQuery();
		
		if(rs.next()) {
			return new Contacto (rs.getInt("idcontacto")
					,rs.getString("nombre")
					,rs.getString("email")
					,rs.getInt("edad"));
		}
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	
	return null;
}

public List<Contacto> mostrarTodos (){
	
List<Contacto> lista = new ArrayList<>();

	try (Connection con = DriverManager.getConnection(cadenaConexion, user, password)){
		
		String sql = "select * from contactos";
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {
			lista.add(new Contacto (rs.getInt("idcontacto")
					,rs.getString("nombre")
					,rs.getString("email")
					,rs.getInt("edad")));
		}
		return lista;
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	
	return null;
}

}