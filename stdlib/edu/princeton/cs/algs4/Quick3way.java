package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.StdRandom;

/*************************************************************************
 * Compilation: javac Quick3way.java Execution: java Quick3way N
 * 
 * Generate N random real numbers between 0 and 1 and quicksort them. Uses
 * randomized quicksort with Dijkstra's Dutch National Flag 3-way partitioning.
 * 
 *************************************************************************/

public class Quick3way {

	// quicksort the array a[] using 3-way partitioning
	public static void sort(Comparable[] a) {
		sort(a, 0, a.length - 1);
		assert isSorted(a);
	}

	// quicksort the subarray a[lo .. hi] using 3-way partitioning
	private static void sort(Comparable[] a, int lo, int hi) {
		if (hi <= lo)
			return;
		int lt = lo, gt = hi;
		Comparable v = a[lo];
		int i = lo;
		while (i <= gt) {
			int cmp = a[i].compareTo(v);
			if (cmp < 0)
				exch(a, lt++, i++);
			else if (cmp > 0)
				exch(a, i, gt--);
			else
				i++;
		}

		// a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
		sort(a, lo, lt - 1);
		sort(a, gt + 1, hi);
		assert isSorted(a, lo, hi);
	}

	/***********************************************************************
	 * Helper sorting functions
	 ***********************************************************************/

	// is v < w ?
	private static boolean less(Comparable v, Comparable w) {
		return (v.compareTo(w) < 0);
	}

	// does v == w ?
	private static boolean eq(Comparable v, Comparable w) {
		return (v.compareTo(w) == 0);
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	/***********************************************************************
	 * Check if array is sorted - useful for debugging
	 ***********************************************************************/
	private static boolean isSorted(Comparable[] a) {
		return isSorted(a, 0, a.length - 1);
	}

	private static boolean isSorted(Comparable[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}

	// test client
	public static void main(String[] args) {

		// generate array of N random integers between 0 and 99
		int N = Integer.parseInt(args[0]);
		Double[] a = new Double[N];
		for (int i = 0; i < N; i++) {
			a[i] = (double) StdRandom.uniform(100);
		}

		// sort the array
		Quick3way.sort(a);

		// display results
		for (int i = 0; i < N; i++) {
			System.out.println(a[i]);
		}
		System.out.println("isSorted = " + isSorted(a));
	}

}
