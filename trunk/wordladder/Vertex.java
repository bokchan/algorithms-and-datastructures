package wordladder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Vertex<K> implements IVertex<K>{

	private K value;
	private char[] suffixArray;
	private ArrayList<Character> valueArray;

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
		adj = new HashSet<IVertex<K>>(); 
		this.value = value;
		// Create auxillary arrays
		char[] tmp = this.value.toString().toCharArray(); 
		Arrays.sort(tmp);
		valueArray =  cloneCharArray(tmp);
		
		suffixArray = this.value.toString().substring(valueArray.size()-subKeySize,valueArray.size()).toCharArray();		
		Arrays.sort(suffixArray);
	}  

	public boolean isNeighbour(IVertex<K> v) 
	{
		int similarity = 0;
		// Clone of the v's char array value
		List<Character> vCopy = new ArrayList<Character>(v.getValueArray());
		
		int fail = 0;
		int threshold = this.valueArray.size()-subKeySize;
		for (int i = 0; i< this.suffixArray.length; i++) 
		{
			if (fail>threshold) break; // Stop the search if number of fails exceeds 1 
			char key =  this.suffixArray[i]; // get the key 
			// Decide whether to use binary search or 'contains'   
			if (this.valueArray.size()>5)  
			{
				if (Collections.binarySearch(vCopy, key) >=0) {
					similarity++;
					vCopy.remove((Object)key);
				} else fail++;
			} else	
			{ 
				if (vCopy.contains(key)) 
				{
					similarity++;
					vCopy.remove((Object)key);
				} else fail++;
			}
			
		}
		return similarity >=subKeySize;
	}
	
	public Set<IVertex<K>> adj() {
		return this.adj;
	}

	public char[] getSuffixArray() {
		return this.suffixArray;
	}

	public ArrayList<Character> getValueArray() {
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
	
	private ArrayList<Character> cloneCharArray(char[] ca) {
		ArrayList<Character> obj = new ArrayList<Character>();
		for (int i =0; i<ca.length; i++) {
			obj.add(ca[i]);
		}
		return obj;
	}

	public int compareTo(IVertex<K> o) {
		return this.getValue().toString().compareTo(o.getValue().toString());
	}
}