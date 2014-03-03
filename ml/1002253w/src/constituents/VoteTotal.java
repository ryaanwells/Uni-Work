package constituents;

public class VoteTotal {

	private int[] inFavour;
	private int[] neutral;
	private int[] against;
	private int[] votesCast;
	private int numMPS;
	
	public VoteTotal(){
		inFavour = new int[1288];
		neutral = new int[1288];
		against = new int[1288];
		votesCast = new int[1288];
		numMPS = 0;
	}
	
	public void addVote(int voteID, int vote){
		if (vote > 0){
			inFavour[voteID]++;
		}
		else if (vote == 0){
			neutral[voteID]++;
		}
		else {
			against[voteID]++;
		}
		votesCast[voteID]++;
	}
	
	public void incMPS(){
		numMPS++;
	}
	
	public int totalMPS(){
		return numMPS;
	}
	
	public int[] getResultsForVote(int voteID){
		int[] result = new int[4];
		result[0] = inFavour[voteID];
		result[1] = neutral[voteID];
		result[2] = against[voteID];
		result[3] = votesCast[voteID];
		return result;
	}
	
	public int[] aggregate(){
		int[] result = new int[3];
		for (int i = 0; i < 1288; i++){
			result[0] += inFavour[i];
			result[1] += neutral[i];
			result[2] += against[i];
		}
		return result;
	}
	
	public double[] aggregateNormalized(){
		int[] agg = aggregate();
		double[] result = new double[3];
		result[0] = ((double) agg[0]) / numMPS;
		result[1] = ((double) agg[1]) / numMPS;
		result[2] = ((double) agg[2]) / numMPS;
		return result;
	}
	
}
