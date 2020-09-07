package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.Cuenta;

public class ServiceAdministrador {
	
	public boolean nuevoCliente(Cliente nuevo) {
try (Connection con = Datos.getConnection()) {
			
			String sql ="INSERT INTO bancabd.clientes (dni, nombre, direccion, telefono) VALUES (?,?,?,?)";
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, nuevo.getDni());
			st.setString(2, nuevo.getNombre());
			st.setString(3, nuevo.getDireccion());
			st.setInt(4, nuevo.getTlf());
			st.execute();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
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
	
	public List<Cliente> mostrarTitulares (int numeroCuenta){
		List <Cliente> cliList = new ArrayList <>();

		try (Connection con = Datos.getConnection()){
			String sql1 = "SELECT * FROM bancabd.titulares WHERE idCuenta =?";

			PreparedStatement pst1 = con.prepareStatement(sql1);
			pst1.setInt(1, numeroCuenta);
			ResultSet rs = pst1.executeQuery();
			while (rs.next()){
				String sql2 = "SELECT * FROM bancabd.clientes WHERE dni=?";
				PreparedStatement pst2 = con.prepareStatement(sql2);
				pst2.setInt(1, rs.getInt("idCliente"));
				ResultSet rs2 = pst2.executeQuery();
				while (rs2.next()){
					cliList.add(new Cliente(rs2.getInt("dni"),rs2.getString("nombre"),rs2.getString("direccion"),rs2.getInt("telefono")));
				}

			}
			return cliList;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}
	
	public boolean comprobarCuenta(int numero) {
		ArrayList <Integer> numeros = new ArrayList<>();
		
		try (Connection con = Datos.getConnection()) {
			String sql = "SELECT numeroCuenta FROM bancabd.cuentas";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()){
				numeros.add( rs.getInt("numeroCuenta"));
			}
			return numeros.stream().anyMatch(n -> n==numero);
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	

}
