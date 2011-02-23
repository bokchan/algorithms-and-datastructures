package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.StdIn;
import edu.princeton.cs.stdlib.StdOut;

/*************************************************************************
 * Compilation: javac GREP.java Execution: java GREP regexp < input.txt
 * Dependencies: NFA.java
 * 
 * This program takes an RE as a command-line argument and prints the lines from
 * standard input having some substring that is in the language described by the
 * RE.
 * 
 *************************************************************************/

public class GREP {
	public static void main(String[] args) {
		String regexp = "(.*" + args[0] + ".*)";
		NFA nfa = new NFA(regexp);
		while (StdIn.hasNextLine()) {
			String txt = StdIn.readLine();
			if (nfa.recognizes(txt)) {
				StdOut.println(txt);
			}
		}
	}
}
