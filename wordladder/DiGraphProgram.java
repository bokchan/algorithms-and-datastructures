package wordladder;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class DiGraphProgram {
	private static String dir = System.getProperty("user.dir");
	private static String fileSep = System.getProperty("file.separator"); 
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if (args.length == 2) {  
			String datafile = dir + fileSep + args[0];
			String testfile = dir + fileSep + args[1];
			
			// Init new digraph
			DiGraph<String> dg = new DiGraph<String>();
			dg.buildGraph(datafile);
			
			SortedMap<IVertex<String>, HashSet<IVertex<String>>> tests = new TreeMap<IVertex<String>, HashSet<IVertex<String>>>();
			
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
				HashSet<IVertex<String>> targets  = null;
				if ((targets = tests.get(source)) == null) {
					tests.put(source, new HashSet<IVertex<String>>());
					targets = tests.get(source);
				}
				targets.add(target);
				
			}
			BFS<String> bfs;
			for (Entry<IVertex<String>,HashSet<IVertex<String>>> e : tests.entrySet()) {
				bfs  = new BFS<String>(dg, e.getKey());
				for (IVertex<String> t : e.getValue())
				System.out.printf("Dist from %s to %s : %s\n", e.getKey().getValue(), t.getValue(), bfs.distTo(t));
			}
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