/**
 * 
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 
 * @author Andreas
 * 
 */
public class DiGraph<K> implements IDiGraph<K> {
	private List<IVertex<K>> vertices; // Map holding vertices
	private Map<K,Integer> vIndex; //
	private BitSet bIntersection;
	private List<IVertex<K>> intersection;
	private final char[] CharacterSet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}; 

	private static long addedBulk = 0L;
	private static long addedIter = 0L;  
	// Aux 
	private HashMap<Character, BitSet> CIndex;
	private int E = 0; // Number of edges 
	private int V = 0; // Number of vertices

	private int subkeysize = 4;
	public DiGraph() {
		init();
	}
	public DiGraph(int size) {
		this.subkeysize = size;	
		init();
	}

	private void init() {
		addedBulk = 0;
		addedIter = 0;
		// Initialize variables
		vertices = new ArrayList<IVertex<K>>();
		vIndex = new HashMap<K, Integer>();
		CIndex = new HashMap<Character, BitSet>();
		for (char c : CharacterSet) CIndex.put(c, new BitSet());
		bIntersection = new BitSet();
		intersection = new ArrayList<IVertex<K>>();
	}

	/***
	 * Builds a graph consisting of the elements of type K    
	 * @param col
	 */
	public void buildGraph(List<K> col) {
		for (K k : col) {
			IVertex<K> v = new Vertex<K>(k, subkeysize);
			vertices.add(v);
			vIndex.put(k, V);
			index(v);
			V++;
		}
		addEdges();
		CIndex = null;
		bIntersection = null;
		intersection = null;
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
		bIntersection = null;
		intersection = null;
	}

	private void index(IVertex<K> v) {
		for (Character  c : v.getValueTable().keySet()) {
			//CIndex.get(c).set(V);
			BitSet bs = CIndex.get(c); 
			if (bs == null) {
				CIndex.put(c, new BitSet());
				CIndex.get(c).set(V);
			} else {
				bs.set(V);
			}
		}
	}

	private void addEdges() {
		for (IVertex<K> v : vertices) {
			doIntersection(v.getSuffixTable().keySet());
			intersection.remove(v);
			if (intersection.size()> 0) {
				if (!v.hasDuplicateChars()) {
					addCalls(intersection.size(), true);
					v.addEdge(intersection);
					E+= intersection.size();
				}
				else {  
					for (IVertex<K> i : intersection) {
						if (v.isNeighbour(i)) {
							addCalls(1, false);
							E++;
							v.addEdge(i);
						}
					}
				}
			}
		}
	}

	/***
	 * Returns the set of vertices which contains all characters in a given suffix          
	 * @param v
	 * @return
	 */
	private void doIntersection(Set<Character> suffix) {
		bIntersection.clear();
		intersection.clear();

		Iterator<Character> it = suffix.iterator();
		if (it.hasNext()) bIntersection = (BitSet) CIndex.get(it.next()).clone(); 
		while (it.hasNext()) {
			bIntersection.and(CIndex.get(it.next()));
			if (bIntersection.cardinality() == 0) break;
		}

		// Get vertices from index position in the bitset  
		for (int i = bIntersection.nextSetBit(0); i >= 0; i = bIntersection.nextSetBit(i+1)) {
			intersection.add(vertices.get(i));
		}
	}

	public int V() {
		return V;
	}

	public int E() {
		return E;
	}
	
	private void addCalls(int i, boolean isBulk) {		
		if (isBulk) addedBulk += i;
		else addedIter += i;
	}

	public void addEdge(IVertex<K> v, IVertex<K> w) {
		v.adj().add(w);
	}

	public List<IVertex<K>> adj(IVertex<K> v) {
		return v.adj();
	}

	public boolean hasPath(IVertex<K> v, IVertex<K> w) {
		BFS<K> bfs = new BFS<K>(this, v);
		return bfs.hasPathTo(w);
	}

	public List<IVertex<K>> vertices() {
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
	
	public List<IVertex<K>> getIntersection(Set<Character> suffix, List<IVertex<K>> gIntersection) {
		throw new UnsupportedOperationException(); 
	}

	public List<IVertex<K>> getIntersection(Set<Character> suffix) {
		throw new UnsupportedOperationException(); 
	}
	
	public static long getBulkCount() {
		return addedBulk;
	} 
	
	public static long getIterCount() {
		return addedIter;
	} 
}