package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.In;
import edu.princeton.cs.stdlib.StdOut;

/*************************************************************************
 * Compilation: javac LazyPrimMST.java Execution: java LazyPrimMST V E
 * Dependencies: EdgeWeightedGraph.java Edge.java Queue.java MinPQ.java UF.java
 * 
 * Prim's algorithm to compute a minimum spanning forest.
 * 
 *************************************************************************/

public class LazyPrimMST {
	private double weight; // total weight of MST
	private Queue<Edge> mst; // edges in the MST
	private boolean[] marked; // marked[v] = true if v on tree
	private MinPQ<Edge> pq; // edges with one endpoint in tree

	// compute minimum spanning forest of G
	public LazyPrimMST(EdgeWeightedGraph G) {
		mst = new Queue<Edge>();
		pq = new MinPQ<Edge>();
		marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); v++)
			// run Prim from all vertices to
			if (!marked[v])
				prim(G, v); // get a minimum spanning forest

		// check optimality conditions
		assert check(G);
	}

	// run Prim's algorithm
	private void prim(EdgeWeightedGraph G, int s) {
		scan(G, s);
		while (!pq.isEmpty()) { // better to stop when mst has V-1 edges
			Edge e = pq.delMin(); // smallest edge on pq
			int v = e.either(), w = e.other(v); // two endpoints
			assert marked[v] || marked[w];
			if (marked[v] && marked[w])
				continue; // lazy, both v and w already scanned
			mst.enqueue(e); // add e to MST
			weight += e.weight();
			if (!marked[v])
				scan(G, v); // v becomes part of tree
			if (!marked[w])
				scan(G, w); // w becomes part of tree
		}
	}

	// add all edges e incident to v onto pq if the other endpoint has not yet
	// been scanned
	private void scan(EdgeWeightedGraph G, int v) {
		assert !marked[v];
		marked[v] = true;
		for (Edge e : G.adj(v))
			if (!marked[e.other(v)])
				pq.insert(e);
	}

	// return edges in MST as an Iterable
	public Iterable<Edge> edges() {
		return mst;
	}

	// return weight of MST
	public double weight() {
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

		// print graph
		if (G.V() <= 10)
			StdOut.println(G);

		// compute and print MST
		LazyPrimMST mst = new LazyPrimMST(G);
		StdOut.println("total cost = " + mst.weight());
		for (Edge e : mst.edges())
			StdOut.println(e);
	}

}
