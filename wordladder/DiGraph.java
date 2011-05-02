package wordladder;
/**
 * 
 */


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author Andreas
 * 
 */
public class DiGraph<K> implements IDiGraph<K> {
	private Map<K, IVertex<K>> vertices; // Map holding vertices
	int count = 0;

	public Set<K> marked; 
	//An index for all vertices over the alphabet 
	HashMap<Character, HashSet<IVertex<K>>> KIndex;
	//An index of the sub key in sorted order:
	HashMap<String, HashSet<IVertex<K>>> SuffixIndex;
	
	private int E; // Number of edges 
	private int V; // Number of vertices

	private final Comparator<HashSet<IVertex<K>>> HashSetComparator = new Comparator<HashSet<IVertex<K>>>() {
		public int compare(HashSet<IVertex<K>> o1, HashSet<IVertex<K>> o2) {
			return Integer.valueOf(o1.size()).compareTo(o2.size());
		}
	};

	public void buildGraph(String filename) throws IOException {
		// Initialize variables
		vertices = new HashMap<K, IVertex<K>>();
		marked = new HashSet<K>();
		KIndex = new HashMap<Character, HashSet<IVertex<K>>>();
		SuffixIndex = new HashMap<String, HashSet<IVertex<K>>>();

		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
		Stopwatch w = new Stopwatch();

		// Read all lines in the input file 
		while(true) {
			String s = r.readLine();
			if (s == null) break;
			s = s.toLowerCase(); 
			Vertex<K> v = new Vertex<K>((K) s, 4); // Create a new vertex 
			vertices.put((K)s,v);

			ArrayList<Character> vArray = v.getValueArray();
			for (Character  c : vArray) {
				if (KIndex.get(c) == null) KIndex.put(c, new HashSet<IVertex<K>>());
				KIndex.get(c).add(v);
			}
			
			if (SuffixIndex.get(String.valueOf(v.getSuffixArray()))== null) 
			SuffixIndex.put(String.valueOf(v.getSuffixArray()), new HashSet<IVertex<K>>());			
			V++;
		}
		
		for (Entry<String,HashSet<IVertex<K>>> e : SuffixIndex.entrySet()) {
			e.getValue().addAll(getIntersection(e.getKey()));
		}

		//System.out.println("Read lines in: " + w.elapsedTime());
		//w = new Stopwatch();

		System.out.println("Indexed suffixes in: " + w.elapsedTime());
		w = new Stopwatch();
		//Do a DFS searh to find all connected edges 
		for (IVertex<K> v : vertices.values()) {
			if (!marked.contains(v.getValue())) {
				count++;
				dfs(v);
			}
		}
		//System.out.println("Built edges in: " + w.elapsedTime());
		KIndex = null;
		marked = null;
	}

	/***
	 * Builds graph using dfs
	 * @param v
	 */
	public void dfs(IVertex<K> v) {
		marked.add(v.getValue()); 
		for (IVertex<K> w : getIntersectedAdj(v)) {
			count++;
			if (v.isNeighbour(w)) {
				E++;
				v.addEdge(w);
				if (!marked.contains(w.getValue())) dfs(w);
			}
		}
	} 
	
	private Set<IVertex<K>> getIntersectedAdj(IVertex<K> v) {
		HashSet<IVertex<K>> adj = new HashSet<IVertex<K>>(SuffixIndex.get(String.valueOf(v.getSuffixArray())));
		adj.remove(v);
		return adj;
	}

	/***
	 * Returns the set of vertices where first or second 
	 * character of its sorted value matches the first character of the suffix        
	 * @param v
	 * @return
	 */
	private Set<IVertex<K>> getIntersection(String suffix) {
		ArrayList<HashSet<IVertex<K>>> sets = new ArrayList<HashSet<IVertex<K>>>();
		
		for (char c : suffix.toCharArray()) {
			sets.add(KIndex.get(c));
		}
		
		Collections.sort(sets, HashSetComparator);
		HashSet<IVertex<K>> intersection = new HashSet<IVertex<K>>(sets.get(0));
		for (int i = 1; i<suffix.length(); i++) {
			intersection.retainAll(sets.get(i));
		}
		
		return intersection;
	}

	public int V() {
		return V;
	}

	public int E() {
		return E;
	}

	public void addEdge(IVertex<K> v, IVertex<K> w) {
		v.adj().add(w);
	}

	public Set<IVertex<K>> adj(IVertex<K> v) {
		return v.adj();
	}

	public boolean hasPath(IVertex<K> v, IVertex<K> w) {
		BFS<K> bfs = new BFS<K>(this, v);
		return bfs.hasPathTo(w);
	}

	public Collection<IVertex<K>> vertices() {
		return vertices.values();
	}	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DiGraph\nVERTICES:").append("\n");
		for (IVertex<K> v : vertices.values()) { 
			builder.append(v.toString()).append("\n");
		}
		builder.append("\nV=")
		.append(V).append("\nE=").append(E).append("");
		return builder.toString();
	}

	public IVertex<K> getByValue(K k) {
		return vertices.get(k);
	}
}