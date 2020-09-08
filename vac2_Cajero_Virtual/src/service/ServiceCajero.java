package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import model.Movimiento;

public class ServiceCajero {
	
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
	
	public boolean sacarDinero (int cantidad, int cuenta) {
		
		try (Connection con = Datos.getConnection()) {
			
			if (saldo(cuenta)>=cantidad) {
				String sql = "UPDATE bancabd.cuentas SET saldo = ? WHERE numeroCuenta = ?";
				
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setInt(1, saldo(cuenta)-cantidad);
				pst.setInt(2, cuenta);
				pst.executeUpdate();
				
				anotarMovimiento(new Movimiento (cuenta, new java.util.Date(), cantidad, "extracción"));
				
				return true;
			}else return false;
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean ingresarDinero (int cantidad, int cuenta) {
		
		try (Connection con = Datos.getConnection()) {
			
			String sql = "UPDATE bancabd.cuentas SET saldo = ? WHERE numeroCuenta = ?";
			
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, saldo(cuenta)+cantidad);
			pst.setInt(2, cuenta);
			pst.executeUpdate();
			
			anotarMovimiento(new Movimiento (cuenta, new java.util.Date(), cantidad, "ingreso"));
			
			return true;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
		
	
	}
	
	public int saldo (int cuenta) {
		
		try (Connection con = Datos.getConnection()){
			String sql1 = "SELECT saldo FROM bancabd.cuentas WHERE numeroCuenta=?";
			
			PreparedStatement pst1 = con.prepareStatement(sql1);
			pst1.setInt(1, cuenta);
			ResultSet rs = pst1.executeQuery();
			
			if(rs.next()) {
			return rs.getInt("saldo");
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<Movimiento> movimientos (int cuenta){
		
		List <Movimiento> mvlist = new ArrayList <>();
		
		try (Connection con = Datos.getConnection()){
			String sql1 = "SELECT * FROM bancabd.movimientos WHERE idCuenta =?";
			
			PreparedStatement pst1 = con.prepareStatement(sql1);
			pst1.setInt(1, cuenta);
			ResultSet rs = pst1.executeQuery();
			while (rs.next()){
				mvlist.add(new Movimiento(rs.getInt("idMovimiento"), rs.getInt("idCuenta"), rs.getDate("fecha"), rs.getInt("cantidad"), rs.getString("operacion")));
			}
			return mvlist.parallelStream()
					.filter(mv -> mv.getFecha().getTime()
							>localDateToDate(LocalDate.now().minusDays(30)).getTime())
					.collect(Collectors.toList());
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	
	public boolean trasferencia (int cantidad, int origen, int destino) {
		if(sacarDinero(cantidad, origen)) {
			ingresarDinero(cantidad, destino);
			return true;
		}
		
		return false;
	}
	
	private void anotarMovimiento (Movimiento mv) {
		
		try (Connection con = Datos.getConnection()) {
			
			String sql ="INSERT INTO bancabd.movimientos (idCuenta, fecha, cantidad, operacion) VALUES (?,?,?,?)";
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, mv.getIdCuenta());
			st.setDate(2, new java.sql.Date(mv.getFecha().getTime()));
			st.setInt(3, mv.getCantidad());
			st.setString(4, mv.getOperacion());
			st.execute();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	private Date localDateToDate (LocalDate ld) {
		//Recibe un objeto java.util.LocalDate y lo transforma en java.util.Date
		return Date.from(
				ld.atStartOfDay(ZoneId.systemDefault())
				.toInstant());
	}

}
