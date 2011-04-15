package chapter1;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	protected Node first;
	protected Node last;
	int N;

	private class Node {
		Item item;
		Node previous;
		Node next;
	}

	public Deque() {
		first = null;
		last = null;
	}

	public boolean isEmpty() {
		return first == null; 
	}

	public int size() {
		return N;
	}

	/***
	 * Adds to the end of queue 
	 * @param left
	 */
	public void pushLeft(Item left) {
		Node node = new Node();
		node.item = left;
		if (isEmpty()) {
			node.previous = null;
			node.next = null;
			last = node;
			first = node;
		} else {
			node.previous = null;
			node.next = last;
			last.previous = node;
			last = node;
		}
	}

	public void pushRight(Item item) {

		Node node = new Node();
		node.item = item;
		if (!isEmpty()) {
			node.previous = first;
			first.next = node;
			node.next = null;
			first = node;
		}  else {
			node.previous = null;
			node.next = null;
			last = node;
			first = node;
		}
	}

	/***
	 * Pop from the back of the queue
	 * @return
	 */
	public Item popLeft() {
		if (isEmpty()) throw new RuntimeException("Stack underflow");
		Item item = last.item; 
		if (last.equals(first)) {
			last = null;
			first = null;
		}else {

			last = last.next;
			last.previous = null;
		} 
		return item;
	}

	/***
	 * Pop from the front of the queue  
	 * @return
	 */
	public Item popRight() {
		if (isEmpty()) throw new RuntimeException("Stack underflow");
		Item item = first.item;
		if (first.equals(last)) {
			last = null; 
			first = null;
		} else {
			first = first.previous;
			first.next = null; 
		}
		first.next = null;
		return item;
	}

	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new FIFOIterator();  
	}

	private class FIFOIterator implements Iterator<Item>
	{	
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}		
	}

	/**
	 * Return string representation.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Item item : this)
			s.append(item + " ");
		return s.toString().trim();
	}

}


