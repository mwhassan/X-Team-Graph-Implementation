import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OLD_GraphProcessorTest {
	
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
	 * fails if the graph populated with no vertices doesn't return -1 (catches
	 * FileNotFoundException())
	 */
	@Test
	public final void populateGraph_empty_return_0() {
		int exp = -1;
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
	
	@Test
	public final void getShortestDistance_between_word_and_itself() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		int act = graphProc.getShortestDistance("hot", "hot");
		int exp = -1;
		if (exp != act) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	@Test
	public final void getShortestDistance_between_words_replacement() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		int act = graphProc.getShortestDistance("hot", "hat");
		int exp = 1;
		if (exp != act) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	@Test
	public final void getShortestDistance_between_words_addition() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		int act = graphProc.getShortestDistance("hat", "heat");
		int exp = 1;
		if (exp != act) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	@Test
	public final void getShortestDistance_between_words_deletion() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		int act = graphProc.getShortestDistance("hat", "at");
		int exp = 1;
		if (exp != act) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	@Test
	public final void getShortestDistance_between_words_reachable() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		int act = graphProc.getShortestDistance("it", "wheat");
		int exp = 4;
		if (exp != act) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	@Test
	public final void getShortestPath_between_words_unreachable() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		String act = graphProc.getShortestDistance("streak", "husband").toString();
		String exp = "[]";
		if (!act.equals(exp)) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	@Test
	public final void getShortestPath_between_word_and_itself() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		String act = graphProc.getShortestDistance("hot", "hot").toString();
		String exp = "[]";
		if (!act.equals(exp)) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	@Test
	public final void getShortestPath_between_words_replacement() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		String act = graphProc.getShortestDistance("hot", "hat").toString();
		String exp = "[hot, hat]";
		if (!act.equals(exp)) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	@Test
	public final void getShortestPath_between_words_addition() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		String act = graphProc.getShortestDistance("hat", "heat").toString();
		String exp = "[hat, heat]";
		if (!act.equals(exp)) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	

	@Test
	public final void getShortestPath_between_words_deletion() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		String act = graphProc.getShortestDistance("hat", "at").toString();
		String exp = "[hat, at]";
		if (!act.equals(exp)) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
	
	@Test
	public final void getShortestPath_between_words_reachable() {
		graphProc.populateGraph("GraphProcessorTestFile.txt");
		String act = graphProc.getShortestDistance("it", "wheat").toString();
		String exp = "[it, at, hat, heat, wheat]";
		if (!act.equals(exp)) {
			fail("expected:" + exp + "actual:" + act);
		}
	}
}
