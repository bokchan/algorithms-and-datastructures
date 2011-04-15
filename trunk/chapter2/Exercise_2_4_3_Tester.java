package chapter2;

import org.junit.Test;

import edu.princeton.cs.stdlib.StdRandom;

public class Exercise_2_4_3_Tester {
	
	@Test
	public void OrderedArrray() {
		Exercise_2_4_3_OrderedArrray<Integer> a = new Exercise_2_4_3_OrderedArrray<Integer>();
		fillData(a);
		removeMax(a);
	}

	@Test
	public void UnorderedArrray() {
		Exercise_2_4_3_UnorderedArray<Integer> a = new Exercise_2_4_3_UnorderedArray<Integer>();
		fillData(a);
		removeMax(a);

	}

	@Test
	public void UnorderedLinkedList() {
		Exercise_2_4_3_UnorderedLinkedList<Integer> a = new Exercise_2_4_3_UnorderedLinkedList<Integer>();
		fillData(a);
		removeMax(a);
	}
	@Test 
	public void OrderedLinkedList() {
		Exercise_2_4_3_OrderedLinkedList<Integer> a = new Exercise_2_4_3_OrderedLinkedList<Integer>();
		fillData(a);
		removeMax(a);
	}
	
	public void fillData(Exercise_2_4_3_Base<Integer> a) {
		for (int i = 0; i < 100; i++) {
			a.insert(StdRandom.uniform(100)); 
		}
	}
	
	public void removeMax(Exercise_2_4_3_Base<Integer> a) {
		System.out.println(a.getClass().getCanonicalName());
		System.out.println(a.removeMaximum());
	}
}