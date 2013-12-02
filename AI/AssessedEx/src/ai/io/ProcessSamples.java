package ai.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import ai.signals.Signal;
import ai.signals.SignalEnergy;
import ai.signals.SignalMagnitude;
import ai.signals.SignalProcess;
import ai.signals.SignalZeroCrossingRate;

public class ProcessSamples {

	Path pathSilence;
	Path pathSpeech;
	
	SignalProcess SE;
	SignalProcess SM;
	SignalProcess SZ;
	
	double[][] silences;
	double[][] speeches;
	
	public ProcessSamples(String outputSilence, String outputSpeech){
		pathSilence = Paths.get(outputSilence);
		pathSpeech = Paths.get(outputSpeech);
		SE = new SignalEnergy();
		SM = new SignalMagnitude();
		SZ = new SignalZeroCrossingRate();
		silences = new double[50][3];
		speeches = new double[50][3];
	}
	
	
	/*
	 * This function reads the speech and silence samples in, computes the 
	 * energy, magnitude and zero crossing rate for each sample and stores them
	 * in the appropriate array for later use.
	 * 
	 */
	public void process(double scaleFactor, int windowSize, int sampleLength){
		
		BufferedWriter outSpeech = null;
		BufferedWriter outSilence = null;
		
		// Delete the output files if they exist.x
		
		try {
			Files.deleteIfExists(pathSilence);
			pathSilence = Files.createFile(pathSilence);
		} catch (IOException IOE){
			System.err.println("Could not instantiate file.");
			System.exit(0);
		}
		
		try {
			Files.deleteIfExists(pathSpeech);
			pathSpeech = Files.createFile(pathSpeech);
		} catch (IOException IOE){
			System.err.println("Could not instantiate file.");
			System.exit(0);
		}
		
		// Create writers to write the data out to the two files.
		
		try {
			outSilence = Files.newBufferedWriter(pathSilence, Charset.defaultCharset());
		} catch (IOException IOE){
			System.err.println("Could not create writer for file.");
			System.exit(0);
		}
		
		try {
			outSpeech = Files.newBufferedWriter(pathSpeech, Charset.defaultCharset());
		} catch (IOException IOE){
			System.err.println("Could not create writer for file.");
			System.exit(0);
		}
	
		Path path;
		Scanner scanner;
		double[] data;
		
		Signal signal;
		
		Signal energySignal;
		Signal magnitudeSignal;
		Signal zeroCrossingSignal;
		
		
		// For each of the 50 samples of speech and audio.
		for (int i = 1; i < 51; i++){
			path = Paths.get("audio/speech_" + (i > 9 ? i : "0" + i) + ".dat");
			try {
				scanner = new Scanner(path);
			} catch (IOException e) {
				System.out.println("Could not find audio sample for audio/speech_" + (i > 9 ? i : "0" + i) + ".dat"); 
				continue;
			}
			// There are 2400 points per sample.
			int count = 0;
			data = new double[2400];
			while (scanner.hasNext()){
				data[count] = scanner.nextInt() * scaleFactor;
				count++;
			}
			
			// Create the signal from this data and create the three computed signals from it.
			signal = new Signal(data, sampleLength);
			energySignal = SE.process(signal, windowSize);
			magnitudeSignal = SM.process(signal, windowSize);
			zeroCrossingSignal = SZ.process(signal, windowSize);
			
			// Log this entry.
				
			speeches[i-1][0] = Math.log(energySignal.getAverage(false));
			speeches[i-1][1] = Math.log(magnitudeSignal.getAverage(false));
			speeches[i-1][2] = zeroCrossingSignal.getAverage(false);
			
			
			try {
				outSpeech.write(speeches[i-1][0] + " " + speeches[i-1][1] + " " + speeches[i-1][2] + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			// Repeat for silence
			path = Paths.get("audio/silence_" + (i > 9 ? i : "0" + i) + ".dat");
			try {
				scanner = new Scanner(path);
			} catch (IOException e) {
				System.out.println("Could not find audio sample for audio/silence_" + (i > 9 ? i : "0" + i) + ".dat"); 
				continue;
			}
			
			count = 0;
			data = new double[2400];
			while (scanner.hasNext()){
				data[count] = scanner.nextInt() * scaleFactor;
				count++;
			}
			signal = new Signal(data, sampleLength);
			energySignal = SE.process(signal, windowSize);
			magnitudeSignal = SM.process(signal, windowSize);
			zeroCrossingSignal = SZ.process(signal, windowSize);
			
			silences[i-1][0] = Math.log(energySignal.getAverage(false));
			silences[i-1][1] = Math.log(magnitudeSignal.getAverage(false));
			silences[i-1][2] = zeroCrossingSignal.getAverage(false);
			
			//System.out.println(silences[i-1][0] + " " + silences[i-1][1] + " " + silences[i-1][2]);
			
			try {
				outSilence.write(silences[i-1][0] + " " + silences[i-1][1] + " " + silences[i-1][2] +" \n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		try {
			outSilence.flush();
			outSpeech.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double[][] getSpeeches(){
		return speeches;
	}
	
	public double[][] getSilences(){
		return silences;
	}
	
}
