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
			}
		}while(opcion!=2);

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
	static int menu() {

		int op=0;
		System.out.println("*******************************************************");
		System.out.println("* Sistema de gestion de Contactos SQL                 *");
		System.out.println("*******************************************************");
		System.out.println("1.- Añadir contacto nuevo");
		System.out.println("2.- Salir");

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
