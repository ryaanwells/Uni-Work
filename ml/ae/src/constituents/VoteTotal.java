package constituents;

public class VoteTotal {

	private int[] inFavour;
	private int[] neutral;
	private int[] against;
	private int[] votesCast;
	
	public VoteTotal(){
		inFavour = new int[1288];
		neutral = new int[1288];
		against = new int[1288];
		votesCast = new int[1288];
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
	
	public int[] getResultsForVote(int voteID){
		int[] result = new int[4];
		result[0] = inFavour[voteID];
		result[1] = neutral[voteID];
		result[2] = against[voteID];
		result[3] = votesCast[voteID];
		return result;
	}
	
}
