package principal;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tareas.Factorial;

public class TestFactorial {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService exec=Executors.newCachedThreadPool();
		Future<Long> f=exec.submit(new Factorial(7));
		while(!f.isDone()) {
			System.out.println("Haciendo cosas en el main");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("El resultado del factorial es "+f.get());
		exec.shutdown();
	}

}
