package experiments;

import java.util.Iterator;

import edu.princeton.cs.stdlib.Stopwatch;

public class BinaryTreeQueueTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BinaryTreeQueue<String> BTQ = new BinaryTreeQueue<String>();
		System.out.println("Testing string input");
		BTQ.enqueue("Andreas");
		BTQ.enqueue("Stine");
		BTQ.enqueue("Mette");
		
		Iterator<String> it = BTQ.depthFirstIterator();
		 
		while(it.hasNext()) {
			System.out.println(it.next());
		
		}
		
		System.out.println("\nTesting BinaryTreeQueue<Integer>");
		BinaryTreeQueue<Integer> BTQ2 = new BinaryTreeQueue<Integer>();
		
		Stopwatch w = new Stopwatch();
		int size = 50000;
		for(int i = 0; i < size; i++) {
			BTQ2.enqueue(i);
		}
		System.out.printf("Time taken to build binarytree with %s items: %s\n\n", size, w.elapsedTime());
		
		System.out.println("Testing DepthFirstIterator");
		Iterator<Integer> it2 = BTQ2.iterator();
		
		while(it2.hasNext()) {
			it2.next();
		}
		System.out.printf("Iteration using depthfirst: %s\n", w.elapsedTime());
		
		System.out.println("Testing BreadthFirstIterator");
		it2 = BTQ2.breadthFirstIterator();
		
		while(it2.hasNext()) {
			it2.next();
		}
		System.out.printf("Iteration using breadthfirst: %s\n", w.elapsedTime());
		
		
	}

}
