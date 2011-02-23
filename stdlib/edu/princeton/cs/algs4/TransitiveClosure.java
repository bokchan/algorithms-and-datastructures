package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.StdOut;

/*************************************************************************
 * Compilation: javac TransitiveClosure.java Execution: java TransitiveClosure V
 * E Dependencies: Digraph.java DepthFirstDirectedPaths.java
 * 
 * Compute transitive closure of a digraph and support reachability queries.
 * 
 * Preprocessing time: O(V(E + V)) time. Query time: O(1). Space: O(V^2).
 * 
 * % java TransitiveClosure 10 20 V = 10 E = 20 0: 9 2 7 5 1: 6 7 2: 3: 9 9 9 7
 * 6 4: 7 5: 7 6: 7 1 7: 2 8: 7 6 0 9: 2
 * 
 * Transitive closure ----------------------------------- 0 1 2 3 4 5 6 7 8 9 0:
 * x x x x x 1: x x x x 2: x 3: x x x x x x 4: x x x 5: x x x 6: x x x x 7: x x
 * 8: x x x x x x x x 9: x x
 * 
 *************************************************************************/

public class TransitiveClosure {
	private DirectedDFS[] tc; // tc[v] = reachable from v

	public TransitiveClosure(Digraph G) {
		tc = new DirectedDFS[G.V()];
		for (int v = 0; v < G.V(); v++)
			tc[v] = new DirectedDFS(G, v);
	}

	public boolean reachable(int v, int w) {
		return tc[v].marked(w);
	}

	// test client
	public static void main(String[] args) {
		int V = Integer.parseInt(args[0]);
		int E = Integer.parseInt(args[1]);
		Digraph G = new Digraph(V, E);
		StdOut.println(G);
		TransitiveClosure tc = new TransitiveClosure(G);

		// print header
		StdOut.println("Transitive closure");
		StdOut.println("-----------------------------------");
		StdOut.print("     ");
		for (int v = 0; v < G.V(); v++)
			StdOut.printf("%3d", v);
		StdOut.println();

		// print transitive closure
		for (int v = 0; v < G.V(); v++) {
			StdOut.printf("%3d: ", v);
			for (int w = 0; w < G.V(); w++) {
				if (tc.reachable(v, w))
					StdOut.printf("  x");
				else
					StdOut.printf("   ");
			}
			StdOut.println();
		}
	}

}
