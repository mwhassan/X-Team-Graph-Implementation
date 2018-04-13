// ////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: CS 400 GroupX Graph Processor Assignment
// Files: Graph.java
// GraphProcessor.java
// GraphProcessorTest.java
// WordPrecessor.java
// Course: CS 400, Spring, 2018
//
// Author - Group: X002_58
// Mostafa Wail Hassan
// Christopher Todd Hayes-Birchler - hayesbirchle@wisc.edu
// Emma He
// Maggie Guo
// Hannah Greene
//
// Lecturer's Name: Deb Deppeler
// Due Date : 2/5/2018 by 10PM
//
// /////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Outlines for some classes were provided by CS 400 faculty
//
// ///////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * @author Chris Hayes (hayesbirchle@wisc.edu)
 * 
 * 
 */
public class Graph<E> implements GraphADT<E> {



    /**************************
     * Private Class Fields
     **************************/
    private LinkedList<GraphNode<E>> graph; // List of verticies

    /**************************
     * Constructors
     **************************/

    public Graph() {
        graph = new LinkedList<GraphNode<E>>(); //instantiate graph
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

        // Add vertex as new graphNode
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


        // Remove reciprical edges if present
        removeComplementryEdges(removeNode);

        //remove vertex node
        graph.remove(graph.indexOf(removeNode));


        //return node we removed
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
        // Return false if vertexes are equal
        if (vertex1.equals(vertex2))
            return false;

        // Find the nodes based on vertex
        GraphNode<E> node1 = getNode(vertex1);
        GraphNode<E> node2 = getNode(vertex2);

        // If nodes are null return false
        if (node1 == null || node2 == null)
            return false;


        // Add Edge to v1
        node1.addNeighbor(node2);

        // add Edge to v2
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

        // Return false if vertexes are equal
        if (vertex1.equals(vertex2))
            return false;

        // Find the nodes based on vertex
        GraphNode<E> node1 = getNode(vertex1);
        GraphNode<E> node2 = getNode(vertex2);

        // If nodes are null return false
        if (node1 == null || node2 == null)
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
        // Return false if vertexes are equal
        if (vertex1.equals(vertex2))
            return false;

        // Find the nodes based on vertex
        GraphNode<E> node1 = getNode(vertex1);
        GraphNode<E> node2 = getNode(vertex2);


        // If nodes are null return false
        if (node1 == null || node2 == null)
            return false;

        // If the nodes are neighbors return true
        if (node1.isNodeNeighbor(node2))
            return true;

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

        // return null if bad data or already exists
        if (vertex == null)
            return null;

        GraphNode<E> tempNode = getNode(vertex);

        //if node does not exist return null
        if (tempNode == null)
            return null;

        //Have node return its neighbors (returns from node as linked list <E>
        return tempNode.getNeighborVertices();
    }

    /**
     * Get all the vertices in the graph
     * 
     * @return an iterable for all the vertices
     */
    @Override
    public Iterable<E> getAllVertices() {
        //Create linked list to hold vertecies instead of nodes
        LinkedList<E> vertices = new LinkedList<E>();
        
        //For every node, add vertex to our return
        for (GraphNode<E> node : graph) {
            vertices.add(node.getVertex());
        }
        return vertices;
    }
    
    

    /**
     * Outputs list of vertices as well as vertex neighbors
     * TODO: REMOVE ME AFTER TESTING
     */
    @Override
    public String toString() {
        String mReturn = "";

        for (int i = 0; i < graph.size(); i++)
            mReturn += graph.get(i) + "\n";

        return mReturn;
    }
    
    /**************************
     * private class methods
     **************************/

    /**
     * Does the graph contain the given vertex
     * 
     * @param vertex - Item we want to id if in graph
     * @return - true if the vertex is in the graph
     */
    private boolean contains(E vertex) {
        boolean mReturn = false;
        for (GraphNode<E> node : graph) {
            if (node != null && node.vertex.equals(vertex))
                return true;
        }

        return mReturn;
    }

    /**
     * Return the node associated with the given vertex or null if it does not exist
     * 
     * @param vertex - vertex we want the associated node to
     * @return - return the node associated with the vertex
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

    



    /*
     * ##########################################################################################
     * private class node
     * ##########################################################################################
     */


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

        /**
         * Identifies if the node sent in (node) is a neighbor, if it is return the node else
         * return null
         * @param node - node we want to ID as neighbor
         * @return true if nodes are neighbors
         */
        public boolean isNodeNeighbor(GraphNode<E> node) {
            //if neighbor, indexOf will != -1, 
            return (neighbors.indexOf(node) > -1);
        }

        public LinkedList<E> getNeighborVertices() {
            LinkedList<E> neighborsVertex = new LinkedList<E>();
            for (GraphNode<E> node : neighbors) {
                neighborsVertex.add(node.getVertex());
            }
            return neighborsVertex;
        }


        /**
         * toString for purposes of testing
         * TODO: REMOVE WHEN DONE TESTING
         */
        @Override
        public String toString() {
            String mReturn = "";

            mReturn += ("Vertex: " + vertex.toString() + "\n");
            for (int i = 0; i < neighbors.size(); i++) {
                mReturn += ("          --> " + neighbors.get(i).vertex.toString() + " (" + i
                                + ")\n");
            }


            return mReturn;
        }
        
        /**************************
         * Getters and Setters
         **************************/
        //Vertex
        public E getVertex() {
            return vertex;
        }

        public void setVertex(E vertex) {
            this.vertex = vertex;
        }

        //Neighbors
        public void addNeighbor(GraphNode<E> node) {
            neighbors.add(node);
        }


        public void removeNeighbor(GraphNode<E> node) {
            int nodeIndex = neighbors.indexOf(node);

            if (nodeIndex > -1) {
                neighbors.remove(nodeIndex);
            }
        }
        
        


    }

    // ##########################################################################################
}
