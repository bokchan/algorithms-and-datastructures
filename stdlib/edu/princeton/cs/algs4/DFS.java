package edu.princeton.cs.algs4;
/*************************************************************************
 * Compilation: javac DFS.java Execution: java DFS V E Dependencies: Bag.java
 * Digraph.java
 * 
 * An extension of the familiar reachability computation in a digraph. In a
 * standard DFS, our interest was in determining the set of vertices reachable
 * from a given vertex; this version computes the set of vertices reachable from
 * a given set of vertices.
 * 
 *************************************************************************/

public class DFS {
	private boolean[] marked;

	public DFS(Digraph G, int s) {
		if (s < 0 || s >= G.V())
			throw new RuntimeException("Vertex out of bounds");
		marked = new boolean[G.V()];
		dfs(G, s);
	}

	public DFS(Digraph G, Iterable<Integer> sources) {
		marked = new boolean[G.V()];
		for (int s : sources)
			if (!marked[s])
				dfs(G, s);
	}

	private void dfs(Digraph G, int v) {
		marked[v] = true;
		for (int w : G.adj(v))
			if (!marked[w])
				dfs(G, w);
	}

	public boolean isReachable(int v) {
		if (v < 0 || v >= marked.length)
			throw new RuntimeException("Vertex out of bounds");
		return marked[v];
	}

	public Iterable<Integer> reachable() {
		Bag<Integer> bag = new Bag<Integer>();
		for (int v = 0; v < marked.length; v++)
			if (marked[v])
				bag.add(v);
		return bag;
	}

}
