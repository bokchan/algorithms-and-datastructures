package chapter1;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.stdlib.StdRandom;

public class RandomBag<Item> implements Iterable<Item> {
	private int N; // number of elements in bag
	private Node first; // beginning of bag

	// helper linked list class
	private class Node {
		private Item item;
		private Node next;
	}

	/**
	 * Create an empty stack.
	 */
	public RandomBag() {
		first = null;
		N = 0;
	}

	/**
	 * Is the BAG empty?
	 */
	public boolean isEmpty() {
		return first == null;
	}

	/**
	 * Return the number of items in the bag.
	 */
	public int size() {
		return N;
	}

	/**
	 * Add the item to the bag.
	 */
	public void add(Item item) {
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		N++;
	}


	/**
	 * Return an iterator that iterates over the items in the bag.
	 */
	public Iterator<Item> iterator() {
		return new LIFOIterator();
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Item i : this)
		{
			s.append(i + " ");
		} 
		return s.toString().trim();
	}


	// an iterator, doesn't implement remove() since it's optional
	private class LIFOIterator implements Iterator<Item> {
		private Node current = first;

		public LIFOIterator() {
			randommizeOrder();
		} 

		@SuppressWarnings("unchecked")
		private void randommizeOrder() {
			Object[] nodes  = new Object[N];
			int i2 = 0;
			while(current != null) {
				nodes[i2] = current;
				current = current.next;
			}
						
			StdRandom.shuffle(nodes);
			
			for (Object o : nodes) {
				System.out.println(((Node)o).item.toString());
			}

			first = (Node) nodes[0];
			for (int i = 0; i < nodes.length-1;i++) {
				
				((Node)nodes[i]).next = (Node) nodes[i+1];
				System.out.println(((Node)nodes[i]).item.toString());
			}
			((Node)nodes[nodes.length-1]).next  = null;
			current = first;			
		}



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
}
