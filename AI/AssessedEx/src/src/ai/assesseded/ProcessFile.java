package src.ai.assesseded;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;



public class ProcessFile {
	private String filename;
	private Path path;
	private ArrayList<Double> values;
	private double scaleFactor;
	private double sampleLength;
	
	public ProcessFile(String filename, Double scaleFactor, Double sampleLength){
		this.filename = filename;
		this.path = Paths.get(filename);
		this.values = new ArrayList<Double>(200);
		this.scaleFactor = scaleFactor;
		this.sampleLength = sampleLength;
	}
	
	public void readFile() throws IOException{
		try(Scanner scanner = new Scanner(path)){
			String next;
			while (scanner.hasNextLine()){
				next = scanner.nextLine();
				values.add(Integer.parseInt(next)*scaleFactor);
			}
		}
	}
	
	public int getValueCount(){
		return values.size();
	}
	
	public double getSamplingRate(){
		return getValueCount()/this.sampleLength;
	}
	
	public static void main(String args[]){
		String filename = System.getProperty("user.dir") + "/laboratory.dat";
		final double scaleFactor = 0.001;
		final double sampleLength = 0.3;
		ProcessFile pf = new ProcessFile(filename, scaleFactor, sampleLength);
		try {
			pf.readFile();
		}
		catch (IOException e){
			System.err.println("Could not read file");
		}
		System.out.println("Finished");
		System.out.println(pf.getSamplingRate());
	}

}
