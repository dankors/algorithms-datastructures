import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();
		ArrayList<Integer> visited = new ArrayList<Integer>();
		path.add(source);
		visited.add(source);

		return dfsHelper(source, destination, graph, path, visited);
	}
	
	public static ArrayList<Integer> dfsHelper(int current, int destination, WGraph graph, ArrayList<Integer> path, ArrayList<Integer> visited) {
		if (current == destination) {return path;}

		ArrayList<Integer> neighbors = getNeighbors(graph, current, visited);
		if (!neighbors.isEmpty()) {
			for (Integer v : neighbors) {
				path.add(v);
				visited.add(v);
				current = v;
				ArrayList<Integer> returnedPath = dfsHelper(current, destination, graph, path, visited);
				if (!returnedPath.isEmpty()) {
					return returnedPath;
				} else {
					path.remove(v);
					visited.remove(v);
				}
			}
		}

		// returns empty path if no neighbors of current node, or if node cannot be reached
		return new ArrayList<Integer>();
	}

	//Helper method: returns neighbors of given vertex
    public static ArrayList<Integer> getNeighbors(WGraph graph, int vertex, ArrayList<Integer> visited) {
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
    	for (Edge e : graph.getEdges()) {
    		if (e.nodes[0] == vertex && e.weight > 0 && !visited.contains(e.nodes[1])) {
    			neighbors.add(e.nodes[1]);
    		}
    	}
		return neighbors;
    }
    
	
	public static String fordfulkerson(WGraph graph){
		String answer="";
		int maxFlow = 0;
		int source = graph.getSource();
		int dest = graph.getDestination();
		
		WGraph residual = new WGraph(graph);
		for (Edge e : graph.getEdges()) {
			// add all backwards edges with capacity 0
			Edge backEdge = new Edge(e.nodes[1], e.nodes[0], 0);
			residual.addEdge(backEdge);
			e.weight = 0; //no flow at start of algorithm
		}
	
		ArrayList<Integer> path = pathDFS(source, dest, residual);
		while (!path.isEmpty()) {
			int bottleneck = findBottleneck(residual, path); 
			// Augment path
			for (int i = 0; i < path.size()-1; i++) {
	    		int v1 = path.get(i);
	    		int v2 = path.get(i+1);
	    		Edge e = graph.getEdge(v1, v2);
	    
	    		if (e == null) { //if backward edge
	    			int edge_w = graph.getEdge(v2, v1).weight - bottleneck;
	    			graph.setEdge(v2, v1, edge_w);
	    			int forward_w = residual.getEdge(v2, v1).weight + bottleneck;
	    			residual.setEdge(v2, v1, forward_w);
	    			int backward_w = residual.getEdge(v1, v2).weight - bottleneck;
	    			residual.setEdge(v1, v2, backward_w);
	    		} else { //if forward edge
	    			int edge_w = e.weight + bottleneck;
	    			graph.setEdge(v1, v2, edge_w);
	    			int forward_w = residual.getEdge(v1, v2).weight - bottleneck;
	    			residual.setEdge(v1, v2, forward_w);
	    			int backward_w = residual.getEdge(v2, v1).weight + bottleneck;
	    			residual.setEdge(v2, v1, backward_w);
	    		}
	    		
	    	}
			maxFlow += bottleneck;
			path = pathDFS(source, dest, residual);
		}
		
		answer += maxFlow + "\n" + graph.toString();
		return answer;
	}

    
    public static int findBottleneck(WGraph graph, ArrayList<Integer> path) {
    	int bottleneck = -1;
    	for (int i = 0; i < path.size()-1; i++) {
    		int v1 = path.get(i);
    		int v2 = path.get(i+1);
    		int weight = graph.getEdge(v1, v2).weight;
    		if (weight < bottleneck || bottleneck == -1) {
    			bottleneck = weight;
    		}
    	}
    	return bottleneck;
    }


	 public static void main(String[] args){
		 String file = args[0];
		 WGraph g = new WGraph(file);
	     System.out.println(fordfulkerson(g));
	 }
}

