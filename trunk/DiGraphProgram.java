

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DiGraphProgram {
	private static String dir = System.getProperty("user.dir");
	private static String fileSep = System.getProperty("file.separator"); 
	/**
	 * @param args
	 * @throws IOException 
	 */
	
	class VertexTest<K> implements Comparable<VertexTest<K>>{
		private IVertex<K> source; 
		private HashMap<IVertex<K>, Integer> targets ;
		
		Comparator<Map.Entry<IVertex<K>, Integer>> resComp = new Comparator<Map.Entry<IVertex<K>,Integer>>() {
			public int compare(Map.Entry<IVertex<K>, Integer> o1,
					Map.Entry<IVertex<K>, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		};
		
		public VertexTest(IVertex<K> s) {
			this.source = s;
			targets = new HashMap<IVertex<K>, Integer>();
		}

		public void addTarget(IVertex<K> t) {
			targets.put(t,-1);
		}

		public int compareTo(VertexTest<K> o) {
			return this.source.compareTo(o.source);
		}
		public void addResult(IVertex<K> v, int dist) {
			targets.put(v, dist);
		} 

		public Collection<IVertex<K>> getTargets() {
			return targets.keySet();
		} 
		
		public List<Entry<IVertex<K>, Integer>> getResults() {
			List<Entry<IVertex<K>, Integer>> list = new ArrayList<Map.Entry<IVertex<K>,Integer>>( targets.entrySet() );
			Collections.sort(list, resComp);
			return list;
		}
	}
	
	 

	Comparator<VertexTest<String>> testComp= new Comparator<VertexTest<String>>() {
		public int compare(VertexTest<String> o1,
				VertexTest<String> o2) {
			return o1.source.compareTo(o2.source);
		}
	};

	public DiGraphProgram(String datafile, String testfile) throws IOException {
		// Init new digraph
		DiGraph<String> dg = new DiGraph<String>();
		dg.buildGraph(datafile);

		HashMap<String, VertexTest<String>> tests = new HashMap<String, VertexTest<String>>();
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(testfile), "UTF-8"));

		// Read test file
		while(true) {
			String str = r.readLine();
			if (str == null || str.length() == 0) break;
			String[] sa =  str.split(" ");

			IVertex<String> source = dg.getByValue(sa[0]);
			IVertex<String> target = dg.getByValue(sa[1]);
			// Add source and target to the collection of tests
			// It ensures that BFS is only performed once for tests with the same source
			if (!tests.containsKey(source.getValue())) {
				tests.put(source.getValue(), new VertexTest<String>(source));
			}
			tests.get(source.getValue()).addTarget(target);
		}

		for (VertexTest<String> test : tests.values()) {
			BFS<String> bfs = new BFS<String>(dg, test.source);
			for (IVertex<String> t : test.getTargets()) {
				test.addResult(t, bfs.distTo(t));
			}
		}

		ArrayList<VertexTest<String>> printlist = new ArrayList<VertexTest<String>>(tests.values());
		Collections.sort(printlist, testComp);
		
		for (VertexTest<String> test : printlist) {
			for (Entry<IVertex<String>, Integer> result: test.getResults()) {
				System.out.printf("Dist from %s to %s : %s\n", test.source.getValue(), result.getKey().getValue(), result.getValue());
			} 
		}
	}

	public static void main(String[] args) throws IOException {
		if (args.length == 2) {  
			String datafile = dir + fileSep + args[0];
			String testfile = dir + fileSep + args[1];
			DiGraphProgram dgp = new DiGraphProgram(datafile, testfile);

		} else {
			System.out.println(gethelp());
		}
	}

	private static String gethelp() {
		StringBuilder sb = new StringBuilder();

		sb.append("********************************************************************\n");
		sb.append("* 								   *\n");
		sb.append("* USAGE								   *\n");
		sb.append("* Source							   *\n");
		sb.append("* javac *.java 							   *\n");
		sb.append("* java DiGraphProgram [datafilename] [testfilename]		   *\n");
		sb.append("* 								   *\n");
		sb.append("* Wordladder.jar						   *\n");
		sb.append("* java -cp Wordladder DiGraphProgram [datafilename] [testfilename] *\n");
		sb.append("*								   *\n");
		sb.append("* DataGen tool 							   *\n");
		sb.append("* java -cp Wordladder DataGen					   *\n");
		sb.append("* 								   *\n");
		sb.append("********************************************************************\n");
		return sb.toString();
	} 
}