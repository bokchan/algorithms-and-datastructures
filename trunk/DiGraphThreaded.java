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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 
 * @author Andreas
 * 
 */
public class DiGraphThreaded<K> implements IDiGraph<K> {
	private List<IVertex<K>> vertices; // Map holding vertices
	private Map<K,Integer> vIndex; //
	private BitSet bIntersection;
	private List<IVertex<K>> intersection;
	
	int count = 0;
	// Aux 
	private HashMap<Character, BitSet> CIndex;
	private int E; // Number of edges 
	private int V; // Number of vertices

	private int subkeysize = 4;
	public DiGraphThreaded() {
		
		init();
		}
	public DiGraphThreaded(int size) {
		this.subkeysize = size;
		init();
	}

	private void init() {
		// Initialize variables
		vertices = new ArrayList<IVertex<K>>();
		vIndex = new HashMap<K, Integer>();
		CIndex = new HashMap<Character, BitSet>(36);
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
		addEdgesThreaded();
		CIndex = null;
		bIntersection = null;
		intersection = null;
	}

	private void index(IVertex<K> v) {
		for (Character  c : v.getValueTable().keySet()) {
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
			if (!v.hasDuplicateChars()) {
				v.adj().addAll(intersection);
				E+= intersection.size();
			}
			else {  
				for (IVertex<K> i : intersection) {
					count++;
					if (v.isNeighbour(i)) {
						E++;
						v.addEdge(i);
					}
				}
			}
		}
	}
	
	private void addEdgesThreaded() {
		
		Thread t1 = new Thread( new AddEdge(0, (int)Math.floor(vertices.size()/2)));
		t1.run();
		Thread t2 = new Thread( new AddEdge((int) Math.ceil(vertices.size()/2),vertices.size() ));
		t2.run();
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
	
	private void getIntersection(Set<Character> suffix, List<IVertex<K>> gIntersection) {
		bIntersection.clear();
		gIntersection.clear();

		Iterator<Character> it = suffix.iterator();
		if (it.hasNext()) bIntersection = (BitSet) CIndex.get(it.next()).clone(); 
		while (it.hasNext()) {
			bIntersection.and(CIndex.get(it.next()));
			if (bIntersection.cardinality() == 0) break;
		}

		// Get vertices from index position in the bitset  
		for (int i = bIntersection.nextSetBit(0); i >= 0; i = bIntersection.nextSetBit(i+1)) {
			gIntersection.add(vertices.get(i));
		}

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

	public List<IVertex<K>> adj(IVertex<K> v) {
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
	
	class AddEdge implements Runnable {
		private List<IVertex<K>> gIntersection;
		int start;
		int end;
		
		public AddEdge(int start, int end) {
			gIntersection = new ArrayList<IVertex<K>>();
			this.start = start;
			this.end = end;
		}
		public void run() {
			addEdges();
		}
		
		private void addEdges() {
			for (IVertex<K> v : vertices.subList(start, end)) {
				getIntersection(v.getSuffixTable().keySet(), gIntersection);
				gIntersection.remove(v);
				if (!v.hasDuplicateChars()) {
					v.adj().addAll(gIntersection);
					E+= gIntersection.size();
					
				}
				else {  
					for (IVertex<K> i : gIntersection) {
						count++;
						if (v.isNeighbour(i)) {
							E++;
							v.addEdge(i);
						}
					}
				}
			}
		}
	}
}