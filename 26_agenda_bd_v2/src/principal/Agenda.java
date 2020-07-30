package principal;

import java.util.List;
import java.util.Scanner;

import model.Contacto;
import service.ContactosService;

public class Agenda {
	static ContactosService service=new ContactosService();
	static Scanner sc=new Scanner(System.in);
	public static void main(String[] args) {	
		int op;
		do {
			menu();
			op=Integer.parseInt(sc.nextLine());
			switch(op) {
				case 1:
					agregarContacto();
					break;	
				case 2:
					eliminarContacto();
					break;
				case 3:
					buscarContacto();
					break;
				case 4:
					mostrarContactos();
					break;
			}
		}while(op!=2);
	}
	private static void menu() {
		System.out.println("1.- Agregar contacto: ");
		System.out.println("2.- Eliminar contacto: ");
		System.out.println("3.- Buscar contacto: ");
		System.out.println("4.- Mostrar contactos: ");
		System.out.println("5.- Salir: ");
	}
	private static void agregarContacto() {
		//Scanner sc=new Scanner(System.in);
		int edad;
		String nom,email;
		System.out.println("Dame nombre: ");
		nom=sc.nextLine();
		System.out.println("Dame email: ");
		email=sc.nextLine();
		System.out.println("Dame un edad: ");
		edad=Integer.parseInt(sc.nextLine());
		//creamos objeto con sus datos
		Contacto per=new Contacto(nom,email,edad);
		
		if(service.agregarContacto(per)) {
			System.out.println("Contacto añadido");
		}else {
			System.out.println("No se añadió el contacto");
		}	
	}
	private static void mostrarContactos() {
		List<Contacto> contactos=service.recuperarContactos();
		contactos.forEach(c->
			System.out.println(c.getNombre()+" - "+c.getEdad()+" - "+c.getEmail())
		);
		
	}
	private static void buscarContacto() {
		String email;
		
		System.out.println("Dame un email: ");
		email=sc.nextLine();
		Contacto p=service.buscarContacto(email);
		
		if(p==null) {
			System.out.println("No existe");
		}else {
			System.out.println("La persona es "+p.getNombre()+" - "+p.getEmail()+" - "+p.getEdad());
		}
	}
	private static void eliminarContacto() {
		String email;
		
		System.out.println("Dame un email: ");
		email=sc.nextLine();
		if(service.eliminarContacto(email)) {
			System.out.println("Contacto eliminado con éxito");
		}else {
			System.out.println("El contacto no existe y no se ha podido eliminar");
		}
	}

}




