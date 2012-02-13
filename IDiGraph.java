
import java.io.IOException;
import java.util.List;
import java.util.Set;

/***
 * Interface for an digraph holding vertices of type IVertex<K>
 * @author Andreas
 *
 */
public interface IDiGraph<K> {
	/***
	 * Builds a directed graph from an input file
	 * @param filename 
	 * @throws IOException
	 */
	void 
	buildGraph(String filename) throws IOException;
	
	/***
	 * Returns number of vertices 
	 * @return
	 */
	int V();
	/***
	 * Returns number of edges
	 * @return
	 */
	int E();
	/***
	 * Adds an edge
	 * @param v
	 */
	void addEdge(IVertex<K> v, IVertex<K> w);
	/***
	 * Returns adjacency list for a vertex 
	 * @param v
	 * @return
	 */
	List<IVertex<K>> adj(IVertex<K> v);
	
	/***
	 * Returens an iterable collection of vertices  
	 * @return
	 */
	List<IVertex<K>> vertices();
	
	/***
	 * Returns a vertice with value k  
	 * @param k
	 * @return
	 */
	IVertex<K> getByValue(K k);
	
	/***
	 * Returns true if a path exists from v to w
	 * @param v
	 * @param w
	 * @return
	 */
	boolean hasPath(IVertex<K> v,IVertex<K> w);
	List<IVertex<K>> getIntersection(Set<Character> suffix, List<IVertex<K>> gIntersection);	
}