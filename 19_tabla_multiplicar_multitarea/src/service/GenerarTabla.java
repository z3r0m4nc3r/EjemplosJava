package service;


public class GenerarTabla implements Runnable {
	
	private int n;
	
	
	public GenerarTabla(int n) {
		super();
		this.n = n;
		
	}

	@Override
	public void run() {
		
		for(int i=1;i<=10;i++) {
			
			System.out.println(n+" x "+i+" = "+n*i);
						
			
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				
			}
		}
		
		

	}


