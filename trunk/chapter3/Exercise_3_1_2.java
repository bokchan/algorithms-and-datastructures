package chapter3;

import java.util.ArrayList;

public class Exercise_3_1_2<Key, Value> implements STAPI<Key, Value>{
	ArrayList<Key> keys;
	ArrayList<Value> values; 
	
	public Exercise_3_1_2() {
		keys = new ArrayList<Key>();
		values = new ArrayList<Value>();
	}

	public void put(Key key, Value val) {
		if (Contains(key)) values.set(keys.indexOf(key), val);
		else {
			keys.add(key);
			values.add(val);
		}		
	}

	public Value get(Key key) {
		if (Contains(key)) return values.get(keys.indexOf(key));
		return null;
	}

	public void Delete(Key key) {
		
		if (Contains(key)) {
			int i = keys.indexOf(key);
			keys.remove(i);
			values.remove(i);
		}
	}

	public boolean Contains(Key key) {

		for (Key k : keys) {
			if (k.equals(key)) return true;
		}
		return false;
	}
	

	public boolean isEmpty() {
		
		return (keys == null | keys.size() == 0);
	}

	public int size() {
		
		return keys.size();
	}

	public Iterable<Key> keys() {
		return keys;
	} 	
}
