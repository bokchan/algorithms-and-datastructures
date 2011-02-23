package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.In;
import edu.princeton.cs.stdlib.StdOut;

/******************************************************************************
 * Compilation: javac PrimMST.java Execution: java PrimMST V E Dependencies:
 * EdgeWeightedGraph.java Edge.java Queue.java IndexMinPQ.java UF.java
 * 
 * Prim's algorithm to compute a minimum spanning forest.
 * 
 ******************************************************************************/

public class PrimMST {
	private Edge[] edgeTo; // edgeTo[v] = shortest edge from tree vertex to
							// non-tree vertex
	private double[] distTo; // distTo[v] = weight of shortest such edge
	private boolean[] marked; // marked[v] = true if v on tree, false otherwise
	private IndexMinPQ<Double> pq;

	public PrimMST(EdgeWeightedGraph G) {
		edgeTo = new Edge[G.V()];
		distTo = new double[G.V()];
		marked = new boolean[G.V()];
		pq = new IndexMinPQ<Double>(G.V());
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;

		for (int v = 0; v < G.V(); v++)
			// run from each vertex to find
			if (!marked[v])
				prim(G, v); // minimum spanning forest

		// check optimality conditions
		assert check(G);
	}

	// run Prim's algorithm in graph G, starting from vertex s
	private void prim(EdgeWeightedGraph G, int s) {
		distTo[s] = 0.0;
		pq.insert(s, distTo[s]);
		while (!pq.isEmpty()) {
			int v = pq.delMin();
			scan(G, v);
		}
	}

	// scan vertex v
	private void scan(EdgeWeightedGraph G, int v) {
		marked[v] = true;
		for (Edge e : G.adj(v)) {
			int w = e.other(v);
			if (marked[w])
				continue; // v-w is obsolete edge
			if (e.weight() < distTo[w]) {
				distTo[w] = e.weight();
				edgeTo[w] = e;
				if (pq.contains(w))
					pq.change(w, distTo[w]);
				else
					pq.insert(w, distTo[w]);
			}
		}
	}

	// return iterator of edges in MST
	public Iterable<Edge> edges() {
		Bag<Edge> mst = new Bag<Edge>();
		for (int v = 0; v < edgeTo.length; v++) {
			Edge e = edgeTo[v];
			if (e != null) {
				mst.add(e);
			}
		}
		return mst;
	}

	// return weight of MST
	public double weight() {
		double weight = 0.0;
		for (Edge e : edges())
			weight += e.weight();
		return weight;
	}

	// check optimality conditions (takes time proportional to E V lg* V)
	private boolean check(EdgeWeightedGraph G) {

		// check weight
		double weight = 0.0;
		for (Edge e : edges()) {
			weight += e.weight();
		}
		double EPSILON = 1E-12;
		if (Math.abs(weight - weight()) > EPSILON) {
			System.err.printf(
					"Weight of edges does not equal weight(): %f vs. %f\n",
					weight, weight());
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
			for (Edge f : edges()) {
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
		}

		else if (args.length == 1) {
			// read graph from file
			G = new EdgeWeightedGraph(new In(args[0]));
		}

		else {
			// random graph with V vertices and E edges
			int V = Integer.parseInt(args[0]);
			int E = Integer.parseInt(args[1]);
			G = new EdgeWeightedGraph(V, E);
		}

		if (G.V() <= 10)
			StdOut.println(G);

		// compute MST and print it
		PrimMST mst = new PrimMST(G);
		StdOut.println("total weight = " + mst.weight());
		for (Edge e : mst.edges())
			StdOut.println(e);
	}

}
