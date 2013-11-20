package ai.io;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class ProcessFile {

	private double[] contents;
	
	public ProcessFile(String filename, double scaleFactor) throws IOException{
		Path path = Paths.get(filename);
		Scanner scanner = new Scanner(path);
		String next;
		ArrayList<Double> AL = new ArrayList<Double>(200);
		
		while(scanner.hasNext()){
			next = scanner.nextLine();
			AL.add(Integer.parseInt(next) * scaleFactor);
		}
		
		scanner.close();
		AL.trimToSize();
		
		contents = new double[AL.size()];
		
		Iterator<Double> iter = AL.iterator();
		
		for (int i = 0; i < contents.length; i++){
			contents[i] = iter.next().doubleValue();
		}
	}
	
	public double[] getContents(){
		return contents;
	}
	
}
