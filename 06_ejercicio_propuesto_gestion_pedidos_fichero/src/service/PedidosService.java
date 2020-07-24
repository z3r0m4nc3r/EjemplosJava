package service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import model.Pedido;

public class PedidosService {
	private final String ruta ="pedidos.txt";
	
	double facturacion=0;
	
	public boolean agregarPedido(Pedido pedido) {
		if(!existePedido(pedido)) {
			
			try (FileOutputStream fout = new FileOutputStream(ruta,true);
					PrintStream out = new PrintStream(fout)) {
				
				out.println(pedido.toString());

			} catch (FileNotFoundException e) {
				e.getMessage();
				e.printStackTrace();    
			} catch (IOException e) {
				e.getMessage();
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	private boolean existePedido(Pedido pedido) {
		try(BufferedReader bf = new BufferedReader(new FileReader(ruta))){
			String linea;
			while ((linea=bf.readLine())!=null) {
				String [] datos=linea.split(",");
				int codigo = Integer.parseInt(datos[0]);
				if(codigo==pedido.getNumeroPedido()) {
					return true;
				}
			}
			
		}catch (FileNotFoundException e) { //solucionamos la excepcion de fichero no existe
			e.printStackTrace();
			try(PrintStream salida = new PrintStream(ruta)){
				
			}catch (IOException ex) {
				e.printStackTrace();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	public Pedido procesarPedido() {
		List<Pedido> pedidos= pedidosPendientes();
		Pedido pedidoProcesado = pedidos.get(0);
		
		if(!pedidos.isEmpty()) {
			pedidos.remove(0);

			try (PrintStream out = new PrintStream(ruta)) {

				pedidos.forEach(p -> out.println(p.toString()));

			} catch (FileNotFoundException e) {
				e.getMessage();
				e.printStackTrace();
			} 
			return pedidoProcesado;
		}else return null;
	}
	
	public Pedido priorizarPedido(int numeroPedido) {
		Pedido pedido,aux;
		pedido=obtenerPedido(numeroPedido);
		List<Pedido> pedidos= pedidosPendientes();
		if(pedido!=null) {
			int pos=pedidos.indexOf(pedido);
			//se intercambia con el anterior, si no es el primero
			if(pos>0) {
				//intercambiamos el pedido por el de la
				//posición anterior
				aux=pedidos.get(pos-1);
				pedidos.set(pos-1, pedido);
				pedidos.set(pos, aux);
			}
		}
		try (PrintStream out = new PrintStream(ruta)) {

			pedidos.forEach(p -> out.println(p.toString()));

		} catch (FileNotFoundException e) {
			e.getMessage();
			e.printStackTrace();
		} 
		
		return pedido;
		
		
	}
	public Pedido obtenerPedido(int numeroPedido) {
		
		for(Pedido p:pedidosPendientes()) {
			if(p.getNumeroPedido()==numeroPedido) {
				return p;
			}
		}
		return null;
	}
	
	public double facturacionPendiente() {
		facturacion = 0;
		pedidosPendientes().forEach(p -> facturacion += p.getPrecio());
		return facturacion;
	}
	//elimina todos los pedidos, cuyo nombre de producto
	//contenga ese texto
	public void eliminarPedidos(String producto) {
		List<Pedido> pedidos= pedidosPendientes();
		pedidos.removeIf(p -> p.getProducto().contains(producto));
		
		try (PrintStream out = new PrintStream(ruta)) {

			pedidos.forEach(p -> out.println(p.toString()));

		} catch (FileNotFoundException e) {
			e.getMessage();
			e.printStackTrace();
		} 
		
		
	}
	
	public List<Pedido> pedidosPendientes() {
		List<Pedido> pedidos=new ArrayList<>();
		
		try (FileReader fr = new FileReader(ruta);
				BufferedReader bf = new BufferedReader(fr)) {
			String linea;
			
			while ((linea=bf.readLine())!=null) {
					String [] atributos = linea.split(",");
					
					Pedido nuevoPedido = new Pedido (Integer.parseInt(atributos[0]),
							atributos[1],Double.parseDouble(atributos[2]));
					pedidos.add(nuevoPedido);
					
					
				}
			
				
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		return pedidos;
	}
}