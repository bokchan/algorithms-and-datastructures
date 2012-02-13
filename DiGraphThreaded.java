/**
 * 
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kccoder.fracture.Fracture;
import com.kccoder.fracture.IProcessor;
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
	private final char[] CharacterSet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

	private static int addedBulk = 0;
	private static int addedIter = 0;
	// Aux 
	private HashMap<Character, BitSet> CIndex = new HashMap<Character, BitSet>();

	private int E = 0; // Number of edges 
	private int V = 0; // Number of vertices

	private int subkeysize = 4;
	public DiGraphThreaded() {
		init();
	}
	public DiGraphThreaded(int size) {

		this.subkeysize = size;
		init();
	}

	private void init() {
		addedBulk = 0;
		addedIter = 0;
		// Initialize variables
		vertices = new ArrayList<IVertex<K>>();
		vIndex = new HashMap<K, Integer>();
		for (char c : CharacterSet) CIndex.put(c, new BitSet());
		bIntersection = new BitSet();
		intersection = new ArrayList<IVertex<K>>();
	}

	/***
	 * Builds a graph consisting of the elements of type K    
	 * @param col
	 * @ 
	 */
	public void buildGraph(List<K> col)  {
		for (K k : col) {
			IVertex<K> v = new Vertex<K>(k, subkeysize);
			vertices.add(v);
			vIndex.put(k, V);
			index(v);
			V++;
		}

		addEdges();
		//CIndex = null;
		bIntersection = null;
		intersection = null;
		
		cern.colt.bitvector.BitVector qbv = new cern.colt.bitvector.BitVector(10);
		
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
		for (IVertex<K> v : vertices) {
			E += v.adj().size();
		}
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

	private void addEdges()  {
		for (IVertex<K> v : vertices) {
			doIntersection(v.getSuffixTable().keySet());
			intersection.remove(v);
			if (!v.hasDuplicateChars()) {
				addCalls(intersection.size(), true);
				v.adj().addAll(intersection);
				E+= intersection.size();
			}
			else {  
				for (IVertex<K> i : intersection) {
					addCalls(1, false);
					if (v.isNeighbour(i)) {
						E++;
						v.addEdge(i);
					}
				}
			}
		}
	}

	/***
	 * Returns the set of vertices which contains all characters in a given suffix          
	 * @param v
	 * @return
	 * @ 
	 */
	private void doIntersection(Set<Character> suffix)  {
		bIntersection.clear();
		intersection.clear();
		Iterator<Character> it = suffix.iterator();
		if (it.hasNext()) bIntersection = (BitSet) CIndex.get(it.next()).clone(); 
		while (it.hasNext()) {
			bIntersection.and(CIndex.get(it.next()));
			if (bIntersection.cardinality() == 0) break;
		}

		// Get vertices from index position in the BitSet  
		for (int i = bIntersection.nextSetBit(0); i >= 0; i = bIntersection.nextSetBit(i+1)) {
			intersection.add(vertices.get(i));
		}
		
		
	}

	public List<IVertex<K>> getIntersection(Set<Character> suffix, List<IVertex<K>> gIntersection) {
		BitSet _bIntersection = new BitSet();
		
		Iterator<Character> it = suffix.iterator();
		
		if (it.hasNext()) _bIntersection = (BitSet) getBitSet(it.next()).clone();
		
		while (it.hasNext()) {
			_bIntersection.and(getBitSet(it.next()));
			if (_bIntersection.cardinality() == 0) break;
		}  

		for (int i = _bIntersection.nextSetBit(0); i >= 0; i = _bIntersection.nextSetBit(i+1)) {
			gIntersection.add(vertices.get(i));
		}
		
		return gIntersection;
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

	public void addEdge(IVertex<K> v, List<IVertex<K>> w) {
		v.adj().addAll(w);
	}

	public List<IVertex<K>> adj(IVertex<K> v) {
		return v.adj();
	}

	private static void addCalls(int i, boolean isBulk) {
		if (isBulk) addedBulk += i;
		else addedIter += i;
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

	private void addEdgesThreaded() {
		int nThreads = Runtime.getRuntime().availableProcessors();
		int step = 0;
		step =  V() >= 20000 ? 20000 : (int) (V() / (Math.log(V()) / Math.log(nThreads)));
		//step =  20000;

		int start = 0;
		int end = step;
		int length = V();
		ArrayList<AddEdge> tasks = new ArrayList<AddEdge>(); 
		while (end + step < length){ 
			tasks.add(new AddEdge(start, end));
			start += step;
			end += step;
		}

		if (step % length != 0) tasks.add(new AddEdge(start, length));
		Fracture.forEach(tasks, new IProcessor<AddEdge>() {
			public void processElement(AddEdge arg0) {
				arg0.addEdges();
			}
		}); 
		tasks.clear();
		tasks = null;
	}	

	public BitSet getBitSet(char c) {
		return CIndex.get(c);
	}

	public static long getBulkCount() {
		return addedBulk;
	}

	public static long getIterCount() {
		return addedIter;
	} 

	class AddEdge {
		int start;
		int end;

		public AddEdge(int start, int end) {
			this.start = start;
			this.end = end;
		}

		private void addEdges()
		{
			List<IVertex<K>> gIntersection = new ArrayList<IVertex<K>>();
			List<IVertex<K>> _vertices = vertices();
			for (IVertex<K> v : _vertices.subList(start, end)) {
				gIntersection.clear();
				gIntersection = getIntersection(v.getSuffixTable().keySet(), gIntersection);
				gIntersection.remove(v);
				if (gIntersection.size()>0) {
					if (!v.hasDuplicateChars()) {
						addCalls(gIntersection.size(), true);
						v.addEdge(gIntersection);
					}
					else {  
						for (IVertex<K> i : gIntersection) {
							if (v.isNeighbour(i)) {
								addCalls(1, false);
								v.addEdge(i);
							}
						}
					}
				}
			}
		}
	}
}