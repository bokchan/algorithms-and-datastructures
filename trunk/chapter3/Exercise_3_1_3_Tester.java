package chapter3;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class Exercise_3_1_3_Tester {
	Exercise_3_1_3<String, Integer> orderedList; 
	
	
	@Test
	public void testPut() {
		orderedList = new Exercise_3_1_3<String, Integer>();
		
		orderedList.put("Andreas", 30);
		
		orderedList.put("Pelle", 35);
		
		Assert.assertEquals((Object)30, orderedList.get("Andreas"));
		orderedList.put("Andreas", 31);
		Assert.assertEquals((Object)31, orderedList.get("Andreas"));
		Assert.assertEquals(35, (int)orderedList.get("Pelle"));
		
		Assert.assertTrue(orderedList.Contains("Andreas"));
		orderedList.Delete("Andreas");
		Assert.assertFalse(orderedList.Contains("Andreas"));
		
		Assert.assertEquals(35, (int)orderedList.get("Pelle"));
		
		orderedList.put("Stine", 26);
		String[] exp = new String[] {"Stine", "Pelle"};
		ArrayList<String> it =(ArrayList<String>) orderedList.keys();
		Assert.assertArrayEquals(exp, it.toArray());
	}
}
