package model;

import java.util.Date;

public class Movimiento {
	
	private int idMovimiento;  //campo autoincrementado en la BD, solo get.
	private int idCuenta;
	private Date fecha;
	private int cantidad;
	private String operacion;
	
	public Movimiento(int idCuenta, Date fecha, int cantidad, String operacion) {
		super();
		this.idCuenta = idCuenta;
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.operacion = operacion;
	}
	

	public Movimiento(int idMovimiento, int idCuenta, Date fecha, int cantidad, String operacion) {
		super();
		this.idMovimiento = idMovimiento;
		this.idCuenta = idCuenta;
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.operacion = operacion;
	}


	public int getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public int getIdMovimiento() {
		return idMovimiento;
	}
	
	
	
	
	

}
