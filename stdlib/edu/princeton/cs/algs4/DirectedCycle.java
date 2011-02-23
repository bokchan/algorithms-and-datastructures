package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.StdOut;
import edu.princeton.cs.stdlib.StdRandom;

/*************************************************************************
 * Compilation: javac DirectedCycle.java Execution: java DirectedCycle V E F
 * Dependencies: Digraph.java Stack.java
 * 
 * Finds a directed cycle in a digraph. Runs in O(E + V) time.
 * 
 * 
 *************************************************************************/

public class DirectedCycle {
	private boolean[] marked; // marked[v] = has vertex v been marked?
	private int[] edgeTo; // edgeTo[v] = previous vertex on path to v
	private boolean[] onStack; // onStack[v] = is vertex on the stack?
	private Stack<Integer> cycle; // directed cycle (or null if no such cycle)

	public DirectedCycle(Digraph G) {
		marked = new boolean[G.V()];
		onStack = new boolean[G.V()];
		edgeTo = new int[G.V()];
		for (int v = 0; v < G.V(); v++)
			if (!marked[v])
				dfs(G, v);

		// check that digraph has a cycle
		assert check(G);
	}

	// check that algorithm computes either the topological order or finds a
	// directed cycle
	private void dfs(Digraph G, int v) {
		onStack[v] = true;
		marked[v] = true;
		for (int w : G.adj(v)) {

			// short circuit if directed cycle found
			if (cycle != null)
				return;

			// found new vertex, so recur
			else if (!marked[w]) {
				edgeTo[w] = v;
				dfs(G, w);
			}

			// trace back directed cycle
			else if (onStack[w]) {
				cycle = new Stack<Integer>();
				for (int x = v; x != w; x = edgeTo[x]) {
					cycle.push(x);
				}
				cycle.push(w);
				cycle.push(v);
			}
		}

		onStack[v] = false;
	}

	public boolean hasCycle() {
		return cycle != null;
	}

	public Iterable<Integer> cycle() {
		return cycle;
	}

	// certify that digraph is either acyclic or has a directed cycle
	private boolean check(Digraph G) {

		if (hasCycle()) {
			// verify cycle
			int first = -1, last = -1;
			for (int v : cycle()) {
				if (first == -1)
					first = v;
				last = v;
			}
			if (first != last) {
				System.err.printf("cycle begins with %d and ends with %d\n",
						first, last);
				return false;
			}
		}

		return true;
	}

	public static void main(String[] args) {

		// create random DAG with V vertices and E edges; then add F random
		// edges
		int V = Integer.parseInt(args[0]);
		int E = Integer.parseInt(args[1]);
		int F = Integer.parseInt(args[2]);
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
			G.addEdge(v, w);
		}

		// add F extra edges
		for (int i = 0; i < F; i++) {
			int v = (int) (Math.random() * V);
			int w = (int) (Math.random() * V);
			G.addEdge(v, w);
		}

		StdOut.println(G);

		// find a directed cycle
		DirectedCycle finder = new DirectedCycle(G);
		if (finder.hasCycle()) {
			StdOut.print("Cycle: ");
			for (int v : finder.cycle()) {
				StdOut.print(v + " ");
			}
			StdOut.println();
		}

		// or give topologial sort
		else {
			StdOut.println("No cycle");
		}
	}

}
