package ai.signals;

public class Signal {
	
	double[] signal;
	double[] normalizedSignal;
	int sampleLength;
	
	public Signal(double[] signal, int sampleLength){
		this.signal = new double[signal.length];
		this.normalizedSignal = new double[signal.length];
		double max = 0.0;
		for (int i = 0; i < this.signal.length; i++){
			this.signal[i] = signal[i];
			this.normalizedSignal[i] = signal[i];
			if (this.signal[i] > max) { max = this.signal[i]; }
		}
		for (int i = 0; i < this.normalizedSignal.length; i++){
			this.normalizedSignal[i] = this.normalizedSignal[i] / max;
		}
		this.sampleLength = sampleLength;
	}
	
	public int sampleCount(){
		return this.signal.length;
	}
	
	public int sampleLength(){
		return this.sampleLength;
	}
	
	public double samplingRate(){
		return this.signal.length / this.sampleLength;
	}
	
	public int samplesInWindow(int windowLength){
		return this.signal.length * windowLength / this.sampleLength;
	}
	
	public double[] getSignalValues(){
		return this.signal;
	}
	
	public double[] getNormalizedSignalValues(){
		return this.normalizedSignal;
	}
	
}
