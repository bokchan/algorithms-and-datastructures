import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class WordLadderTest {
	private final BitSet bs1 = new BitSet(1000000);
	private int iterations = 2;
	private String datafile = "500000_alpha_5_output.txt";
	
	
	@Test
	public void Benchmark() throws IOException {
		TestDiGraphThreaded();
		//TestDiGraphAnt();
		TestDiGraphCarrot();
	}

	@Test 
	public void TestDiGraphThreaded() throws IOException {

		int suffix = 4;
		String dir = System.getProperty("user.dir");
		String filename = datafile;
		Stopwatch w = new Stopwatch();	
		double elapsed  = w.elapsedTime();

		System.out.println("Building Threaded");		
		DiGraphThreaded<String> dgtAnt = null;
		for (int i = 0; i < iterations; i++) {
			dgtAnt = new DiGraphThreaded<String>(suffix);

			dgtAnt.buildGraph(dir + "\\trunk\\wordladder\\ressources\\" + filename);
			System.out.println("Iteration : " + (i+1) + ": " + (w.elapsedTime() - elapsed));
			elapsed = w.elapsedTime();
		} 
		System.out.println("Mean buildtime Iter: " + w.elapsedTime()/iterations);
		System.out.println("Vertices: " + dgtAnt.V());
		System.out.println("Edges: " + dgtAnt.E());
		System.out.println("Added bulk: " + dgtAnt.getBulkCount());
		System.out.println("Added iter: " + dgtAnt.getIterCount());

	}

	@Test
	public void TestDiGraphAnt() throws IOException {

		int suffix = 4;
		String dir = System.getProperty("user.dir");
		String filename = datafile;
		Stopwatch w = new Stopwatch();	
		double elapsed  = w.elapsedTime();

		System.out.println("Building Ant ");		
		DiGraphThreadedAnt<String> dgt = null;
		for (int i = 0; i < iterations; i++) {
			dgt = new DiGraphThreadedAnt<String>(suffix);
			dgt.buildGraph(dir + "\\trunk\\wordladder\\ressources\\" + filename);
			System.out.println("Iteration : " + (i+1) + ": " + (w.elapsedTime() - elapsed));
			elapsed = w.elapsedTime();
		} 
		System.out.println("Mean buildtime Iter: " + w.elapsedTime()/iterations);
		System.out.println("Vertices: " + dgt.V());
		System.out.println("Edges: " + dgt.E());
		System.out.println("Added bulk: " + dgt.getBulkCount());
		System.out.println("Added iter: " + dgt.getIterCount());

	}
	
	@Test
	public void TestDiGraphCarrot() throws IOException {
		int suffix = 4;
		String dir = System.getProperty("user.dir");
		String filename = datafile;
		Stopwatch w = new Stopwatch();	
		double elapsed  = w.elapsedTime();

		System.out.println("Building Carrot ");		
		DiGraphThreadedCarrot<String> dgt = null;
		for (int i = 0; i < iterations; i++) {
			dgt= new DiGraphThreadedCarrot<String>(suffix);

			dgt.buildGraph(dir + "\\trunk\\wordladder\\ressources\\" + filename);
			System.out.println("Iteration : " + (i+1) + ": " + (w.elapsedTime() - elapsed));
			elapsed = w.elapsedTime();
		} 
		System.out.println("Mean buildtime Iter: " + w.elapsedTime()/iterations);
		System.out.println("Vertices: " + dgt.V());
		System.out.println("Edges: " + dgt.E());
		System.out.println("Added bulk: " + dgt.getBulkCount());
		System.out.println("Added iter: " + dgt.getIterCount());

	}

	@Test
	public void TestDiGraphProgram() throws IOException {
		HashMap<String, String> cmdargs = new  HashMap<String, String>();
		cmdargs.put("df", "words-15046-data.txt");
		cmdargs.put("tf", "words-5757-test.txt");
		cmdargs.put("mc", "1");

		Stopwatch w = new Stopwatch();
		for (int i = 0; i < 10; i++  ) {
			//DiGraphProgram dgp = new DiGraphProgram(cmdargs);
		}
		System.out.println(w.elapsedTime()/10);
	} 

	//@Test
	public void TestBuildGraph() throws IOException {
		String dir = System.getProperty("user.dir");

		Stopwatch w = new Stopwatch();
		System.out.println("Building Digraph");
		DiGraph<String> dg = new DiGraph<String>(1);
		dg.buildGraph(dir + "\\trunk\\wordladder\\ressources\\5757_alpha_10_output.txt");

		System.out.println("Buildtime: " + w.elapsedTime());
		System.out.println("Vertices: " + dg.V());
		System.out.println("Edges: " + dg.E());
		System.out.println("Added bulk: " + dg.getBulkCount());
		System.out.println("Added iter: " + dg.getIterCount());

		int count = 0;
		for (IVertex<String> iv : dg.vertices()) {
			count += iv.adj().size();
		}
		System.out.println(count);

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
			System.out.println("Calls during build: " + dg.getIterCount());
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
		System.out.println("Calls during build: " + dg.getIterCount());

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
			System.out.println("Calls during build: " + dg.getIterCount());
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
			System.out.println("Calls during build: " + dg.getIterCount());
		}
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
		String string = "abcdefghijkl";
		System.out.println(string.substring(0, 3));
		System.out.println(string.substring(3, 3));		
	}

	@Test 
	public void TestLogicalAnd() {
		BitSet bs1 = new BitSet(1000000);
		BitSet bs2 = new BitSet(4000);
		BitSet bs3 = new BitSet(300);
		BitSet bs4 = new BitSet(100000);

		for (int i = 0; i < 1000; i++) {
			bs1.set((int)StdRandom.uniform(bs1.size()));
			bs2.set((int)StdRandom.uniform(bs2.size()));
			bs3.set((int)StdRandom.uniform(bs3.size()));
			bs4.set((int)StdRandom.uniform(bs4.size()));
		}

		ArrayList<BitSet> bitsets = new ArrayList<BitSet>();

		bitsets.add(bs1);
		bitsets.add(bs4);
		bitsets.add(bs2);
		bitsets.add(bs3);

		for (BitSet bs : bitsets) 
			System.out.print(bs.size() + ";");
		BitSet tmp2 = bitsets.get(0);
		System.out.println(tmp2.equals(bitsets.get(0)));

		Stopwatch w = new Stopwatch();
		for (int i = 0; i < 50000000; i++) {
			BitSet intersection = (BitSet) bitsets.get(0);
			for (int j = 1; j < bitsets.size(); j++) 
				intersection.and(bitsets.get(j)); 
		}
		System.out.println(w.elapsedTime());
		System.out.println(tmp2.equals(bitsets.get(0)));

		bitsets.clear();

		bitsets.add(bs3);
		bitsets.add(bs2);
		bitsets.add(bs4);
		bitsets.add(bs1);

		w = new Stopwatch();

		for (int i = 0; i < 50000000; i++) {
			BitSet tmp = (BitSet) bitsets.get(0);
			BitSet intersection = (BitSet) bitsets.get(0);
			for (int j = 1; j < bitsets.size(); j++) 
				intersection.and(bitsets.get(j));
			bitsets.remove(0);
			bitsets.add(0, tmp);
		}
		System.out.println(w.elapsedTime());
		System.out.println("");
		for (BitSet bs : bitsets) 
			System.out.print(bs.size() + ";");
		//Collections.sort(bitsets, DiGraphThreaded.bsComp);
		System.out.println("");
		for (BitSet bs : bitsets) 
			System.out.print(bs.size() + ";");
	}

	@Test
	public void testClone() {		
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>(20000000);
		int iters = 2;
		System.out.println("iter: " + iters);
		for (ArrayList<Integer> al : list) {
			for (int i = 0; i < list.size();i++) {
				al.add(i);
			}
			list.add(al);
		}
		StringBuilder sb = new StringBuilder();
		Stopwatch w ;
		double elapsed = 0.0; 
		for (int i = 0 ; i<iters; i++) {
			Collections.shuffle(list);
			w = new Stopwatch();
			ArrayList<ArrayList<Integer>> sublist1 = new ArrayList<ArrayList<Integer>>();
			sublist1.addAll(list);
			elapsed += w.elapsedTime();
		}
		sb.append("addAll: " + elapsed+ "\n");

		elapsed = 0.0;

		for (int i = 0 ; i<iters; i++) {
			Collections.shuffle(list);
			w = new Stopwatch();
			ArrayList<ArrayList<Integer>> sublist2 = new ArrayList<ArrayList<Integer>>();
			Collections.copy(sublist2, list);
			elapsed += w.elapsedTime();
		}
		sb.append("copy: " + elapsed+ "\n");

		elapsed = 0.0;

		for (int i = 0 ; i<iters; i++) {
			Collections.shuffle(list);
			w = new Stopwatch();
			ArrayList<ArrayList<Integer>> tmp = new ArrayList<ArrayList<Integer>>();
			for (ArrayList v : list) tmp.add(v);
			ArrayList<ArrayList<Integer>> sublist2 = new ArrayList<ArrayList<Integer>>(tmp);
			elapsed += w.elapsedTime();
		}
		sb.append("tmp new: " + elapsed+ "\n");

		System.out.println(sb.toString());

		//		EWAHCompressedBitmap ewahBitmap1 = new EWAHCompressedBitmap();
		//		EWAHCompressedBitmap ewahBitmap2 = new EWAHCompressedBitmap();
		//		sb.append("ewah: " + elapsed+ "\n");
		//		for (int i = 0; i<80000;i++) {
		//			ewahBitmap1.set(StdRandom.uniform(1000000));
		//			ewahBitmap2.set(StdRandom.uniform(1000000));
		//		}		
		//		EWAHCompressedBitmap xorbitmap = ewahBitmap1.xor(ewahBitmap2);
		sb.append("ewah: " + elapsed+ "\n");
	}
}