import java.io.*;
import java.util.*;

/**
 program to find shortest path using the backtrack search algorithm
 */
public class Main {

	public static void main(String[] args) throws IOException {

		long start = System.currentTimeMillis();

		String inputFileName = args[0]; // input file name
  
		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);
		
		int floatNode, sinkNode;
		int nodeTotal = Integer.parseInt(in.next());

		Graph bGraph = new Graph(nodeTotal,in);
		floatNode = Integer.parseInt(in.next());
		sinkNode = Integer.parseInt(in.next());

		reader.close();	
		bGraph.bestPath(floatNode, sinkNode);
		LinkedList<AdjListNode> bestPath = bGraph.getPath();
		
		if (bestPath.peek() == null){
			System.out.println("\nNo route to the sink node could be found");
		}
		else{
		    System.out.println("Shortest distance from vertex " + floatNode + " to vertex " + sinkNode + " is " + bestPath.peek().getWeight());
		    System.out.print("Shortest path: ");
		    while(bestPath.peek()!= null){
		    	AdjListNode next = (AdjListNode) bestPath.removeLast();
		    	System.out.print(next.getVertexNumber() + " ");
		    }
		// end timer and print total time
			long end = System.currentTimeMillis();
			System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
	}
	}
}
