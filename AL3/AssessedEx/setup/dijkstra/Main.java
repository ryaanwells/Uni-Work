import java.io.*;
import java.util.*;


/**
 program to find shortest path using Dijkstra's algorithm
 */
public class Main {

	public static void main(String[] args) throws IOException {

		long start = System.currentTimeMillis();

		String inputFileName = args[0]; // input file name
  
		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);
		
		int floatNode, sinkNode, vWeight;
		int nodeTotal = in.nextInt();
		
		Graph dGraph = new Graph(nodeTotal);
		for (int i = 0; i<nodeTotal; i++){
			for (int j = 0; j<nodeTotal; j++){
				vWeight = in.nextInt();
				if (vWeight != 0){
					dGraph.getVertex(i).addToAdjList(j,vWeight);
				}
			}
		}
		floatNode = in.nextInt();
		sinkNode = in.nextInt();

		reader.close();
		
		
		
		// do the work here
		

		// end timer and print total time
		long end = System.currentTimeMillis();
		System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
	}

}
