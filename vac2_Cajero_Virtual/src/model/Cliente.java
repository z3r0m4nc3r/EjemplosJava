package model;

public class Cliente {
	private int dni;
	private String nombre;
	private String direccion;
	private int tlf;
	
	public Cliente(int dni, String nombre, String direccion, int tlf) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.direccion = direccion;
		this.tlf = tlf;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getTlf() {
		return tlf;
	}
	public void setTlf(int tlf) {
		this.tlf = tlf;
	}
	
	

}
