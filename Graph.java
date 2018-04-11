import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
    
	
	/**************************
     * Private Class Fields
     **************************/
	private LinkedList<GraphNode> graph; //List of verticies
	
	/**************************
     * Constructors
     **************************/

	/**
	 * Add new vertex to the graph
	 * 
	 * Valid argument conditions:
	 * 1. vertex should be non-null
	 * 2. vertex should not already exist in the graph 
	 * 
	 * @param vertex the vertex to be added
	 * @return vertex if vertex added, else return null if vertex can not be added (also if valid conditions are violated)
	 */
    @Override
    public E addVertex(E vertex) {
        //return null if bad data or already exists
    	if (vertex==null || this.contains(vertex))
        	return null;
    	
    	//Add vertex
    	graph.add(new GraphNode(vertex));
    	return vertex;
    }

    /**
	 * Remove the vertex and associated edge associations from the graph
	 * 
	 * Valid argument conditions:
	 * 1. vertex should be non-null
	 * 2. vertex should exist in the graph 
	 *  
	 * @param vertex the vertex to be removed
	 * @return vertex if vertex removed, else return null if vertex and associated edges can not be removed (also if valid conditions are violated)
	 */
    @Override
    public E removeVertex(E vertex) {
        return null;
    }

    /**
	 * Add an edge between two vertices (edge is undirected and unweighted)
	 * 
	 * Valid argument conditions:
	 * 1. both the vertices should exist in the graph
	 * 2. vertex1 should not equal vertex2
	 *  
	 * @param vertex1 the first vertex
	 * @param vertex2 the second vertex
	 * @return true if edge added, else return false if edge can not be added (also if valid conditions are violated)
	 */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        return false;
    }    

    /**
	 * Remove the edge between two vertices (edge is undirected and unweighted)
	 * 
	 * Valid argument conditions:
	 * 1. both the vertices should exist in the graph
	 * 2. vertex1 should not equal vertex2
	 *  
	 * @param vertex1 the first vertex
	 * @param vertex2 the second vertex
	 * @return true if edge removed, else return false if edge can not be removed (also if valid conditions are violated)
	 */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        return false;
    }

    /**
	 * Check whether the two vertices are adjacent
	 * 
	 * Valid argument conditions:
	 * 1. both the vertices should exist in the graph
	 * 2. vertex1 should not equal vertex2
	 *  
	 * @param vertex1 the first vertex
	 * @param vertex2 the second vertex
	 * @return true if both the vertices have an edge with each other, else return false if vertex1 and vertex2 are not connected (also if valid conditions are violated)
	 */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        return false;
    }

    /**
	 * Get all the neighbor vertices of a vertex
	 * 
	 * Valid argument conditions:
	 * 1. vertex is not null
	 * 2. vertex exists
	 * 
	 * @param vertex the vertex
	 * @return an iterable for all the immediate connected neighbor vertices
	 */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        return null;
    }

    /**
	 * Get all the vertices in the graph
	 * 
	 * @return an iterable for all the vertices
	 */
    @Override
    public Iterable<E> getAllVertices() {
        return null;
    }

    /**************************
     * private class node
     **************************/
    
    /**
     * Does the graph contain the given vertex
     * @param vertex
     * @return
     */
    private boolean contains(E vertex) {
    	boolean mReturn = false;
    	for(GraphNode node: graph) {
    		if (node.equals(vertex)) return true;
    	}
    	
    	return mReturn;
    }
    
    /**************************
     * private class node
     **************************/
    
    //##########################################################################################
	/**
	 * Each node represent a vertex and its associated neighbors
	 * 
	 * @author hayesbirchle
	 *
	 * @param <E> generic sent in by graph class
	 */
	private class GraphNode<E>{
		/**************************
	     * Private Class Fields
	     **************************/
		
		private E vertex; //My vertex
    	private LinkedList<E> neighbors; //My Neighbors
    	
    	/**************************
	     * Constructors
	     **************************/
    	
    	public GraphNode(E vertex) {
    		this.vertex = vertex;
    	}
    	/**************************
	     * Public Interface
	     **************************/
    	
    	public E getVertex() {
    		return vertex;
    	}
    	
    	public void setVertex(E vertex) {
    		this.vertex = vertex;
    	}
    	
    	
    	public void addNeighbor(E item) {
    		neighbors.add(item);
    	}
    	
    	public void getNeighbor(E item) {
    		neighbors.get(neighbors.indexOf(item));
    	}
    	
    	public void removeNeighbor(E item) {
    		neighbors.indexOf(item);
    	}
    	
    }
	
	//##########################################################################################
}
