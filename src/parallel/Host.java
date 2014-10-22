package parallel;

import java.util.Enumeration;
import java.util.Hashtable;

public class Host {
	private Hashtable<String,Boolean> hm = new Hashtable<String,Boolean>();

	public void setHostReachability(String host, boolean status){
		hm.put(host, status);
	}
	
	public boolean getHostReachability(String host){
		return hm.get(host);
	}
	
	public void describe(){
		Enumeration<String> hostname = hm.keys();
		String name;
		
		while (hostname.hasMoreElements()){
			name = (String) hostname.nextElement();
			System.out.println(name + " " + Boolean.toString(hm.get(name)));
		}
		
	}
}
