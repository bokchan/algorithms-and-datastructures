package FileSE;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.stdlib.Stopwatch;



public class FileSETest {

	@Test
	public void testSeashells() throws IOException {
		FileSE2 e = new FileSE2();
		e.indexFile(".\\bin\\shells.txt");
		System.out.println(e.toString());
		
		Object[] expecteds = new Object[] {2};
		e.search("so");
		Assert.assertArrayEquals(expecteds, e.getResult());
		
		expecteds = new Object[] {0};
		e.search("by the");
		Assert.assertArrayEquals(expecteds, e.getResult());
		
		expecteds = new Object[] {3};
		e.search("i'm shells she sells");
		Assert.assertArrayEquals(expecteds, e.getResult());
	}
	
	@Test
	public void testBible() throws IOException {
		FileSE2 e = new FileSE2();
		System.out.println("Indexing the bible...");
		Stopwatch w = new Stopwatch();
		e.indexFile(".\\bin\\bible.txt");
		System.out.print("it took: " + w.elapsedTime() + "\n");
		
		Object[]  expecteds = new Object[] {30205, 30206, 30228};
		w = new Stopwatch();
		System.out.println(e.search("number beast"));
		System.out.println("Search time: " + w.elapsedTime());
		System.out.println(Arrays.toString(e.getResult()));
		Assert.assertArrayEquals(expecteds, e.getResult());

		expecteds = new Object[] {30205, 30206, 30228};
		w = new Stopwatch();
		System.out.println(e.searchBS("number beast"));
		System.out.println("BS Search time: " + w.elapsedTime());
		
		Assert.assertArrayEquals(expecteds, e.getResult());
		
		expecteds = new Object[] {19117, 19276, 23943};		
		w = new Stopwatch();
		System.out.println(e.search("i you he and but for to"));
		System.out.println("Search time: " + w.elapsedTime());
		Assert.assertArrayEquals(expecteds, e.getResult());
		
		expecteds = new Object[] {19117, 19276, 23943};		
		w = new Stopwatch();
		System.out.println(e.search("i you he and but for to"));
		System.out.println("BS Search time: " + w.elapsedTime());
		Assert.assertArrayEquals(expecteds, e.getResult());
		
		w = new Stopwatch();
		System.out.println(e.search("i am you he will"));
		System.out.println("Search time: " + w.elapsedTime());
		
		w = new Stopwatch();
		System.out.println(e.search("and if"));
		System.out.println("Search time: " + w.elapsedTime());
		
	}
}
