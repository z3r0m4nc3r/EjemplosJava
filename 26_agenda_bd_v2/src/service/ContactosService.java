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

public class ContactosService {

	public boolean agregarContacto(Contacto contacto) {
		//conexión con la base de datos
		try(Connection con=Datos.getConnection()) {
			//envío de instrucción SQL
			/*opción Statement
			 * String sql="insert into contactos(nombre,email,edad) values('"+contacto.getNombre()+"','"+contacto.getEmail()+"',"+contacto.getEdad()+")";
			Statement st=con.createStatement();
			st.execute(sql);*/
			//opción preparedStatement
			String sql="insert into contactos(nombre,email,edad) values(?,?,?)";
			PreparedStatement st=con.prepareStatement(sql);
			st.setString(1, contacto.getNombre());
			st.setString(2, contacto.getEmail());
			st.setInt(3, contacto.getEdad());
			st.execute();
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean eliminarContacto(String email) {
		//conexión con la base de datos
		try(Connection con=Datos.getConnection()) {
			String sql="delete from contactos where email=?";
			PreparedStatement st=con.prepareStatement(sql);
			st.setString(1, email);
			return st.executeUpdate()>0;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}
	public Contacto buscarContacto(String email) {
		//conexión con la base de datos
		try(Connection con=Datos.getConnection()) {		
			String sql="select * from contactos where email=?";
			PreparedStatement st=con.prepareStatement(sql);
			st.setString(1, email);
			ResultSet rs=st.executeQuery();
			if(rs.next()) {
				return new Contacto(rs.getInt("idContacto"),rs.getString("nombre"),rs.getString("email"),rs.getInt("edad"));
			}
			return null;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Contacto> recuperarContactos() {
		//conexión con la base de datos
		try(Connection con=Datos.getConnection()) {		
			String sql="select * from contactos";
			Statement st=con.createStatement();
			
			ResultSet rs=st.executeQuery(sql);
			List<Contacto> resultados=new ArrayList<>();
			while(rs.next()) {
				resultados.add(new Contacto(rs.getInt("idContacto"),rs.getString("nombre"),rs.getString("email"),rs.getInt("edad")));
			}
			return resultados;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
				
	}
}
