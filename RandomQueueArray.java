

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.stdlib.StdRandom;

/***
 * Implement the RandomQueue with a next() that returns  
 * 
 * @author Andreas
 *
 * @param <Item>
 */

public class RandomQueueArray<Item> implements  Iterable<Item>{

	private int N;
	private Node first;
	private Node last;
	public Object[] array;

	/***
	 * Node in the randomqueue
	 * @author Andreas
	 * previous allows backwards traversing
	 */
	private class Node {
		private Item item;
		private Node next;
		private Node previous; 
	}
	
	/***
	 * Constructor
	 */
	public RandomQueueArray() {
		N = 0;
		first = null;
		last = null;
		array = new Object[2];
	}

	public void enqueue(Item item) {
		if (isEmpty()) {
			//The queue is empty set first and last to the new node 
			first = new Node();
			first.item = item;
			first.next = null;
			first.previous = null;
			last = first;
			last.next = null;
			array[N] = first;
			
			N++;
		} else {
			// Insert an element at the end
			if (array.length == N) resize(2*array.length);
			Node oldLast = last;
			last = new Node();
			last.item = item;
			last.next = null;
			last.previous = oldLast;
			oldLast.next = last;
			array[N] =  last;
			N++;
			
		}
	}

	public Item peek() {
		if(!isEmpty()) throw new RuntimeException("Stack underflow");
		return first.item;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return N;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Item i : this)
		{
			s.append(i + " ");
		} 
		return s.toString().trim();
	}


	/**
	 * return (but do not remove) a random item
	 */
	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		int random = StdRandom.uniform(N);
		
		return ((Node) array[random]).item;
	}

	/**
	 *  remove and return a random item
	 *  a random number i, 0<= i < N
	 *  iterate through the queue from front to end
	 */
	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		int random = StdRandom.uniform(N);
		Node n = (Node)array[random];
		
		// 
		
		// Update node
		Item item = n.item;
		Node prev = n.previous;
		Node next = n.next;

		if (prev == null) {
			if (next == null) {				
				// prev == null && next == null (Only this element left in the queue) 
				first = null;
				last = null;
				N--;
			}else {
				// prev == null && next != null (First element)
				first = next;
				first.previous = null;
				N--;
			}
		} else {
			if (next == null) {
				// prev != null && next == null  (Last element )
				last = n.previous;
				last.next = null;
				N--;
			}else {
				// prev != null && next != null (Middle element)
				prev.next = n.next;
				next.previous = prev;
				N--;
			}
		}
		
		/* swap elements, putting the dequeued item at the back of the array  
		 * {1,2,3}
		 */
		
		for (int i = random; i < N; i++) {
			array[i] = array[i+1];
		}
		
		/*
		 * Performance can be enhanced by not downsizing the array
		 */
		//if (N > 0 && N == array.length/4) resize(array.length/2);
		
		return item;
	}
	
	// resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        Object[] temp = new Object[capacity];
        for (int i = 0; i < N; i++)
            temp[i] = array[i];
        array = temp;
    }

	public Iterator<Item> iterator() {
		return new RandomIterator();
	}


	private class RandomIterator implements Iterator<Item> {
		// Randomized copy of the queue  
		private RandomQueueArray<Item> rq = new RandomQueueArray<Item>();
		private Node current = first;

		public RandomIterator() {
			Requeue();
		}

		private void Requeue() { 
			Object[] nodes = new Object[size()];
			int i = 0;
			// Create an array of nodes from the original randomqueue

			while(hasNext()) {
				nodes[i] = next();
				i++;
			}

			// Use StdRandom.shuffle to randomize the array of nodes  
			StdRandom.shuffle(nodes);

			// Fille the randomqueue rq with elements in randomized order.    
			for (Object o : nodes) {
				rq.enqueue((Item)o);
			}

			// Set the current of the iterator to the new RandomQueues first 
			current = rq.first;
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