package chapter2;

import java.util.ArrayList;
import java.util.List;


public class Exercise_2_4_3_UnorderedArray<E extends Comparable<E>> extends Exercise_2_4_3_Base<E>{
	
	List<E> list;
	
	public Exercise_2_4_3_UnorderedArray() {
		list = new ArrayList<E>();
		
	}
	public void insert(E item) {
		
		list.add(item);
		
	}
	
	public boolean isEmpty() {
		return list.size() ==0;
	}
	
	public E removeMaximum()  {
		if (isEmpty()) throw new RuntimeException("Stack underflow");
		
		E max = list.get(0);
		for (E e : list) {
			max = (max.compareTo(e) < 0) ? max : e;
		}	
		list.remove(max);
		return max;
	}
}