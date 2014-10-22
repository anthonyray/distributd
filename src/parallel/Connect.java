package parallel;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.net.*;

public class Connect
{

	public static void main(String... args) throws IOException, InterruptedException
	{
		
		
		List<RemoteJob> jobs = new ArrayList<RemoteJob>();
		
		// Initialize array
		
		for (int i = 0 ; i < 10 ; i++){
			jobs.add(new RemoteJob("thread" + Integer.toString(i)) );
			
		}
		
		// Launch threads
		
		for (int i = 0 ; i < jobs.size() ; i++){
			jobs.get(i).start();

		}
		for (int i = 0 ; i < jobs.size() ; i++){
			jobs.get(i).join();
		}
		
		System.out.println("Done !");

		
		
		
		
		/*final SSHClient ssh = new SSHClient();
		ssh.addHostKeyVerifier(new PromiscuousVerifier());
		//ssh.loadKnownHosts();
		
		ssh.connect("joffrey");
		
		try {
			ssh.authPublickey(System.getProperty("user.name"));
			final Session session = ssh.startSession();
			
			try {
				final Command cmd = session.exec("ping -c 1 google.com");
				System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
				cmd.join(5, TimeUnit.SECONDS);
				System.out.println("\n** exit status: " + cmd.getExitStatus());
			} 
			
			finally {
				session.close();
			}
		} 
		
		finally {
			ssh.disconnect();
		}*/
	}
}


