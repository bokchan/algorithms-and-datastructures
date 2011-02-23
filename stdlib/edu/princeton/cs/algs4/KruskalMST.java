package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.In;
import edu.princeton.cs.stdlib.StdOut;

/*************************************************************************
 * Compilation: javac KruskalMST.java Execution: java KruskalMST V E
 * Dependencies: EdgeWeightedGraph.java Edge.java Queue.java UF.java
 * 
 * Kruskal's algorithm to compute a minimum spanning forest.
 * 
 * % java KruskalMST < graph8.txt
 * 
 *************************************************************************/

public class KruskalMST {
	private double weight; // weight of MST
	private Queue<Edge> mst = new Queue<Edge>(); // edges in MST

	// Kruskal's algorithm
	public KruskalMST(EdgeWeightedGraph G) {
		// more efficient to build heap by passing array of edges
		MinPQ<Edge> pq = new MinPQ<Edge>();
		for (Edge e : G.edges()) {
			pq.insert(e);
		}

		// run greedy algorithm
		UF uf = new UF(G.V());
		while (!pq.isEmpty() && mst.size() < G.V() - 1) {
			Edge e = pq.delMin();
			int v = e.either();
			int w = e.other(v);
			if (!uf.connected(v, w)) { // v-w does not create a cycle
				uf.union(v, w); // merge v and w components
				mst.enqueue(e); // add edge e to mst
				weight += e.weight();
			}
		}

		// check optimality conditions
		assert check(G);
	}

	// edges in minimum spanning forest as an Iterable
	public Iterable<Edge> edges() {
		return mst;
	}

	// weight of minimum spanning forest
	public double weight() {
		return weight;
	}

	// check optimality conditions (takes time proportional to E V lg* V)
	private boolean check(EdgeWeightedGraph G) {

		// check total weight
		double total = 0.0;
		for (Edge e : edges()) {
			total += e.weight();
		}
		double EPSILON = 1E-12;
		if (Math.abs(total - weight()) > EPSILON) {
			System.err.printf(
					"Weight of edges does not equal weight(): %f vs. %f\n",
					total, weight());
			return false;
		}

		// check that it is acyclic
		UF uf = new UF(G.V());
		for (Edge e : edges()) {
			int v = e.either(), w = e.other(v);
			if (uf.connected(v, w)) {
				System.err.println("Not a forest");
				return false;
			}
			uf.union(v, w);
		}

		// check that it is a spanning forest
		for (Edge e : edges()) {
			int v = e.either(), w = e.other(v);
			if (!uf.connected(v, w)) {
				System.err.println("Not a spanning forest");
				return false;
			}
		}

		// check that it is a minimal spanning forest (cut optimality
		// conditions)
		for (Edge e : edges()) {
			int v = e.either(), w = e.other(v);

			// all edges in MST except e
			uf = new UF(G.V());
			for (Edge f : mst) {
				int x = f.either(), y = f.other(x);
				if (f != e)
					uf.union(x, y);
			}

			// check that e is min weight edge in crossing cut
			for (Edge f : G.edges()) {
				int x = f.either(), y = f.other(x);
				if (!uf.connected(x, y)) {
					if (f.weight() < e.weight()) {
						System.err.println("Edge " + f
								+ " violates cut optimality conditions");
						return false;
					}
				}
			}

		}

		return true;
	}

	public static void main(String[] args) {
		EdgeWeightedGraph G;

		if (args.length == 0) {
			// read graph from stdin
			G = new EdgeWeightedGraph(new In());
		} else {
			// random graph with V vertices and E edges
			int V = Integer.parseInt(args[0]);
			int E = Integer.parseInt(args[1]);
			G = new EdgeWeightedGraph(V, E);
		}

		// print G
		if (G.V() <= 10)
			StdOut.println(G);

		// compute and print MST
		KruskalMST mst = new KruskalMST(G);
		StdOut.println("total weight = " + mst.weight());
		for (Edge e : mst.edges())
			StdOut.println(e);
	}

}
