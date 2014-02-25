package constituents;

public class MP implements Comparable<MP>{

	private String name;
	private Party party;
	private int[] votes;
	private double similarity;
	
	private Party guessedClass;
	
	public MP (String name, String party, int[] votes){
		this.name = name;
		this.party = Party.getParty(party);
		this.votes = votes;
		this.similarity = 0;
	}
	
	public void setSimilarity(MP other){
		int crossProduct = 0;
		int magA = 0;
		int magB = 0;
		double normalization;
		for (int i = 0; i < this.votes.length; i++){
			crossProduct += this.votes[i] * other.votes[i];
			// Since a vote is either 1, 0 or -1, squaring the 
			// magnitude of this component will always result in 
			// 1 or 0. Hence we do not need to square this component 
			// and can instead just check for non-zero numbers.
			magA += (this.votes[i] != 0) ? 1 : 0;
			magB += (other.votes[i] != 0) ? 1 : 0;
		}
		normalization = Math.sqrt(magA) * Math.sqrt(magB);
		similarity = crossProduct / normalization;
	}
	
	public String getName(){
		return name;
	}
	
	public Party getParty(){
		return party;
	}
	
	public double getSimilarity(){
		return similarity;
	}
	
	public void guessParty(Party party){
		this.guessedClass = party;
	}
	
	public Party getGuess(){
		return this.guessedClass;
	}
	
	public boolean guessedCorrect(){
		return this.guessedClass == this.party;
	}
	
	public int compareTo(MP other){
		return this.similarity > other.similarity ? -1 : 1;
	}

}
