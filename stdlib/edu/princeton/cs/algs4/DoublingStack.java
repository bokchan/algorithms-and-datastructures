package edu.princeton.cs.algs4;
/*************************************************************************
 *  Compilation:  javac DoublingStack.java
 *  Execution:    java DoublingStack
 *  
 *  Stack implementation with an array. Resizes by doubling and halving.
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

// suppress unchecked warnings in Java 1.5.0_6 and later
@SuppressWarnings("unchecked")
public class DoublingStack<Item> implements Iterable<Item> {
	private Item[] a; // array of items
	private int N = 0; // number of elements on stack

	// create an empty stack
	public DoublingStack() {
		a = (Item[]) new Object[2];
	}

	public boolean isEmpty() {
		return N == 0;
	}

	public int size() {
		return N;
	}

	// resize the underlying array holding the elements
	private void resize(int capacity) {
		assert capacity >= N;
		Item[] temp = (Item[]) new Object[capacity];
		for (int i = 0; i < N; i++)
			temp[i] = a[i];
		a = temp;
	}

	// push a new item onto the stack
	public void push(Item item) {
		if (N == a.length)
			resize(2 * a.length); // double size of array if necessary
		a[N++] = item; // add item
	}

	// delete and return the item most recently added
	public Item pop() {
		if (isEmpty()) {
			throw new RuntimeException("Stack underflow error");
		}
		Item item = a[N - 1];
		a[N - 1] = null; // to avoid loitering
		N--;
		// shrink size of array if necessary
		if (N > 0 && N == a.length / 4)
			resize(a.length / 2);
		return item;
	}

	public Iterator<Item> iterator() {
		return new LIFOIterator();
	}

	// an iterator, doesn't implement remove() since it's optional
	private class LIFOIterator implements Iterator<Item> {
		private int i = N;

		public boolean hasNext() {
			return i > 0;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return a[--i];
		}
	}

	/***********************************************************************
	 * Test routine.
	 **********************************************************************/
	public static void main(String[] args) {
		DoublingStack<String> stack = new DoublingStack<String>();
		stack.push("Hello");
		stack.push("World");
		stack.push("how");
		stack.push("are");
		stack.push("you");

		for (String s : stack)
			System.out.println(s);

		System.out.println();

		while (!stack.isEmpty())
			System.out.println(stack.pop());
	}

}
