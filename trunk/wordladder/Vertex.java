package wordladder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Vertex<K> implements IVertex<K>{

	private K value;
	private char[] suffixArray;
	private char[] valueArray;

	private Set<IVertex<K>> adj;
	private int threshold = 4; 
	public Vertex(K value) {
		adj = new HashSet<IVertex<K>>(); 
		this.value = value;
		// Create auxillary arrays
		suffixArray = this.value.toString().substring(1, 5).toCharArray();
		Arrays.sort(suffixArray);
		valueArray = this.value.toString().toCharArray();
		Arrays.sort(valueArray);
	}

	public boolean isNeighbour(IVertex<K> v) 
	{
		int similarity = 0;

		// Clone of the v's char array value
		ArrayList<Character> vCopy = new ArrayList<Character>();
		for (char c : v.getValueArray()) 
			vCopy.add((Character) c);

		int fail = 0;
		for (int i = 0; i< this.suffixArray.length; i++) 
		{
			if (fail>1) break; // Stop the search if number of fails exceeds 1 
			char key =  this.suffixArray[i]; // get the key 
			// Decide whether to use binary search or 'contains'   
			if (this.valueArray.length>5)  
			{
				int idx = Arrays.binarySearch(vCopy.toArray(), key);
				if (idx>=0) {
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
		return similarity >=threshold;
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
}