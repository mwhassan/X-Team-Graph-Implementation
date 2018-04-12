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
    private LinkedList<GraphNode <E>> graph; // List of verticies

    /**************************
     * Constructors
     **************************/

    public Graph() {
        graph = new LinkedList<GraphNode <E>>();
    }
    /**
     * Add new vertex to the graph
     * 
     * Valid argument conditions: 1. vertex should be non-null 2. vertex should not already exist in
     * the graph
     * 
     * @param vertex the vertex to be added
     * @return vertex if vertex added, else return null if vertex can not be added (also if valid
     *         conditions are violated)
     */
    @Override
    public E addVertex(E vertex) {
        // return null if bad data or already exists
        if (vertex == null || this.contains(vertex))
            return null;

        // Add vertex
        graph.add(new GraphNode(vertex));
        return vertex;
    }

    /**
     * Remove the vertex and associated edge associations from the graph
     * 
     * Valid argument conditions: 1. vertex should be non-null 2. vertex should exist in the graph
     * 
     * @param vertex the vertex to be removed
     * @return vertex if vertex removed, else return null if vertex and associated edges can not be
     *         removed (also if valid conditions are violated)
     */
    @Override
    public E removeVertex(E vertex) {
        // return null if bad data or already exists
        if (vertex == null)
            return null;

        GraphNode<E> removeNode = getNode(vertex);

        if (removeNode == null)
            return null;

        
        //Remove reciprical edges if present
        removeComplementryEdges(removeNode);
        
        
        graph.remove(graph.indexOf(removeNode));

        

        return removeNode.vertex;
    }
    
    

    /**
     * Add an edge between two vertices (edge is undirected and unweighted)
     * 
     * Valid argument conditions: 1. both the vertices should exist in the graph 2. vertex1 should
     * not equal vertex2
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if edge added, else return false if edge can not be added (also if valid
     *         conditions are violated)
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        //Return false if vertexes are equal
        if (vertex1 == vertex2) return false;
        
        //Find the nodes based on vertex
        GraphNode<E> node1 = getNode(vertex1);
        
        GraphNode<E> node2 = getNode(vertex2);
        
        //If nodes are null return false
        if (node1 == null || 
            node2 == null) 
            return false;
        
        
        //Add Edge to v1
        node1.addNeighbor(node2);

        //add Edge to v2
        node2.addNeighbor(node1);
        
      
        return true;
    }

    /**
     * Remove the edge between two vertices (edge is undirected and unweighted)
     * 
     * Valid argument conditions: 1. both the vertices should exist in the graph 2. vertex1 should
     * not equal vertex2
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if edge removed, else return false if edge can not be removed (also if valid
     *         conditions are violated)
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        
        //Return false if vertexes are equal
        if (vertex1 == vertex2) return false;
        
        //Find the nodes based on vertex
        GraphNode<E> node1 = getNode(vertex1);
        GraphNode<E> node2 = getNode(vertex2);
        
        //If nodes are null return false
        if (node1 == null || 
            node2 == null) 
            return false;
        
        // Remove Edge to v1
        node1.removeNeighbor(node2);


        // Remove Edge to v2
        node2.removeNeighbor(node1);
        
        return true;
    }

    /**
     * Check whether the two vertices are adjacent
     * 
     * Valid argument conditions: 1. both the vertices should exist in the graph 2. vertex1 should
     * not equal vertex2
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if both the vertices have an edge with each other, else return false if vertex1
     *         and vertex2 are not connected (also if valid conditions are violated)
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        //Return false if vertexes are equal
        if (vertex1 == vertex2) return false;
        
        //Find the nodes based on vertex
        GraphNode<E> node1 = getNode(vertex1);
        GraphNode<E> node2 = getNode(vertex2);
        
        
        //If nodes are null return false
        if (node1 == null || 
            node2 == null) 
            return false;
        
        //If the nodes are neighbors return true
        if (node1.getNeighbor(node2) != null) return true;
        
        return false;
    }

    /**
     * Get all the neighbor vertices of a vertex
     * 
     * Valid argument conditions: 1. vertex is not null 2. vertex exists
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
     * 
     * @param vertex
     * @return
     */
    private boolean contains(E vertex) {
        boolean mReturn = false;
        for (GraphNode<E> node : graph) {
            if (node != null && node.equals(vertex))
                return true;
        }

        return mReturn;
    }

    /**
     * Return the node associated with the given vertex or null if it does not exist
     * 
     * @param vertex
     * @return
     */
    private GraphNode<E> getNode(E vertex) {
        for (GraphNode<E> node : graph) {
            if (node != null && node.getVertex().equals(vertex))
                return node;
        }

        return null;
    }
    
    /**
     * Finds and removes edges from other verticies that link to vertex sent in
     * 
     * @param vertex - vertex we want to remove complementry edges from
     * @return - true if all compl edges are removed
     */
    private void removeComplementryEdges(GraphNode<E> removeNode) {
        
        for (GraphNode<E> node : graph) {
            if (node != null && isAdjacent(node.vertex, removeNode.vertex))
                removeEdge(node.vertex, removeNode.vertex);
        }
        
    }
    
    @Override
    public String toString() {
        String mReturn = "";
        
        for (int i = 0; i< graph.size(); i++)
            mReturn += graph.get(i) + "\n";
        
        return mReturn;
    }

    /**************************
     * private class node
     **************************/

    // ##########################################################################################
    /**
     * Each node represent a vertex and contains a vertex specific adjacency list of its associated
     * neighbors
     * 
     * @author hayesbirchle
     *
     * @param <E> generic sent in by graph class
     */
    private class GraphNode<E> {
        /**************************
         * Private Class Fields
         **************************/

        private E vertex; // My vertex
        private LinkedList<GraphNode<E>> neighbors; // My Neighbors

        /**************************
         * Constructors
         **************************/

        public GraphNode() {
            neighbors = new LinkedList<GraphNode<E>>();
        }
        
        public GraphNode(E vertex) {
            this();
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


        public void addNeighbor(GraphNode<E> node) {
            neighbors.add(node);
        }

        public GraphNode<E> getNeighbor(GraphNode<E> node) {
            int nodeIndex = neighbors.indexOf(node);

            if (nodeIndex > -1) {
                return neighbors.get(neighbors.indexOf(node));
            } else {
                return null;
            }
        }

        public void removeNeighbor(GraphNode<E> node) {
            int nodeIndex = neighbors.indexOf(node);
            
            if (nodeIndex > -1) {
                neighbors.remove(nodeIndex);
            }
        }
        
        
        @Override
        public String toString() {
            String mReturn = "";
            
            mReturn += ("Vertex: " + vertex.toString() + "\n");
            for (int i = 0; i < neighbors.size(); i++) {
                mReturn += ("          --> " + neighbors.get(i).vertex.toString() + " (" + i + ")\n");
            }
                
            
            return mReturn;
        }
        

    }

    // ##########################################################################################
}
