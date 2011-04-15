package chapter3;

public interface STAPI<Key, Value> {
	
	
	void put(Key key, Value val);
	
	Value get(Key key);
	void Delete(Key key);
	boolean Contains(Key key);
	boolean isEmpty();
	int size();
	Iterable<Key> keys();
}
