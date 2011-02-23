package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.StdOut;
import edu.princeton.cs.stdlib.StdRandom;

/*********************************
 * ****************************************
 * Compilation: javac AcyclicSP.java Execution: java AcyclicSP V E Dependencies:
 * EdgeWeightedDigraph.java DirectedEdge.java Topological.java
 * 
 * Computes shortest paths in an edge-weighted acyclic digraph.
 * 
 * Remark: should probably check that graph is a DAG before running
 * 
 *************************************************************************/

public class AcyclicSP {
	private double[] distTo; // distTo[v] = distance of shortest s->v path
	private DirectedEdge[] edgeTo; // edgeTo[v] = last edge on shortest s->v
									// path

	public AcyclicSP(EdgeWeightedDigraph G, int s) {
		distTo = new double[G.V()];
		edgeTo = new DirectedEdge[G.V()];
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[s] = 0.0;

		// visit vertices in toplogical order
		Topological topological = new Topological(G);
		for (int v : topological.order()) {
			for (DirectedEdge e : G.adj(v))
				relax(e);
		}
	}

	// relax edge e
	private void relax(DirectedEdge e) {
		int v = e.from(), w = e.to();
		if (distTo[w] > distTo[v] + e.weight()) {
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;
		}
	}

	// return length of the shortest path from s to v, infinity if no such path
	public double distTo(int v) {
		return distTo[v];
	}

	// is there a path from s to v?
	public boolean hasPathTo(int v) {
		return distTo[v] < Double.POSITIVE_INFINITY;
	}

	// return view of the shortest path from s to v, null if no such path
	public Iterable<DirectedEdge> pathTo(int v) {
		if (!hasPathTo(v))
			return null;
		Stack<DirectedEdge> path = new Stack<DirectedEdge>();
		for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
			path.push(e);
		}
		return path;
	}

	public static void main(String[] args) {

		// create random DAG with V vertices and E edges
		int V = Integer.parseInt(args[0]);
		int E = Integer.parseInt(args[1]);
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);

		// create random permutation
		int[] vertices = new int[V];
		for (int i = 0; i < V; i++) {
			vertices[i] = i;
		}
		StdRandom.shuffle(vertices);

		// add E random edges that respect topological order
		while (G.E() < E) {
			int v, w;
			do {
				v = StdRandom.uniform(V);
				w = StdRandom.uniform(V);
			} while (v >= w);
			double weight = (int) (100 * Math.random()) / 100.0;
			G.addEdge(new DirectedEdge(vertices[v], vertices[w], weight));
		}

		// print graph
		StdOut.println("Graph");
		StdOut.println("--------------");
		StdOut.println(G);

		// find shortest path from s to each other vertex in DAG
		int s = vertices[0];
		AcyclicSP sp = new AcyclicSP(G, s);
		StdOut.println();

		StdOut.println("Shortest paths from " + s);
		StdOut.println("------------------------");
		for (int v = 0; v < G.V(); v++) {
			if (sp.hasPathTo(v)) {
				StdOut.printf("%d to %d (%.2f)  ", s, v, sp.distTo(v));
				for (DirectedEdge e : sp.pathTo(v)) {
					StdOut.print(e + "   ");
				}
				StdOut.println();
			} else {
				StdOut.printf("%d to %d         no path\n", s, v);
			}
		}
	}
}
