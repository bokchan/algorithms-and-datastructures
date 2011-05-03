package wordladder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Test;
public class DiGraphTester {
	// testfiles_dynamickeysize
	// testfilesrandom
	// testfiles
	HashMap<String, String[]> testFiles;
	//ArrayList<String> testFiles ;
	String dir = System.getProperty("user.dir");
	String linebreak = System.getProperty("line.separator");
	String path = dir + "\\trunk\\wordladder\\ressources\\";
	StringBuilder result = new StringBuilder();
	int iterations = 10;
	public static final String DATE_FORMAT_NOW = "yyyy_MM_dd-HH_mm_ss";

	@Test
	public void TestDynamicKeySize() throws IOException {
		result = new StringBuilder();
		Stopwatch w;
		for (int i=1; i <10; i++) {
			w = new Stopwatch();
			DiGraph<String> dg = null;
			for (int j = 0; j < iterations; j++) {
				dg = new DiGraph<String>(i);
				dg.buildGraph(path  + "5757_alpha_10_output.txt");
			}
			Object[] result = {"5757_alpha_10_output.txt", "TRUE", 10, i, dg.count, w.elapsedTime()/iterations, dg.V(), dg.E()};
			writeresult(result); // Edges
		}

		for (int i=1; i <20; i++) {
			w = new Stopwatch();
			DiGraph<String> dg = null;
			for (int j = 0; j < iterations; j++) {
				dg = new DiGraph<String>(i);
				dg.buildGraph(path  + "5757_alpha_20_output.txt");
			}
			Object[] result = {"5757_alpha_20_output.txt", "TRUE", 20, i, dg.count, w.elapsedTime()/iterations, dg.V(), dg.E()};
			writeresult(result); // Edges
		}

		FileWriter fw = new FileWriter(path + "testresults_" + now() + ".txt");
		fw.write(result.toString());
		fw.close();
	}
	@Test
	public void testRandomKeyAndInputSize() throws IOException {
		result = new StringBuilder();
		Stopwatch w;
		int count = 2;
		
//		int[] args = {10000,20000,30000,40000,50000,60000,70000,80000,90000, 100000};
//		String orgfilename = "%s_random_alpha_10_output.txt";
//		for (int i = 0; i< args.length; i++) {
//			String filename = String.format(orgfilename, args[i]);
//			w = new Stopwatch();
//			DiGraph<String> dg = null;
//			for (int j = 0; j<iterations; j++) {
//				dg = new DiGraph<String>(5);
//				dg.buildGraph(path  + filename);
//				
//			} 
//			Object[] result = {count, filename, "FALSE", "2-10", "2-5", 10, dg.count, w.elapsedTime()/iterations, dg.V(), dg.E()};
//			writeresult(result); // Edges
//		}
//		
//		writeToFile(result.toString());		
//		result = new StringBuilder();
//		
//		orgfilename = "100000_random_alpha_%s_output.txt";
//		count = 2;
//		for (int i = 10; i<= 20; i++) {
//			String filename = String.format(orgfilename, i);
//			w = new Stopwatch();
//			DiGraph<String> dg = null;
//			for (int j = 0; j<iterations; j++) {
//				dg = new DiGraph<String>(4);
//				dg.buildGraph(path  + filename);
//				
//			} 
//			Object[] result = {count, filename, "FALSE", "2-" + i, "2-5", 10, dg.count, w.elapsedTime()/iterations, dg.V(), dg.E()};
//			writeresult(result); // Edges
//		}
//		writeToFile(result.toString());		
		
		
		iterations = 3;
		//resultsb = new StringBuilder();
		for (int i = 21; i > 2; i--) {
			
			count = 2;
			String filename = "word_list_moby_crossword.txt";
			w = new Stopwatch();
			int E= 0;
			int V= 0;
			int count2 = 0; 
			
			for (int j = 0; j<iterations; j++) {
				DiGraph<String> dg = null;
				dg = new DiGraph<String>(i);
				dg.buildGraph(path  + filename);
				E = dg.E();
				V= dg.V();
				count2 = dg.count;
			} 
			Object[] result = {count, filename, "FALSE", "2-21", "2-" + i, 10, count2, w.elapsedTime()/iterations, V,E};
			System.out.println(Arrays.toString(result));
			//writeresult(result); // Edges
			
			//writeToFile(result.toString());
		}
				
		
	} 
	
