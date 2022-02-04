import java.util.*;

public class Kruskal {

    public static WGraph kruskal(WGraph g){
    	WGraph MST = new WGraph();
    	ArrayList<Edge> sortedEdges = g.listOfEdgesSorted();
    	DisjointSets djs = new DisjointSets(g.getNbNodes());

    	for (int e = 0; e < sortedEdges.size(); e++) {
    		Edge edge = sortedEdges.get(e);
    		if (IsSafe(djs, edge)) {
    			djs.union(edge.nodes[0], edge.nodes[1]);
    			MST.addEdge(edge);
    		}
    	}
        
        return MST;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){
    	int i = e.nodes[0];
    	int j = e.nodes[1];
    	
    	if (p.find(i) == p.find(j)) {return false;}     
        return true;
    }

    public static void main(String[] args){

        //String file = args[0];
    	String file = "/Users/danielkorsunsky/Desktop/McGill/Fall 2020/COMP 251/HW2/g1.txt";
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
