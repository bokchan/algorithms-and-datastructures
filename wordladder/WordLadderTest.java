package wordladder;


import it.uniroma3.mat.extendedset.intset.ConciseSet;

import java.io.IOException;
import java.util.BitSet;

import javaewah.EWAHCompressedBitmap;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.stdlib.StdRandom;

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
	public void TestMeanDFSTime() throws IOException {
		String dir = System.getProperty("user.dir");

		System.out.println("Building Digraph");
		Stopwatch w = new Stopwatch();
		DiGraph<String> dg = null;
		for (int i = 0; i < 5; i++) {
			dg = new DiGraph<String>(4);
			dg.buildGraph(dir + "\\trunk\\wordladder\\ressources\\words-15046-data.txt");
		} 
		System.out.println("Mean buildtime DFS: " + w.elapsedTime()/5);
		System.out.println("Vertices: " + dg.V());
		System.out.println("Edges: " + dg.E());
		System.out.println("Calls during build: " + dg.count);
	}

	@Test 
	public void TestMeanIterTime() throws IOException {
		int iterations = 2;
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
		DiGraph<String> dg = new DiGraph<String>();
		dg.buildGraph(dir + "\\trunk\\wordladder\\ressources\\words-5757-data.txt");
		
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
	public void TestBitSets() {
		System.out.println("ewah: ");
		EWAHCompressedBitmap ewahBitmap1 = new EWAHCompressedBitmap();
		EWAHCompressedBitmap ewahBitmap2 = new EWAHCompressedBitmap();
		
		for (int i = 0; i<80000;i++) {
			ewahBitmap1.set(StdRandom.uniform(1000000));
			ewahBitmap2.set(StdRandom.uniform(1000000));
		}		
		
		
		EWAHCompressedBitmap xorbitmap = ewahBitmap1.xor(ewahBitmap2);
		System.out.println("ewah: "  + xorbitmap.getPositions());
		
		ConciseSet cs1 = new ConciseSet();
		cs1.add(3);
		ConciseSet cs2 = new ConciseSet();
		cs2.add(5);
		System.out.println(cs1.intersection(cs2).size()> 0);
	}
}