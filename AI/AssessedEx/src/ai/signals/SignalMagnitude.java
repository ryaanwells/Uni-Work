package ai.signals;

public class SignalMagnitude extends SignalProcess{
	
	@Override
	public Signal process(Signal signal, int windowSize) {
		double[] base = signal.getSignalValues();
		double[] M = new double[base.length];
		int samplesInWindow = signal.samplesInWindow(windowSize);
		int k, n;
		double magnitude;
		for (n = 0; n < M.length; n++){
			magnitude = 0.0;
			for (k = n; k >= n - samplesInWindow && k >= 0; k--){
				magnitude += base[k] >= 0 ? base[k] : -base[k];
			}
			M[n] = magnitude;
		}
		return new Signal(M, signal.sampleLength());
	}

}
