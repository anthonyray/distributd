package parallel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

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
			
			final SSHClient ssh = new SSHClient();
			ssh.addHostKeyVerifier(new PromiscuousVerifier());
			
			ssh.connect(hostname);
			
			try {
				ssh.authPublickey(System.getProperty("user.name"));
				final Session session = ssh.startSession();
				
				try {
					Random rand = new Random();
					int randomNum = rand.nextInt((5 - 1) + 1) + 1;
					final Command cmd = session.exec("sleep "+Integer.toString(randomNum) +" s");
					System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
					cmd.join(5, TimeUnit.SECONDS);
				} 
				
				finally {
					session.close();
				}
			} 
			
			finally {
				ssh.disconnect();
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
