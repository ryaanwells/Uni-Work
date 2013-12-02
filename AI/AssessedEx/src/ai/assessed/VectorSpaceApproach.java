package ai.assessed;

import ai.core.SampleVector;
import ai.core.VectorSpace;
import ai.io.ProcessSamples;

public class VectorSpaceApproach {


	public static void main(String[] args) {
		// Initialize and read in the data.
		ProcessSamples PS = new ProcessSamples("silenceValues.txt", "speechValues.txt");
		PS.process(0.001, 30, 300);

		// Vectorize the signals
		SampleVector[] SV = new SampleVector[100];
		
		int count = 0;
		
		for(double[] v: PS.getSpeeches()){
			SV[count] = new SampleVector(v[0], v[1], v[2], true);
			count++;
		}
		
		for(double[] v: PS.getSilences()){
			SV[count] = new SampleVector(v[0], v[1], v[2], false);
			count++;
		}
		
		
		// Create the vector space in which to compute similarity.
		VectorSpace V = new VectorSpace(SV);
		
		// V.classify needs one speech sample to determine which class
		// is speech out of the classification it makes. It will still do
		// the classification correctly, but unless we can identify which of
		// the classes represent speech then all we have managed to do is 
		// determine which samples could be grouped in a certain way.
		System.out.println(V.classify(SV[0]));

	}

}
