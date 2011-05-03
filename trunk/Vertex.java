import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Vertex<K> implements IVertex<K>{
	private K value;
	
	private HashMap<Character, Integer> valueTable;
	private HashMap<Character, Integer> suffixTable;
	private boolean hasDuplicates= false;
	private int threshold = 0; 

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
		// Create auxillary arrays
		//char[] valueArray = this.value.toString().toCharArray();
		   
		// Init to max size
		valueTable = new HashMap<Character, Integer>(strVal.length());
		suffixTable = new HashMap<Character, Integer>(suffixLen);

		for (Character c : this.value.toString().toCharArray()) {
			
			if (!valueTable.containsKey(c))
				valueTable.put(c, 0);
			Integer c1 = valueTable.get(c);
			c1++;
			valueTable.put(c, c1);
		}
		
		///char[] suffixArray = this.value.toString().substring(valueArray.length-suffixLen).toCharArray();
		for (Character c : strVal.substring(strVal.length()-suffixLen).toCharArray()) {
			if (!suffixTable.containsKey(c))  
				suffixTable.put(c, 1); 
			else { 
				hasDuplicates= true;
				Integer c2 = suffixTable.get(c);
				c2++;
				suffixTable.put(c, c2);
			}
		}
		threshold =  strVal.length()-suffixLen < 0 ? 0 : strVal.length()-suffixLen;
//		valueArray = null;
//		suffixArray = null;
	}  

	public boolean isNeighbour(IVertex<K> v) {
		int similarity = 0;
		HashMap<Character, Integer> vCopy = v.getValueTable();
		int fail = 0;
		for (Entry<Character, Integer> e : suffixTable.entrySet()) 
		{
			if (fail>threshold) break; // Stop the search if number of fails exceeds 1
			if (e.getValue() <= vCopy.get(e.getKey())) {
				similarity+=e.getValue();
			} else fail++;
		}
		vCopy = null;
		return similarity >=suffixLen;
		
	}

	public List<IVertex<K>> adj() {
		return this.adj;
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

	public HashMap<Character, Integer> getValueTable() {
		return this.valueTable;
	}
	public boolean hasDuplicateChars() {
		return hasDuplicates;
	}
	
	public HashMap<Character, Integer> getSuffixTable() {		
		return this.suffixTable;
	}
}