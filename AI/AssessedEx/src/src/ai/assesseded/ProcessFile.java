package src.ai.assesseded;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
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
	
	public int getSampleCount(){
		return values.size();
	}
	
	public double getSamplingRate(){
		return getSampleCount()/this.sampleLength;
	}
	
	public double getSamplesInWindow(double windowLength){
		return getSampleCount() * (windowLength / this.sampleLength);
	}
	
	public double[] getEnergy(int windowSamples){
		int k, n; 
		double energyValue = 0.0;
		double[] E = new double[this.getSampleCount()];
		for (n = 0; n < this.getSampleCount(); n++){
			for (k = n; k >= n - windowSamples && k >= 0; k--){
				energyValue += values.get(k) * values.get(k);
			}
			System.out.print(n + " ");
			System.out.println(energyValue * 1000);
						
			E[n] = energyValue;
			energyValue = 0.0;
		}
		System.out.println("windowSamples: " + windowSamples);
		return E;
	}
	
	public static void main(String args[]){
		String filename = System.getProperty("user.dir") + "/laboratory.dat";
		final double scaleFactor = 0.001;
		final double sampleLength = 0.3;
		final double windowSize = 0.03;
		ProcessFile pf = new ProcessFile(filename, scaleFactor, sampleLength);
		try {
			pf.readFile();
		}
		catch (IOException e){
			System.err.println("Could not read file");
		}
		System.out.println("Finished Reading");
		
		Path energyData = Paths.get("energyData.txt");
		try {
			Files.deleteIfExists(energyData);
			energyData = Files.createFile(energyData);
		} catch(IOException e){
			System.err.println("Could not create energyData.txt");
		}
		
		try (BufferedWriter writer = Files.newBufferedWriter(energyData, Charset.defaultCharset())){
			double[] energy = pf.getEnergy((int)pf.getSamplesInWindow(windowSize));
			for (int i = 0; i < energy.length; i++){
				writer.write(String.format("%.2f", energy[i]));
				writer.newLine();
			}
			writer.flush();
		} catch(IOException e){
			System.err.println("Could not create buffered writer for energyData.txt");
		}
		
		System.out.println(pf.getSamplesInWindow(windowSize));
		
	}
	
}
