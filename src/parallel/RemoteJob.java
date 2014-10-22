package parallel;

import java.util.Random;

public class RemoteJob implements Runnable {
	
	private Thread t;
	private String threadName;
	
	RemoteJob( String name){
		threadName = name;
		System.out.println("Creating remote job");
	}
	
	public void run(){
		System.out.println("Running : " + threadName);
		
		Random rand = new Random();
		int randomNum = rand.nextInt((5 - 1) + 1) + 1;
		
		try {
			Thread.sleep(randomNum*1000);
		}
		
		catch (InterruptedException e){
			System.out.println("Interrupted Exception");
		}
		System.out.println("Thread " + threadName + " completed in " + Integer.toString(randomNum) + " seconds.");
		System.out.println("Thread " + threadName + " exiting.");
	}
	
	public void start(){
		System.out.println("Starting " + threadName);
		
		if (t == null){
			t = new Thread(this,threadName);
			t.start();
			
		}
	}
	
	public void join() throws InterruptedException{
		t.join();
	}
	
}
