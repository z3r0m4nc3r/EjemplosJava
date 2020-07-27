package principal;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tareas.Factorial;

public class TestFactorial {

	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Future<Long> f = executor.submit(new Factorial(7));
		
		while(!f.isDone()) {
			System.out.println("Haciendo otras tareas....");
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			System.out.println("El resultado es: "+f.get());
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		} catch (ExecutionException e) {
			
			e.printStackTrace();
		}
		
		executor.shutdown();

	}

}
