import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Vertex<K> implements IVertex<K>{
	private K value;

	private HashMap<Character, Integer> valueTable;
	private HashMap<Character, Integer> suffixTable;
	private ArrayList<Character> suffixDuplicates;
	private boolean hasDuplicates= false;

	private List<IVertex<K>> adj;
	private int suffixLen = 4;

	public Vertex(K value) {
		create(value);
	}
	public Vertex(K value, int size) {
		this.suffixLen = size;
		create(value);
	}

	private void create(K value) {

		if (value.toString().length()- suffixLen < 0) this.suffixLen = value.toString().length();
		adj = new ArrayList<IVertex<K>>();
		this.value = value;
		String strVal = this.value.toString();

		// Init to max size
		valueTable = new HashMap<Character, Integer>(strVal.length());
		suffixTable = new HashMap<Character, Integer>(suffixLen);
		suffixDuplicates = new ArrayList<Character>();

		for (Character c : this.value.toString().toCharArray()) {
			if (!valueTable.containsKey(c))
				valueTable.put(c, 0);
			Integer c1 = valueTable.get(c);
			c1++;
			valueTable.put(c, c1);
		}

		for (Character c : strVal.substring(strVal.length()-suffixLen).toCharArray()) {
			if (!suffixTable.containsKey(c))  
				suffixTable.put(c, 1); 
			else { 
				hasDuplicates= true;
				suffixDuplicates.add(c);
				Integer c2 = suffixTable.get(c);
				c2++;
				suffixTable.put(c, c2);
			}
		}
	}  

	public synchronized boolean isNeighbour(IVertex<K> v) {		
		for (Character c : suffixDuplicates) {
			if (suffixTable.get(c)> v.getValueTable().get(c))
				return false;
		}
		return true;
	}

	public List<IVertex<K>> adj() {
		return adj;
	}

	public K getValue() {
		return value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vertex\n [key=").append(value).append(", \n Adjacancy list\n[");
		for (IVertex<K> iv :  adj) 
			builder.append(iv.getValue() + ", ");
		builder.replace(builder.length()-1, builder.length(), ""); 
		builder.append("]");
		return builder.toString();
	}

	public void addEdge(IVertex<K> w) {
		if (adj == null ) adj = new ArrayList<IVertex<K>>();
		adj.add(w);
	}

	public int compareTo(IVertex<K> o) {
		return this.getValue().toString().compareTo(o.getValue().toString());
	}

	public HashMap<Character, Integer> getValueTable() {
		return valueTable;
	}
	public boolean hasDuplicateChars() {
		return hasDuplicates;
	}

	public HashMap<Character, Integer> getSuffixTable() {		
		return suffixTable;
	}
	public void addEdge(List<IVertex<K>> w) {
		adj = new ArrayList<IVertex<K>>(w);
	}
}