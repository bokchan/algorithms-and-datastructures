package experiments;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class BinaryTreeQueue<E> implements Iterable<E>{
	LinkedList<Node> frontier = new LinkedList<Node>();
	Node first = null;
	Node last = null;
	int N =0;

	class Node{
		public E item = null;;
		public Node parent = null;
		public Node nextR = null;
		public Node nextL= null;
	}

	public void enqueue(E item) {
		if (isEmpty()) {
			first = new Node();
			first.item = item;
			first.parent = null;
			first.nextL = null;
			first.nextR = null;
			last = first;

			frontier.add(first);
			N++;
		} else {
			Node nodeF = frontier.getFirst();
			if (nodeF.nextL == null) {
				Node nodeNew = new Node();
				nodeNew.item = item;
				nodeNew.parent = nodeF;
				nodeF.nextL = nodeNew;

				frontier.add(nodeNew);
				last = frontier.getLast();
				N++;


			} else if (nodeF.nextR == null){
				Node nodeNew = new Node();
				nodeNew.item = item;
				nodeNew.parent = nodeF;
				nodeF.nextR = nodeNew;

				frontier.removeFirst();
				frontier.add(nodeNew);
				last = frontier.getLast();

				N++;
			}
		}
	}

	public int size() {
		return N;
	}


	public BinaryTreeQueue() {
		N = 0;
		first = null;
	}

	public boolean isEmpty(){
		return size() ==0;		
	}

	public Iterator<E> iterator() {
		return new DepthFirstIterator<E>();
	}

	public Iterator<E> depthFirstIterator() {
		return new DepthFirstIterator<E>();
	}

	public Iterator<E> breadthFirstIterator() {
		return new BreadthFirstIterator<E>();
	}


	private class BreadthFirstIterator<E> implements Iterator<E> {
		LinkedList<Node> frontier = new LinkedList<Node>();
		LinkedList<Node> dequeued = new LinkedList<Node>();
		Node current;

		public BreadthFirstIterator() {
			if (isEmpty()) throw new NoSuchElementException();
			current = first;
			frontier.add(current);
		}

		@SuppressWarnings("unchecked")
		public E nextParent() {
			if(hasParent())
				throw new NoSuchElementException();
			E item = (E) current.parent.item;
			current = current.parent;
			return item;
		}

		public boolean hasNext() {
			return (dequeued.size() < N);

		}

		public boolean hasNextR() {
			return current.nextR != null;
		}
		public boolean hasNextL() {
			return current.nextL != null;
		}

		@SuppressWarnings("unchecked")
		public E next() {
			E item = (E) current.item;

			/* {Andreas, Stine}
			 * {Andreas}
			 * {Andreas, Stine}
			 * {Stine, Mette}
			 */
			Node lastParent  = frontier.getFirst();
			if (lastParent.nextL != null && !dequeued.contains(lastParent.nextL)) {
				current = lastParent.nextL;
				frontier.add(current);

			}  else if (lastParent.nextR != null&& !dequeued.contains(lastParent.nextR) ) {
				current = lastParent.nextR;
				frontier.add(current);
				frontier.removeFirst();

			} else {
				frontier.removeFirst();

			} 
			dequeued.add(current);
			return item;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public boolean hasParent() {
			return current.parent != null;
		}
	} 

	@SuppressWarnings("hiding")
	private class DepthFirstIterator<E> implements Iterator<E> {
		LinkedList<Node> frontier = new LinkedList<Node>();
		Node current;

		public DepthFirstIterator() {
			if (isEmpty()) throw new NoSuchElementException();
			current = first;
		}

		@SuppressWarnings("unchecked")
		public E nextParent() {
			if(hasParent())
				throw new NoSuchElementException();
			E item = (E) current.parent.item;
			current = current.parent;
			return item;
		}

		public boolean hasNext() {
			return (frontier.size() < N);
		}

		public boolean hasNextR() {
			return current.nextR != null;
		}
		public boolean hasNextL() {
			return current.nextL != null;
		}

		@SuppressWarnings("unchecked")
		public E next() {
			E item = (E) current.item;

			if (hasNextL()) {
				current = current.nextL;
			} else {
				while(hasParent()) {
					current = current.parent;
					if (hasNextR() && !frontier.contains(current.nextR)) {

						break;

					}
				}
				current = current.nextR;
			}

			frontier.add(current);
			return item;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public boolean hasParent() {
			return current.parent != null;
		}
	}
}
