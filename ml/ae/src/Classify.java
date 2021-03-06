import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import constituents.*;

public class Classify {

	private MP[] MPS;
	private MP[] testSet;
	private MP[] gapSet;
	private int K;
	private int crossValidationSize = 7; // alternatively could be 61
	
	private HashMap<String, VoteTotal> votesCast;

	private boolean verbose;
	
	public Classify(String filename, String unknownData, String gapData, boolean verbose) {
		
		this.verbose = verbose;
		
		FileReader frTraining = null;
		FileReader frTest = null;
		FileReader frGap = null;
		ArrayList<MP> mps = new ArrayList<MP>();
		ArrayList<MP> test = new ArrayList<MP>();
		ArrayList<MP> gap = new ArrayList<MP>();

		votesCast = new HashMap<String, VoteTotal>();
		for (Party p : Party.values()) {
			votesCast.put(p.getName(), new VoteTotal());
		}

		try {
			frTraining = new FileReader(filename);
			frTest = new FileReader(unknownData);
			frGap = new FileReader(gapData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}

		Scanner s = new Scanner(frTraining);
		Scanner u = new Scanner(frTest);
		Scanner g = new Scanner(frGap);

		while (s.hasNextLine()) {
			String line = s.nextLine();
			String[] tokens = line.split(",");
			String name = tokens[0];
			String party = tokens[1];
			int[] votes = new int[1288];

			VoteTotal partyVoted = votesCast.get(party);
			partyVoted.incMPS();
			for (int i = 2; i < tokens.length; i++) {
				votes[i - 2] = Integer.parseInt(tokens[i]);
				partyVoted.addVote(i - 2, votes[i - 2]);
			}

			MP mp = new MP(name, party, votes);
			mps.add(mp);
		}

		while (u.hasNextLine()) {
			String line = u.nextLine();
			String[] tokens = line.split(",");
			int[] votes = new int[1288];
			for (int i = 2; i < tokens.length; i++) {
				votes[i - 2] = Integer.parseInt(tokens[i]);
			}
			MP mp = new MP("", null, votes);
			test.add(mp);
		}

		while (g.hasNextLine()) {
			String line = g.nextLine();
			String[] tokens = line.split(",");
			String name = tokens[0];
			String party = tokens[1];
			int[] votes = new int[1288];
			for (int i = 2; i < tokens.length; i++) {
				int vote;
				try {
					vote = Integer.parseInt(tokens[i]);
				} catch (NumberFormatException e) {
					vote = -2;
				}
				votes[i - 2] = vote;
			}
			MP mp = new MP(name, party, votes);
			gap.add(mp);
		}

		s.close();
		u.close();
		g.close();

		MPS = new MP[mps.size()];
		MPS = mps.toArray(MPS);

		testSet = new MP[test.size()];
		testSet = test.toArray(testSet);

		gapSet = new MP[gap.size()];
		gapSet = gap.toArray(gapSet);
	}

	public Party nearestNeighbour(MP test, int k, MP[] testSet) {

		for (MP mp : testSet) {
			// Check to see if this MP exists in the training set. If
			// the MP is in the training set then it will skew the results
			// of KNN. I use cosine similarity to determine how alike two
			// MP's are in terms of their voting history. The cosine
			// similarity of a vector with itself is always 1 so this
			// MP, if included in the training set, will be at the top of
			// the "close MP" list and will skew picking a fair class.
			if (mp.equals(test)) {
				// Void this similarity.
				mp.voidSimilarity();
			} else {
				mp.setSimilarity(test);
			}
		}

		// MP implements Comparable so we can use built in sorting. The
		// compareTo
		// method sorts those MPs with higher similarity (closer to 1) to the
		// beginning of the array.
		Arrays.sort(testSet);

		Party[] parties = Party.values();
		int[] counts = new int[parties.length];

		// Count Parties of the K closest MPs.
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < parties.length; j++) {
				if (testSet[i].getParty() == parties[j]) {
					counts[j]++;
				}
			}
		}

		Party chosen = null;
		int highest = 0;

