package handins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapQueue<K, V> implements Map<K, V> {
	/**
	 * 
	 */
	private HashMap<K,V> map = new HashMap<K, V>();
	private static final long serialVersionUID = 1L;
	private ArrayList<K> queue = new ArrayList<K>();
	public int size() {
		
		return map.size();
	}
	public boolean isEmpty() {
		return map.size() ==0;
	}
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}
	public V get(Object key) {
		
		return map.get(key);
	}
	public V put(K key, V value) {
		queue.add(key);
		return map.put(key, value);
	}
	public V remove(Object key) {
		queue.remove(key);
		return map.remove(key);
	}
	public void putAll(Map<? extends K, ? extends V> m) {
		queue.addAll(m.keySet());
		map.putAll(m);
		
	}
	public void clear() {
		queue.clear();
		map.clear();
		
	}
	public Set<K> keySet() {
		return map.keySet();
		
	}
	public Collection<V> values() {
		return map.values();
	}
	public Set<Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}
}
