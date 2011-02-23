package chapter1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<Item> implements Iterable<Item> {
	private int N; // size of the stack
	private Node first; // top of stack

	// helper linked list class
	private class Node {
		private Item item;
		private Node next;
	}

	/**
	 * Create an empty stack.
	 */
	public MyLinkedList() {
		setFirst(null);
		N = 0;
	}
	
	/***
	 * creates a node 
	 */
	public Node createNode(Item item) {
		Node node = new Node(); 
		node.item = item;
		return node;
	}

	/**
	 * Is the stack empty?
	 */
	public boolean isEmpty() {
		return getFirst() == null;
	}

	/**
	 * Return the number of items in the stack.
	 */
	public int size() {
		return N;
	}

	/**
	 * Add the item to the stack.
	 */
	public void push(Item item) {
		Node oldfirst = getFirst();
		setFirst(new Node());
		getFirst().item = item;
		getFirst().next = oldfirst; 
		N++;
	}

	/**
	 * Delete and return the item most recently added to the stack. Throw an
	 * exception if no such item exists because the stack is empty.
	 */
	public Item pop() {
		if (isEmpty())
			throw new RuntimeException("Stack underflow");
		Item item = getFirst().item; // save item to return
		setFirst(getFirst().next); // delete first node
		N--;
		return item; // return the saved item
	}

	/**
	 * Return the item most recently added to the stack. Throw an exception if
	 * no such item exists because the stack is empty.
	 */
	public Item peek() {
		if (isEmpty())
			throw new RuntimeException("Stack underflow");
		return getFirst().item;
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

	/**
	 * Return an iterator to the stack that iterates through the items in LIFO
	 * order.
	 */
	public Iterator<Item> iterator() {
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

	public void setFirst(Node first) {
		this.first = first;
	}

	public Node getFirst() {
		return first;
	}

	public void delete(int k) {
		if (k > size()) throw new NoSuchElementException("Index out of range"); 
		/***
		 * {a,b,c}
		 */
		Node n = getFirst();
		Node prev = n;
		int count = 1; 
		while (count < k) {
			prev = n;
			n = n.next;
			count++;

		}
		// Save old item
		Node old = n;
		// Delete n.next
		if (n!= getFirst()) { 
			// Set n.next to olds.next
			prev.next = old.next;
		} else {
			setFirst(n.next);
		}
		N--;	
	}

	public static boolean find(MyLinkedList list, String key) {
		Iterator i = list.iterator();
		boolean found = false;
		while(i.hasNext()) {
			if (i.next().equals(key)) found = true; 
		}
		return found;
	}

	public void removeAfter(Node n) {
		if (n.next == null | n == null ) return;
		Node current = this.getFirst();
		boolean done = false;
		while(current != null && !done) {
			if (current == n) {
				done = true;
				continue;
			} 
			current = current.next;
		}
		Node next = current.next.next != null ? current.next.next : null;
		current.next = next;
		N--;
	}

	public Node getNode(int k ) {
		if (k > size()) throw new NoSuchElementException(); 
		Node n = getFirst();
		int count = 1; 
		while (count < k) {
			n = n.next;
			count++;
		}

		return n; 

	}

	/***
	 * Inserts a node after another node
	 * Defensive copying, otherwise a node from the same linked list 
	 * will result in a circular linkedlist   
	 * @param first The node to insert after.  
	 * @param second The node to insert
	 */
	public void insertAfter(Node first, Node second) {

		if (first != null && second != null) {
			Node old = first.next != null ? first.next : null;
			Node n = new Node();
			n.item = second.item;
			first.next = n;
			n.next = old;
			N++;

		}
	}

	public void remove(MyLinkedList<String> list, String key) {
		Iterator<String> i = list.iterator();
		Node first = (Node) list.getFirst();

		int idx = 1;
		for (Node n = first; n != null; n = n.next) {
			if (n.item.equals(key)) 
			{
				list.delete(idx);
				idx--;
			}
			idx++;
		} 
	}
	
	public int max(Node first) {
		int max = 0;
		while(first.next != null) {
			max = Math.max(Integer.valueOf(first.item.toString()), max);
			first = first.next;
		}
		return max;		
	}
	
	public int maxRecursive(Node first) {
		if (first == null) return 0;
		return Math.max((Integer) first.item, maxRecursive(first.next));	 
	}
}