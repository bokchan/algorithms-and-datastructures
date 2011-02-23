package chapter1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Steque<Item> implements Iterable<Item>, ISteque<Item> {
	private int N;
	private Node first;
	private Node last;

	private class Node {
		private Item item;
		private Node next;
		private Node previous;
	}

	public Steque() {
		N = 0;
	}

	public Item pop() {
		if (isEmpty()) throw new RuntimeException("Stack underflow");
		Item item = getFirst().item;
		Node newFirst = getFirst().next;
		setFirst(newFirst);	
		N--;
		return item;
	}

	public void push(Item item) {
		if (isEmpty()) {
			setFirst(new Node());
			getFirst().item = item;
			getFirst().previous = null;
			getFirst().next = null;
			setLast(getFirst());
			return;
		}
		Node oldFirst = getFirst();
		setFirst(new Node());
		getFirst().item = item;
		getFirst().next = oldFirst;
		getFirst().previous = null;
		oldFirst.previous = getFirst();
		N++;
	}

	public void enqueue(Item item) {
		Node oldLast = getLast();
		setLast(new Node());
		last.item = item;
		last.next = null;
		last.previous = oldLast;
		oldLast.next = getLast();
		N++;
	}

	public Item peek() {
		if(!isEmpty()) throw new RuntimeException("Stack underflow");
		return getFirst().item;
	}

	public boolean isEmpty() {
		return getFirst() == null;
	}

	public int size() {
		return N;
	}

	Node getFirst() {
		return first;
	}

	public Node getLast() {
		return last;
	}

	public void setFirst(Node node) {
		first = node;
	}

	public void setLast(Node node) {
		last = node; 
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Item i : this)
		{
			s.append(i + " ");
		} 
		return s.toString().trim();
	}

	public Iterator iterator() {
		return new LIFOIterator();
	}

	// an iterator, doesn't implement remove() since it's optional
	private class LIFOIterator implements Iterator<Item> {
		private Node current = getFirst();

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			Node n = getFirst();			
			while(n.next.next != null) {
				n = n.next;
			}
			n.next = null;
			N--;
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
}