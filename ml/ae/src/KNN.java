import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import constituents.*;

public class KNN {

	private MP[] MPS;
	private MP[] testSet;
	private MP[] gapSet;
	private int K = 2;
	private HashMap<String, VoteTotal> votesCast;

	public KNN(String filename, String unknownData, String gapData) {
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
		
		System.out.println(MPS.length);
		System.out.println(testSet.length);
		System.out.println(gapSet.length);
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
		int[] counts = new int[11]; // 11 Parties.

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
			// Break here simply on first-assigned wins.
			if (counts[m] > highest) {
				chosen = parties[m];
				highest = counts[m];
			}
		}
		return chosen;
	}

	public void findBestK() {
		int bestK = 0;
		double bestPercent = 0;

		for (int i = 2; i < 11; i++) {
			int classifiedCorrectly = 0;
			for (int j = 0; j < MPS.length; j += 7) {
				// Initialize test sets
				MP[] testing = new MP[7];
				MP[] order = new MP[MPS.length - 7];
				// Split the MPS array into two arrays,
				// One for training, and other for testing.
				for (int k = 0; k < MPS.length; k++) {
					if (j <= k && k < j + 7) {
						testing[k - j] = MPS[k];
					} else {
						if (k < j) {
							order[k] = MPS[k];
						} else {
							order[k - 7] = MPS[k];
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
			System.out.println("This K: " + i + " Percent: " + percentCorrect);
			System.out.println("Errors: "
					+ (MPS.length - (MPS.length * percentCorrect)));
		}
		System.out.println("Best K: " + bestK + " Percent: " + bestPercent);
		K = bestK;
	}

	public void setVotesForGap() {
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
					// Combine this with a random probability of individuality based on
					// how spread the votes were. This is a number in (0, 1].
					double confidence;
					
					if (ayes == noes){
						proposedVote = r.nextBoolean() ? 1 : -1;
						confidence = 0.5;
					}
					else {
						proposedVote = ayes > noes ? 1 : -1;
						confidence = proposedVote > 0 ? 
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
					System.out.println("MP " + gap.getName() + " voted " + proposedVote + " on vote " + i);
				}
			}
		}
		System.out.println("Number of counter votes: " + counters);
		System.out.println("Total votes: " + total);
	}

	public void classifyUnknown() {
		PrintWriter out = null;
		try {
			out = new PrintWriter("predictions_1002253w.csv", "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		for (MP unknown : testSet) {
			Party proposed = nearestNeighbour(unknown, K, MPS);
			unknown.setParty(proposed);
			System.out.println(unknown.getParty().getName());
			out.println(unknown.toString());
		}
		out.close();
	}

	public static void main(String[] args) {
		KNN knn = new KNN(args[0], args[1], args[2]);
		// knn.findBestK();
		// knn.classifyUnknown();
		knn.setVotesForGap();
	}
}
