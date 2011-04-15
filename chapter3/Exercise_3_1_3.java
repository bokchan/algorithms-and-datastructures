package chapter3;

import java.util.ArrayList;

public class Exercise_3_1_3<Key extends Comparable<Key>,Value> implements STAPI<Key, Value>{
	private Node first = null;
	private int size = 0;

	public Exercise_3_1_3() {
		size = 0;
	}

	private class Node {
		Key key;
		Value val;
		Node next;
		Node previous;

		public Node(Key key, Value val, Node next, Node previous) {
			this.key = key;
			this.val = val;
			this.next = next;
		}
		
	}

	public void put(Key key, Value val) {
		boolean found = false;
		Node n;
		if (first == null) 
		{
			first = new Node(key, val, null, null);
			found = true;

		} else {
			for (Node x = first; x!= null; x=x.next) {
				if (key.equals(x.key)) {
					x.val = val;
					found = true;
				}
			}
		}

		if (!found) {
			Node x = first;
			while (x.key.compareTo(key) > 0 ) {
				if (x.next != null) 
					x = x.next;
				else break;
			}

			n = new Node(key, val, x, x.previous);
			x.previous = n;
			if (x.equals(first)) first = n;
		}

		
	}

	public Value get(Key key) {
		Node n = null;
		for (Node x = first; x != null; x = x.next ) {
			if (x.key.equals(key)) {
				n = x;
				break;
			} 
		}

		return n.val;
	}


	public void Delete(Key key) {
		for (Node x = first; x != null; x = x.next) {
			
			if (key.equals(x.key)) {
				System.out.println(x.toString());
				if (x.previous != null) {

					if (x.next != null)  {
						x.previous.next = x.next;
						x.next.previous = x.previous;
					}
					else {
						
						x.previous.next = null;
					}
				} else {
					if (x.next != null) {
						x.next.previous = null;
						first = x.next;
					}
					else {
						first = null;

					}
				}				
			}
		}
		
	}


	public boolean Contains(Key key) {
		for (Node x = first; x != null; x = x.next) {
			if (key.equals(x.key)) return true;
		}
		return false;
	}


	public boolean isEmpty() {
		return first == null;
	}


	public int size() {
		return size;
	}


	public Iterable<Key> keys() {
		ArrayList<Key> keys = new ArrayList<Key>();
		for (Node x = first; x != null; x = x.next ) 	
			keys.add(x.key);
		return keys;
	}
}
