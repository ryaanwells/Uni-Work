package src.ai.assesseded;


import ai.core.Classify;
import ai.io.ProcessSamples;


public class Lab2 {

	public static void main(String[] args) {
		ProcessSamples PS = new ProcessSamples("silenceValues.txt", "speechValues.txt");
		PS.process(0.001, 30, 300);
				
		double[][] silences = PS.getSilences();
		double[][] speeches = PS.getSpeeches();
		
		double[][] testSilence;
		double[][] testSpeech;
		double[][] groundSilence;
		double[][] groundSpeech;
		
		double correct = 0;
		double incorrect = 0;
		Classify C;
		for (int initial = 0; initial <= 45; initial+=5){
			testSilence = new double[5][3];
			testSpeech = new double[5][3];
			groundSilence = new double[45][3];
			groundSpeech = new double[45][3];
			for (int k = 0; k < 50; k++){
				if (k >= initial && k < initial+5){
					testSilence[k % 5] = silences[k];
					testSpeech[k % 5] = speeches[k];
				}
				else {
					groundSilence[k <= initial? k : k - 5] = silences[k];
					groundSpeech[k <= initial? k : k - 5] = speeches[k];
				}
			}
			
			C = new Classify(groundSilence, groundSpeech);
			
			for (int i = 0; i < testSilence.length; i++){
				if (C.isSpeech(testSilence[i][0], testSilence[i][1], testSilence[i][2])){
					incorrect++;
				}
				else {
					correct++;
				}
				
				if (C.isSpeech(testSpeech[i][0], testSpeech[i][1], testSpeech[i][2])){
					correct++;
				}
				else {
					incorrect++;
				}
			}
		}
		System.out.println(correct + " " + incorrect + " " + correct / (correct + incorrect));
	}
}
