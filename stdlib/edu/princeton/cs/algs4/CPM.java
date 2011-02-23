package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.StdIn;
import edu.princeton.cs.stdlib.StdOut;

/*************************************************************************
 * Compilation: javac CPM.java Execution: java CPM < input.txt Dependencies:
 * EdgeWeightedDigraph.java AcyclicDigraphLP.java
 * 
 * CPM.
 * 
 * % java CPM < jobs10.txt
 * 
 *************************************************************************/

public class CPM {

	public static void main(String[] args) {

		// number of jobs
		int N = StdIn.readInt();

		// source and sink
		int source = 2 * N;
		int sink = 2 * N + 1;

		// build network
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(2 * N + 2);
		for (int i = 0; i < N; i++) {
			double duration = StdIn.readDouble();
			G.addEdge(new DirectedEdge(source, i, 0.0));
			G.addEdge(new DirectedEdge(i + N, sink, 0.0));
			G.addEdge(new DirectedEdge(i, i + N, duration));

			// precedence constraints
			int M = StdIn.readInt();
			for (int j = 0; j < M; j++) {
				int precedent = StdIn.readInt();
				G.addEdge(new DirectedEdge(N + i, precedent, 0.0));
			}
		}

		// compute longest path
		AcyclicLP lp = new AcyclicLP(G, source);

		// print results
		StdOut.println(" job   start  finish");
		StdOut.println("--------------------");
		for (int i = 0; i < N; i++) {
			StdOut.printf("%4d %7.1f %7.1f\n", i, lp.distTo(i),
					lp.distTo(i + N));
		}
		StdOut.printf("Finish time: %7.1f\n", lp.distTo(sink));
	}

}
