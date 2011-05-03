import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WordladderUnitTest extends junit.framework.TestCase{
	String dir = System.getProperty("user.dir");
	String path = dir + "\\trunk\\wordladder\\ressources\\";
	int iterations = 2;
	String filename = "word_list_moby_crossword.txt";
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuildGraphCollectionOfQextendsK() throws IOException {
		int iterations = 10;
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(path + filename), "UTF-8"));
		List<String> words = new ArrayList<String>();  
		// Read all lines in the input file 
		while(true) {
			String s = r.readLine();
			if (s == null) break;
			words.add(s.toLowerCase()); 
		}
		Stopwatch w = new Stopwatch();
		DiGraph<String> dg = null;
		for (int i = 0; i<iterations; i++) {
			 dg = new DiGraph<String>(7);
			dg.buildGraph(words);
		}
		System.out.println("Mean buildtime Iter: " + w.elapsedTime()/iterations);
		System.out.println("Vertices: " + dg.V());
		System.out.println("Edges: " + dg.E());
		System.out.println("Calls during build: " + dg.count);
	}

	@Test
	public void testBuildGraphString() throws IOException {
		Stopwatch w = new Stopwatch();
		DiGraph<String> dg = null;
		for (int i = 0; i < iterations; i++) {
			dg = new DiGraph<String>(7);
			dg.buildGraph(path + filename);
		} 
		System.out.println("Mean buildtime Iter: " + w.elapsedTime()/iterations);
		System.out.println("Vertices: " + dg.V());
		System.out.println("Edges: " + dg.E());
		System.out.println("Calls during build: " + dg.count);
	}

}
