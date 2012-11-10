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

	int floatNode, sinkNode;
	int nodeTotal = Integer.parseInt(in.next());

	Graph dGraph = new Graph(nodeTotal,in);
	floatNode = Integer.parseInt(in.next());
	sinkNode = Integer.parseInt(in.next());

	reader.close();

	int[] S = new int[nodeTotal];
	int[] d = new int[nodeTotal];
	for(int i=0;i<nodeTotal;i++){
	    S[i]=0;
	    d[i]=dGraph.getVertex(floatNode).wt(dGraph.getVertex(i));
	    if (d[i]!=-1){
		dGraph.getVertex(i).setPredecessor(floatNode);
	    }
	    else{
		dGraph.getVertex(i).setPredecessor(-1);
	    }
	}

	S[floatNode]=1;
	boolean active = false;
	boolean found = false;


	for(int i=0; i<nodeTotal;i++){
	    if(S[i]!=1 && d[i]>=0){
		active = true;
	    }
	}

	while (active){
	    int leastDistance = Integer.MAX_VALUE;
	    int newestNode = -1;
	    for(int i=0; i<nodeTotal;i++){
		if (S[i]!=1 && (d[i]>0 && d[i]<=leastDistance)){
		    leastDistance = d[i];
		    newestNode = i;
		}
	    }
	    if(newestNode == -1){
		active = false;
		break;
	    }
	    S[newestNode]=1;
	    if(newestNode == sinkNode){
		active = false;
		found = true;
		break;
	    }
	    for(int i=0; i<nodeTotal; i++){
		if(S[i]!=1){
		    if(d[i] == -1){
			d[i] = dGraph.getVertex(newestNode).wt(dGraph.getVertex(i));
			if (d[i] != -1){
			    dGraph.getVertex(i).setPredecessor(newestNode);
			    d[i] = dGraph.getVertex(newestNode).wt(dGraph.getVertex(i)) +d[newestNode];
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
		    if(S[i]!=1 && d[i]!=-1){
			active = true;
		    }
		}
	    }
	}
	long end = System.currentTimeMillis();
	if(!found){
	    System.out.println("\nNo route to the sink node could be found.");
	}
	else{
	    System.out.println("Shortest distance from vertex " + floatNode + " to vertex " + sinkNode + " is " + d[sinkNode]);
	    Stack<Integer> reverse = new Stack<Integer>();
	    int parent = sinkNode;
	    while (parent!=floatNode){
		reverse.add(parent);
		parent = dGraph.getVertex(parent).getPredecessor();
	    }
	    reverse.add(floatNode);
	    System.out.print("Shortest path: ");
	    while (!reverse.isEmpty()){
		System.out.print(reverse.pop());
		System.out.print(" ");
	    }
	}

	// end timer and print total time

	System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
    }


}