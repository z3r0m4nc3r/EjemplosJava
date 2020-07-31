package tareas;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import model.Pedido;
import model.PedidoTienda;
import service.GestorPedidos;
import service.GestorPedidosTotales;

public class TareaPedidos implements Runnable{
	private String tienda;
	private String ruta;
	
	static Lock lk=new ReentrantLock();
	public TareaPedidos(String tienda, String ruta) {
		super();
		this.tienda = tienda;
		this.ruta = ruta;
	}
	@Override
	public void run() {
		GestorPedidos gPedidos=new GestorPedidos(ruta);
		GestorPedidosTotales gTotales=new GestorPedidosTotales();
		//debemos recuperar los pedidos de la tienda
		//realizados en el día de hoy, y transferirlos
		//al almacen de pedidos totales
		
		lk.lock();
		List<Pedido> pedidos=gPedidos.pedidosFechaActual();		
		System.out.println(pedidos.size());
		
		gTotales.grabarPedidos(pedidos.stream()
								.map(p->new PedidoTienda(p.getProducto(),
														p.getUnidades(),
														p.getPrecioUnitario(),
														p.getSeccion(),
														p.getFecha(),
														tienda))
								.collect(Collectors.toList()));
				
		lk.unlock();
	}
	
}
