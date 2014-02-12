package networkFlow;

import java.util.*;

/**
 * The Class ResidualGraph. Represents the residual graph corresponding to a
 * given network.
 */
public class ResidualGraph extends Network {
	/**
	 * Instantiates a new ResidualGraph object. Builds the residual graph
	 * corresponding to the given network net. Residual graph has the same
	 * number of vertices as net.
	 * 
	 * @param net
	 *            the network
	 */
	public ResidualGraph(Network net) {
		super(net.numVertices);
		for (int i = 0; i < net.numVertices; i++) {
			for (int j = 0; j < net.numVertices; j++) {
				Edge originalEdge = net.adjMatrix[i][j];
				if (originalEdge != null) {
					int flow = originalEdge.getFlow();
					int capacity = originalEdge.getCap();
					if (capacity <= 0)
						continue;
					if (flow < capacity) {
						this.addEdge(this.vertices[i], this.vertices[j],
								capacity - flow);
					}
					if (flow > 0) {
						this.addEdge(this.vertices[j], this.vertices[i], flow);
					}
				}
			}
		}
	}

	/**
	 * Find an augmenting path if one exists. Determines whether there is a
	 * directed path from the source to the sink in the residual graph -- if so,
	 * return a linked list containing the edges in the augmenting path in the
	 * form (s,v_1), (v_1,v_2), ..., (v_{k-1},v_k), (v_k,t); if not, return an
	 * empty linked list.
	 * 
	 * @return the linked list
	 */
	public LinkedList<Edge> findAugmentingPath() {
		boolean[] hasVisited = new boolean[numVertices];
		Vertex[] predec = new Vertex[numVertices];
		LinkedList<Edge> path = new LinkedList<Edge>();
		LinkedList<Vertex> pending = new LinkedList<Vertex>();

		for (int i = 0; i < numVertices; i++){
			hasVisited[i] = false;
			predec[i] = null;
		}
		
		pending.addFirst(source);
		boolean augPathFound = false;
		// While we have reachable, unvisited vertices
		while (!pending.isEmpty() && !augPathFound) {
			Vertex next = pending.removeFirst();
			// Only visit each vertex once.
			if (hasVisited[next.getLabel()])
				continue;
			// Mark it visited locally since we cannot edit vertex class.
			hasVisited[next.getLabel()] = true;
			// For each vertex reachable from this one, set it's predecessor to
			// be this one, and if we can reach the sink from this vertex then
			// we have found an augmenting path and so can stop searching.
			for (Vertex v : getAdjList(next)) {
				if (hasVisited[v.getLabel()]) continue;
				predec[v.getLabel()] = next;
				if (v == sink) {
					augPathFound = true;
					break;
				}
				pending.add(v);
			}
		}
		
		if (augPathFound) {
			// Create two pointers for two vertices on the augmenting path
			// working from the sink to the source.
			Vertex prev = predec[sink.getLabel()];
			Vertex current = sink;
			while (true) {
				// Prepend each edge, since working from sink -> source
				Edge edge = this.adjMatrix[prev.getLabel()][current.getLabel()];
				path.addFirst(edge);
				if (prev == source)
					break;
				// Move backwards one edge in the path
				current = prev;
				prev = predec[current.getLabel()];
			}
		}

		return path;
	}
}
