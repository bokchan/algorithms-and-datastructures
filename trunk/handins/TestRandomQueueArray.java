package handins;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.stdlib.StdOut;
import edu.princeton.cs.stdlib.StdStats;
import edu.princeton.cs.stdlib.Stopwatch;

/**
 * Test program for random queue. Code by Thore Husfeldt.
 */
public class TestRandomQueueArray {

	public static void main(String args[])
	{
		// Build a queue containing the Integers 1,2,...,6:
		RandomQueueArray<Integer> Q= new RandomQueueArray<Integer>();
		for (int i = 1; i < 7; ++i) Q.enqueue(i); // autoboxing! cool!

		// Print 30 die rolls to standard output
		StdOut.print("Some die rolls: ");
		for (int i = 1; i < 30; ++i) StdOut.print(Q.sample() +" ");
		StdOut.println();

		// Let's be more serious: do they really behave like die rolls?
		int[] rolls= new int [6000000];
		for (int i = 0; i < 6000000; ++i)
			rolls[i] = Q.sample(); // autounboxing! Also cool!
		StdOut.printf("Mean (should be around 3.5): %5.4f\n", StdStats.mean(rolls));
		StdOut.printf("Standard deviation (should be around 1.7): %5.4f\n",
				StdStats.stddev(rolls));

		// Let's look at the iterator. First, we make a queue of colours:
	}



	@Test
	public void TestIterator() {
		RandomQueueArray<String> C= new RandomQueueArray<String>();
		C.enqueue("red"); C.enqueue("blue"); C.enqueue("green"); C.enqueue("yellow"); 

		Iterator<String> I= C.iterator();
		Iterator<String> J= C.iterator();

		StdOut.print("Two colours from first shuffle: ");
		StdOut.print(I.next()+" ");
		StdOut.print(I.next()+" ");

		StdOut.print("\nEntire second shuffle: ");
		while (J.hasNext()) StdOut.print(J.next()+" ");

		StdOut.print("\nRemaining two colours from first shuffle: ");
		StdOut.print(I.next()+" ");
		StdOut.println(I.next());

		StdOut.println("\nDoing iteration with a large array ");
		int size = 200000;
		RandomQueueArray<Integer> rq = new RandomQueueArray<Integer>();
		int[] queue = new int[size];
		for (int i = 0; i < size; ++i) {
			rq.enqueue(i);
			queue[i] = i;
		}

		Iterator<Integer> it1 = rq.iterator();
		Iterator<Integer> it2 = rq.iterator();
		int[] queue2 = new int[size];
		int[] queue3 = new int[size];
		int i = 0;
		while(it1.hasNext()) {
			queue2[i] = it1.next();
			i++;
		}

		i = 0;
		while(it2.hasNext()) {
			queue3[i] = it2.next();
			i++;
		}			
		String q2 = "";
		String q3 = "";
		int max = size > 10 ? 10 : size;
		for (int k = 0; k< max; k++) {
			q2 += queue2[k] + ",";
			q3 += queue3[k] + ",";
		}
		q2 += "...";
		q3 += "...";
		System.out.println("Look at the first 10 elements");
		System.out.println(q2);
		System.out.println(q3);
	}

	@Test
	public void TestDequeue() {
		int size = 10;
		
		System.out.println("Testing random dequeue1");
		
		RandomQueueArray<Integer> rq = new RandomQueueArray<Integer>();
		
		int[] queue = new int[size];
		/*
		 *  Fill the randomqueue with numbers from 0 to N
		 *  Also make an int[] copy of the queue  
		 */
		
		for (int i = 0; i < size; ++i) {
			rq.enqueue(i);
			queue[i] = i;
		}
		
		// dequeue the randomqueue to an int[]
		int[] dequeue= new int [size];
		for (int i = 0; i < size; ++i) {
			dequeue[i] = rq.dequeue();
		}

		String q2 = "";
		String q3 = "";
		int max = size > 10 ? 10 : size;
		for (int k = 0; k< max; k++) {
			q2 += queue[k] + ",";
			q3 += dequeue[k] + ",";
		}
		System.out.println("Look at the first 10 elements");
		System.out.println(q2);
		System.out.println(q3);

		// clone the dequeed array, sort it and compare it to the original random queue 
		int[] dequeueClone = dequeue.clone();
		Arrays.sort(dequeueClone);
		Assert.assertArrayEquals(queue, dequeueClone);
	}

	@Test
	public void TestDequeue2() {
		System.out.println("Testing random dequeue2");
		
		for (int i = 1; i <= 16; i++ ) {
			RandomQueueArray<Integer> rq = new RandomQueueArray<Integer>();
			int elements =  (int) Math.pow(2,i); 
			for(int k = 0; k < elements; k++) {
				rq.enqueue(k);
			}
			Stopwatch w = new Stopwatch();
			for (int k = 0; k < elements; k++) {
				rq.dequeue();
			}
			System.out.printf("%s;%s\n", elements, w.elapsedTime());
		}
	}

	//@Test 
	public void TestSampling() {
		System.out.println("Testing sampling");
		RandomQueueArray<Integer> q = new RandomQueueArray<Integer>();
		for (int i= 0; i < Math.pow(2, 16); i++) {
			q.enqueue(i);
		}
		int T = 16;
		Stopwatch w = new Stopwatch();
		for (int k =1; k <= T; k++) {
			int ceil = (int) Math.pow(2, k);
			for (int i = 0; i < ceil; i++) {
				q.sample();
			}
			System.out.printf("Sampling %s times: %s\n", Math.pow(2,k), w.elapsedTime());
		}
	}	 
}