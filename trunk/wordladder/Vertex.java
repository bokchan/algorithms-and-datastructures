package wordladder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class Vertex<K> implements IVertex<K>{
	private K value;
	private char[] suffixArray;
	private char[] valueArray;
	private HashMap<Character, Integer> valueTable;
	private HashMap<Character, Integer> suffixTable;
	private boolean hasDuplicates= false;
	private int threshold = 0; 

	private Set<IVertex<K>> adj;
	private int subKeySize = 4;

	public Vertex(K value) {	 
		create(value);
	}
	public Vertex(K value, int size) {
		this.subKeySize = size;
		create(value);
	}

	private void create(K value) {
		if (value.toString().length()- subKeySize < 0) this.subKeySize = value.toString().length();
		adj = new HashSet<IVertex<K>>(); // lazy
		this.value = value;
		// Create auxillary arrays
		valueArray = this.value.toString().toCharArray();
		suffixArray = Arrays.copyOfRange(valueArray, valueArray.length-subKeySize, valueArray.length);   
		Arrays.sort(valueArray);
		Arrays.sort(suffixArray);

		valueTable = new HashMap<Character, Integer>();
		suffixTable = new HashMap<Character, Integer>();

		for (Character c : valueArray) {
			if (!valueTable.containsKey(c))
				valueTable.put(c, 0);
			Integer c1 = valueTable.get(c);
			c1++;
			valueTable.put(c, c1);
		}

		for (Character c : suffixArray) {
			if (!suffixTable.containsKey(c))  
				suffixTable.put(c, 1); 
			else { 
				hasDuplicates= true;
				Integer c2 = suffixTable.get(c);
				c2++;
				suffixTable.put(c, c2);
			}
		}
		threshold =  this.valueArray.length-subKeySize < 0 ? 0 : this.valueArray.length-subKeySize;
	}  

	public boolean isNeighbour(IVertex<K> v) {
		int similarity = 0;
		HashMap<Character, Integer> vCopy =  v.getVTable();
		int fail = 0;
		for (Entry<Character, Integer> e : suffixTable.entrySet()) 
		{
			if (fail>threshold) break; // Stop the search if number of fails exceeds 1
			if (e.getValue() <= vCopy.get(e.getKey())) {
				similarity+=e.getValue();
			} else fail++;
		}
		return similarity >=subKeySize;
	}

	public Set<IVertex<K>> adj() {
		return this.adj;
	}

	public char[] getSuffixArray() {
		return this.suffixArray;
	}

	public char[] getValueArray() {
		return this.valueArray;
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

		this.adj.add(w);
	}

	public int compareTo(IVertex<K> o) {
		return this.getValue().toString().compareTo(o.getValue().toString());
	}

	public HashMap<Character, Integer> getVTable() {
		return this.valueTable;
	}
	public boolean hasDuplicateChars() {
		return hasDuplicates;
	}


}