import java.util.LinkedList;
import java.util.Queue;

/**
 class to represent an undirected graph using adjacency lists
 */
public class Graph {

	private Vertex[] vertices; // the (array of) vertices
	private int numVertices = 0; // number of vertices
	private Path bestPath = null;

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
					for(AdjListNode node: u.getAdjList()){
						n = vertices[node.getVertexNumber()];
						if (n.getVisited()){
							n.setVisited(true);
							n.setPredecessor(u.getIndex());
							toVisit.add(n);
						}
					}
				}
			}
		}
	}
	
	private void resetVisited(){
		for (int i=0; i<numVertices; i++){
			vertices[i].setVisited(false);
		}
	}
	
	public Path getPath(){
		return bestPath;
	}
	
	public void bestPath(int flt, int snk, int size){
		if(this.bestPath == null || this.bestPath.getFloat() != flt || this.bestPath.getSink() != snk){
			resetVisited();
			Path workingPath = new Path(size,flt,snk);
			workingPath.addToPath(flt,0);
			vertices[flt].setVisited(true);
			Try(workingPath);
		}
	}

	private void Try(Path wp){
		Vertex start = vertices[wp.getLatest()];
		for(AdjListNode a: start.getAdjList()){
			if(!vertices[a.getVertexNumber()].getVisited()){
				wp.addToPath(a.getVertexNumber(), a.getWeight());
				vertices[a.getVertexNumber()].setVisited(true);
				if(this.bestPath == null || wp.getDistance()<this.bestPath.getDistance()){
					if(a.getVertexNumber() == wp.getSink()){
						this.bestPath = new Path(wp);
					}
					else{
						Try(wp);
					}
				}
				wp.remove();
				vertices[a.getVertexNumber()].setVisited(false);
			}
		}
	}
	
	
	/**
	public Path Try(int dest, Path cp, Path bp){
		Vertex current = vertices[cp.getLatest()];
		for (AdjListNode v: current.getAdjList()){
			if (vertices[v.getVertexNumber()].getVisited() != true){
				cp.addToPath(v.getVertexNumber(),v.getWeight());
				vertices[v.getVertexNumber()].setVisited(true);
				if ((cp.getDistance()<bp.getDistance()) || bp.getDistance()== -1){
					if (v.getVertexNumber() == dest){
						bp = new Path(cp);
						System.out.println(bp.toString());
					}
					else {
						System.out.println("trying");
						bp = Try(dest,cp,bp);
					}
				}		
			cp.remove();
			vertices[v.getVertexNumber()].setVisited(false);
			}				
		}
	return bp;
	}
	*/

}
