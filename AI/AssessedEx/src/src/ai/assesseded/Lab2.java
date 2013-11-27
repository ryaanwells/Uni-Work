package src.ai.assesseded;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import ai.signals.Signal;
import ai.signals.SignalEnergy;
import ai.signals.SignalMagnitude;
import ai.signals.SignalProcess;
import ai.signals.SignalZeroCrossingRate;

public class Lab2 {

	public static void main(String[] args) {
		SignalProcess SE = new SignalEnergy();
		SignalProcess SM = new SignalMagnitude();
		SignalProcess SZ = new SignalZeroCrossingRate();
		
		double scaleFactor = 0.001;
		int sampleLength = 300;
		int windowSize = 30;

		Path path;
		Scanner scanner;
		
		double[] data;
		Signal signal;
		Signal energySignal;
		Signal magnitudeSignal;
		Signal zeroCrossingSignal;
		
		for (int i = 1; i < 51; i++){
			path = Paths.get("audio/silence_" + (i > 9 ? i : "0" + i) + ".dat");
			try {
				scanner = new Scanner(path);
			} catch (IOException e) {
				System.out.println("Could not find audio sample for audio/silence_" + (i > 9 ? i : "0" + i) + ".dat"); 
				continue;
			}
			int count = 0;
			data = new double[2400];
			while (scanner.hasNext()){
				data[count] = scanner.nextInt() * scaleFactor;
				count++;
			}
			signal = new Signal(data, sampleLength);
			energySignal = SE.process(signal, windowSize);
			magnitudeSignal = SM.process(signal, windowSize);
			zeroCrossingSignal = SZ.process(signal, windowSize);
			System.out.println(energySignal.getAverage(false) + " " +
					magnitudeSignal.getAverage(false) + " " +
					zeroCrossingSignal.getAverage(false));
		}

	}

}
