package wordladder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.stdlib.Stopwatch;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
public class WordLadderTest {

	@Test
	public void TestBuildGraph() throws IOException {
		String dir = System.getProperty("user.dir");

		Stopwatch w = new Stopwatch();
		System.out.println("Building Digraph");
		DiGraph<String> dg = new DiGraph<String>();
		dg.buildGraph(dir + "\\trunk\\wordladder\\ressources\\output_5.txt");
		System.out.println("Buildtime: " + w.elapsedTime());
		System.out.println("Vertices: " + dg.V());
		System.out.println("Edges: " + dg.E());
		System.out.println("Calls during build: " + dg.count);
		for (Entry<Character, HashSet<IVertex<String>>> e :  dg.getIndex().entrySet()) {
			System.out.println(e.getKey() + ": " + e.getValue().size());
		}
				
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
			for (Entry<Character, HashSet<IVertex<String>>> e :  dg.getIndex().entrySet()) {
				System.out.println(e.getKey() + ": " + e.getValue());
			}
		
	}
}