package parallel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

public class RemoteAliveJob implements Runnable{
	
	private Thread t;
	private String threadName;
	private String hostname;
	
	RemoteAliveJob( String thread_name, String host){
		threadName = thread_name;
		hostname = host;
	}
	
	public void run(){
		
		try {
			InetAddress address = InetAddress.getByName(hostname);
			System.out.println(hostname+" "+address.isReachable(3000));
		}
		catch (IOException e) {
			System.out.println(hostname + " false");
		}
		
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
