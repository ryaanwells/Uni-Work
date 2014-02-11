import java.util.*;
import java.io.*;
import networkFlow.*;

/**
 * The Class FordFulk. Contains main part of the Ford-Fulkerson implementation
 * and code for file input
 */
public class FordFulk {

	/** The name of the file that encodes the given network. */
	private String filename;

	private int numStudents;
	private int numProjects;
	private int numSupervisors;
	
	private LinkedList<Student> students;
	private LinkedList<Project> projects;
	private LinkedList<Lecturer> lecturers;
	
	/** The network on which the Ford-Fulkerson algorithm is to be run. */
	private Network net;

	/**
	 * Instantiates a new FordFulk object.
	 * 
	 * @param s
	 *            the name of the input file
	 */
	public FordFulk(String s) {
		filename = s; // store name of input file
		students = new LinkedList<Student>();
		projects = new LinkedList<Project>();
		lecturers = new LinkedList<Lecturer>();
	}

	/**
	 * Read in network from file. See assessed exercise specification for the
	 * file format.
	 */
	public void readNetworkFromFile() {
		FileReader fr = null;
		// open file with name given by filename
		try {
			try {
				fr = new FileReader(filename);
				Scanner in = new Scanner(fr);
				String line;
				
				// get number of vertices
				numStudents = Integer.parseInt(in.nextLine());
				numProjects = Integer.parseInt(in.nextLine());
				numSupervisors = Integer.parseInt(in.nextLine());
				
				// create new network with desired number of vertices
				net = new Network(numStudents + numProjects + numSupervisors + 2);
				
				int id = 1;
				// Add Student Edges
				for (int counter = 1; counter <= numStudents; counter++, id++){
					line = in.nextLine();
					String[] tokens = line.split(" ");
					Student s = new Student(id, counter, tokens[1] == "Y" ? true : false, tokens);
					students.add(s);
				}
				for (int counter = 1; counter <= numProjects; counter++, id++){
					line = in.nextLine();
					String[] tokens = line.split(" ");
					int lecturer = Integer.parseInt(tokens[2]);
					int capacity = Integer.parseInt(tokens[3]);
					Project p = new Project(id, counter, tokens[1] == "Y" ? true : false, lecturer, capacity);
					projects.add(p);
				}
				for (int counter = 1; counter < numSupervisors; counter++, id++){
					line = in.nextLine();
					String[] tokens = line.split(" ");
					Lecturer l = new Lecturer(id ,counter, Integer.parseInt(tokens[1]));
					lecturers.add(l);
				}
				while (in.hasNextLine()) {
					line = in.nextLine();
					String[] tokens = line.split("[( )]+");
					// this line corresponds to add vertices adjacent to vertex
					// u
					int u = Integer.parseInt(tokens[0]);
					// get corresponding Vertex object
					Vertex uu = net.getVertexByIndex(u);
					int i = 1;
					while (i < tokens.length) {
						// get label of vertex v adjacent to u
						int v = Integer.parseInt(tokens[i++]);
						// get corresponding Vertex object
						Vertex vv = net.getVertexByIndex(v);
						// get capacity c of (uu,vv)
						int c = Integer.parseInt(tokens[i++]);
						// add edge (uu,vv) with capacity c to network
						net.addEdge(uu, vv, c);
					}
				}
			} finally {
				if (fr != null)
					fr.close();
			}
		} catch (IOException e) {
			System.err.println("IO error:");
			System.err.println(e);
			System.exit(1);
		}
	}

	/**
	 * Executes Ford-Fulkerson algorithm on the constructed network net.
	 */
	public void fordFulkerson() {
		while (true) {
			ResidualGraph rg = new ResidualGraph(net);
			LinkedList<Edge> path = rg.findAugmentingPath();
			if (path.size() > 0) {
				System.out.println("Path Length: " + path.size());
				int increase = path.peek().getCap();
				for (Edge e : path) {
					if (e.getCap() < increase)
						increase = e.getCap();
				}
				System.out.println(increase);
				for (Edge e : path) {
					Vertex start = e.getSourceVertex();
					Vertex end = e.getTargetVertex();
					Edge netEdge = net.getAdjMatrixEntry(start, end);
					System.out.println(start.getLabel() + " " + end.getLabel());
					// Forwards Edge
					if (netEdge != null
							&& (netEdge.getCap() - netEdge.getFlow()) >= increase) {
						netEdge.setFlow(netEdge.getFlow() + increase);
					}
					else { // Backwards edge
						netEdge = net.getAdjMatrixEntry(end, start);
						netEdge.setFlow(netEdge.getFlow() - increase);
					}
				}
			}
			else {
				break;
			}
		}
	}

	/**
	 * Print the results of the execution of the Ford-Fulkerson algorithm.
	 */
	public void printResults() {
		if (net.isFlow()) {
			System.out.println("The assignment is a valid flow");
			System.out.println("A maximum flow has value: " + net.getValue());
			System.out.println("The flows along the edges are as follows:");
			net.printFlow();
		} else
			System.out.println("The assignment is not a valid flow");
	}
}