package networkFlow;
import java.util.*;

/**
 * The Class Network.
 * Represents a network - inherits from DirectedGraph class.
 */
public class Network extends DirectedGraph {

	/** The source vertex of the network. */
	protected Vertex source;
	
	/** The label of the source vertex. */
	protected int sourceLabel;
	
	/** The sink vertex of the network. */
	protected Vertex sink;
	
	/** The label of the sink vertex. */
	protected int sinkLabel;

	/**
	 * Instantiates a new network.
	 * @param n the number of vertices
	 */
	public Network (int n) {
		super(n);

		// add the source vertex - assumed to have label 0
		sourceLabel = 0;
		source = addVertex(sourceLabel);
		// add the sink vertex - assumed to have label numVertices - 1
		sinkLabel = numVertices - 1;
		sink = addVertex(sinkLabel);

		// add the remaining vertices
		for (int i = 1 ; i <=numVertices-2 ; i++) 
			addVertex(i);
	}

	/**
	 * Gets the source vertex.
	 * @return the source vertex
	 */
	public Vertex getSource() {
		return source;
	}

	/**
	 * Gets the sink vertex.
	 * @return the sink vertex
	 */
	public Vertex getSink() {
		return sink;
	}

	/**
	 * Adds the edge with specified source and target vertices and capacity.
	 * @param sourceEndpoint the source endpoint vertex
	 * @param targetEndpoint the target endpoint vertex
	 * @param capacity the capacity of the edge
	 */
	public void addEdge(Vertex sourceEndpoint, Vertex targetEndpoint, int capacity) { 
		Edge e = new Edge(sourceEndpoint, targetEndpoint, capacity);
		adjLists.get(sourceEndpoint.getLabel()).addLast(targetEndpoint);
		adjMatrix[sourceEndpoint.getLabel()][targetEndpoint.getLabel()]=e;
	}

	/**
	 * Returns true if and only if the assignment of integers to the flow fields of 
	 * each edge in the network is a valid flow.
	 * @return true, if the assignment is a valid flow
	 */
	public boolean isFlow() {
		// complete this method as part of Task 1
		return true;
	}

	/**
	 * Gets the value of the flow.
	 * @return the value of the flow
	 */
	public int getValue() {
		// complete this method as part of Task 1
		return 0;
	}

	/**
	 * Prints the flow.
	 * Display the flow through the network in the following format:
	 * (u,v) c(u,v)/f(u,v)
	 * where (u,v) is an edge, c(u,v) is the capacity of that edge and f(u,v) 
         * is the flow through that edge - one line for each edge in the network
	 */
	public void printFlow() {
		// complete this method as part of Task 1
	}
}
