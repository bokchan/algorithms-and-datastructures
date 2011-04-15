package handins;


/*************************************************************************
 *  Compilation:  javac ResizingArrayStack.java
 *  Execution:    java ResizingArrayStack
 *  
 *  Stack implementation with a resizing array.
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

// suppress unchecked warnings in Java 1.5.0_6 and later
@SuppressWarnings("unchecked")

public class ResizingArrayStack<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int N = 0;        // number of elements on stack

    // create an empty stack
    public ResizingArrayStack() {
        a = (Item[]) new Object[2];
    }

    public boolean isEmpty() { return N == 0; }
    public int size()        { return N;      }



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
        if (N == a.length) resize(2*a.length);    // double size of array if necessary
        a[N++] = item;                            // add item
    }

    // delete and return the item most recently added
    public Item pop() {
        if (isEmpty()) { throw new RuntimeException("Stack underflow error"); }
        Item item = a[N-1];
        a[N-1] = null;                              // to avoid loitering
        N--;
        // shrink size of array if necessary
        if (N > 0 && N == a.length/4) resize(a.length/2);
        return item;
    }


    public Iterator<Item> iterator()  { return new LIFOIterator();  }

    // an iterator, doesn't implement remove() since it's optional
    private class LIFOIterator implements Iterator<Item> {
        private int i = N;
        public boolean hasNext()  { return i > 0;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[--i];
        }
    }

}
