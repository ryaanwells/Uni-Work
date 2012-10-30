import java.util.LinkedList;


/**
 class to represent a vertex in a graph
*/
public class Vertex {
   
    private LinkedList<AdjListNode> adjList ; // the adjacency list of the vertex 
    private int index; // the index of the vertex
    private int distance;
    
    // possibly other fields, for example representing data
    // stored at the node, whether the vertex has been visited
    // in a traversal, its predecessor in such a traversal, etc.

    boolean visited; // whether vertex has been visited in a traversal
    int predecessor = -1; // index of predecessor vertex in a traversal

    /**
	 creates a new instance of Vertex
	*/
    public Vertex(int n) {
    	adjList = new LinkedList<AdjListNode>();
    	index = n;
    	visited = false;
    	distance = -1;
    }
    
    /**
	 copy constructor
	*/
    public Vertex(Vertex v){
    	adjList = v.getAdjList();
    	index = v.getIndex();
    	visited = v.getVisited();
    	distance = v.getDistance();
    	predecessor = v.getPredecessor();
    }
     
    public LinkedList<AdjListNode> getAdjList(){
        return adjList;
    }
    
    public int getIndex(){
    	return index;
    }
    
    public void setIndex(int n){
    	index = n;
    }
    
    public boolean getVisited(){
    	return visited;
    }
    
    public void setVisited(boolean b){
    	visited = b;
    }
    
    public int getPredecessor(){
    	return predecessor;
    }
    
    public void setPredecessor(int n){
    	predecessor = n;
    }
    
    public int getDistance(){
    	return distance;
    }
    
    public void setDistance(int d){
    	distance = d;
    }
    
    public void addToAdjList(int n, int w){
        adjList.addLast(new AdjListNode(n,w));
    }
    
    public int vertexDegree(){
        return adjList.size();
    }
    
	public int wt(Vertex x){
		for(AdjListNode A: adjList){
			if(A.getVertexNumber() == x.getIndex()){
				return A.getWeight();
			}
		}
		return -1;
	}
}
