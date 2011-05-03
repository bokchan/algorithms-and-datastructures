import java.util.HashMap;

import wordladder.IVertex;


public interface IVertexOptimized<K> extends IVertex<K> {
	
	public HashMap<Character, Byte> getSuffixTable();
	public HashMap<Character, Byte> getValueTable();
}
	

