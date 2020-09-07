package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Cliente;
import model.Cuenta;

public class ServiceAdministrador {
	
	public void nuevoCliente(Cliente nuevo) {
try (Connection con = Datos.getConnection()) {
			
			String sql ="INSERT INTO bancabd.clientes (dni, nombre, direccion, telefono) VALUES (?,?,?,?)";
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, nuevo.getDni());
			st.setString(2, nuevo.getNombre());
			st.setString(3, nuevo.getDireccion());
			st.setInt(4, nuevo.getTlf());
			st.execute();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void nuevaCuenta(Cuenta nueva) {
try (Connection con = Datos.getConnection()) {
			
			String sql ="INSERT INTO bancabd.clientes (numeroCuenta, saldo, tipocuenta) VALUES (?,?,?)";
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, nueva.getNumeroCuenta());
			st.setDouble(2, nueva.getSaldo());
			st.setString(3, nueva.getTipocuenta());
			st.execute();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void anadirTitular(int cuenta, int cliente) {
try (Connection con = Datos.getConnection()) {
			
			String sql ="INSERT INTO bancabd.titulares (idCuenta, idCliente) VALUES (?,?)";
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1,cuenta);
			st.setInt(2, cliente);
			st.execute();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void borrarTitular(int idCuenta, int cliente) {
try (Connection con = Datos.getConnection()) {
			
			String sql ="DELETE FROM bancabd.titulares WHERE idCuenta=? AND idCliente=?";
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1,idCuenta);
			st.setInt(2, cliente);
			st.execute();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	

}