		// Find most common Party.
		for (int m = 0; m < counts.length; m++) {
			// Break ties here simply on first-assigned wins.
			if (counts[m] > highest) {
				chosen = parties[m];
				highest = counts[m];
			}
		}
		return chosen;
	}

	public void findBestK() {
		System.out.println("Determining best K for k-nearest neighbour.");
		System.out.println("===========================================");
		int bestK = 0;
		double bestPercent = 0;

		// Do k nearest neighbour for k in [2, 11]
		for (int i = 2; i < 11; i++) {
			int classifiedCorrectly = 0;
			for (int j = 0; j < MPS.length; j += crossValidationSize) {
				// Initialize test sets
				MP[] testing = new MP[crossValidationSize];
				MP[] order = new MP[MPS.length - crossValidationSize];
				
				// Split the MPS array into two arrays for cross validation
				for (int k = 0; k < MPS.length; k++) {
					if (j <= k && k < j + crossValidationSize) {
						testing[k - j] = MPS[k];
					} else {
						if (k < j) {
							order[k] = MPS[k];
						} else {
							order[k - crossValidationSize] = MPS[k];
						}
					}
				}
				
				
				for (MP test : testing) {
					Party proposed = nearestNeighbour(test, i, order);
					if (proposed == test.getParty()) {
						classifiedCorrectly++;
					}
				}
			}
			double percentCorrect = ((double) classifiedCorrectly) / MPS.length;
			if (percentCorrect > bestPercent) {
				bestPercent = percentCorrect;
				bestK = i;
			}
			System.out.println("This K: " + i + " - Classified: " + percentCorrect * 100 + "% correctly.");
			System.out.println("\tErrors: "
					+ (MPS.length - (MPS.length * percentCorrect)));
		}
		System.out.println("\nBest K: " + bestK + ". Classified " + bestPercent * 100 + "% correctly.\n\n");
		K = bestK;
	}

	public void setVotesForGap() {
		System.out.println("Filling in vote gaps for MPs");
		System.out.println("===========================================");
		PrintWriter out;
		try {
			out = new PrintWriter("gaps_1002253w.csv", "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		Random r = new Random();
		int counters = 0;
		int total = 0;
		for (MP gap : gapSet) {
			int[] votes = gap.getVotes();
			for (int i = 0; i < votes.length; i++) {
				if (votes[i] < -1) {
					total++;
					VoteTotal partyVotes = votesCast.get(gap.getParty().getName());
					int[] voteResult = partyVotes.getResultsForVote(i);
					int ayes = voteResult[0];
					int noes = voteResult[2];
					int cast = voteResult[3];
					
					// Start off with voter goes with majority. Randomly split on 
					// equal ayes and noes.
					int proposedVote;
					
					// I use a metric of confidence in this majority vote as a way
					// to determine if this MP will vote with the party or not.
					// For example: 30% of the current MPs party votes aye
					//              10% of the current MPs party votes no
					//              60% of the current MPs party abstain from voting.
					// The majority vote is aye (since the vote we have to guess is
					// always an aye or no, never not voted) but 70% of the party
					// didn't follow this vote so the confidence that an aye vote is 
					// the vote that best represents the party isn't high.
					double confidence;
					
					if (ayes == noes){
						// We have a tie. Split randomly and set the confidence based
						// on ayes or noes. Doesn't matter which vote we choose to
						// determine confidence as both ayes and noes will give the
						// same value.
						proposedVote = r.nextBoolean() ? 1 : -1;
						confidence = ((double) ayes) / ((double) cast);
					}
					else {
						proposedVote = ayes > noes ? 1 : -1;
						confidence = ayes > noes ? 
							((double) ayes)	/ ((double) cast) : 
							((double) noes)	/ ((double) cast);
					}
										
					// Use the confidence as a decider as to whether or not this
					// MP follows the herd and votes like the party or votes
					// against the majority. If their individuality is 
					// greater than their confidence in the party then the vote
					// toggles.
					double individuality = r.nextDouble();
					if (individuality > confidence){
						proposedVote = -proposedVote;
						counters++;
					}
					
					// Finally set this as the users vote.
					votes[i] = proposedVote;
					if (verbose){
						System.out.println("MP " + gap.getName() + " voted " + proposedVote + " on vote " + i);
					}
				}
			}
			// Write this MP to the output file.
			out.println(gap.toString());
		}
		out.close(); 
		System.out.println("Number of votes against party confidence: " + counters);
		System.out.println("Total numver of votes guessed: " + total);
		
		int classificationMatch = 0;
		for (MP check: gapSet){
			Party guessedParty = this.nearestNeighbour(check, 2, MPS);
			if (guessedParty == check.getParty()){
				classificationMatch++;
			}
		}
		System.out.println("Believable predictions: " + classificationMatch + " from " + gapSet.length + " MPS.\n\n");
	}

	public void classifyUnknown() {
		System.out.println("Creating classification of unknown MPs");
		System.out.println("===========================================");
		PrintWriter out = null;
		try {
			out = new PrintWriter("predictions_1002253w.csv", "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		for (int i = 0; i < testSet.length; i++) {
			MP unknown = testSet[i];
			Party proposed = nearestNeighbour(unknown, K, MPS);
			unknown.setParty(proposed);
			if (verbose){
				System.out.println("MP " + i + " was put in Party " + unknown.getParty().getName());
			}
			out.println(unknown.toString());
		}
		out.close();
		System.out.println("Finished classification of " + testSet.length+ " unknown MPs\n\n");
	}
	
	public void generateApathy(){
		PrintWriter out = null;
		try {
			out = new PrintWriter("apathy_1002253w.csv", "UTF-8");
		} catch (Exception e){
			e.printStackTrace();
			return;
		}
		Party[] keys = Party.values();
		for (int i = 0; i < keys.length; i++){
			double[] aggregate = votesCast.get(keys[i].getName()).aggregateNormalized();
			double percent = aggregate[1] / (aggregate[0] + aggregate[1] + aggregate[2]) * 100;
			out.printf(keys[i].getName() + " (%2$.2f),%1$.2f\n", percent, aggregate[1]);
		}
		
		out.close();
	}
	
	public void generateSimilarityOfClass(){
		HashMap<Party, ArrayList<MP>> split = new HashMap<Party, ArrayList<MP>>();
		for (Party p: Party.values()){
			split.put(p, new ArrayList<MP>());
		}
		for (MP mp: MPS){
			ArrayList<MP> thisPartyMPS = split.get(mp.getParty());
			thisPartyMPS.add(mp);
		}
		
		for (Party p: split.keySet()){
			double closestDistance = -1.0;
			double furthestDistance = 1.0;
			double average = 0;
			int comparisons = 0;
			for (MP mpA: split.get(p)){
				for (MP mpB: split.get(p)){
					if (mpA.equals(mpB)){
						continue;
					}
					comparisons++;
					mpA.setSimilarity(mpB);
					double similarity = mpA.getSimilarity();
					average += similarity;
					if (similarity < furthestDistance){
						furthestDistance = similarity;
					}
					else if (similarity > closestDistance){
						closestDistance = similarity;
					}
				}
			}
			if (comparisons == 0){
				average = 0;
				furthestDistance = 0;
				closestDistance = 0;
			}
			else {
				average = average / comparisons;
			}
			System.out.println(p.getName());
			System.out.println("\t- Max Distance: " + furthestDistance);
			System.out.println("\t- Min Distance: " + closestDistance);
			System.out.println("\t- Average: " + average);
			System.out.println("\t- MPs:" + split.get(p).size());
		}
	}

	public static void main(String[] args) {
		if (args.length < 3){
			System.out.println("Usage: java -cp . Classify [-v] [-s] <trainingData.csv> <unknownData.csv> <gapData.csv>");
			System.exit(1);
		}
		Classify classify;
		boolean verbose = false;
		boolean similarity = false;
		if (args[0].equals("-v") || args[1].equals("-v")){
			verbose = true;
		}
		if (args[0].equals("-s") || (verbose && args[1].equals("-s"))){
			similarity = true;
		}
		classify = new Classify(args[args.length-3], args[args.length-2], args[args.length-1], verbose);
		if (similarity){
			classify.generateSimilarityOfClass();
		}
		classify.findBestK();
		classify.classifyUnknown();
		classify.setVotesForGap();
		classify.generateApathy();
		
	}
}
