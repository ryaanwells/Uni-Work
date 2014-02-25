import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import constituents.*;

public class KNN {
	
	private MP[] MPS;
	
	public KNN(String filename){
		FileReader fr = null;
		ArrayList<MP> mps = new ArrayList<MP>();
		try {
			fr = new FileReader(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Scanner s = new Scanner(fr);
		while (s.hasNextLine()){
			String line = s.nextLine();
			String[] tokens = line.split(",");
			String name = tokens[0];
			String party = tokens[1];
			int[] votes = new int[1288];
			for (int i = 2; i < tokens.length; i++){
				votes[i-2] = Integer.parseInt(tokens[i]);
			}
			MP mp = new MP(name, party, votes);
			mps.add(mp);
		}
		s.close();
		MPS = new MP[mps.size()];
		MPS = mps.toArray(MPS);
		System.out.println(MPS.length);
	}
	
	public void nearestNeighbour(){
		int bestK = 0;
		double bestPercent = 0;
		Party[] parties = Party.values();
		
		for (int i = 1; i < 11; i++){
			int classifiedCorrectly = 0;
			for (int j = 0; j < MPS.length; j+=7){
				// Initialize test sets
				MP[] testing = new MP[7];
				MP[] order = new MP[MPS.length - 7];
				// Split the MPS array into two arrays,
				// One for training, and other for testing.
				for (int k = 0; k < MPS.length; k++){
					if (j <= k && k < j + 7){
						testing[k-j] = MPS[k];
					}
					else{
						if (k < j){
							order[k] = MPS[k];
						}
						else {
							order[k-7] = MPS[k];
						}
					}
				}

				for (int k = 0; k < 7; k++){
					for (MP mp : order){
						mp.setSimilarity(testing[k]);
					}
					Arrays.sort(order);
					int[] result = new int[11];
					
					for (int l = 0; l < i; l++){
						for (int m = 0; m < parties.length; m++){
							if (order[l].getParty() == parties[m]){
								result[m]++;
							}
						}
					}
					Party chosen = null;
					int highest = 0;
					for (int m = 0; m < result.length; m++){
						if (result[m] > highest){
							chosen = parties[m];
							highest = result[m];
						}
					}
					if (chosen == testing[k].getParty()){
						classifiedCorrectly++;
					}
				}				
			}
			double percentCorrect = ((double) classifiedCorrectly) / MPS.length;
			if (percentCorrect > bestPercent){
				bestPercent = percentCorrect;
				bestK = i;
			}
			System.out.println("This K: " + i + " Percent: " + percentCorrect);
			System.out.println("Errors: " + (MPS.length - (MPS.length * percentCorrect)));
		}
		System.out.println("Best K: " + bestK + " Percent: " + bestPercent);
	}
	
	public static void main(String[] args) {
		KNN knn = new KNN(args[0]);
		knn.nearestNeighbour();
	}
}
