package chapter1;
/*************************************************************************
 *  Compilation:  javac Queue.java
 *  Execution:    java Queue
 *
 *  A generic queue, implemented using a linked list. Each queue
 *  element is of type Item.
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The <tt>Queue</tt> class represents a first-in-first-out (FIFO) queue of
 * generic items. It supports the usual <em>enqueue</em> and <em>dequeue</em>
 * operations, along with methods for peeking at the top item, testing if the
 * queue is empty, and iterating through the items in FIFO order.
 * <p>
 * All queue operations except iteration are constant time.
 * <p>
 * For additional documentation, see <a href="/algs4/13stacks">Section 1.3</a>
 * of <i>Algorithms in Java, 4th Edition</i> by Robert Sedgewick and Kevin
 * Wayne.
 */
public class Queue<Item> implements Iterable<Item> {
	protected int N; // number of elements on queue
	protected DoubleNode first; // beginning of queue
	protected DoubleNode last; // end of queue

	// helper linked list class
	protected class DoubleNode {
		private Item item;
		private DoubleNode next;
		private DoubleNode previous;
		
	}

	/**
	 * Create an empty queue.
	 */
	public Queue() {
		first = null;
		last = null;
	}
	
	public DoubleNode createNode(Item item) {
		DoubleNode node=  new DoubleNode();
		node.item = item;
		return node;
	}

	/**
	 * Is the queue empty?
	 */
	public boolean isEmpty() {
		return first == null;
	}

	/**
	 * Return the number of items in the queue.
	 */
	public int size() {
		return N;
	}

	/**
	 * Return the item least recently added to the queue. Throw an exception if
	 * the queue is empty.
	 */
	public Item peek() {
		if (isEmpty())
			throw new RuntimeException("Queue underflow");
		return first.item;
	}

	/**
	 * Add the item to the queue.
	 */
	public void enqueue(Item item) {
		DoubleNode x = new DoubleNode();
		x.item = item;
		if (isEmpty()) {
			first = x;
			last = x;

		} else {
			last.next = x;
			x.previous = last;
			last = x;
		}
		N++;
	}

	/**
	 * Remove and return the item on the queue least recently added. Throw an
	 * exception if the queue is empty.
	 */
	public Item dequeue() {
		if (isEmpty())
			throw new RuntimeException("Queue underflow");
		Item item = first.item;
		first = first.next;
		N--;
		if (isEmpty())
			last = null; // to avoid loitering
		return item;
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
	 * Return an iterator that iterates over the items on the queue in FIFO
	 * order.
	 */
	public Iterator<Item> iterator() {
		return new FIFOIterator();
	}

	// an iterator, doesn't implement remove() since it's optional
	private class FIFOIterator implements Iterator<Item> {
		private DoubleNode current = first;

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

	public static void insertAtHead(Queue q, Queue.DoubleNode node)  {
		// Put the node on top of the queue
		Queue.DoubleNode first = q.first; 
		node.next = q.first;
		node.previous = null;
		first.previous = node;
		q.first = node;
		q.N++;
	}

	public static void insertAtEnd(Queue q, Queue.DoubleNode node) {
		q.last.next = node;
		node.next = null;
		node.previous = q.last;
		q.last = node;
		q.N++;
	}

	public static void removeFromHead(Queue q) {
		Queue.DoubleNode newFirst = q.first.next;
		newFirst.previous = null;
		q.first = newFirst;
		q.N--;

	}

	public static void removeFromEnd(Queue q) {
		Queue.DoubleNode newLast = q.last.previous;
		newLast.next = null;
		q.last = newLast;
		q.N--;
	}

	public static void insertBefore(Queue q, Queue.DoubleNode first, Queue.DoubleNode second) {
		if (first.previous== null) { 
			insertAtHead(q, second);
			return;
			} 
		
		Queue.DoubleNode oldPrevious = first.previous;
		oldPrevious.next = second;
		second.previous = oldPrevious;
		second.next = first;
		first.previous = second;
		q.N++;
	}

	public static void insertAfter(Queue q, Queue.DoubleNode before, Queue.DoubleNode after) {
		if (before.next == null) { 
			insertAtEnd(q, after);
			return; 
		}
		Queue.DoubleNode oldNext = before.next;
		before.next = after;
		after.previous = before;
		after.next = oldNext;
		oldNext.previous = after;
		q.N++;
	}

	public static void removeNode(Queue q, Queue.DoubleNode node) {
		Queue.DoubleNode first = q.first;
		if (node.equals(first)) {
			removeFromHead(q);
			return;
		}

		while(first.next !=null) {
			if (first.equals(node)) {
				Queue.DoubleNode prev = first.previous;     
				if (node.next != null) {
					prev.next = node.next;
					prev.next.previous = prev;
				} else {
					prev.next = null;
				}
				node = null;
				break;
			}
			first = first.next;
		}
	} 
}
