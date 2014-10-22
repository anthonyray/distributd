package parallel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Hostsreader {
	
	public List<String> getHostsList(){
		
		String s = null;
		List<String> hosts = new ArrayList<String>(); // Instantiate a data structure to hold the hosts list
		
		try{
			Process p = Runtime.getRuntime().exec("arp -a"); // Retrieve list of hosts
			
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			System.out.println("Output of the command:\n");
            
			while ((s = stdInput.readLine()) != null) {
                String hostname_output = s.split("\\s+")[0]; // Retrieving the hostname
				hosts.add(hostname_output); // Adding it to the data structure
            }
			
			return hosts;

        }
        catch (IOException e) {
            System.out.println("Error while trying to retrieve the list of hosts ... ");
            System.exit(-1);
        }
		
		return hosts;
		
	}

}
