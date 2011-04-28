package wordladder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DiGraphProgram {
	private static String dir = System.getProperty("user.dir");
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {  
			String datafile = dir + "\\trunk\\wordladder\\ressources\\words-5757-data.txt"; //args[0];
			String testfile = dir + "\\trunk\\wordladder\\ressources\\words-5757-test.txt"; //args[1];
			
			DiGraph<String> dg = new DiGraph<String>();
			dg.buildGraph(datafile);
			
			BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(testfile), "UTF-8"));
			BFS<String> bfs;
			while(true) {
				String str = r.readLine();
				if (str == null || str.length() == 0) break;
				String[] sa =  str.split(" ");
				IVertex<String> source = dg.getByValue(sa[0]);
				IVertex<String> target = dg.getByValue(sa[1]);
				bfs  = new BFS<String>(dg, source);
				System.out.printf("Dist from %s to %s : %s\n", source.getValue(), target.getValue(), bfs.distTo(target));
			}
		} else {
			System.out.println(gethelp());
		}
	}
	
	private static String gethelp() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("*****************************************************************\n");
		sb.append("*								*\n");
		sb.append("*	USAGE							*\n");
		sb.append("*	1. Compiling the files 					*\n");
		sb.append("*	2. Run the program: 					*\n");
		sb.append("*	java DiGraphProgram [datafilename] [testfilename]	*\n");
		sb.append("*								*\n");
		sb.append("*****************************************************************\n");
		return sb.toString();
	} 
}