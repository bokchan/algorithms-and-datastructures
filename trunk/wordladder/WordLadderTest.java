package wordladder;

import java.io.IOException;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
public class WordLadderTest {
	
	@Test
	public void TestCompare() {
		Vertex<String> v1 = new Vertex<String>("there");
		Vertex<String> v2 = new Vertex<String>("there");
		Integer i = 1;
		Integer j = 2;
		System.out.println(v1.compareTo(v2));
		System.out.println(i.compareTo(j));
	}
	
	@Test
	public void TestBuildGraph() throws IOException {
		String dir = System.getProperty("user.dir");

		Stopwatch w = new Stopwatch();
		System.out.println("Building Digraph");
		DiGraph<String> dg = new DiGraph<String>();
		dg.buildGraph(dir + "\\trunk\\wordladder\\ressources\\words-5757-data.txt");
		System.out.println("Buildtime: " + w.elapsedTime());
		System.out.println("Vertices: " + dg.V());
		int E= 0;

		for (IVertex<String> v :  dg.vertices()) {
			E += v.adj().size();
		}
		System.out.println("Edges: " + E);
		System.out.println("Calls during build: " + dg.count);
		System.out.println("Index");
//		for (Entry<Character, ArrayList<IVertex<String>>> e :  dg.getIndex().entrySet()) {
//			System.out.println(e.getKey() + ": " + e.getValue().size());
//			System.out.println(e.getKey() + ": " + Arrays.toString(e.getValue().toArray()));
//		}
//		System.out.println("_Index");
//		for (Entry<Character, HashSet<IVertex<String>>> e :  dg._Index.entrySet()) {
//			System.out.println(e.getKey() + ": " + e.getValue().size());
//			System.out.println(e.getKey() + ": " + Arrays.toString(e.getValue().toArray()));
//		}
//		for (Entry<String, HashSet<IVertex<String>>> e :  dg.SuffixIndices.entrySet()) {
//			System.out.println(e.getKey() + ": " + Arrays.toString(e.getValue().toArray()));
//		}
//		
//		for (IVertex<String> e :  dg.vertices()) {
//			System.out.println(e.getValue() + ": " + Arrays.toString(e.adj().toArray()));
//		}
				
		IVertex<String> v1 = dg.getByValue("other");
		System.out.println("V1: " + v1.getValue() + ","+ v1.adj().size());
		IVertex<String> v2 = dg.getByValue("there");
		System.out.println("V2: " + v2.getValue() + ","+ v2.adj().size());
		System.out.printf("BFS starting from '%s'", v1.getValue());
		w = new Stopwatch();
		BFS<String> bfs = new BFS<String>(dg, v1);
		System.out.println("..." + w.elapsedTime());
		Assert.assertTrue(bfs.hasPathTo(v2));

		System.out.printf("Dist from %s to %s : %s\n", v1.getValue(), v2.getValue(), bfs.distTo(v2));
		Iterator<IVertex<String>> i = bfs.pathTo(v2).iterator();
		StringBuilder sb = new StringBuilder(); 
		while(i.hasNext()) sb.append(i.next().getValue()+ "-->");
		int idx = sb.lastIndexOf("-->");
		sb.replace(idx, sb.length(), "");
		System.out.printf("Shortest path from %s to %s : %s\n", v1.getValue(), v2.getValue(), sb.toString());
	}

	//@Test
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
 
	//@Test
	public void TestBigFiles() throws IOException  {
		String dir = System.getProperty("user.dir");
		Stopwatch w = new Stopwatch();
			System.out.print("Building Digraph");
			DiGraph<String> dg = new DiGraph<String>();
			dg.buildGraph(dir + "\\trunk\\wordladder\\ressources\\" + "alpha_" + 20000 + "_output.txt");
			System.out.println("..." + w.elapsedTime());
			System.out.println("Vertices: " + dg.V());
			System.out.println("Edges: " + dg.E());
			System.out.println("Edges: " );
	}
}