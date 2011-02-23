package chapter1;

import java.util.Arrays;

import edu.princeton.cs.algs4.BinarySearch;
import edu.princeton.cs.stdlib.StdOut;
import edu.princeton.cs.stdlib.StdRandom;
import edu.princeton.cs.stdlib.Stopwatch;

public class Exercise_1_4_22 {
	static long fib1 = 1;
	static long fib2 = 1;
	static int[] a;

	public static void main(String[] args) {
		Exercise_1_4_22 e = new Exercise_1_4_22();
	}

	public Exercise_1_4_22() {

		int N = (int) Math.pow(2, 23);
		int input = StdRandom.uniform(N);

		a = new int[N];
		Stopwatch w1 = new Stopwatch();
		for (int i = 0; i < N; i++) {
			a[i] = StdRandom.uniform(N);
		}
		
		StdOut.println("Time to fill array: " + (w1.elapsedTime()));
		Arrays.sort(a);
		StdOut.println("Time to sort: " + (w1.elapsedTime()));
		StdOut.println("searching for..." + input );
		StdOut.println("Time for Binary Fibonacci Search: " + findBinarySearchFib(input, a));
		StdOut.println((w1.elapsedTime()));
		StdOut.println("Time for Binary Fibonacci Search: " + BinarySearch.rank(input, a));
		StdOut.println((w1.elapsedTime()));
	}

	public static boolean findBinarySearchFib(int input, int[] a) {
		boolean done = false;
		boolean found = false;
		int floor = 0;

		fibRecursive(input);

		int ceil = (int) fib1;
		int idx = floor;

		int count = 1;
		while (!done) {
			idx = (int) (floor + fib2);
			

			if (idx >= ceil || idx > a.length-1) { 
				done = true;
				continue;
			}
			
			StdOut.println("Number array splits: " + count  +  " floor: " + floor + " - ceil: " + ceil + " id: " + idx
					+ " fib1: " + fib1 + " fib2: " + fib2 + " a[idx]: " + a[idx]);
			

			if (a[idx] == input) {
				done = true;
				found = true;		
				continue;
			} else if (input > a[idx]){ 
				int i = floor;  
				floor = (int) (i + fib2); // fib2 == fib-2
				ceil = (int) (i + fib1 + fib2); // fib1 == fib-1
			} else {
				ceil = (int) (floor + fib2);
			}

			updateFib();
			count++;
		}
		return found;
	}

	public static void updateFib() {
		// set f1 = f1-1
		// and f2 = f1-2
		if (fib1 == 1) {
			fib2 = 0;
			fib1 = 1;
		}
		long tmp = fib1;
		fib1 = fib2;
		fib2 =  tmp - fib1;
		
	}

	public static void fibRecursive(long in) {
		fib1 = 1;
		fib2 = 0;

		while(fib1 < a.length) {
			long tmp =fib1; 
			fib1  = fib1 + fib2;
			fib2 = tmp;
		}
		updateFib();
	} 
}