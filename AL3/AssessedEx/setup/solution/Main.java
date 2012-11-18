import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {

		String inputFileName = args[0];
		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);
		String line = in.nextLine();
        	Scanner lineScanner = new Scanner(line);
		int numVertices = lineScanner.nextInt();

		// insert code here to build the graph from the input file
		Graph G = new Graph(numVertices);
		for (int i = 0; i < numVertices; i++){
			// update information for vertex with index i
			line = in.nextLine(); // read information
			lineScanner = new Scanner(line);
			for (int j = 0; j < numVertices; j++)
				if (lineScanner.nextInt() == 1)
					G.getVertex(i).addToAdjList(j);
		}

		reader.close();

		// conduct the breadth-first search
		G.bfs();
		
		String outputFileName = args[1];
		FileWriter writer = new FileWriter(outputFileName);
		
		// insert code here to output the predecessor information
		for (int i=0; i < numVertices; i++)
			writer.write(G.getVertex(i).getPredecessor() + " ");

		writer.close();

	}

}
