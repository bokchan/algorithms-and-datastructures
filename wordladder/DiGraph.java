/**
 * 
 */
package wordladder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.stdlib.Stopwatch;

/**
 * 
 * @author Andreas
 * 
 */
public class DiGraph<K> implements IDiGraph<K> {
	private Map<K, IVertex<K>> vertices; // Map holding vertices
	int count = 0;
	
	private Set<K> marked; 
	
	//An index for the sorted value of all vertices.           
	private Map<Character, HashSet<IVertex<K>>> Index;
	
	 //An index for vertices and the first character of their sorted suffix
	private Map<IVertex<K>, Character> Suffixes;
	
	private int E; // Number of edges 
	private int V; // Number of vertices

	public void buildGraph(String filename) throws IOException {
		// Initialize variables 
		vertices = new HashMap<K, IVertex<K>>();
		marked = new HashSet<K>();
		Suffixes = new HashMap<IVertex<K>, Character>();
		Index = new HashMap<Character, HashSet<IVertex<K>>>();
		
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
		Stopwatch w = new Stopwatch();

		// Read all lines in the input file 
		while(true) {
			String s = r.readLine();
			if (s == null) break;
			s = s.toLowerCase(); 
			Vertex<K> v = new Vertex<K>((K) s); // Create a new vertex 
			vertices.put((K)s,v);
			
			Suffixes.put(v, v.getSuffixArray()[0]); // Add to suffix array 
			
			Character first = v.getValueArray()[0]; // Get the first character of the sorted value of the vertice  
			Character second  = v.getValueArray()[1]; // Get the second character of the sorted value of the vertice
			
			// Add vertice to index 
			if (Index.get(first) == null) Index.put(first, new HashSet<IVertex<K>>());
			Index.get(first).add(v);
			if (Index.get(second) == null) Index.put(second, new HashSet<IVertex<K>>());
			Index.get(second).add(v);
			V++;
		}

		System.out.println("Read lines in: " + w.elapsedTime());
		w = new Stopwatch();

		//Do a DFS searh to find all connected edges 
		for (IVertex<K> v : vertices.values()) {
			if (!marked.contains(v.getValue())) {
				count++;
				dfs(v);
			}
		}
		System.out.println("Built edges in: " + w.elapsedTime());
	}
	/***
	 * Builds graph using dfs
	 * @param v
	 */
	public void dfs(IVertex<K> v) {
		marked.add(v.getValue()); 
		for (IVertex<K> w : getUnion(v)) {
			count++;
			if (!v.equals(w) && !v.adj().contains(w) && v.isNeighbour(w)) {
				E++;
				v.addEdge(w);
				if (!marked.contains(w.getValue())) dfs(w);
			}
		}
	} 

	/***
	 * Returns the set of vertices where first or second 
	 * character of its sorted value matches the first character of the suffix        
	 * @param v
	 * @return
	 */
	private Set<IVertex<K>> getUnion(IVertex<K> v) {
		return Index.get(Suffixes.get(v));
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
		
	public Map<Character, HashSet<IVertex<K>>> getIndex(){
		return this.Index;
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