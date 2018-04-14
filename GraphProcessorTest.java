import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphProcessorTest {
	
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
	
	@Test
	public final void populateGraph_set_correct_edges_unreachable() {
		
	}

}
