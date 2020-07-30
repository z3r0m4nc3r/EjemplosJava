package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import model.Contacto;
import service.AgendaService;


public class AgendaBD {
	static AgendaService service = new AgendaService();
	public static void main(String[] args) {
		
		int opcion=0;

		do{		
			opcion = menu();
			switch(opcion){
			case 1:
				System.out.println("");
				agregarContacto();
				break;
			case 2:
				System.out.println("");
				eliminarContacto();
				break;
			case 3:
				System.out.println("");
				buscarContacto();
				break;
			case 4:
				System.out.println("");
				mostrarTodos();
				break;
			}
		}while(opcion!=5);

	}
	
	static void agregarContacto() {
		String nombre;
		String email;
		int edad;
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		
		try {
			System.out.println("Introducir nombre: ");
			nombre = bf.readLine();
			System.out.println("Introducir email: ");
			email = bf.readLine();
			System.out.println("Introducir edad: ");
			edad = Integer.parseInt(bf.readLine());
			
			if(service.agregarContacto(new Contacto(nombre,email,edad))) {
				System.out.println("\nContacto añadido con exito\n");
			}else System.out.println("No se ha podido añadir contacto");
		
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	static void eliminarContacto() {
		String email;
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		
		try {
			System.out.println("Introducir email: ");
			email = bf.readLine();
			
			if(service.eliminarContacto(email)) {
				System.out.println("\nContacto eliminado con exito\n");
			}else System.out.println("Email inexistente en la BD");
		
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	static void buscarContacto() {
		String email;
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		
		try {
			System.out.println("Introducir email: ");
			email = bf.readLine();
			
			Contacto contacto = service.buscarContacto(email);
			
			if(contacto!=null) {
				System.out.println("ID: "+contacto.getIdcontacto());
				System.out.println("nombre: "+contacto.getNombre());
				System.out.println("email: "+contacto.getEmail());
				System.out.println("edad: "+contacto.getEdad());
				
			}else System.out.println("Email inexistente en la BD");
		
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	static void mostrarTodos() {
		
		service.mostrarTodos().forEach(c -> {System.out.println("");
		        System.out.println("ID: "+c.getIdcontacto());
				System.out.println("nombre: "+c.getNombre());
				System.out.println("email: "+c.getEmail());
				System.out.println("edad: "+c.getEdad());
				System.out.println("");});
		
	}
	
	static int menu() {

		int op=0;
		System.out.println("*******************************************************");
		System.out.println("* Sistema de gestion de Contactos SQL                 *");
		System.out.println("*******************************************************");
		System.out.println("1.- Añadir Contacto Nuevo");
		System.out.println("2.- Eliminar Contacto");
		System.out.println("3.- Buscar Contacto");
		System.out.println("4.- Mostrar Contactos");
		System.out.println("5.- Salir");

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
