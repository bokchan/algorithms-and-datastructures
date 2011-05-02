/**
 * 
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
/**
 * 
 * @author Andreas
 * 
 */
public class DiGraph<K> implements IDiGraph<K> {
	private ArrayList<IVertex<K>> vertices; // Map holding vertices
	private Hashtable<K,Integer> vIndex; // 
	int count = 0;

	// Aux 
	private HashMap<Character, BitSet> CIndex;
	private HashSet<Character> aux;

	private int E; // Number of edges 
	private int V; // Number of vertices

	private int subkeysize = 4;
	public DiGraph() {init();}
	public DiGraph(int size) {
		this.subkeysize = size;
		init();
	}

	private void init() {
		// Initialize variables
		vertices = new ArrayList<IVertex<K>>();
		vIndex = new Hashtable<K, Integer>();
		CIndex = new HashMap<Character, BitSet>();
		aux = new HashSet<Character>();
	}

	/***
	 * Builds a graph consisting of the elements of type K    
	 * @param col
	 */
	public void buildGraph(Collection<? extends K> col) {
		for (K k : col) {
			IVertex<K> v = new Vertex<K>(k, subkeysize);
			vertices.add(v);
			vIndex.put(k, V);
			index(v);
			V++;
		}
		addEdges();
		aux = null;
	}

	public void buildGraph(String filename) throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
		// Read all lines in the input file 
		while(true) {
			String s = r.readLine();
			if (s == null) break;
			s = s.toLowerCase(); 
			IVertex<K> v = new Vertex((K) s, subkeysize); // Create a new vertex 
			vertices.add(v); // Vertex v has Index V 
			vIndex.put(v.getValue(), V);
			index(v);
			V++;
		}
		addEdges();
		CIndex = null;
		aux = null;
	}

	private void index(IVertex<K> v) {
		aux.clear();
		for (Character  c : v.getValueArray()) {
			if (!aux.contains(c)) {
				aux.add(c);
				BitSet bs = CIndex.get(c);  
				if (bs == null) {
					CIndex.put(c, new BitSet());
					CIndex.get(c).set(V);
				} else {
					bs.set(V);
				}
			}
		}
	}

	private void addEdges() {
		for (IVertex<K> v : vertices) {
			for (IVertex<K> i : getIntersectedAdj(v)) {
				count++;
				if (v.isNeighbour(i)) {
					E++;
					v.addEdge(i);
				}
			}
		}
	}

	private Set<IVertex<K>> getIntersectedAdj(IVertex<K> v) {
		HashSet<IVertex<K>> adj = (HashSet<IVertex<K>>) doIntersection(String.valueOf(v.getSuffixArray()));
		adj.remove(v);
		return adj;
	}

	/***
	 * Returns the set of vertices which contains all characters in a given suffix          
	 * @param v
	 * @return
	 */
	private Set<IVertex<K>> doIntersection(String suffix) {		
		BitSet bIntersection = null;
		HashSet<IVertex<K>> intersection = new HashSet<IVertex<K>>();

		char[] tmp = suffix.toCharArray();

		bIntersection = tmp.length>0 ? (BitSet) CIndex.get(tmp[0]).clone() : new BitSet();  
		for (int i = 1; i<tmp.length; i++) {
			bIntersection.and(CIndex.get(tmp[i]));
			//if (bIntersection.cardinality() == 0) break;
		}

		// Get vertices from index position in the bitset  
		for (int i = bIntersection.nextSetBit(0); i >= 0; i = bIntersection.nextSetBit(i+1)) {
			intersection.add(vertices.get(i));
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
		return vertices;
	}	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DiGraph\nVERTICES:").append("\n");
		for (IVertex<K> v : vertices) { 
			builder.append(v.toString()).append("\n");
		}
		builder.append("\nV=")
		.append(V).append("\nE=").append(E).append("");
		return builder.toString();
	}

	public IVertex<K> getByValue(K k) {
		return vertices.get(vIndex.get(k));
	}
}