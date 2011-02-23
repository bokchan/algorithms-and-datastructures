package chapter1;

import org.junit.Assert;
import org.junit.Test;

public class StackExercises {
	@Test
	public void Exercise_1_3_20() {
		MyLinkedList<String> s = new MyLinkedList<String>();
		s.push("A");
		s.push("B");
		s.push("C");
		
		s.delete(1);
		String expected = "B A";
		Assert.assertEquals(expected, s.toString());
		
		Exception exceptionActual = null;
		try {
			s.delete(5);
			} catch (Exception e) {
				exceptionActual = e;
			}
			
		Assert.assertEquals("Index out of range", exceptionActual.getMessage());
	}
	
	
	@Test
	public void Exercise_1_3_21() {
		MyLinkedList<String> s = new MyLinkedList<String>();
		s.push("Andreas");
		s.push("Stine");
		s.push("Mette");
		
		Assert.assertTrue(MyLinkedList.find(s, "Andreas"));
	}
	@Test
	public void Exercise_1_3_24() {
		MyLinkedList<String> s = new MyLinkedList<String>() ;
		s.push("Andreas");
		s.push("Stine");
		s.push("Mette");
		
		s.removeAfter(s.getNode(1));
		String expected = "Mette Andreas";
		Assert.assertEquals(expected, s.toString());
	} 
	
	@Test
	public void Exercises_1_3_19() {
		MyLinkedList<String> s = new MyLinkedList<String>();
		s.push("A");
		s.push("B");
		s.push("C");
		 
		/***
		 * Removes last
		 */
		s.iterator().remove();
		String expected = "C B";
		Assert.assertEquals(expected, s.toString());
	}
	
	

}
