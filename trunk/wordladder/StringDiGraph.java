package wordladder;
/**
 * 
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
/**
 * 
 * @author Andreas
 * 
 */
public class StringDiGraph<K> {
	private ArrayList<String> vertices; // Map holding vertices
	private Hashtable<String, ArrayList<String>> adjList;
	private Hashtable<String,Integer> vIndex; //
	int count = 0;

	// Aux 
	private HashMap<Character, BitSet> CIndex;
	private HashMap<String, ArrayList<String>> kArray;
	private HashMap<String, ArrayList<HashMap<Character, Integer>>> kTable;
	private HashSet<Character> aux;

	private int E; // Number of edges 
	private int V; // Number of vertices

	private int subkeysize = 4;
	public StringDiGraph() {init();}
	public StringDiGraph(int size) {
		this.subkeysize = size;
		init();
	}

	private void init() {
		// Initialize variables
		vertices = new ArrayList<String>();
		vIndex = new Hashtable<String, Integer>();
		adjList = new Hashtable<String, ArrayList<String>>();
		kArray = new HashMap<String, ArrayList<String>>();
		kTable = new HashMap<String, ArrayList<HashMap<Character,Integer>>>();
		CIndex = new HashMap<Character, BitSet>();
		aux = new HashSet<Character>();
	}

	/***
	 * Builds a graph consisting of the elements of type K    
	 * @param col
	 */
	public void buildGraph(Collection<? extends String> col) {
		for (String k : col) {
			vertices.add(k);
			vIndex.put(k, V);
			index(k);
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
			vertices.add(s); // Vertex v has Index V 
			vIndex.put(s, V);
			index(s);
			
			V++;
		}
		System.out.println("Read all input");
		addEdges();
		CIndex = null;
		aux = null;

	}

	private void index(String v) {
		aux.clear();
		 
		for (Character  c : v.toCharArray()) {
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
		
		// Create auxillary arrays
		char[] valueArray = v.toCharArray();
		char[] suffixArray = new char[subkeysize]; 
		for (int i=0; i<subkeysize; i++) 
		suffixArray[i] = valueArray[valueArray.length-1-i];
		Arrays.sort(valueArray);
		Arrays.sort(suffixArray);
		
		kArray.put(v, new ArrayList<String>());
		kArray.get(v).add(String.valueOf(valueArray));
		kArray.get(v).add(String.valueOf(suffixArray));
		
		HashMap<Character, Integer> valueTable = new HashMap<Character, Integer>();
		HashMap<Character, Integer> suffixTable = new HashMap<Character, Integer>();
		
		for (Character c : valueArray) {
			if (!valueTable.containsKey(c))
				valueTable.put(c, 0);
			Integer c1 = valueTable.get(c);
			c1++;
			valueTable.put(c, c1);
		}
		
		for (Character c : suffixArray) {
			if (!suffixTable.containsKey(c))  
				suffixTable.put(c, 0);
			Integer c2 = suffixTable.get(c);
			c2++;
			suffixTable.put(c, c2);
		}
		ArrayList<HashMap<Character, Integer>> l = new ArrayList<HashMap<Character,Integer>>();
		l.add(valueTable);
		l.add(suffixTable);
		kTable.put(v,  l);
	}

	private void addEdges() {
		for (String v : vertices) {
			for (String w : getIntersectedAdj(v)) {
				count++;
				if (isNeighbour(v, w)) {
					E++;
					if (!adjList.contains(v)) adjList.put(v, new ArrayList<String>());
					adjList.get(v).add(w);
				}
			}
		}
	}

	private Set<String> getIntersectedAdj(String v) {
		HashSet<String> adj = (HashSet<String>) doIntersection(kArray.get(v).get(1));
		adj.remove(v);
		return adj;
	}

	/***
	 * Returns the set of vertices which contains all characters in a given suffix          
	 * @param v
	 * @return
	 */
	private Set<String> doIntersection(String suffix) {		
		BitSet bIntersection = null;
		HashSet<String> intersection = new HashSet<String>();

		char[] tmp = suffix.toCharArray();

		bIntersection = tmp.length>0 ? (BitSet) CIndex.get(tmp[0]).clone() : new BitSet();  
		for (int i = 1; i<tmp.length; i++) {
			bIntersection.and(CIndex.get(tmp[i]));
			if (bIntersection.cardinality() == 0) break;
		}

		// Get vertices from index position in the bitset  
		for (int i = bIntersection.nextSetBit(0); i >= 0; i = bIntersection.nextSetBit(i+1)) {
			intersection.add(vertices.get(i));
		}
		
		// V * 4 + V*V
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

//	public boolean hasPath(String v, String w) {
//		BFS<String> bfs = new BFS<String>(this, v);
//		return bfs.hasPathTo(w);
//	}

	public Collection<String> vertices() {
		return vertices;
	}	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DiGraph\nVERTICES:").append("\n");
		for (String v : vertices) { 
			builder.append(v.toString()).append("\n");
		}
		builder.append("\nV=")
		.append(V).append("\nE=").append(E).append("");
		return builder.toString();
	}

	public String getByValue(String k) {
		return vertices.get(vIndex.get(k));
	}

	private List<Character> clone(char[] src) {
		List<Character> list = new ArrayList<Character>();
		for (Object o: src) {
			list.add((Character) o);
		}
		return list;
	}
	
	public boolean isNeighbour(String v, String w) {
		int similarity = 0;
		HashMap<Character, Integer> vCopy = kTable.get(w).get(0);
		HashMap<Character, Integer> suffix = kTable.get(v).get(1); 
		int fail = 0;
		
		for (Entry<Character, Integer> e : suffix.entrySet()) 
		{
			if (fail>1) break; // Stop the search if number of fails exceeds 1
			if (e.getValue() <= vCopy.get(e.getKey())) {
				similarity+=e.getValue();
			} else fail++;
		}
		return similarity >=4;
	}
}