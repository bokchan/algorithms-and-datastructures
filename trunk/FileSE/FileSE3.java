package FileSE;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FileSE3 implements SEServer.SearchService {
	private final String LINEBREAK = System.getProperty("line.separator");
	private final String REGEX_SPLIT = "[\\W&&[^'´`]]+";
	private final String EMPTY_SEARCH = "0 lines. Try again"; 

	private HashMap<String, ArrayList<Integer>> words= new HashMap<String, ArrayList<Integer>>();
	private ArrayList<ArrayList<Integer>> searchList = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> result;

	public void indexFile(String fileName) throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
		int line = 0;
		while (true) {
			String s = r.readLine();
			if (s == null) break; // no more lines

			ArrayList<String> tmp = new ArrayList<String>(); 
			for (String t : s.split(REGEX_SPLIT)) {
				String w = t.toLowerCase(); // convert to lower case
				if (!tmp.contains(w)) {
					tmp.add(w);
					
					ArrayList<Integer> al = null;
					if ((al = words.get(w)) == null) {
						words.put(w, new ArrayList<Integer>());
						al = words.get(w);
					}
					al.add(line);
				}
			}
			line++;
		}
	}

	public String search(String queryString) {
		searchList.clear();
		result = new ArrayList<Integer>();

		String retval = "<p><b>%s found on:</b> %s</p>";  

		String[] qStrArray = queryString.toLowerCase().split(REGEX_SPLIT); // Split the querystring 
		
		ArrayList<String> tmp = new ArrayList<String>(); 
		for (String q : qStrArray) {
			if (!tmp.contains(q)) { // Filter out duplicates  
				// Return if one of the words does not exist in the list
				ArrayList<Integer> al = null;
				if ((al = words.get((Object)q)) == null) return String.format(retval, queryString, EMPTY_SEARCH);
				addToSearchList(al); // add the sorted array to the search priority queue
			}
		}
		tmp = null;

		for (int i : searchList.get(0)) result.add(i); // Copy all values from the first item on the searchqueue

		for (int i = 1;i < searchList.size(); i++) {
			Object[] o = result.toArray();
			ArrayList<Integer> a2 = searchList.get(i);
			for (Object oi : o) {
				if (Arrays.binarySearch(a2.toArray(), oi) < 0) {
					result.remove((Object)oi);
				}
			} 
			if (result.isEmpty()) return String.format(retval, queryString, EMPTY_SEARCH); 
		}
		return String.format(retval, queryString, Arrays.toString(getResult()));
	}

	private void addToSearchList(ArrayList<Integer> a2) {
		int i = 0;
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
			sb.append(LINEBREAK);
		}
		return sb.toString();	
	}

	/***
	 * Returns the result
	 * @return
	 */
	public Object[] getResult() {
		return result.toArray();
	}

	/***
	 * Returns a word(s) by index/indices
	 * @param idxs
	 * @return
	 */
	public Object[] getWord(ArrayList<Integer> idxs) {
		Object[] wordsAsArray = words.keySet().toArray();
		ArrayList<String> w = new ArrayList<String>();

		for (int idx : idxs) 
			w.add((String) wordsAsArray[idx]);
		return w.toArray();
	}

	public Object getData() {
		return words;
	}

	public int getIndexFileCount() {
		return words.size();
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Please give a single file name as argument.");
			System.exit(2);
		}
		String fileName = args[0];

		FileSE3 engine = new FileSE3();
		engine.indexFile(fileName);

		SEServer server = new SEServer(8888, engine);
		server.run();
	}
}