package principal;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tareas.TareaPedidos;
import util.Utilidades;

public class ProcesarPedidos {

	public static void main(String[] args) {
		System.out.println(Utilidades.convertirADate(LocalDate.now()));
		
		ExecutorService exec=Executors.newCachedThreadPool();
		Future<?> f1=exec.submit(new TareaPedidos("tienda A","pedido_tienda1.txt"));	
		Future<?> f2=exec.submit(new TareaPedidos("tienda B","pedido_tienda2.txt"));
		Future<?> f3=exec.submit(new TareaPedidos("tienda C","pedido_tienda3.txt"));
		while(!f1.isDone()||!f2.isDone()||!f3.isDone()) {
			System.out.println("Esperando...");
		}
		 
		System.out.println("Tareas completadas");
		exec.shutdown();
	}

}
