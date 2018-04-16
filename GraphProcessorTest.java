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


import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphProcessorTest {
	
    //NOTE:  Expectation is that text files are stored in project directory not src
    
    
	private static GraphProcessor graphProc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {  }
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {  }
	
	@Before
	public void setUp() throws Exception {
		graphProc = new GraphProcessor();
	}

	@After
	public void tearDown() throws Exception {
		graphProc = null;
	}

	/**
	 * fails if the graph populated with no vertices doesn't return 0 
	 */
	@Test
	public final void populateGraph_empty_return_0() {
		int exp = 0;
		int act = graphProc.populateGraph("EmptyFile.txt");
		if (exp != act) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	/**
	 * fails if the graph populated with 11 vertices doesn't return 11
	 */
	@Test
	public final void populateGraph_fileOfStrings_return_11() {
		int exp = 11;
		int act = graphProc.populateGraph("GraphProcessorTestFile.txt");
		if (exp != act) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	/**
     * fails if the graph populated with 441 strings(427 unique strings) doesn't return 427
     */
    @Test
    public final void populateGraph_fileOfStrings_return_427() {
        int exp = 427;
        int act = graphProc.populateGraph("word_list.txt");
        if (exp != act) {
            fail("expected:" + exp + "actual:" + act);
        }
    }
    
    /**
     * fails if the graph populated with 11 vertex first and then 
     *  441 strings (427 unique strings) doesn't return 11 first and then 426
     */
    @Test
    public final void populateGraph_Twice_fileOfStrings_return_11_427() {
        String exp = 11 + " " + 426;
        String act = "" + graphProc.populateGraph("GraphProcessorTestFile.txt");
        act = act + " " + graphProc.populateGraph("word_list.txt");
        if (!exp.equalsIgnoreCase(act)) {
            fail("expected:" + exp + "actual:" + act);
        }
    }
    
    /**
     * fails if getShorestDistance between words not in graph does not return -1 
     */
    @Test
    public final void getShortestDistance_between_word_not_in_graph() {
        graphProc.populateGraph("GraphProcessorTestFile.txt");
        int act = graphProc.getShortestDistance("aka", "hot");
        int exp = -1;
        if (exp != act) {
            fail("expected:" + exp + "actual:" + act);
        }
    }
	
	/**
	 * fails if getShortestDistance between "streak" and "husband" doesn't return -1
	 */
	@Test
	public final void getShortestDistance_between_words_unreachable() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		int act = graphProc.getShortestDistance("streak", "husband");
		int exp = -1;
		if (exp != act) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	/**
	 * fails if getShorestDistance between two same words does not return -1 
	 */
	@Test
	public final void getShortestDistance_between_word_and_itself() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		int act = graphProc.getShortestDistance("hot", "hot");
		int exp = -1;
		if (exp != act) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	/**
	 * fails if getShorestDistance between two adjacent words by replacement does not return 1
	 */
	@Test
	public final void getShortestDistance_between_words_replacement() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		int act = graphProc.getShortestDistance("hot", "hat");
		int exp = 1;
		if (exp != act) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	/**
	 * fails if getShorestDistance between two adjacent words by addition does not return 1
	 */
	@Test
	public final void getShortestDistance_between_words_addition() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		int act = graphProc.getShortestDistance("hat", "heat");
		int exp = 1;
		if (exp != act) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	

	/**
	 * fails if getShorestDistance between two adjacent words by deletion does not return 1
	 */
	@Test
	public final void getShortestDistance_between_words_deletion() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		int act = graphProc.getShortestDistance("hat", "at");
		int exp = 1;
		if (exp != act) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	/**
	 * fails if getShorestDistance between two reachable words does not return 4
	 */
	@Test
	public final void getShortestDistance_between_words_reachable() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		int act = graphProc.getShortestDistance("it", "wheat");
		int exp = 4;
		if (exp != act) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	/**
     * fails if getShorestDistance between two reachable words does not return 2
     */
	@Test
    public final void getShortestDistance_between_words_reachable_distance_2() {
        graphProc.populateGraph("word_list.txt");
        int act = graphProc.getShortestDistance("BELLIES", "JOLLIES");
        int exp = 2;
        if (exp != act) {
            fail("expected:" + exp + "actual:" + act);
        }
    }
	
	/**
     * fails if getShorestDistance between two reachable words does not return 26
     */
	@Test
    public final void getShortestDistance_between_words_reachable_distance_26() {
        graphProc.populateGraph("word_list.txt");
        int act = graphProc.getShortestDistance("DEFINE", "SHINNY");
        int exp = 26;
        if (exp != act) {
            fail("expected:" + exp + "actual:" + act);
        }
    }
	
	/**
     * fails if getShorestDistance between two reachable words does not return 49
     */
	@Test
    public final void getShortestDistance_between_words_reachable_distance_49() {
        graphProc.populateGraph("word_list.txt");
        int act = graphProc.getShortestDistance("COMEDO", "CHARGE");
        int exp = 49;
        if (exp != act) {
            fail("expected:" + exp + "actual:" + act);
        }
    }
	
	/**
     * fails if getShorestDistance between two reachable words does not return 78
     */
	@Test
    public final void getShortestDistance_between_words_reachable_distance_78() {
        graphProc.populateGraph("word_list.txt");
        int act = graphProc.getShortestDistance("CHARGE", "GIMLETS");
        int exp = 78;
        if (exp != act) {
            fail("expected:" + exp + "actual:" + act);
        }
    }
	
	/**
	 * fails if getShortestPath between words not in graph does not return an empty list
	 */
	@Test
    public final void getShortestPath_between_words_not_in_graph() {
        graphProc.populateGraph("GraphProcessorTestFile.txt");
        String act = graphProc.getShortestPath("aka", "husband").toString();
        String exp = "[]";
        if (!act.equalsIgnoreCase(exp)) {
            fail("expected:" + exp + "actual:" + act);
        }
    }
	
	/**
     * fails if getShortestPath between unreachable words does not return an empty list
     */
	@Test
	public final void getShortestPath_between_words_unreachable() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		String act = graphProc.getShortestPath("streak", "husband").toString();
		String exp = "[]";
		if (!act.equalsIgnoreCase(exp)) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	/**
     * fails if getShortestPath between the same words does not return an empty list
     */
	@Test
	public final void getShortestPath_between_word_and_itself() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		String act = graphProc.getShortestPath("hot", "hot").toString();
		String exp = "[]";
		if (!act.equalsIgnoreCase(exp)) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	/**
     * fails if getShortestPath between two adjacent words does not return a list of themselves
     */
	@Test
	public final void getShortestPath_between_words_replacement() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		String act = graphProc.getShortestPath("hot", "hat").toString();
		String exp = "[hot, hat]";
		if (!act.equalsIgnoreCase(exp)) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	/**
     * fails if getShortestPath between two adjacent words does not return a list of themselves
     */
	@Test
	public final void getShortestPath_between_words_addition() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		String act = graphProc.getShortestPath("hat", "heat").toString();
		String exp = "[hat, heat]";
		if (!act.equalsIgnoreCase(exp)) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	/**
     * fails if getShortestPath between two adjacent words does not return a list of themselves
     */
	@Test
	public final void getShortestPath_between_words_deletion() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		String act = graphProc.getShortestPath("hat", "at").toString();
		String exp = "[hat, at]";
		if (!act.equalsIgnoreCase(exp)) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	/**
     * fails if getShortestPath between two adjacent words does not return
     *  a list of words between them
     */
	@Test
	public final void getShortestPath_between_words_reachable() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		String act = graphProc.getShortestPath("it", "wheat").toString();
		String exp = "[it, at, hat, heat, wheat]";
		if (!act.equalsIgnoreCase(exp)) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
}