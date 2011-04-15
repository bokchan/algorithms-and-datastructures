package handins;

import java.util.Iterator;

public interface IRandomQueue<Item> {
	
	public boolean isEmpty(); // is it empty?
	public int size(); // return the number of elements
	public void enqueue(Item item); // add an item
	public Item sample(); // return (but do not remove) a random item
	public Item dequeue(); // remove and return a random item
	public Iterator<Item> iterator(); // return an iterator over the items in random order

}
