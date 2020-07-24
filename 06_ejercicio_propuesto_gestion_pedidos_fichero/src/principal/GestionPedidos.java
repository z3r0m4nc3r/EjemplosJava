package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Pedido;
import service.PedidosService;

public class GestionPedidos {
	
	static PedidosService service = new PedidosService();

	public static void main(String[] args) {
		
		int opcion=0;
		
		do{		
			opcion = menu();
			switch(opcion){
			case 1:
				System.out.println("");
				agregarPedido();
				break;
			case 2:
				System.out.println("");
				procesarPedido();
				break;
			case 3:
				System.out.println("");
				priorizarPedido();
				break;
			case 4:
				System.out.println("");
				facturacionPendiente();
				break;
			case 5:
				System.out.println("");
				pedidosPendientes();
				break;
			case 6:
				System.out.println("");
				eliminarPedidos();
			case 7:
				System.out.println("");
				System.out.println("Programa Finalizado");
				break;
			default:
				System.out.println("");
				System.out.println("debes escribir una opcion valida");
			}
		}while(opcion!=7);

	}
	
	static void agregarPedido() {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		int numero;
		String producto;
		Double precio;
		
		try {
			System.out.println("Numero de pedido?");
			numero = Integer.parseInt(bf.readLine());
			System.out.println("Producto?");
			producto = bf.readLine();
			System.out.println("Precio?");
			precio = Double.parseDouble(bf.readLine());
			Pedido nuevoPedido = new Pedido(numero,producto,precio);
			service.agregarPedido(nuevoPedido);
			System.out.println("Pedido "+nuevoPedido.getNumeroPedido()+" Añadido");
			
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	static void priorizarPedido() {
		int numero;
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		try {
			System.out.println("Numero de pedido?");
			numero = Integer.parseInt(bf.readLine());
			if(service.obtenerPedido(numero)!=null) {
				service.priorizarPedido(numero);
				System.out.println("Pedido "+numero+" Priorizado");
			}else System.out.println("Pedido inexistente");
			
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	static void facturacionPendiente() {
		System.out.println("La facturacion pendiente es: "+service.facturacionPendiente());
	}
	
	static void pedidosPendientes() {
		System.out.println("Lista de pedidos pendientes:");
		service.pedidosPendientes().forEach(p -> System.out.println("Pedido "+p.getNumeroPedido()
		+" Producto: "+p.getProducto()+" Precio: "+p.getPrecio()));
	}
	
	static void procesarPedido() {
		if(!service.pedidosPendientes().isEmpty()) {
			System.out.println("Pedido "+service.procesarPedido().getNumeroPedido()+" Procesado");
		}else System.out.println("No quedan pedidos pendientes");
		
	}
	
	static void eliminarPedidos() {
		String producto;
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		try {
			System.out.println("Producto a eliminar?");
			producto = bf.readLine();
			service.eliminarPedidos(producto);
			
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		
	}
	
	static int menu() {
		
		int op=0;
		
		System.out.println("1.- Agregar pedido");
		System.out.println("2.- Procesar pedido");
		System.out.println("3.- Priorizar pedido");
		System.out.println("4.- Facturacion pendiente");
		System.out.println("5.- Pedidos pendientes");
		System.out.println("6.- Eliminar pedidos por producto");
		System.out.println("7.- Salir");
		
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
