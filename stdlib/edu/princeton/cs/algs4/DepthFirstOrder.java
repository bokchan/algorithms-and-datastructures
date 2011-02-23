package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.StdOut;
import edu.princeton.cs.stdlib.StdRandom;

/*************************************************************************
 * Compilation: javac DepthFirstOrder.java Execution: java DepthFirstOrder V E F
 * Dependencies: Digraph.java EdgeWeightedDigraph.java DirectedEdge.java
 * Queue.java Stack.java StdOut.java
 * 
 * Compute preorder and postorderfor a digraph or edge-weighted digraph. Runs in
 * O(E + V) time.
 * 
 * 
 *************************************************************************/

public class DepthFirstOrder {
	private boolean[] marked; // marked[v] = has v been marked in dfs?
	private int[] pre; // pre[v] = preorder number of v
	private int[] post; // post[v] = postorder number of v
	private Queue<Integer> preorder; // vertices in preorder
	private Queue<Integer> postorder; // vertices in postorder
	private int preCounter; // counter or preorder numbering
	private int postCounter; // counter for postorder numbering

	// depth-first search preorder and postorder in a digraph
	public DepthFirstOrder(Digraph G) {
		pre = new int[G.V()];
		post = new int[G.V()];
		postorder = new Queue<Integer>();
		preorder = new Queue<Integer>();
		marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); v++)
			if (!marked[v])
				dfs(G, v);
	}

	// depth-first search preorder and postorder in an edge-weighted digraph
	public DepthFirstOrder(EdgeWeightedDigraph G) {
		pre = new int[G.V()];
		post = new int[G.V()];
		postorder = new Queue<Integer>();
		preorder = new Queue<Integer>();
		marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); v++)
			if (!marked[v])
				dfs(G, v);
	}

	// run DFS in digraph G from vertex v and compute preorder/postorder
	private void dfs(Digraph G, int v) {
		marked[v] = true;
		pre[v] = preCounter++;
		preorder.enqueue(v);
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				dfs(G, w);
			}
		}
		postorder.enqueue(v);
		post[v] = postCounter++;
	}

	// run DFS in edge-weighted digraph G from vertex v and compute
	// preorder/postorder
	private void dfs(EdgeWeightedDigraph G, int v) {
		marked[v] = true;
		pre[v] = preCounter++;
		preorder.enqueue(v);
		for (DirectedEdge e : G.adj(v)) {
			int w = e.to();
			if (!marked[w]) {
				dfs(G, w);
			}
		}
		postorder.enqueue(v);
		post[v] = postCounter++;
	}

	public int pre(int v) {
		return pre[v];
	}

	public int post(int v) {
		return post[v];
	}

	// return vertices in postorder as an Iterable
	public Iterable<Integer> postorder() {
		return postorder;
	}

	// return vertices in postorder as an Iterable
	public Iterable<Integer> preorder() {
		return preorder;
	}

	// return vertices in reverse postorder as an Iterable
	public Iterable<Integer> reversePostorder() {
		Stack<Integer> reverse = new Stack<Integer>();
		for (int v : postorder)
			reverse.push(v);
		return reverse;
	}

	// certify that digraph is either acyclic or has a directed cycle
	private boolean check(Digraph G) {

		// check that postorder() is consistent with rank()
		int r = 0;
		for (int v : postorder()) {
			if (post(v) != r) {
				System.err.println("postorder() and post() inconsistent");
				return false;
			}
			r++;
		}

		// check that preorder() is consistent with pre()
		r = 0;
		for (int v : preorder()) {
			if (pre(v) != r) {
				System.err.println("preorder() and pre() inconsistent");
				return false;
			}
			r++;
		}

		return true;
	}

	public static void main(String[] args) {

		// create random DAG with V vertices and E edges
		int V = Integer.parseInt(args[0]);
		int E = Integer.parseInt(args[1]);
		Digraph G = new Digraph(V);
		for (int i = 0; i < E; i++) {
			int v = StdRandom.uniform(V);
			int w = StdRandom.uniform(V);
			G.addEdge(v, w);
		}

		StdOut.println(G);

		DepthFirstOrder dfs = new DepthFirstOrder(G);
		StdOut.println("   v  pre post");
		StdOut.println("--------------");
		for (int v = 0; v < G.V(); v++) {
			System.out.printf("%4d %4d %4d\n", v, dfs.pre(v), dfs.post(v));
		}

		StdOut.print("Postorder: ");
		for (int v : dfs.postorder()) {
			StdOut.print(v + " ");
		}
		StdOut.println();

		StdOut.print("Preorder:  ");
		for (int v : dfs.preorder()) {
			StdOut.print(v + " ");
		}
		StdOut.println();

	}

}
