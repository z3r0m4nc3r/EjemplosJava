package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Cliente;
import model.Cuenta;
import service.ServiceAdministrador;

public class AdministradorCajero {
	static ServiceAdministrador serviceAdmin = new ServiceAdministrador ();
	public static void main(String[] args) {
		int opcion=0;
		do{		
			opcion = menuAdministrador();
			switch(opcion){
			case 1:
				System.out.println("");
				nuevoCliente();
				break;
			case 2:
				System.out.println("");
				int numero;
				InputStreamReader isr = new InputStreamReader(System.in);
				BufferedReader bf=new BufferedReader(isr);
				
				System.out.println("Introduzca numero de cuenta:");
				try {
					numero=Integer.parseInt(bf.readLine());
					mostrarTitulares(numero);
					
				} catch (NumberFormatException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				break;
			case 3:
				System.out.println("");
				modificarTitulares();
				break;
			case 4:
				System.out.println("");
				System.out.println("Programa Finalizado");
				break;
			default:
				System.out.println("");
				System.out.println("debes escribir una opcion valida");
			}
		}while(opcion!=4);

	}
	
static int menuAdministrador() {
		
		int op=0;
		System.out.println("1.- Nuevo Cliente");
		System.out.println("2.- Mostrar titulares de cuenta");
		System.out.println("3.- Modificar titulares de cuenta");
		System.out.println("4.- Salir");
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		
		try {
			System.out.println("Selecciona opcion");
			op = Integer.parseInt(bf.readLine());
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		return op;
		
	}

static void nuevoCliente() {
	int dni;
	String nombre;
	String direccion;
	int tlf;
	int numeroCuenta;
	double saldo;
	String tipocuenta;
	int numero=0;
	
	InputStreamReader isr = new InputStreamReader(System.in);
	BufferedReader bf=new BufferedReader(isr);
	
	System.out.println("MENU DE NUEVO CLIENTE");
	try {
		System.out.println("Introduce DNI del cliente");
		dni= Integer.parseInt(bf.readLine());
		System.out.println("Introduce nombre del cliente");
		nombre=bf.readLine();
		System.out.println("Introduce direccion del cliente");
		direccion=bf.readLine();
		System.out.println("Introduce Telefono del cliente");
		tlf= Integer.parseInt(bf.readLine());
		
		if(serviceAdmin.nuevoCliente(new Cliente(dni,nombre,direccion,tlf))) {
			System.out.println("Nuevo cliente añadido con exito");
			System.out.println("¿Desea crear una nueva cuenta para este cliente? s/N");
			if(bf.readLine().toLowerCase().contentEquals("s")) {
				System.out.println("Introduce numero de cuenta");
				numeroCuenta= Integer.parseInt(bf.readLine());
				System.out.println("Introduce saldo inicial");
				saldo=Double.parseDouble(bf.readLine());
				System.out.println("Introduce tipo de cuenta");
				tipocuenta=bf.readLine();
				
				serviceAdmin.nuevaCuenta(new Cuenta(numeroCuenta,saldo,tipocuenta));
				serviceAdmin.anadirTitular(numeroCuenta, dni);

			}else {
				System.out.println("¿Desea añadir a este cliente como titular de una cuenta existente? s/N");

				if(bf.readLine().toLowerCase().contentEquals("s")) {
					System.out.println("Introduzca numero de cuenta:");
					try {
						numero = Integer.parseInt(bf.readLine());
					} catch (NumberFormatException | IOException e) {
						e.printStackTrace();
					}

					if (serviceAdmin.comprobarCuenta(numero)) {
						
						serviceAdmin.anadirTitular(numero, dni);
						System.out.println("Se ha añadido al cliente con DNI "+dni+" a la cuenta numero "+numero+" en la base de datos");	
						
					}
				}
			}

			System.out.println("Proceso Finalizado");

		}else System.out.println("ERROR! No se pudo añadir el cliente");
		
	} catch (NumberFormatException e) {
		
		e.printStackTrace();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	
	
	
}

static void mostrarTitulares(int numero) {

		serviceAdmin.mostrarTitulares(numero)
		.forEach(t -> System.out.println("DNI: "+t.getDni()+ " Nombre: "+t.getNombre()));
	
}


static void modificarTitulares() {
	int numero;
	InputStreamReader isr = new InputStreamReader(System.in);
	BufferedReader bf=new BufferedReader(isr);
	
	System.out.println("Introduzca numero de cuenta:");
	try {
		numero=Integer.parseInt(bf.readLine());
		mostrarTitulares(numero);
		int op=0;
		do{		
			op = menuModificar();
			switch(op){
			case 1:
				System.out.println("");
				
				break;
			case 2:
				System.out.println("");

				break;
			case 3:
				System.out.println("");
				System.out.println("Programa Finalizado");
				break;
			default:
				System.out.println("");
				System.out.println("debes escribir una opcion valida");
			}
		}while(op!=3);
		
	} catch (NumberFormatException e) {
		
		e.printStackTrace();
	} catch (IOException e) {
		
		e.printStackTrace();
	}

}

static int menuModificar() {
	
	int op=0;
	System.out.println("1.- Eliminar Titular");
	System.out.println("2.- Añadir Titular");
	System.out.println("3.- Salir");
	
	InputStreamReader isr = new InputStreamReader(System.in);
	BufferedReader bf=new BufferedReader(isr);
	
	try {
		System.out.println("Selecciona opcion");
		op = Integer.parseInt(bf.readLine());
	} catch (IOException e) {
		e.getMessage();
		e.printStackTrace();
	}
	return op;
	
}
}