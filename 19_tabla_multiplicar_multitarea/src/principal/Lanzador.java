package principal;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import service.GenerarTabla;

public class Lanzador {
	
	static ExecutorService executor=Executors.newCachedThreadPool();

	public static void main(String[] args) {
		final int tareas=3;
		
		Scanner sc;
		sc = new Scanner(System.in);
		int n=0;
		int [] array = new int[tareas];
		System.out.println("Introduce Numeros del 1 al 10");
		for (int i=1;i<=tareas;i++) {
			
		System.out.print("Numero " +i+": ");
		n=Integer.parseInt(sc.nextLine());
		array[i-1]= n;
		System.out.println("");
		
		}
		System.out.println("Presentado Tabla de Multiplicar:");
		
		for(int x:array) {
			//Multitarea Clasica
			//lanzador(new GenerarTabla(x));
			
			//Nueva Multitarea
			lanzadorExecutor(new GenerarTabla(x));
			
		}
		
		sc.close();
		executor.shutdown();

	}
	
	//Multitarea clasica con Runnable
	private static void lanzador(Runnable t) {

		new Thread(t).start();

	}
	
	//Nueva Multitarea con ExecutorService
	private static void lanzadorExecutor (Runnable t) {
		
		executor.submit(t);
		
	}
}
