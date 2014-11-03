package parallel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Master {

	public static void main(String... args) throws IOException, InterruptedException{
		FileSplitter fs = new FileSplitter("data/data.txt","data/s");
		
		MappingUMMapper um = new MappingUMMapper();
		
		List<String> splits = fs.split();
		
		
		List<SplitMapper> map_threads = new ArrayList<SplitMapper>();
		
		for (int i=0 ; i < splits.size() ; i++){
			map_threads.add(
					new SplitMapper("thread_um"+Integer.toString(i),splits.get(i),i,um)
			);
		}
		
		for (SplitMapper map : map_threads){
			map.start();
		}
		
		for (SplitMapper map: map_threads){
			map.join();
		}
		System.out.println("------");
		um.describe();
		System.out.println("------");
		System.out.println("[>MASTER] Mapping done");
		
	}
}
