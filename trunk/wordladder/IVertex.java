package wordladder;
import java.util.HashMap;
import java.util.Set;

public interface IVertex<K> extends Comparable<IVertex<K>>{
	/***
	 * Returns true if v is adjacent.     
	 * @param v
	 * @return
	 */
	public boolean isNeighbour(IVertex<K> v) ;
	/**
	 * Returns the adjacencylist
	 * @return
	 */
	public Set<IVertex<K>> adj();
	/***
	 * Returns the vertice's value as a sorted char array   
	 * @return
	 */
	public char[] getValueArray();
	/***
	 * Returns the vertice's suffix as a sorted char array
	 * @return
	 */
	public char[] getSuffixArray();
	/***
	 * Returns the value of the vertice
	 * @return
	 */
	public K getValue();
	/***
	 * Adds vertice w the adjacencylist  
	 * @param w
	 */
	public void addEdge(IVertex<K> w) ;
	
	public boolean hasDuplicateChars();
	
	public HashMap<Character, Integer> getVTable();
}
