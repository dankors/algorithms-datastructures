import java.util.*;

public class BellmanFord{

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    class BellmanFordException extends Exception{
        public BellmanFordException(String str){
            super(str);
        }
    }

    class NegativeWeightException extends BellmanFordException{
        public NegativeWeightException(String str){
            super(str);
        }
    }

    class PathDoesNotExistException extends BellmanFordException{
        public PathDoesNotExistException(String str){
            super(str);
        }
    }

    /* Constructor, input a graph and a source
     * Computes the Bellman Ford algorithm to populate the
     * attributes 
     *  distances - at position "n" the distance of node "n" to the source is kept
     *  predecessors - at position "n" the predecessor of node "n" on the path
     *                 to the source is kept
     *  source - the source node
     *
     *  If the node is not reachable from the source, the
     *  distance value must be Integer.MAX_VALUE
     */
    BellmanFord(WGraph g, int source) throws NegativeWeightException{
    	this.source = source;
    	int vertices = g.getNbNodes();
    	distances = new int[vertices];
    	predecessors = new int[vertices];
    	ArrayList<Edge> edges = g.getEdges();
    	
    	// Set nodes at distance of infinity
    	for (int i = 0; i < vertices; i++) {distances[i] = Integer.MAX_VALUE;}
    	distances[source] = 0;
    	for (int i = 0; i < vertices; i++) {predecessors[i] = -1;}
    	
    	// Relax edges
    	for (int v = 0; v < vertices; v++) {
    		for (Edge e : edges) {
    			int u1 = e.nodes[0];
    			int u2 = e.nodes[1];
    			int w = e.weight;
    			if (distances[u1] + w < distances[u2] && distances[u1] != Integer.MAX_VALUE) {
    				distances[u2] = distances[u1] + w;
    				predecessors[u2] = u1;
    			}
    		}
    	}
    	
    	// Check graph for negative weight cycle
    	for (Edge e : edges) {
			int u1 = e.nodes[0];
			int u2 = e.nodes[1];
			int w = e.weight;
			if (distances[u1] + w < distances[u2] && distances[u1] != Integer.MAX_VALUE) {
				throw new NegativeWeightException("Exception: Graph has negative weight cycle!");
			}
    	}
    }

    /*Returns the list of nodes along the shortest path from 
     * the object source to the input destination
     * If not path exists an Error is thrown
     */
    public int[] shortestPath(int destination) throws PathDoesNotExistException{
    	if (predecessors[destination] == -1) {
    		throw new PathDoesNotExistException("Exception: Path does not exist!");
    	}

    	// Set temp path with variable length (we don't know length of shortest path in advance)
    	ArrayList<Integer> temp_path = new ArrayList<Integer>();
    	int parent = destination;
		while (parent != -1) {
			temp_path.add(0, parent);
			parent = predecessors[parent];
		}
		
		int[] path = new int[temp_path.size()];
		for (int i = 0; i < temp_path.size(); i++ ) {
			path[i] = temp_path.get(i);
		}
		return path;
    }

    /*Print the path in the format s->n1->n2->destination
     *if the path exists, else catch the Error and 
     *prints it
     */
    public void printPath(int destination){
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}

