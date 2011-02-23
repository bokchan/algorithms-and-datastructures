package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.StdRandom;

/*************************************************************************
 * Compilation: javac Topoological.java Dependencies: Digraph.java
 * DepthFirstOrder.java DirectedCycle.java EdgeWeightedDigraph.java
 * EdgeWeightedDirectedCycle.java
 * 
 * Compute topological ordering of a DAG. Runs in O(E + V) time.
 * 
 * 
 *************************************************************************/

public class Topological {
	private Iterable<Integer> order; // topological order

	// topological sort in a digraph
	public Topological(Digraph G) {
		DirectedCycle finder = new DirectedCycle(G);
		if (!finder.hasCycle()) {
			DepthFirstOrder dfs = new DepthFirstOrder(G);
			order = dfs.reversePostorder();
		}
	}

	// topological sort in an edge-weighted digraph
	public Topological(EdgeWeightedDigraph G) {
		EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
		if (!finder.hasCycle()) {
			DepthFirstOrder dfs = new DepthFirstOrder(G);
			order = dfs.reversePostorder();
		}
	}

	// return topological order if a DAG; null otherwise
	public Iterable<Integer> order() {
		return order;
	}

	// does digraph have a topological order?
	public boolean hasOrder() {
		return order != null;
	}

	public static void main(String[] args) {

		// create random DAG with V vertices and E edges
		int V = Integer.parseInt(args[0]);
		int E = Integer.parseInt(args[1]);
		Digraph G = new Digraph(V);
		int[] vertices = new int[V];
		for (int i = 0; i < V; i++)
			vertices[i] = i;
		StdRandom.shuffle(vertices);

		for (int i = 0; i < E; i++) {
			int v, w;
			do {
				v = StdRandom.uniform(V);
				w = StdRandom.uniform(V);
			} while (v >= w);
			G.addEdge(vertices[v], vertices[w]);
		}

		System.out.println("Acyclic graph G");
		System.out.println("---------------");
		System.out.println(G);

		System.out.println();
		System.out.println("Vertices in topological order by construction");
		System.out.println("---------------------------------------------");
		for (int i = 0; i < V; i++)
			System.out.print(vertices[i] + " ");
		System.out.println();
		System.out.println();

		// compute its topological order
		// may not match above since there can be many topological orders
		System.out.println("Vertices in topological order by algorithm");
		System.out.println("---------------------------------------------");
		Topological topological = new Topological(G);
		for (int v : topological.order()) {
			System.out.print(v + " ");
		}
		System.out.println();
	}

}
