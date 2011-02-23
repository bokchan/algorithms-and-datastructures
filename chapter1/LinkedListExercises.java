package chapter1;

import junit.framework.Assert;

import org.junit.Test;

public class LinkedListExercises {
	
		
	
	@Test
	public void Exercise_1_3_25() {
		MyLinkedList<String> s = new MyLinkedList<String>() ;
		s.push("Andreas");
		s.push("Stine");
		s.push("Mette");
		s.insertAfter(s.getFirst(), s.getNode(3));
		String expected = "Mette Andreas Stine Andreas";
		Assert.assertEquals(expected, s.toString().trim());
	}  
	
	/***
	 * Exercise 1_3_26
	 */
	 @Test
	public void Exercise_1_3_26() {
		MyLinkedList<String> s = new MyLinkedList<String>() ;
		s.push("Andreas");
		s.push("Stine");
		s.push("Mette");
		s.remove(s, "Andreas");
		String expected = "Mette Stine";
		
		Assert.assertEquals(expected, s.toString().trim());
	}

	 /***
	  * Exercise_1_3_27
	  */
	@Test
	public void Exercise_1_3_27() {
		MyLinkedList<Integer> s= new MyLinkedList<Integer>()  ;
		s.push(1);
		s.push(6);
		s.push(10);
		int max = s.max(s.getFirst());
		Assert.assertEquals(10, max);
		max = s.maxRecursive(s.getFirst());
		Assert.assertEquals(10, max);
	}
	
	@Test
	public void Exercise_1_3_28() {
		MyLinkedList<Integer> s = new MyLinkedList<Integer>()  ;
		s.push(1);
		s.push(6);
		s.push(10);
		
		int max = s.maxRecursive(s.getFirst());
		Assert.assertEquals(10, max);
	}
	
	@Test 
	public void Exercise_1_3_31() {
		Queue<String> q = new Queue<String>();
		q.enqueue("Andreas");
		q.enqueue("Stine");
		q.enqueue("Mette");
		
		Queue.DoubleNode node = q.createNode("Alin");
		Queue.insertAtHead(q, node);
		String expected = "Alin Andreas Stine Mette";
		Assert.assertEquals(expected, q.toString().trim());
		
		Queue.insertAtEnd(q, q.createNode("Visti"));
		expected = "Alin Andreas Stine Mette Visti";
		Assert.assertEquals(expected, q.toString().trim());
		
		
		Queue.insertBefore(q, node, q.createNode("Egil"));
		expected = "Egil Alin Andreas Stine Mette Visti";
		Assert.assertEquals(expected, q.toString().trim());
		
		Queue.insertAfter(q, node, q.createNode("Jonas"));
		expected = "Egil Alin Jonas Andreas Stine Mette Visti";
		Assert.assertEquals(expected, q.toString().trim());
		
		
		Queue.removeFromEnd(q);
		expected = "Egil Alin Jonas Andreas Stine Mette";
		Assert.assertEquals(expected, q.toString().trim());
		
		Queue.removeFromHead(q);
		expected = "Alin Jonas Andreas Stine Mette";
		Assert.assertEquals(expected, q.toString().trim());
		
		Queue.removeNode(q, node);
		expected = "Jonas Andreas Stine Mette";
		Assert.assertEquals(expected, q.toString().trim());		
	}
}