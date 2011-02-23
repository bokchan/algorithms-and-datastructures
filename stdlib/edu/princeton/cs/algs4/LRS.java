package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.StdIn;
import edu.princeton.cs.stdlib.StdOut;

/*************************************************************************
 * Compilation: javac LRS.java Execution: java LRS < file.txt Dependencies:
 * StdIn.java SuffixArray.java
 * 
 * Reads a text string from stdin, replaces all consecutive blocks of whitespace
 * with a single space, and then computes the longest repeated substring in that
 * text using a suffix array.
 * 
 * % java LRS < mobydick.txt ',- Such a funny, sporty, gamy, jesty, joky,
 * hoky-poky lad, is the Ocean, oh! Th'
 * 
 * % java LRS aaaaaaaaa 'aaaaaaaa'
 * 
 * % java LRS abcdefg ''
 * 
 *************************************************************************/

public class LRS {

	public static void main(String[] args) {
		String text = StdIn.readAll().replaceAll("\\s+", " ");
		SuffixArray sa = new SuffixArray(text);

		int N = sa.length();

		String substring = "";
		for (int i = 1; i < N; i++) {
			int length = sa.lcp(i);
			if (length > substring.length())
				substring = sa.select(i).substring(0, length);
		}

		StdOut.println("'" + substring + "'");
	}
}
