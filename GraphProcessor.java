// ////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: CS 400 GroupX Graph Processor Assignment
// Files: GraphProcessor.java
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This class adds additional functionality to the graph as a whole.
 * 
 * Contains an instance variable, {@link #graph}, which stores information for all the vertices and edges.
 * @see #populateGraph(String)
 *  - loads a dictionary of words as vertices in the graph.
 *  - finds possible edges between all pairs of vertices and adds these edges in the graph.
 *  - returns number of vertices added as Integer.
 *  - every call to this method will add to the existing graph.
 *  - this method needs to be invoked first for other methods on shortest path computation to work.
 * @see #shortestPathPrecomputation()
 *  - applies a shortest path algorithm to precompute data structures (that store shortest path data)
 *  - the shortest path data structures are used later to 
 *    to quickly find the shortest path and distance between two vertices.
 *  - this method is called after any call to populateGraph.
 *  - It is not called again unless new graph information is added via populateGraph().
 * @see #getShortestPath(String, String)
 *  - returns a list of vertices that constitute the shortest path between two given vertices, 
 *    computed using the precomputed data structures computed as part of {@link #shortestPathPrecomputation()}.
 *  - {@link #shortestPathPrecomputation()} must have been invoked once before invoking this method.
 * @see #getShortestDistance(String, String)
 *  - returns distance (number of edges) as an Integer for the shortest path between two given vertices
 *  - this is computed using the precomputed data structures computed as part of {@link #shortestPathPrecomputation()}.
 *  - {@link #shortestPathPrecomputation()} must have been invoked once before invoking this method.
 *  
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class GraphProcessor {

    /**
     * Graph which stores the dictionary words and their associated connections
     */
    private GraphADT<String> graph;

    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the object
     */
    public GraphProcessor() {
        this.graph = new Graph<>();
    }
        
    /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the dictionary as vertices
     * and finding and adding the corresponding connections (edges) between 
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph.
     * Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent {@link WordProcessor#isAdjacent(String, String)}
     * If a pair is adjacent, adds an undirected and unweighted edge between the pair of vertices in the graph.
     * 
     * Log any issues encountered (print the issue details)
     * 
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added; return -1 if file not found or if encountering other exceptions
     */
    public Integer populateGraph(String filepath) {
        //Stream<String> wordStream = Files.lines(Paths.get(filepath)).map(String::trim).filter(x->x!=null && !x.equals("")).map(String::toUpperCase);
        try {
            Stream<String> wordStream = WordProcessor.getWordStream(filepath);
            wordStream.forEach(s->graph.addVertex(s));
            int numOfVertex = 0;
            for(String s: graph.getAllVertices()) {
                numOfVertex++;
                for(String t: graph.getAllVertices()) {
                    if(graph.isAdjacent(s, t)) {
                        graph.addEdge(s, t);
                    }
                }
            }
            return numOfVertex;
        } catch(FileNotFoundException e) {
            System.out.println(filepath + "is not found. ");
            return -1;
        } catch(Exception e) {
            return -1;
        }
    }
    
    /**
     * Gets the list of words that create the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             neat
     *             wheat
     *             kit
     *  shortest path between cat and wheat is the following list of words:
     *     [cat, hat, heat, wheat]
     *     
     *     If word1 = word2, List will be empty.
     *     Both the arguments will always be present in the graph
     * 
     * @param word1 first word
     * @param word2 second word
     * @return List<String> list of the words
     */
    public List<String> getShortestPath(String word1, String word2) {
        // Using BFS just for checking
        
        if(word1.equals(word2)) {
            return null;
        }
        Queue<Node> q = new LinkedList<Node>();
        for(String s: graph.getNeighbors(word1)) {
            q.add(new Node(s, new Node(word1, null)));
        }
        
        boolean findPath = false;
        
        Node temp = q.remove();
        while(q.isEmpty()) {
            for(String s: graph.getNeighbors(temp.word)) {
                if(s.equals(word2)) {
                    temp = new Node(s, temp);
                    findPath = true;
                    break;
                }
                q.add(new Node(s, temp));
            }
            temp = q.remove();
        }
        
        if(!findPath) {
            return null;
        }
        
        LinkedList<String> lWord = new LinkedList<String>();
        while(temp.prev != null) {
            ((LinkedList<String>) lWord).addFirst(temp.word);
            temp = temp.prev;
        }
        
        return lWord;
    }
    
    /**
     * Gets the distance of the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             neat
     *             wheat
     *             kit
     *  distance of the shortest path between cat and wheat, [cat, hat, heat, wheat]
     *   = 3 (the number of edges in the shortest path)
     * 
     * Distance = -1 if no path found between words (true also for word1 = word2)
     * Both the arguments will always be present in the graph
     * 
     * @param word1 first word
     * @param word2 second word
     * @return Integer distance
     */
    public Integer getShortestDistance(String word1, String word2) {
        // Using BFS just for checking
        
        if(word1.equals(word2)) {
            return -1;
        }
        
        Queue<Node> q = new LinkedList<Node>();
        for(String s: graph.getNeighbors(word1)) {
            q.add(new Node(s, new Node(word1, null)));
        }
        
        boolean findPath = false;
        Node temp = q.remove();
        while(q.isEmpty()) {
            for(String s: graph.getNeighbors(temp.word)) {
                if(s.equals(word2)) {
                    temp = new Node(s, temp);
                    findPath = true;
                    break;
                }
                q.add(new Node(s, temp));
            }
            temp = q.remove();
        }
        
        if(!findPath) {
            return -1;
        }
        
        int distance = 0;
        while(temp != null) {
            distance++;
            temp = temp.prev;
        }
        
        if(distance > 0)
            return distance;
        else 
            return -1;
    }
    
    /**
     * Computes shortest paths and distances between all possible pairs of vertices.
     * This method is called after every set of updates in the graph to recompute the path information.
     * Any shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     */
    public void shortestPathPrecomputation() {
        for(String s: graph.getAllVertices()) {
            PriorityQueue<Node> pq = new PriorityQueue<Node>(0, new Comparator<Node>() {
                @Override
                public int compare(Node arg0, Node arg1) {
                    // TODO Auto-generated method stub
                    return arg0.totalWeight.compareTo(arg1.totalWeight);
                }
            });    
        }
    }
    
 ///////////////////////////Private Node Class/////////////////////////////////////////
    
    private class Node {
        private String word;
        private Node prev;
        private Integer totalWeight;
        private int visited;
        
        private Node(String word, Node prev) {
            this.word = word;
            this.prev = prev;
        }
        
        private Node(String word) {
            this.word = word;
            this.prev = null;
            this.totalWeight = Integer.MAX_VALUE;
            this.visited = 0;
        }
        
        private Node(String word, int totalWeight) {
            this.word = word;
            this.prev = null;
            this.totalWeight = 0;
            this.visited = 0;
        }
        
        private boolean isVisited() {
            return visited == 1;
        }
    }
}
