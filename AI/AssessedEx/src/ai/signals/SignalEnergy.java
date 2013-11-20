package ai.signals;

public class SignalEnergy extends SignalProcess{
	
	@Override
	public Signal process(Signal signal, int windowSize) {
		double[] base = signal.getSignalValues();
		double[] E = new double[base.length];
		int samplesInWindow = signal.samplesInWindow(windowSize);
		int k, n;
		double energyValue;
		for (n = 0; n < E.length; n++){
			energyValue = 0.0;
			for (k = n; k >= n - samplesInWindow && k >= 0; k--){
				energyValue += (base[k] * base[k]);
			}
			E[n] = energyValue;
		}
		return new Signal(E, signal.sampleLength());
	}
	
}