	private void writeToFile(String s) throws IOException {
		FileWriter fw = new FileWriter(path + "testresults_" + now() + ".txt");
		fw.write(s);
		fw.close(); 
	}

	@Test
	public void TestDynamicSize() throws IOException  {
		// 
		testFiles = new HashMap<String, String[]>();
		readTestFiles("testfiles.txt");

		System.out.println("Testfiles:");
		for (Entry<String, String[]> file : testFiles.entrySet()) {
			System.out.println(file.getKey() + Arrays.toString(file.getValue()));
		}

		boolean constrain = true;

		for (Entry<String, String[]> file : testFiles.entrySet()) {
			DiGraph<String> dg = null;

			String orgFilename = file.getKey();
			String filename = "";
			Stopwatch w = null;
			if (file.getValue().length>0) {
				for (String param : file.getValue()) {
					//UnConstrained
					filename = String.format(orgFilename, param);
					System.out.println("Testing: " + String.format(filename, param));
					w = new Stopwatch();
					for (int  i = 1; i<= iterations; i++) { 
						dg = new DiGraph<String>();
						dg.buildGraph(path + filename);
					}
					writeresult(orgFilename, false); // Filnemae
					writeresult("TRUE", false); // constrained 
					writeresult("5", false); // keylength 
					writeresult("4", false); // suffixlen
					writeresult("10", false); // iterations
					writeresult(dg.count, false);
					writeresult(w.elapsedTime()/iterations, false); // buildtime 
					writeresult(dg.V(), false); // Vertices 
					writeresult(dg.E(), true); // Edges

				}

			} else {
				System.out.println("Testing: " +orgFilename);
				w = new Stopwatch();
				for (int  i = 1; i<= iterations; i++) { 
					dg = new DiGraph<String>();
					dg.buildGraph(path + orgFilename);
				}

				writeresult(orgFilename, false); // Filnemae
				writeresult("TRUE", false); // constrained 
				writeresult("5", false); // keylength 
				writeresult("4", false); // suffixlen
				writeresult("10", false); // iterations
				writeresult(dg.count, false);
				writeresult(w.elapsedTime()/iterations, false); // buildtime 
				writeresult(dg.V(), false); // Vertices 
				writeresult(dg.E(), true); // Edges
			}
		}

		FileWriter fw = new FileWriter(path + "testresults_" + now() + ".txt");
		fw.write(result.toString());
		fw.close();
	}
	
	@Test 
	public void TestWrite() {
		Object[] f = {1, 3, "ad", now()};
		writeresult(f);
		System.out.println(result.toString());
	}  

	private void writeresult(Object[] s) {
		for (Object o:s ) result.append(String.valueOf(o) + ";");
		result.append(linebreak);
	}

	private void writeresult(Object s, boolean isNew) {
		result.append(s + ";");
		if (isNew) result.append(linebreak);
	} 
	
	@Test
	public void getDataStats() throws IOException, FileNotFoundException {
		String filename = "word_list_moby_crossword.txt";
		int size = 0;
		int maxlength = Integer.MIN_VALUE;
		int minlength = Integer.MAX_VALUE;
		String max = "";
		String min = "";
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(path + filename), "UTF-8"));
		while(true) {
			String s = r.readLine();
			if (s == null) break;
			size++;
			if (s.length() > maxlength) {
				maxlength = s.length();
				max = s;
			}
			
			if (s.length() < minlength) {
				minlength = s.length();
				min = s;
			}
			
		}
		
		System.out.println("File: " + filename);
		System.out.println("Words: " + size);
		System.out.println("Longest word: " + max + " : " + maxlength);
		System.out.println("Shortest word: " + min  + " : " + minlength);
	}
	

	private void readTestFiles(String filename) throws IOException {

		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(path + filename), "UTF-8"));
		// Read all lines in the input file 
		while(true) {
			String s = r.readLine();
			if (s == null) break;
			String[] args = s.split(" ");
			String file =  args[0];
			if (args.length > 1) {
				String[] params = args[1].split(",");
				for (String p : params) {
					testFiles.put(file, params);
				}
			}else {
				testFiles.put(file, new String[0]);
			}
		}		
	}

	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	
	
	 
}
