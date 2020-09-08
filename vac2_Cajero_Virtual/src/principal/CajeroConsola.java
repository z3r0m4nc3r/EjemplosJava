package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import service.ServiceCajero;

public class CajeroConsola {
	static ServiceCajero service = new ServiceCajero ();
	
	public static void main(String[] args) {
		
		int opcion=0;
		int numero=0;
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		
		System.out.println("Introduzca numero de cuenta:");
		try {
			numero = Integer.parseInt(bf.readLine());
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
		if (service.comprobarCuenta(numero)) {
			System.out.println("La cuenta numero "+numero+" se encuentra en la base de datos");
			
			do{		
				opcion = menuCliente();
				switch(opcion){
				case 1:
					System.out.println("");
					sacarDinero(numero);
					break;
				case 2:
					System.out.println("");
					ingresarDinero(numero);
					break;
				case 3:
					System.out.println("");
					mostrarSaldo(numero);
					break;
				case 4:
					System.out.println("");
					movimientos(numero);
					break;
				case 5:
					System.out.println("");
					System.out.println("Programa Finalizado");
					break;
				default:
					System.out.println("");
					System.out.println("debes escribir una opcion valida");
				}
			}while(opcion!=5);
		}else System.out.println("ERROR la cuenta numero "+numero+" no existe");

	}
	
	static void mostrarSaldo(int cuenta) {
		System.out.println("El saldo actual de la cuenta "+cuenta+" es de "+service.saldo(cuenta)+" €");
	}
	
	static void sacarDinero(int cuenta) {
		int cantidad =0;
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		try {
			System.out.println("Introduzca importe a extraer");
			cantidad = Integer.parseInt(bf.readLine());
			if(service.sacarDinero(cantidad, cuenta)) {
				System.out.println("Extraidos "+cantidad+" de cuenta "+cuenta+" nuevo saldo: "+service.saldo(cuenta));
			}else System.out.println("La operacion no se ha podido realizar. ¿Saldo insuficiente?");
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	static void ingresarDinero (int cuenta) {
		int cantidad =0;
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		try {
			System.out.println("Introduzca importe a ingresar");
			cantidad = Integer.parseInt(bf.readLine());
			if(service.ingresarDinero(cantidad, cuenta)) {
				System.out.println("Extraidos "+cantidad+" de cuenta "+cuenta+" nuevo saldo: "+service.saldo(cuenta));
			}else System.out.println("La operacion no se ha podido realizar. ¿Saldo insuficiente?");
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	static void movimientos(int cuenta) {
		service.movimientos(cuenta).forEach(m -> System.out.println(m.getIdCuenta()+" "+m.getFecha()+" "+m.getCantidad()+" "+m.getOperacion()));
		mostrarSaldo(cuenta);
	}
	
static int menuCliente() {
		
		int op=0;
		System.out.println("1.- Sacar Dinero");
		System.out.println("2.- Ingrear Dinero");
		System.out.println("3.- Mostrar Saldo");
		System.out.println("4.- Ultimos movimientos (30 dias)");
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
