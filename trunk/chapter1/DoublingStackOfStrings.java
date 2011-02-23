package chapter1;

import edu.princeton.cs.stdlib.StdIn;
import edu.princeton.cs.stdlib.StdOut;

/*************************************************************************
 *  Compilation:  javac DoublingStackOfStrings.java
 *  Execution:    java DoublingStackOfStrings
 *  
 *  Stack of strings implementation with an array.
 *  Resizes by doubling and halving.
 *
 *************************************************************************/

public class DoublingStackOfStrings {
    private String[] a;
    private int N;

    public DoublingStackOfStrings() {
        a = new String[2];
        N = 0;
    }

    // is the stack empty?
    public boolean isEmpty() {  return (N == 0);  }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        String[] temp = new String[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    // push a new item onto the stack
    public void push(String item) {
        if (N == a.length) resize(2*a.length);
        a[N++] = item;
    }

    // delete and return the item most recently added
    public String pop() {
        if (isEmpty()) { throw new RuntimeException("Stack underflow error"); }
        String item = a[--N];
        a[N] = null;  // to avoid loitering
        if (N > 0 && N == a.length/4) resize(a.length/2);
        return item;
    }
    
    public int getN() {
    	return this.N;
    }
    
    public String[] getA() {
    	return this.a;
    } 

    // test client
    public static void main(String[] args) {
        DoublingStackOfStrings s = new DoublingStackOfStrings();
        String input = StdIn.readString();
        while (!input.equals("quit")) {
            String item = input;
            if (!item.equals("-")) s.push(item);
            else if (s.isEmpty())  StdOut.println("BAD INPUT");
            else                   StdOut.print(s.pop());
            input = StdIn.readString();
        }
        StdOut.println("Numbers of elements: " + s.getN() + " Size of array" + s.getA().length);
    }
}
