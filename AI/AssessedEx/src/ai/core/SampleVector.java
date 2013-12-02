package ai.core;

public class SampleVector {

	double energy;
	double magnitude;
	double zeroCrossing;
	private boolean isSpeech;
	private boolean classifiedAsSpeech;

	/*
	 * Class for use in computing the Vector Space approach of classification.
	 */
	public SampleVector(double energy, double magnitude, double zeroCrossing,
			boolean isSpeech) {
		this.energy = energy;
		this.magnitude = magnitude;
		this.zeroCrossing = zeroCrossing;
		this.isSpeech = isSpeech;
	}

	/*
	 * Compute the cosine similarity of this SampleVector with another.
	 */
	public double similarity(SampleVector other) {
		return (this.dotProduct(other)) / (this.length() * other.length());
	}

	/*
	 * Compute the dot product of this SampleVector with another
	 */
	public double dotProduct(SampleVector other) {
		return (this.energy * other.energy)
				+ (this.magnitude * other.magnitude)
				+ (this.zeroCrossing * other.zeroCrossing);
	}

	/*
	 * Compute the length of the vector inside this SampleVector class.
	 */
	public double length() {
		return Math.sqrt((this.energy * this.energy)
				+ (this.magnitude * this.magnitude)
				+ (this.zeroCrossing * this.zeroCrossing));
	}

	
	public boolean isSpeech() {
		return isSpeech;
	}

	public void classifyAs(boolean classification){
		this.classifiedAsSpeech = classification;
	}
	
	public boolean classifiedAsSpeech(){
		return this.classifiedAsSpeech;
	}
	
	public boolean correctlyClassified(){
		return this.isSpeech == this.classifiedAsSpeech;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof SampleVector) {
			SampleVector s = (SampleVector) o;
			return ((this.energy == s.energy)
					&& (this.magnitude == s.magnitude) && (this.zeroCrossing == s.zeroCrossing));
		}
		return false;
	}
	
	@Override
	public String toString(){
		return "<" + this.energy + ", " + this.magnitude + ", " + this.zeroCrossing + ">";
	}

}
