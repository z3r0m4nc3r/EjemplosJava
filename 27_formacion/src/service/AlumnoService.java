package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Alumno;

public class AlumnoService {
	
	public List<Alumno> alumnoPorCurso (String curso){
		try(Connection con = Datos.getConnection()) {
			List<Alumno> alumnos = new ArrayList<>();
			String sql = "Select alumnos.* from alumnos,cursos where cursos.denominacion=? AND alumnos.idCurso=cursos.idCurso";
			PreparedStatement st=con.prepareStatement(sql);
			st.setString(1, curso);
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				alumnos.add(new Alumno(rs.getInt("dni"),
						rs.getString("nombre"),
						rs.getString("email"),
						rs.getInt("edad"),
						rs.getInt("idCurso")));
			}
			return alumnos;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}

}
