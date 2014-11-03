package parallel;

import java.util.Enumeration;
import java.util.Hashtable;

public class MappingUMMapper {
	private Hashtable<String,String> hm = new Hashtable<String,String>();

	public void setMapping(String um, String host){
		hm.put(um, host);
	}
	
	public String getHost(String um){
		return hm.get(um);
	}
	
	public void describe(){
		Enumeration<String> um = hm.keys();
		String name;
		
		while (um.hasMoreElements()){
			name = (String) um.nextElement();
			System.out.println(name + " " + hm.get(name) );
		}
		
	}
}
