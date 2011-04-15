package chapter1;

import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

public class CreativeExercises_1_3 {
	@Test
	public void Exercise_1_3_34() {
		RandomBag<String> bag = new RandomBag<String>();
		bag.add("Andreas") ;
		bag.add("Stine");
		bag.add("Alin");
		bag.add("Visti");
		
		Object[] expecteds = new Object[] {"Andreas", "Stine", "Alin", "Visti"}; 
		
		Iterator<String> i = bag.iterator();
		ArrayList<String> actuals = new ArrayList<String>();
		while(i.hasNext()) {
			actuals.add(i.next());
		}
		Assert.assertNotSame(expecteds, actuals);
	}
	
	
}
