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
// Due Date : 4/16/2018 by 10PM
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
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
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
     * @throws IOException 
     */
    public Integer populateGraph(String filepath) {
        try {
            Stream<String> wordStream = WordProcessor.getWordStream(filepath);
            List<String> list = wordStream
                   .collect(Collectors.toList());  
            list.forEach(s->graph.addVertex(s));
            
            // numOfVertex: keep track of all vertices in the graph 
            // after adding words from stream 
            int numOfVertex = 0;
            for(String s: graph.getAllVertices()) {
                numOfVertex++;
                for(String t: graph.getAllVertices()) {
                    if(graph.isAdjacent(s, t)) {
                        graph.addEdge(s, t);
                    }
                }
            }
            // addedVertex: all vertices in graph after adding minus
            // vertices already in graph before adding
            int addedVertex = numOfVertex - vertexInGraph;
            vertexInGraph = numOfVertex;
            this.shortestPathPrecomputation();
            
            return addedVertex;
        } catch(FileNotFoundException e) {
            System.out.println(filepath + "is not found. ");
            return -1;
        } catch(IOException e) {
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
        // listOfWords: data structure containing words in the shorted path
        LinkedList<String> listOfWords = new LinkedList<String>();
        
        // n: variable tracks the number of iteration
        // indexOfStart: index of the LinkedList stored in the ArrayList 
        // with word1 as the starting vertex
        int n = 0;
        int indexOfStart = -1;
        for(String str: graph.getAllVertices()) {
            if(word1.equalsIgnoreCase(str)) {
                indexOfStart = n;
                break;
            }
            n++;
        }
        
        // finding word2 and add words to the path through pointer
        // to the previous  vertex
        if(indexOfStart < 0) return listOfWords;
        for(int i = 0; i < shortestPath.get(indexOfStart).size(); i++) {
            if(word2.equalsIgnoreCase(shortestPath.get(indexOfStart).get(i).word)) {
                Node temp = shortestPath.get(indexOfStart).get(i);
                if(temp.prev == null) {
                    return listOfWords;
                }
                while(temp.prev != null) {
                    listOfWords.addFirst(temp.word.toLowerCase());
                    temp = temp.prev;
                }
                listOfWords.addFirst(temp.word);
            }
        }
        
        return listOfWords;
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
    public Integer getShortestDistance(String word1, String word2)  {
        
        // call getShortestPath() to get the word list to get the distance
        int distance = getShortestPath(word1, word2).size();
        if(distance == 0) return -1;
        else return distance - 1;
        
    }
    
    /**
     * Computes shortest paths and distances between all possible pairs of vertices.
     * This method is called after every set of updates in the graph to recompute the path information.
     * Any shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     */
    public void shortestPathPrecomputation() {
        
        // get all vertices from the graph, use each of them to initialize a Node
        // and put all the corresponding nodes into shortestPath
        // for n vertices, shortestPath holds n LinkedList containing n Nodes
        shortestPath = new ArrayList<LinkedList<Node>>();
        int i = 0;
        for(String s: graph.getAllVertices()) {
            shortestPath.add(new LinkedList<Node>());
            for(String t: graph.getAllVertices()) {
                shortestPath.get(i).addLast(new Node(t, Integer.MAX_VALUE, null));
            }
            i++;
        }
        
        // call implementDjikstras() method for each LinkedList with different starting vertex
        for(int j = 0; j < shortestPath.size(); j++) {
            this.implementDjikstras(j, j);
        }
    }
 
//////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////private fields, inner class, and helper methods//////////////////////////////
    
    // vertexInGraph: number of vertex in the current graph
    private int vertexInGraph = 0;
    // shortestPath: data structure holding nodes used for Djikstra's algorithm
    private ArrayList<LinkedList<Node>> shortestPath;
    
    /**
     * This method implement Djikstra's algorithm for different starting vertex/node
     * @param row: the row index mapping to the LinkedList used
     * @param column: the column index mapping to the starting vertex/node
     */
    private void implementDjikstras(int row, int column) {
        // change the starting vertex's totalWeight to 0
        shortestPath.get(row).get(column).totalWeight = 0;
        
        // Initialize a PriorityQueue overwriting the Comparator that the priority is 
        // the least totalWeight in each node
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node arg0, Node arg1) {
                // TODO Auto-generated method stub
                int num = 0;
                if(arg0.totalWeight > arg1.totalWeight)
                    num = 1;
                if(arg0.totalWeight == arg1.totalWeight) {
                    if(arg0.word.compareTo(arg1.word) > 0) {
                        num = 1;
                    }
                    if(arg0.word.compareTo(arg1.word) < 0) {
                        num = -1;
                    }
                    if(arg0.word.compareTo(arg1.word) == 0) {
                        num = 0;
                    }
                }
                if(arg0.totalWeight < arg1.totalWeight)
                    num = -1;
                
                return num;
            }
        });
        
        // add the starting vertex to the priority queue and start the iteration
        // until all vertex has been popped out of this priority queue
        pq.add(shortestPath.get(row).get(column));
        while(!pq.isEmpty()) {
            
            //pop out the node with the priority: the least totalWeight
            Node cur = pq.poll();      
            cur.visited = true;
            
            // iterate through all unvisited successor of Node cur
            for(Node suc: shortestPath.get(row)) {                      
                if(WordProcessor.isAdjacent(cur.word, suc.word) && !suc.isVisited()) {
                    
                    // if the totalWeight can be reduced, 
                    // update the totalWeight and the prev pointer
                    int distance = cur.totalWeight + 1;
                    if(distance < suc.totalWeight) {
                        suc.totalWeight = distance;
                        suc.prev = cur;
                    }
                    
                    // if the suc node is not in the priorityQueue, add it
                    if(!pq.contains(suc)) {
                        pq.add(suc);
                    }
                }
            }
        }
    }
    
    /////////////// Private inner class//////////////////////
    /**
     * This private inner class is used for implementing Djistra's algorithm
     */
    private class Node {
        // word: string variable carrying the content of each vertex
        private String word;
        // prev: node pointer pointing to the previous node
        private Node prev;
        // totalWeight: incremented weight from the starting node to this node
        private int totalWeight;
        // visited: boolean flag tracking if this node has been popped out of the
        // priority queue used in Djistra's algorithm
        private boolean visited;

        /**
         * Constructor initializing four private fields
         * @param word: the content of vertex
         * @param totalWeight: largest integer
         * @param prev: previous node or null if not applicable
         */
        private Node(String word, int totalWeight, Node prev) {
            this.word = word;
            this.prev = prev;
            this.totalWeight = totalWeight;
            this.visited = false;
        }
        
        /**
         * @return true if this node has been popped out of the priority queue
         */
        private boolean isVisited() {
            return visited == true;
        }
    }
}