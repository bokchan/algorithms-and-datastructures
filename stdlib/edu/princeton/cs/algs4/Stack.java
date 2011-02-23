package edu.princeton.cs.algs4;
/*************************************************************************
 *  Compilation:  javac Stack.java
 *  Execution:    java Stack
 *
 *  A generic stack, implemented using a linked list. Each stack
 *  element is of type Item.
 *  
 *  % java Stack
 *  Pieces of Me
 *  Drop It Like It's Hot
 *  Just Lose It
 *  Vertigo
 *  
 *  17
 *  24
 *  30
 *  35
 *  39
 *  42
 *  44
 *  45
 *  45
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The <tt>Stack</tt> class represents a last-in-first-out (LIFO) stack of
 * generic items. It supports the usual <em>push</em> and <em>pop</em>
 * operations, along with methods for peeking at the top item, testing if the
 * stack is empty, and iterating through the items in LIFO order.
 * <p>
 * All stack operations except iteration are constant time.
 * <p>
 * For additional documentation, see <a href="/algs4/13stacks">Section 1.3</a>
 * of <i>Algorithms in Java, 4th Edition</i> by Robert Sedgewick and Kevin
 * Wayne.
 */
public class Stack<Item> implements Iterable<Item> {
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
	public Stack() {
		setFirst(null);
		N = 0;
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
		return s.toString();
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
	 * A test client.
	 */
	public static void main(String[] args) {

		/***********************************************
		 * A stack of strings
		 ***********************************************/
		Stack<String> s1 = new Stack<String>();
		s1.push("Vertigo");
		s1.push("Just Lose It");
		s1.push("Pieces of Me");
		System.out.println(s1.pop());
		s1.push("Drop It Like It's Hot");
		while (!s1.isEmpty()) {
			System.out.println(s1.pop());
		}
		System.out.println();

		/*********************************************************
		 * A stack of integers. Illustrates autoboxing and auto-unboxing.
		 *********************************************************/
		Stack<Integer> s2 = new Stack<Integer>();
		for (int i = 0; i < 10; i++) {
			s2.push(i);
		}

		// test out the iterator
		for (int i : s2) {
			System.out.print(i + " ");
		}
		System.out.println();

		while (s2.size() >= 2) {
			int a = s2.pop();
			int b = s2.pop();
			int c = a + b;
			System.out.println(c);
			s2.push(a + b);
		}

	}

	public void setFirst(Node first) {
		this.first = first;
	}

	public Node getFirst() {
		return first;
	}
}
