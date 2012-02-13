package FileSE;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;


public class FileSE2 implements SEServer.SearchService {
	private static final String br = System.getProperty("line.separator");
	private static final String split = "[^\\w&&[^'´`]]+";

	private HashMap<String, ArrayList<Integer>> words= new HashMap<String, ArrayList<Integer>>();
	private ArrayList<ArrayList<Integer>> searchList = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> result;

	public void indexFile(String fileName) throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
		int line = 0;
		while (true) {
			String s = r.readLine();

			if (s == null) break; // no more lines

			for (String t : s.split(split)) {
				String w = t.toLowerCase(); // convert to lower case  
				if (!words.containsKey(w)) { // 
					words.put(w, new ArrayList<Integer>()); // insert a new key
				}
				words.get(w).add(line); // add line to word
			}
			line++;
		}
	}

	public String search(String queryString) {
		searchList.clear();
		result = new ArrayList<Integer>();
		String retval = "<p><b>%s found on:</b> %s</p>";  

		String[] qStrArray = queryString.toLowerCase().split(split); // Split the querystring 

		for (String q : qStrArray) {
			// Return if one of the words does not exist in the list 
			if (!words.containsKey(q)) return String.format(retval, queryString, "0 lines. Try again");
			ArrayList<Integer> lines = words.get((Object)q); // get array of line numbers
			Collections.sort(lines); // Sort the array
			addToSearchList(lines); // add the sorted array to the search priority queue 
		}
		 
		for (int i : searchList.get(0)) result.add(i); // Copy all values from the item on the searchqueue
		System.out.println(result);

		for (int i = 1;i < searchList.size(); i++) {
			match(searchList.get(i)); 
			if (result.isEmpty()) return String.format(retval, queryString, "0 lines. Try again"); 
		}	

		return String.format(retval, queryString, result.toString());
	}
	
	public String searchBS(String queryString) {
		searchList.clear();
		result = new ArrayList<Integer>();
		String retval = "<p><b>%s found on:</b> %s</p>";  

		String[] qStrArray = queryString.toLowerCase().split(split); // Split the querystring 

		for (String q : qStrArray) {
			// Return if one of the words does not exist in the list 
			if (!words.containsKey(q)) return String.format(retval, queryString, "0 lines. Try again");
			ArrayList<Integer> lines = words.get((Object)q); // get array of line numbers
			Collections.sort(lines); // Sort the array
			addToSearchList(lines); // add the sorted array to the search priority queue 
		}

		for (int i : searchList.get(0)) result.add(i); // Copy all values from the item on the searchqueue
		System.out.println(result);

		for (int i = 1;i < searchList.size(); i++) {
			Object[] o = result.toArray();
			ArrayList<Integer> a2 = searchList.get(i);
			for (Object oi : o) {
				if (Arrays.binarySearch(a2.toArray(), oi) < 0) {
					result.remove((Object)oi);
				}
			} 
			if (result.isEmpty()) return String.format(retval, queryString, "0 lines. Try again"); 
		}	

		return String.format(retval, queryString, result.toString());
	}


	/***
	 * Match the preliminary results against an array from the searchqueue
	 * The same as finding the intersections of all arrays on the searchqeueue
	 * The time complexity O(i*j), where i is the smallest array and j the largest array on the search queue   
	 * @param a2
	 */
	private void match(ArrayList<Integer> a2) {

		ArrayList<Integer> a1 = new ArrayList<Integer>(result); // local copy of the resultlist 
		int a1_idx = 0; 
		int a2_idx = 0;
		while(a1_idx < a1.size() && a2_idx < a2.size())  
		{
			if (a1.get(a1_idx) - a2.get(a2_idx) > 0) {
				a2_idx++;
				if (a2_idx == a2.size()) { 
					for (int i = a1_idx; i < a1.size(); i++) 
						// remove all entries result[i] where i >= a1_idx  
						result.remove(a1.get(i));
				}
			}
			else if (a1.get(a1_idx) - a2.get(a2_idx) == 0) 
			{ 
				a1_idx++;
				a2_idx++;
			} 
			else { 
				// remove from the result list
				result.remove((Object)a1.get(a1_idx));
				a1_idx++;
			}
		}
	}

	private void addToSearchList(ArrayList<Integer> a2) {
		int i= 0; 

		for (ArrayList<Integer> a1: searchList) {
			if (Integer.valueOf(a1.size()).compareTo(a2.size()) > 0) break;
			i++;
		}
		searchList.add(i, a2);
	}

	/***
	 * Prints the contents of the hashmap.   
	 * @return
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String key : words.keySet()) {
			sb.append(key + ":");
			sb.append(Arrays.toString(words.get(key).toArray()));
			sb.append(br);
		}
		return sb.toString();
	}

	public Object[] getResult() {
		Object[] o = new HashSet<Integer>(result).toArray();
		Arrays.sort(o);
		return o;
		
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Please give a single file name as argument.");
			System.exit(2);
		}
		String fileName = args[0];

		FileSE2 engine = new FileSE2();
		engine.indexFile(fileName);

		SEServer server = new SEServer(8888, engine);
		server.run();
	}
}