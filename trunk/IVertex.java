import java.util.HashMap;
import java.util.List;

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
	public List<IVertex<K>> adj();
	/***
	 * Returns the vertice's value as a sorted char array   
	 * @return
	 */
	public K getValue();
	/***
	 * Adds vertice w the adjacencylist  
	 * @param w
	 */
	public void addEdge(IVertex<K> w) ;
	
	public boolean hasDuplicateChars();
	
	public HashMap<Character, Integer> getValueTable();
	public HashMap<Character, Integer> getSuffixTable();
}
