package chapter1;

import junit.framework.Assert;

import org.junit.Test;

public class Exercise_1_3_32 {
	@Test
	public void Test() {
		Steque<String> s = new Steque<String>();
		s.push("Andreas");
		s.push("Stine");
		
		String expected = "Stine Andreas";
		Assert.assertEquals(expected,s.toString());
		
		/***
		 * [Stine]
		 * [Andreas]
		 * [Pelle]
		 */
		s.enqueue("Pelle");
		expected = "Stine Andreas Pelle";
		Assert.assertEquals(expected,s.toString());
	
		s.pop();
		expected = "Andreas Pelle";
		Assert.assertEquals(expected,s.toString());
		
		s.pop();
		expected = "Pelle";
		Assert.assertEquals(expected,s.toString());
		
		s.pop();
		expected = "";
		Assert.assertEquals(expected,s.toString());
		
		s.pop();
		expected = "";
		Assert.assertEquals(expected,s.toString());
	} 
}
