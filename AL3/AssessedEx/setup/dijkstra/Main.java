import java.io.*;
import java.util.*;
import java.lang.Math;


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
		
		int[] S = new int[nodeTotal];
		int[] d = new int[nodeTotal];
		for(int i=0;i<nodeTotal;i++){
			S[i]=0;
			d[i]=dGraph.getVertex(floatNode).wt(dGraph.getVertex(i));
			if (d[i]!=0){
				dGraph.getVertex(i).setPredecessor(floatNode);
			}
			else{
				dGraph.getVertex(i).setPredecessor(-1);
			}
		}
		
		S[floatNode]=1;
		int previousNode = floatNode;
		boolean active = false;
		boolean found = false;
		
		
		for(int i=0; i<nodeTotal;i++){
			if(S[i]!=1 && d[i]>=0){
				active = true;
			}
		}
		
		
		int leastDistance = Integer.MAX_VALUE;
		int newestNode = 0;
		
		
		while (active){
			leastDistance = Integer.MAX_VALUE;
			for(int i=0; i<nodeTotal;i++){
				if (S[i]!=1 && d[i]>0 && d[i]<=leastDistance){
					leastDistance = d[i];
					newestNode = i;
				}
			}
			S[newestNode]=1;
			if(newestNode == sinkNode){
				active = false;
				found = true;
				continue;
			}
			for(int i=0; i<nodeTotal; i++){
				if(S[i]!=1){
					if(d[i] == -1){
						d[i] = dGraph.getVertex(newestNode).wt(dGraph.getVertex(i));
						if (d[i] != -1){
							dGraph.getVertex(i).setPredecessor(newestNode);
						}
					}
					else{
						int distance = dGraph.getVertex(newestNode).wt(dGraph.getVertex(i));
						if(distance != -1){
							if(d[i] > d[newestNode]+distance){
								d[i] = d[newestNode]+distance;
								dGraph.getVertex(i).setPredecessor(newestNode);
							}
						}
					}
				}
			}
			
			active = false;
			if(!found){
				for(int i=0; i<nodeTotal;i++){
					if(S[i]!=1 && d[i]>=0){
						active = true;
					}
				}
			}
			previousNode = newestNode;
		}
		
		if(!found){
			System.out.println("\nNo route to the sink node could be found.");
		}
		else{
			System.out.println("Shortest distance from vertex " + floatNode + " to vertex " + sinkNode + " is " + d[sinkNode]);
			Stack<Integer> reverse = new Stack<Integer>();
			int parent = sinkNode;
			while (parent!=-1){
				reverse.add(parent);
				if(parent == dGraph.getVertex(parent).getPredecessor()){
					parent = -1;
				}
				else{
					parent = dGraph.getVertex(parent).getPredecessor();
				}
			}
			System.out.print("Shortest path: ");
			while (!reverse.isEmpty()){
				System.out.print(reverse.pop());
				System.out.print(" ");
			}
			System.out.println("\n");
		}
		// do the work here
		

		// end timer and print total time
		long end = System.currentTimeMillis();
		System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
	}
	

}
