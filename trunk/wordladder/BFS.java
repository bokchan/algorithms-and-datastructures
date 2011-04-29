package wordladder;


import java.util.HashMap;
import java.util.Map;
/***
 * Based on Sedgewick and Wayne's Breadth Search First. 
 * Adapted to search in a digraph with generic vertices   
 * @author Andreas
 *
 * @param <K>
 */
public class BFS<K> {

	private static final int INFINITY = Integer.MAX_VALUE;
	private Map<IVertex<K>, Boolean> marked; // marked[v] = is there an s->v path?
	private Map<IVertex<K>, IVertex<K>> edgeTo; // edgeTo[v] = last edge on shortest s->v path
	private Map<IVertex<K>, Integer> distTo; // distTo[v] = length of shortest s->v path
	private final IVertex<K> s; // the source

	public BFS(IDiGraph<K> G, IVertex<K> s) {
		marked = new HashMap<IVertex<K>, Boolean>();
		distTo = new HashMap<IVertex<K>, Integer>();
		edgeTo = new HashMap<IVertex<K>, IVertex<K>>();
		
		for (IVertex<K> v : G.vertices()) {
			marked.put(v, false);
			distTo.put(v, INFINITY);
			edgeTo.put(v, null);
		}
		this.s = s;
		bfs(G, s);
	}

	private void bfs(IDiGraph<K> G, IVertex<K> s) {
		Queue<IVertex<K>> q = new Queue<IVertex<K>>();
		marked.put(s,true);
		distTo.put(s,0);
		q.enqueue(s);
		while (!q.isEmpty()) {
			IVertex<K> v = q.dequeue();
			for (IVertex<K> w : G.adj(v)) {
				if (!marked.get(w)) {
					edgeTo.put(w,v);
					int d = distTo.get(v) + 1;
					distTo.put(w, d);
					marked.put(w,true);
					q.enqueue(w);
				}
			}
		}
	}

	// length of shortest path from s to v
	public int distTo(IVertex<K> v) {
		int dist = distTo.get(v); 
		return dist < INFINITY ? dist : -1; 
	}

	// is there a directed path from s to v?
	public boolean hasPathTo(IVertex<K> v) {
		return marked.get(v);
	}

	// return shortest path from s to v; null if no such path
	public Iterable<IVertex<K>> pathTo(IVertex<K> v) {
		if (!hasPathTo(v))
			return null;
		Stack<IVertex<K>> path = new Stack<IVertex<K>>();
		for (IVertex<K> x = v; x != s; x = edgeTo.get(x)) {
			path.push(x);
		}
		path.push(s);
		return path;
	}
}