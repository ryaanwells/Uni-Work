package ai.core;

public class VectorSpace {

	private SampleVector[] SV;

	public VectorSpace(SampleVector[] SV) {
		this.SV = SV;
	}

	public double classify(SampleVector speech) {
		SampleVector centroidA = SV[(int) (Math.random() * (SV.length - 1))];
		SampleVector centroidB = SV[(int) (Math.random() * (SV.length - 1))];

		boolean speechIsClassA = false;
		
		while (centroidA.equals(centroidB)) {
			centroidB = SV[(int) (Math.random() * (SV.length - 1))];
		}

		for (int i = 0; i < 4; i++) {
			
			double countA = 0;
			double countB = 0;
			double[] classA = new double[3];
			double[] classB = new double[3];

			for (SampleVector s : SV) {
				// if the vector we're comparing is one of our centroids
				// then skip it
				if (s.equals(centroidA) || s.equals(centroidB))
					continue;

				// if the vector we're comparing is closer to centroidA then
				// classify it as in class TRUE, otherwise class FALSE
				if (s.similarity(centroidA) > s.similarity(centroidB)) {
					if (s.equals(speech)){ speechIsClassA = true; }
					s.classifyAs(true);
					countA++;
					classA[0] += s.energy;
					classA[1] += s.magnitude;
					classA[2] += s.zeroCrossing;
				} else {
					if (s.equals(speech)){ speechIsClassA = false; }
					s.classifyAs(false);
					countB++;
					classB[0] += s.energy;
					classB[1] += s.magnitude;
					classB[2] += s.zeroCrossing;
				}
			}

			SampleVector averageA = new SampleVector(classA[0]
					/ (double) countA, classA[1] / (double) countA, classA[2]
					/ (double) countA, true);
			SampleVector averageB = new SampleVector(classB[0]
					/ (double) countB, classB[1] / (double) countB, classB[2]
					/ (double) countB, false);

			// recompute centroids based upon current distribution

			for (SampleVector s : SV) {
				if (averageA.similarity(s) > averageB.similarity(s)) {
					if (averageA.similarity(s) > averageA.similarity(centroidA)) {
						centroidA = s;
					}
				} else {
					if (averageB.similarity(s) > averageB.similarity(centroidB)) {
						centroidB = s;
					}
				}
			}
		}

		double correct = 0;
		for (SampleVector s : SV) {
			if (s.isSpeech() && (speechIsClassA == s.classifiedAsSpeech())){
				correct++;
			}
			else if (!s.isSpeech() && (speechIsClassA != s.classifiedAsSpeech())){
				correct++;
			}
		}
		return correct / (SV.length + 0.0);
	}

}
