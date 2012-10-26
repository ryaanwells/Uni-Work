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
		
		reader.close();

		// insert code here to build the graph from the input file

		// conduct the breadth-first search
		
		String outputFileName = args[1];
		FileWriter writer = new FileWriter(outputFileName);
		
		// insert code here to output the predecessor information

		writer.close();

	}

}
