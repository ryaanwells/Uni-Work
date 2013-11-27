package src.ai.assesseded;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import ai.io.ProcessFile;
import ai.signals.*;

public class Lab1 {
	
	public static void main(String args[]){
		String filename = System.getProperty("user.dir") + "/laboratory.dat";
		final double scaleFactor = 0.001;
		final int sampleLength = 300; //ms
		final int windowSize = 30; //ms
		
		ProcessFile PF = null;
		
		try {
			PF = new ProcessFile(filename, scaleFactor);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		
		System.out.println("Finished Reading");
		
		Signal signal = new Signal(PF.getContents(), sampleLength);
		double[] signalNormalized = signal.getNormalizedSignalValues();
		
		Path energyData = Paths.get("energyData_NEW.txt");
		try {
			Files.deleteIfExists(energyData);
			energyData = Files.createFile(energyData);
		} catch(IOException e){
			System.err.println("Could not create energyData.txt");
		}
		
		System.out.println("Output file created");
		
		SignalProcess energyProcess = new SignalEnergy();
		SignalProcess magnitudeProcess = new SignalMagnitude();
		SignalProcess zeroCrossingProcess = new SignalZeroCrossingRate();

		Signal energy = energyProcess.process(signal, windowSize);
		Signal magnitude = magnitudeProcess.process(signal, windowSize);
		Signal zeroCrossing = zeroCrossingProcess.process(signal, windowSize);
		
		double[] energyNormalized = energy.getNormalizedSignalValues();
		double[] magnitudeNormalized = magnitude.getNormalizedSignalValues();
		double[] zeroCrossingNormalized = zeroCrossing.getNormalizedSignalValues();
		
		try (BufferedWriter writer = Files.newBufferedWriter(energyData, Charset.defaultCharset())){
			for (int i = 0; i < signalNormalized.length; i++){
				writer.write(String.format("%.2f %.2f %.2f %.2f", 
											signalNormalized[i], energyNormalized[i], 
											magnitudeNormalized[i], zeroCrossingNormalized[i]));
				writer.newLine();
			}
			writer.flush();
		} catch(IOException e){
			System.err.println("Could not create buffered writer for energyData.txt");
		}
		System.out.println(energy.getAverage(false));
		System.out.println(magnitude.getAverage(false));
		System.out.println(zeroCrossing.getAverage(false));
		System.out.println("Done.");
	}
	
}
