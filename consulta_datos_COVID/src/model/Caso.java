package model;

import java.util.Date;

public class Caso {
	private String nombreComunidad;
	private Date fecha;
	private int positivos;
	public Caso(String nombreComunidad, Date fecha, int positivos) {
		super();
		this.nombreComunidad = nombreComunidad;
		this.fecha = fecha;
		this.positivos = positivos;
	}
	public String getNombreComunidad() {
		return nombreComunidad;
	}
	public void setNombreComunidad(String nombreComunidad) {
		this.nombreComunidad = nombreComunidad;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getPositivos() {
		return positivos;
	}
	public void setPositivos(int positivos) {
		this.positivos = positivos;
	}
	

}
