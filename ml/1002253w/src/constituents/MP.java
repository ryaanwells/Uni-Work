package constituents;

public class MP implements Comparable<MP>{

	private String name;
	private Party party;
	private int[] votes;
	private double similarity;
	
	public MP (String name, String party, int[] votes){
		this.name = name;
		this.party = Party.getParty(party);
		this.votes = votes;
		this.similarity = 0;
	}
	
	public void voidSimilarity(){
		similarity = -1;
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
	
	public void setParty(Party p){
		this.party = p;
	}
	
	public double getSimilarity(){
		return similarity;
	}
	
	public int[] getVotes(){
		return votes;
	}
	
	public int compareTo(MP other){
		if (this.similarity == other.similarity) return 0;
		return this.similarity >= other.similarity ? -1 : 1;
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof MP){
			MP mp = (MP) o;
			return (this.party == mp.party && 
					this.name == mp.name);
		}
		return false;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.name);
		sb.append(",");
		sb.append(this.party.getName());
		for (int vote: votes){
			sb.append(",");
			sb.append(vote);
		}
		return sb.toString();
	}
	
}
