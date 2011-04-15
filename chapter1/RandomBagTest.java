package chapter1;

import java.util.Iterator;

public class RandomBagTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RandomBag<Integer> bag = new RandomBag<Integer>();
		bag.add(2);
		bag.add(3);
		bag.add(43);	
		
		Iterator<Integer> i = bag.iterator();
		System.out.println(i.hasNext());
		while(i.hasNext()) {
			i.next().toString();
		}
	}

}
