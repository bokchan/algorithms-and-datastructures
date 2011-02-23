package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.StdIn;

/*************************************************************************
 * Compilation: javac FrequencyCounter.java Execution: java FrequencyCounter L <
 * input.txt Dependencies: ST.java StdIn.java
 * 
 * Read in a list of words from standard input and print out the most frequently
 * occurring word.
 * 
 * % java FrequencyCounter 1 < tinyTale.txt it 10
 * 
 * % java FrequencyCounter 8 < tale.txt business 122
 * 
 * % java FrequencyCounter 10 < leipzig1M.txt government 24763
 * 
 * Data files ------------------------
 * http://www.cs.princeton.edu/algs4/41elementary/tnyTale.txt
 * http://www.cs.princeton.edu/algs4/41elementary/tale.txt
 * http://www.cs.princeton.edu/algs4/41elementary/tale.txt
 * http://www.cs.princeton.edu/introcs/data/leipzig/leipzig100k.txt
 * http://www.cs.princeton.edu/introcs/data/leipzig/leipzig300k.txt
 * http://www.cs.princeton.edu/introcs/data/leipzig/leipzig1m.txt
 * 
 *************************************************************************/

public class FrequencyCounter {

	public static void main(String[] args) {
		int distinct = 0, words = 0;
		int minlen = Integer.parseInt(args[0]);
		ST<String, Integer> st = new ST<String, Integer>();

		// compute frequency counts
		while (!StdIn.isEmpty()) {
			String key = StdIn.readString();
			if (key.length() < minlen)
				continue;
			words++;
			if (st.contains(key)) {
				st.put(key, st.get(key) + 1);
			} else {
				st.put(key, 1);
				distinct++;
			}
		}

		// find a key with the highest frequency count
		String max = "";
		st.put(max, 0);
		for (String word : st.keys()) {
			if (st.get(word) > st.get(max))
				max = word;
		}

		System.out.println(max + " " + st.get(max));
		System.out.println("distinct = " + distinct);
		System.out.println("words  = " + words);
	}
}
