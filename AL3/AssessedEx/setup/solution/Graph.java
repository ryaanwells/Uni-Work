import java.util.LinkedList;

/**
 class to represent an undirected graph using adjacency lists
 */
public class Graph {

	private Vertex[] vertices; // the (array of) vertices
	private int numVertices = 0; // number of vertices

	// possibly other fields representing properties of the graph

	/**
	 creates a new instance of Graph with n vertices
	*/
	public Graph(int n) {
		numVertices = n;
		vertices = new Vertex[n];
		for (int i = 0; i < n; i++)
			vertices[i] = new Vertex(i);
	}

	public int size() {
		return numVertices;
	}

	public Vertex getVertex(int i) {
		return vertices[i];
	}

	public void setVertex(int i) {
		vertices[i] = new Vertex(i);
	}

	/**
	 visit vertex v, with predecessor index p,
	 during a depth first traversal of the graph
	 */
	private void Visit(Vertex v, int p) {
		v.setVisited(true);
		v.setPredecessor(p);
		LinkedList<AdjListNode> L = v.getAdjList();
		for (AdjListNode node : L) {
			int n = node.getVertexNumber();
			if (!vertices[n].getVisited()) {
				Visit(vertices[n], v.getIndex());
			}
		}
	}

	/**
     carry out a depth first search/traversal of the graph
	 */
	public void dfs() {
		for (Vertex v : vertices)
			v.setVisited(false);
		for (Vertex v : vertices)
			if (!v.getVisited())
				Visit(v, -1);
	}

	/**
	 carry out a breadth first search/traversal of the graph
	 */
	public void bfs() {
		
		for (Vertex v : vertices) v.setVisited(false); // initialise (all vertices unvisted)
  		LinkedList<Vertex> queue = new LinkedList<Vertex>(); // set up empty queue
  		
		for (Vertex v : vertices) { // go through vertices of the graph
    		if (!v.getVisited()) { // if vertex not visited then visit the vertex
      			v.setVisited(true); // now visited
      			v.setPredecessor(v.getIndex()); // v was initial/starting vertex
      			queue.add(v); // add to queue to be processed
      			while (!queue.isEmpty()) { // while verticies to process
        			Vertex u = queue.remove(); // get next vertex to process
					LinkedList<AdjListNode> list = u.getAdjList(); // get adjacency list of the vertex
        			for (AdjListNode node : list) { // go through the adjecency list...
						Vertex w = vertices[node.getVertexNumber()];
						if (!w.getVisited()) { // ...for vertices that have not been visited
							w.setVisited(true); // they have now been visited
							w.setPredecessor(u.getIndex()); // w was found using u so this is the predecessor
							queue.add(w); // add vertex to the queue to be processed
						}
					}
				}
			}
		}
	}

}
