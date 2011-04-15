package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.StdIn;
import edu.princeton.cs.stdlib.StdOut;

/*************************************************************************
 * Compilation: javac DegreesOfSeparation.java Execution: java
 * DegreesOfSeparation filename delimiter Dependencies: SymbolGraph.java
 * Graph.java BreadthFirstPaths.java
 * 
 * % java DegreesOfSeparation routes.txt " " "JFK" java DegreesOfSeparation
 * routes.txt " " JFK Source: JFK LAS JFK ORD DEN LAS DFW JFK ORD DFW EWR Not in
 * database.
 * 
 * % java DegreesOfSeparation movies.txt "/" "Bacon, Kevin" Source: Bacon, Kevin
 * Kidman, Nicole Bacon, Kevin Few Good Men, A (1992) Cruise, Tom Days of
 * Thunder (1990) Kidman, Nicole Grant, Cary Bacon, Kevin Mystic River (2003)
 * Willis, Susan Majestic, The (2001) Landau, Martin North by Northwest (1959)
 * Grant, Cary
 * 
 * % java DegreesOfSeparation movies.txt "/" "Animal House (1978)" Titanic
 * (1997) Animal House (1978) Allen, Karen (I) Raiders of the Lost Ark (1981)
 * Taylor, Rocky (I) Titanic (1997) To Catch a Thief (1955) Animal House (1978)
 * Vernon, John (I) Topaz (1969) Hitchcock, Alfred (I) To Catch a Thief (1955)
 * 
 *************************************************************************/

public class DegreesOfSeparation {
	public static void main(String[] args) {
		String filename = args[0];
		String delimiter = args[1];
		String source = args[2];

		StdOut.println("Source: " + source);

		SymbolGraph sg = new SymbolGraph(filename, delimiter);
		Graph G = sg.G();
		int s = sg.index(source);
		BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

		while (!StdIn.isEmpty()) {
			String sink = StdIn.readLine();
			if (sg.contains(sink)) {
				int t = sg.index(sink);
				if (bfs.hasPathTo(t)) {
					for (int v : bfs.pathTo(t)) {
						StdOut.println("   " + sg.name(v));
					}
				} else {
					StdOut.println("Not connected");
				}
			} else {
				StdOut.println("   Not in database.");
			}
		}
	}
}