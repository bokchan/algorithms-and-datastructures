package chapter1;

public interface ISteque<Item>{
	
	public Item pop();
	public void push(Item item);
	public void enqueue(Item item);
	
	public Item peek();
	
	public boolean isEmpty();
	
	public int size();
	
	public String toString();
	
}