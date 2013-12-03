package ai.core;

public class Classify {

	double[] meanSilences;
	double[] meanSpeeches;
	
	double[] varianceSilence;
	double[] varianceSpeech;
	
	public Classify(double[][] groundSilence, double[][] groundSpeech){
		meanSilences = new double[3]; // E M Z
		meanSpeeches = new double[3]; // E M Z
		
		varianceSilence = new double[3]; // E M Z
		varianceSpeech = new double[3]; // E M Z
		
		for (int i=0; i < groundSilence.length; i++){
			meanSilences[0] += groundSilence[i][0];
			meanSilences[1] += groundSilence[i][1];
			meanSilences[2] += groundSilence[i][2];
			meanSpeeches[0] += groundSpeech[i][0];
			meanSpeeches[1] += groundSpeech[i][1];
			meanSpeeches[2] += groundSpeech[i][2];
		}
		
		meanSilences[0] = meanSilences[0] / (double) groundSilence.length;
		meanSilences[1] = meanSilences[1] / (double) groundSilence.length;
		meanSilences[2] = meanSilences[2] / (double) groundSilence.length;
		meanSpeeches[0] = meanSpeeches[0] / (double) groundSpeech.length;
		meanSpeeches[1] = meanSpeeches[1] / (double) groundSpeech.length;
		meanSpeeches[2] = meanSpeeches[2] / (double) groundSpeech.length;
		
		varianceSilence[0] = getStandardDev(groundSilence, 0, meanSilences[0]);
		varianceSilence[1] = getStandardDev(groundSilence, 1, meanSilences[1]);
		varianceSilence[2] = getStandardDev(groundSilence, 2, meanSilences[2]);
		varianceSpeech[0] = getStandardDev(groundSpeech, 0, meanSpeeches[0]);
		varianceSpeech[1] = getStandardDev(groundSpeech, 1, meanSpeeches[1]);
		varianceSpeech[2] = getStandardDev(groundSpeech, 2, meanSpeeches[2]);
	}
	
	public boolean isSpeech(double e, double m, double z){
		double silence = getGaussian(e, meanSilences[0], varianceSilence[0]) *
				getGaussian(m, meanSilences[1], varianceSilence[1]) *
				getGaussian(z, meanSilences[2], varianceSilence[2]);
		double speech = getGaussian(e, meanSpeeches[0], varianceSpeech[0]) *
				getGaussian(m, meanSpeeches[1], varianceSpeech[1]) *
				getGaussian(z, meanSpeeches[2], varianceSpeech[2]);
		
		return speech > silence;
	}
	
	private double getStandardDev(double[][] base, int emz, double median){
		double count = 0;
		for (int i = 0; i < base.length; i++){
			count += ((base[i][emz] - median) * (base[i][emz] - median));
		}
		return count / (double) base.length;
	}
	
	private double getGaussian(double x, double m, double sSqd){
		return (Math.exp( - (((x-m)*(x-m)) / (2*sSqd)) ) / (Math.sqrt(2 * Math.PI * sSqd)));
	}

}
