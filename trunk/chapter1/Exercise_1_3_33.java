package chapter1;

import junit.framework.Assert;

import org.junit.Test;

public class Exercise_1_3_33 {
	@Test 
	public void TestDeque() {
		Deque<String> d = new Deque<String>();
		d.pushLeft("Andreas");
		d.pushLeft("John");
		
		Assert.assertEquals("Andreas", d.popRight());
		
		d.pushRight("Stine");
		Assert.assertEquals("John", d.popLeft());
		
		Assert.assertEquals("Stine", d.popLeft());
		
		Assert.assertEquals(0, d.size());
	}
}
