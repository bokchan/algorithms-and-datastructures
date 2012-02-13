	import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
	
	public class WordladderUnitTest {
		String dir = System.getProperty("user.dir");
		String path = dir + "\\trunk\\wordladder\\ressources\\";
		HashMap<String, Integer[]> testFiles;
		String linebreak = System.getProperty("line.separator");
		int iterations = 5;
		public static final String DATE_FORMAT_NOW = "yyyy_MM_dd-HH_mm_ss";
		StringBuilder result = new StringBuilder();
	
		@Before
		public void setUp() throws Exception {
			// File 
			// <filename, <keylength, suffixmax, suffixmin
			testFiles = new HashMap<String, Integer[]>();
			//		testFiles.put("10000_alpha_5_output.txt", new Integer[]  {5,0});
			//		testFiles.put("10000_alpha_6_output.txt", new Integer[]  {6,0});
			//		testFiles.put("10000_alpha_7_output.txt", new Integer[]  {7,0});
			//		testFiles.put("10000_alpha_8_output.txt", new Integer[]  {8,0});
			//		testFiles.put("10000_alpha_9_output.txt", new Integer[]  {9,0});
			//		testFiles.put("10000_alpha_10_output.txt", new Integer[]  {10,0});
			//		testFiles.put("words-10-data.txt", new Integer[] {4,4});
			//		testFiles.put("words-250-data.txt", new Integer[] {5,4});
			//		testFiles.put("words-5757-data.txt", new Integer[] {4,4});
			//		testFiles.put("words-15046-data.txt", new Integer[] {4,4});
			//		testFiles.put("output_ni2.txt", new Integer[] {4,4});
			//		testFiles.put("output_5.txt", new Integer[] {4,4});
			//		testFiles.put("word_list_moby_crossword.txt",new Integer[] { 21, 6});
			//		testFiles.put("5757_alpha_10_output.txt", new Integer[] {10,1});
			//		testFiles.put("5757_alpha_20_output.txt", new Integer[]{20, 1});
			testFiles.put("alpha_20000_output.txt", new Integer[] {4,4});
			//		testFiles.put("alpha_40000_output.txt", new Integer[] {4,4});
			testFiles.put("alpha_60000_output.txt", new Integer[] {4,4});
			testFiles.put("alpha_80000_output.txt", new Integer[] {4,4});
			testFiles.put("alpha_100000_output.txt", new Integer[] {4,4});
			testFiles.put("200000_alpha_5_output.txt", new Integer[] {4,4});
			// 		testFiles.put("300000_alpha_5_output.txt", new Integer[] {5,4});
			//testFiles.put("400000_alpha_5_output.txt", new Integer[] {4,4});
	
			for (String file : testFiles.keySet()) {
				File f = new File(path + file);
				f = null;
			}
		}
	
		//@Test
		public void TestDiGraph() throws IOException {
	
			FileWriter fw = null;
			try {
				DiGraph<String> dg = null;
				for (Entry<String, Integer[]> file : testFiles.entrySet()) {
					fw = new FileWriter(path + "testresults_digraph_" + now() + ".txt");
	
					for (int suffix = file.getValue()[0]; suffix >file.getValue()[1]; suffix--) {
						Stopwatch w = new Stopwatch();
						for (int i = 0; i < iterations; i++) {
							dg = new DiGraph<String>(suffix);
							dg.buildGraph(path + file.getKey());
						} 
						Object[] result = {
								file.getKey(), file.getValue()[0], 
								suffix, dg.getIterCount(),dg.getBulkCount(), 
								w.elapsedTime()/iterations, dg.V(), dg.E()};
						System.out.println(writeresult(result));
						//fw.write(writeresult(result));
					}
					fw.close();				
				} 
			} catch (Exception e) {
				try {
					fw.write("AN ERROR OCCURED" + e.getMessage());
					fw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	
	
		@Test
		public void TestDiGraphThreaded() throws IOException {
			FileWriter fw = null;
			try {
				DiGraph<String> dg = null;
				for (Entry<String, Integer[]> file : testFiles.entrySet()) {
					fw = new FileWriter(path + "testresults_digraphthreaded_" + now() + ".txt");
					for (int suffix = file.getValue()[0]; suffix >=file.getValue()[1]; suffix--) {
						Stopwatch w = new Stopwatch();
						for (int i = 0; i < iterations; i++) {
							dg = new DiGraph<String>(suffix);
							dg.buildGraph(path + file.getKey());
						} 
						Object[] result = {
								file.getKey(), file.getValue()[0], 
								suffix, dg.getIterCount(),dg.getBulkCount(), 
								w.elapsedTime()/iterations, dg.V(), dg.E()};
						System.out.println(writeresult(result));
						//fw.write(writeresult(result));
	
					}
	
					fw.close();
				}
	
			} catch (Exception e) {
				try {
					fw.write("AN ERROR OCCURED" + e.getMessage());
	
					fw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	
	
		public static String now() {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
			return sdf.format(cal.getTime());
		}
	
		private String writeresult(Object[] s) {
			StringBuilder sbTmp = new StringBuilder();
			for (Object o:s ) sbTmp.append(String.valueOf(o) + ";");
			sbTmp.append(linebreak);
			return sbTmp.toString();
		}
	}