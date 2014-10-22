package parallel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class RemoteAliveJob implements Runnable{
	
	private Thread t;
	private String threadName;
	private String hostname;
	Host hosts_status;
	
	RemoteAliveJob( String thread_name, String host, Host h_status){
		threadName = thread_name;
		hostname = host;
		hosts_status = h_status;
	}
	
	public void run(){
		
		try {
			InetAddress address = InetAddress.getByName(hostname);
			boolean status = Boolean.valueOf(address.isReachable(3000)); // Retrieving the status of the host
			
			synchronized (hosts_status){ // Putting the status of the host in the data structure shared among threads.
				hosts_status.setHostReachability(hostname,status);
			}
			
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
