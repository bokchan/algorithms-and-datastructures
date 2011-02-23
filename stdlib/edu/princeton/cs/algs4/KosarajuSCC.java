package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.StdOut;

/*************************************************************************
 * Compilation: javac KosarajuSCC.java Dependencies: Digraph.java
 * TransitiveClosure.java StdOut.java
 * 
 * Compute the strongly-connected components of a digraph using Kosaraju's
 * algorithm.
 * 
 * Runs in O(E + V) time.
 * 
 * 
 *************************************************************************/

public class KosarajuSCC {
	private boolean[] marked; // marked[v] = has vertex v been visited?
	private int[] id; // id[v] = id of strong component containing v
	private int count; // number of strongly-connected components

	public KosarajuSCC(Digraph G) {

		// compute reverse postorder of reverse graph
		DepthFirstOrder dfs = new DepthFirstOrder(G.reverse());

		// run DFS on G, using reverse postorder to guide calculation
		marked = new boolean[G.V()];
		id = new int[G.V()];
		for (int v : dfs.reversePostorder()) {
			if (!marked[v]) {
				dfs(G, v);
				count++;
			}
		}

		// check that id[] gives strong components
		assert check(G);
	}

	// DFS on graph G
	private void dfs(Digraph G, int v) {
		marked[v] = true;
		id[v] = count;
		for (int w : G.adj(v)) {
			if (!marked[w])
				dfs(G, w);
		}
	}

	// return the number of strongly connected components
	public int count() {
		return count;
	}

	// are v and w strongly connected?
	public boolean stronglyConnected(int v, int w) {
		return id[v] == id[w];
	}

	// id of strong component containing v
	public int id(int v) {
		return id[v];
	}

	// does the id[] array contain the strongly connected components?
	private boolean check(Digraph G) {
		TransitiveClosure tc = new TransitiveClosure(G);
		for (int v = 0; v < G.V(); v++) {
			for (int w = 0; w < G.V(); w++) {
				if (stronglyConnected(v, w) != (tc.reachable(v, w) && tc
						.reachable(w, v)))
					return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int V = Integer.parseInt(args[0]);
		int E = Integer.parseInt(args[1]);
		Digraph G = new Digraph(V, E);
		StdOut.println(G);
		KosarajuSCC scc = new KosarajuSCC(G);
		StdOut.println("strongly connected component = " + scc.count());
		for (int v = 0; v < G.V(); v++)
			StdOut.println(v + ": " + scc.id(v));
	}

}
