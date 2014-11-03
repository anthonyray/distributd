package parallel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;


public class SplitMapper implements Runnable{
	
	private Thread t;
	String threadName = null;
	int id = 0;
	String inputFile = null;
	MappingUMMapper um = null;
	
	final static Charset ENCODING = StandardCharsets.UTF_8;
	
	SplitMapper(String threadname, String inputfile, int suffix, MappingUMMapper um_dict){
		threadName = threadname;
		inputFile = inputfile;
		id = suffix;
		um = um_dict;
	}
	
	public List<String> readInput() throws IOException{
		
		Path path = Paths.get(inputFile);
		List<String> words = new ArrayList<String>();
		
		List<String> lines = Files.readAllLines(path, ENCODING);
		
		for (String line : lines){
			for (String word: line.split("\\s+")){
				words.add(word);
			}
		}
		
		return words;
	}
	
	public void writeOutput(List<String> output,String filename) throws IOException{
		Path path = Paths.get(filename);
	    Files.write(path, output, ENCODING);
	}
	
	public List<String> generateKV(List<String> words){
		List<String> output = new ArrayList<String>();
		
		for (String word : words){
			output.add(word+": 1");
		}
		
		return output;
		
	}
	
	public void run(){
		try {
			List<String> words = readInput();
			List<String> output = generateKV(words);
			writeOutput(output,"data/um"+Integer.toString(id)+".txt");
			
			synchronized(um){
				um.setMapping("um"+Integer.toString(id),threadName);
			}
		
			System.out.println("[>Thread] " + threadName + " completed");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start(){
		System.out.println("[>Thread] " + threadName + " starting");
		
		if (t == null){
			t = new Thread(this,threadName);
			t.start();
			
		}
	}
	
	public void join() throws InterruptedException{
		t.join();
	}

}
