import java.io.IOException;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class WordLadderTest {
	
	@Test 
	public void TestMeanIterTime() throws IOException {
		int iterations = 1;
		String dir = System.getProperty("user.dir");

		System.out.println("Building Digraph");
		Stopwatch w = new Stopwatch();
		DiGraph<String> dg = null;
		for (int i = 0; i < iterations; i++) {
			dg = new DiGraph<String>();
			dg.buildGraph(dir + "\\trunk\\wordladder\\ressources\\200000_alpha_5_output.txt");
		} 
		System.out.println("Mean buildtime Iter: " + w.elapsedTime()/iterations);
		System.out.println("Vertices: " + dg.V());
		System.out.println("Edges: " + dg.E());
		System.out.println("Calls during build: " + dg.count);
	}

	@Test
	public void TestBuildGraph() throws IOException {
		String dir = System.getProperty("user.dir");

		Stopwatch w = new Stopwatch();
		System.out.println("Building Digraph");
		DiGraphThreaded<String> dg = new DiGraphThreaded<String>();
		dg.buildGraph(dir + "\\trunk\\wordladder\\ressources\\alpha_100000_output.txt");
		
		System.out.println("Buildtime: " + w.elapsedTime());
		System.out.println("Vertices: " + dg.V());
		System.out.println("Edges: " + dg.E());
		System.out.println("Calls during build: " + dg.count);

//		IVertex<String> v1 = dg.getByValue("other");
//		System.out.println("V1: " + v1.getValue() + ","+ v1.adj().size());
//		IVertex<String> v2 = dg.getByValue("there");
//		System.out.println("V2: " + v2.getValue() + ","+ v2.adj().size());
//		System.out.printf("BFS starting from '%s'", v1.getValue());
//		
//		w = new Stopwatch();
//		BFS<String> bfs = new BFS<String>(dg, v1);
//		System.out.println("..." + w.elapsedTime());
//		Assert.assertTrue(bfs.hasPathTo(v2));
//
//		System.out.printf("Dist from %s to %s : %s\n", v1.getValue(), v2.getValue(), bfs.distTo(v2));
//		Iterator<IVertex<String>> i = bfs.pathTo(v2).iterator();
//		StringBuilder sb = new StringBuilder(); 
//		while(i.hasNext()) sb.append(i.next().getValue()+ "-->");
//		int idx = sb.lastIndexOf("-->");
//		sb.replace(idx, sb.length(), "");
//		System.out.printf("Shortest path from %s to %s : %s\n", v1.getValue(), v2.getValue(), sb.toString());
	}

	@Test
	public void TestVertex() {
		Vertex<String> v1 = new Vertex<String>("there");
		Vertex<String> v2 = new Vertex<String>("heres");
		Vertex<String> v3 = new Vertex<String>("other");
		Vertex<String> v4 = new Vertex<String>("their");

		Assert.assertTrue(v1.isNeighbour(v2));
		Assert.assertFalse(v2.isNeighbour(v1));

		System.out.println("v3-->v4");
		Assert.assertTrue(v3.isNeighbour(v4));
		System.out.println("v4-->v3");
		Assert.assertFalse(v4.isNeighbour(v3));
	}

	@Test
	public void TestBigFiles() throws IOException  {
		System.out.print("Testing big files");
		String dir = System.getProperty("user.dir");
		
		for (int  i = 1; i< 6; i++) {
			Stopwatch w = new Stopwatch();
			DiGraph<String> dg = null;
			dg = new DiGraph<String>();
			dg.buildGraph(dir + "\\trunk\\" + "alpha_" + i * 20000 + "_output.txt");
			
			System.out.print("Built Digraph using Iteration");
			System.out.println("Build time " + w.elapsedTime());
			System.out.println("Vertices: " + dg.V());
			System.out.println("Edges: " + dg.E());
			System.out.println("Calls during build: " + dg.count);
		}
	}
	@Test
	public void TestLongSuffixes() throws IOException {
		System.out.print("Testing long suffixes");
		String dir = System.getProperty("user.dir");
		
		Stopwatch w = new Stopwatch();
		DiGraph<String> dg = null;
		dg = new DiGraph<String>(19);
		dg.buildGraph(dir + "\\trunk\\" + "5757_alpha_20_output.txt");
		
		System.out.println("Built Digraph using Iteration");
		System.out.println("Build time " + w.elapsedTime());
		System.out.println("Vertices: " + dg.V());
		System.out.println("Edges: " + dg.E());
		System.out.println("Calls during build: " + dg.count);
		
	} 
	
	@Test
	public void TesStaticKeyLength() throws IOException  {
		String dir = System.getProperty("user.dir");
		
		for (int  i = 10; i<= 10; i++) {
			Stopwatch w = new Stopwatch();
			DiGraph<String> dg = null;
			dg = new DiGraph<String>(7);
			dg.buildGraph(dir + "\\trunk\\wordladder\\ressources\\" + i*10000 + "_random_alpha_10_output.txt");
			
			System.out.print("Built Digraph using Iteration");
			System.out.println("Build time " + w.elapsedTime());
			System.out.println("Vertices: " + dg.V());
			System.out.println("Edges: " + dg.E());
			System.out.println("Calls during build: " + dg.count);
		}
		
	}
	
	@Test
	public void TestDynamicKeyLength() throws IOException  {
		String dir = System.getProperty("user.dir");
		
		for (int  i = 1; i<= 10; i++) {
			Stopwatch w = new Stopwatch();
			DiGraph<String> dg = null;
			dg = new DiGraph<String>(i);
			dg.buildGraph(dir + "\\trunk\\wordladder\\ressources\\" + i*10000 + "_random_alpha_10_output.txt");
			
			System.out.println("Built Digraph using Iteration");
			System.out.println("Key length");
			System.out.println("Suffix length " + i);
			System.out.println("Build time " + w.elapsedTime());
			System.out.println("Vertices: " + dg.V());
			System.out.println("Edges: " + dg.E());
			System.out.println("Calls during build: " + dg.count);
		}
	}
	
	@Test
	public void TestBitSet() {
		Integer[] a = {1,2,3}; 
		BitSet bs1 = new BitSet();
		BitSet bs2 = new BitSet();
		for (int i = 0; i<1000; i++) {
			bs1.set(StdRandom.uniform(10000));
			bs2.set(StdRandom.uniform(10000));
		}
		Stopwatch w = new Stopwatch();
		for (int j = 0; j < 400000; j++) {
			bs1.and(bs2);
		}
		long l = 10000000000L;
		while (l>0) {
			int p = a[2];
			l--;
		}
		System.out.println(w.elapsedTime());
	}
	
	@Test
	public void TestHashTable() {
		Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
		ht.put("AN", 2);
		ht.put("AN", 2);
		ht.put("AN", 2);
		System.out.println(ht);
		ht.remove("AN");
		System.out.println(ht);
	}
	
	
	@Test
	public void TestRefs() {
		String[] c = {"a","b", "c"};
		System.out.println(c);
		String[] b = c;
		System.out.println(b);
		b = null;
		System.out.println(c);
		System.out.println(b);
		
		Set<Character> set = new HashSet<Character>();
		String str = "abc";
		for (char t : str.toCharArray())
		set.add(t);
		System.out.println("a".charAt(0));
	}
}