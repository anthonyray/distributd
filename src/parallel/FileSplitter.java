package parallel;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileSplitter {
	
	// Properties
	
	final static Charset ENCODING = StandardCharsets.UTF_8;
	String filename = null;
	String split_prefix = null;
	
	// Constructor 
	
	FileSplitter(String datafile, String prefix){
		filename = datafile;
		split_prefix = prefix;
	}
	
	// Methods
	
	List<String> readSmallTextFile(String filename) throws IOException{
		
		Path path = Paths.get(filename);
		return Files.readAllLines(path, ENCODING);
		
	}
	
	void writeSmallTextFile(List<String> aLines, String aFileName) throws IOException {
	    Path path = Paths.get(aFileName);
	    Files.write(path, aLines, ENCODING);
	}
	
	List<String> split() throws IOException{
		
		List<String> splits = readSmallTextFile(filename); // Splits the file
		
		List<String> splits_files = new ArrayList<String>();
		
		for (int i = 0 ; i < splits.size() ; i++){ // Write each line to a file
			String split_filename = split_prefix + Integer.toString(i) + ".txt";
			List<String> single_line = new ArrayList<String>();
			single_line.add(splits.get(i));
			writeSmallTextFile(single_line,split_filename);
			splits_files.add(split_filename);
		}
		
		System.out.println("[>MASTER] Done splitting the file");
		return splits_files;
	}

}
