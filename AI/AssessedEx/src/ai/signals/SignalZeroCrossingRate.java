package ai.signals;

public class SignalZeroCrossingRate extends SignalProcess{
	
	@Override
	public Signal process(Signal signal, int windowSize) {
		double[] base = signal.getSignalValues();
		double[] Z = new double[base.length];
		int samplesInWindow = signal.samplesInWindow(windowSize);
		int k, n;
		double sign, signCurrent, signPrevious;
		for (n = 0; n < Z.length; n++){
			sign = 0.0;
			for (k = n; k >= n - samplesInWindow && k >= 0; k--){
				signCurrent = base[k] >= 0 ? 1 : -1;
				signPrevious = (k-1) >= 0 ? (base[k-1] >= 0 ? 1 : -1) : 0;
				sign += Math.abs(signCurrent - signPrevious);
			}
			Z[n] = sign / 2 * samplesInWindow;
		}
		return new Signal(Z, signal.sampleLength());
	}

}
