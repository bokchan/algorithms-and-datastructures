package chapter1;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.stdlib.StdIn;
import edu.princeton.cs.stdlib.StdOut;

public class Exercise_1_3_2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/*** it was - the best - of times - - - it was - the - -
		 * Expected
		 * stack 
		 * {it}
		 * {it, was}
		 * was
		 * {it the best}
		 * best 
		 * {it the of times}
		 * times of the 
		 * {it}
		 * {it it was}
		 * was 
		 * {it it the}
		 * the it
		 * {it} 
		 *  
		 */

		Stack<String> stack = new Stack<String>();
		StdOut.println("Type input string");


		boolean done= false;
		while(!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-")) {
				stack.push(item);
			} else if (!stack.isEmpty()) {
				StdOut.println(stack.pop() + " ") ;
			}
		}
		StdOut.println("(" + stack.size() + " left on stack");
	}
}
