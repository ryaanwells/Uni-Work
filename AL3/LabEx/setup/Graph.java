import java.util.LinkedList;
import java.util.Queue;

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
	 psedocode version
	 */
	public void bfs() {
		for (Vertex v: vertices){
			v.setVisited(false);
		}
		Vertex u,n;
		Queue<Vertex> toVisit = new LinkedList<Vertex>();
		for (Vertex v: vertices){
			if (!v.visited){
				v.setVisited(true);
				v.setPredecessor(v.getIndex());
				toVisit.add(v);
				while (!toVisit.isEmpty()){
					u = toVisit.remove();
					for(AdjListNode w: u.getAdjList()){
						n = vertices[w.getVertexNumber()];
						n.setPredecessor(w.getVertexNumber());
						toVisit.add(n);
					}
				}
			}
		}
	}
}
