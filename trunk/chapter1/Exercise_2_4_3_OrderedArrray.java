package chapter1;

import chapter2.Exercise_2_4_3_Base;

public class Exercise_2_4_3_OrderedArrray<E extends Comparable<E>> extends Exercise_2_4_3_Base<E> {

	int N;
	E[] list;
	
	public Exercise_2_4_3_OrderedArrray() {
		list = (E[]) new Object[2];
		N = 0;
	}
	
	public void insert(E item) {
		if (N == list.length) resize(2*list.length);
		list[N] = item;
		N++;
	}
	
	public boolean isEmpty() {
		return list.length ==0;
	}
	
	public E removeMaximum()  {
		if (isEmpty()) throw new RuntimeException("Stack underflow");
		E max = (E) list[0];
		for (E e : list) {
			max = (max.compareTo(e) < 0) ? max : e;
		}	
		list[N-1] = null;
		N--;
		if (N > 0 && N == list.length/4) resize(list.length/2);
		
		return max;
	}
	
	public void resize(int capacity) {
		E[] aux = (E[]) new Object[capacity];
		
		for (int i = 0;i< list.length; i++) {
			aux[i] = (E) list[i];
		}
		
		list = aux;
	}
}
