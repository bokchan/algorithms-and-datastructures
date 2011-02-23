package chapter1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import edu.princeton.cs.stdlib.StdOut;

public class Exercise_1_3_6 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/***
		 * 
		 */
		Stack<String> s = new Stack<String>();
		Queue<String> q = new LinkedList<String>();
		
		q.add("a");
		q.add("b");
		q.add("c");
		q.add("d");
		q.add("e");
		StdOut.println(q.toString());
		while(!q.isEmpty() ) {
			s.push(q.remove());
		}
		StdOut.println(s.toString());
		StdOut.println(q.toString());
		while(!s.isEmpty() ) {
			q.add(s.pop());
		}
		StdOut.println(s.toString());
		StdOut.println(q.toString());
		
		
	}
}
