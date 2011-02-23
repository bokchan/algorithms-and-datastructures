package edu.princeton.cs.algs4;

import edu.princeton.cs.stdlib.StdRandom;

/*************************************************************************
 * Compilation: javac Quick.java Execution: java Quick N Dependencies:
 * StdRandom.java
 * 
 * Generate N random real numbers between 0 and 1 and quicksort them.
 * 
 *************************************************************************/

public class Quick {

	// quicksort the array
	public static void sort(Comparable[] a) {
		StdRandom.shuffle(a);
		sort(a, 0, a.length - 1);
	}

	// quicksort the subarray from a[lo] to a[hi]
	private static void sort(Comparable[] a, int lo, int hi) {
		if (hi <= lo)
			return;
		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
		assert isSorted(a, lo, hi);
	}

	// partition the subarray a[lo .. hi] by returning an index j
	// so that a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
	private static int partition(Comparable[] a, int lo, int hi) {
		int i = lo;
		int j = hi + 1;
		Comparable v = a[lo];
		while (true) {

			// find item on lo to swap
			while (less(a[++i], v))
				if (i == hi)
					break;

			// find item on hi to swap
			while (less(v, a[--j]))
				if (j == lo)
					break; // redundant since a[lo] acts as sentinel

			// check if pointers cross
			if (i >= j)
				break;

			exch(a, i, j);
		}

		// put v = a[j] into position
		exch(a, lo, j);

		// with a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
		return j;
	}

	/***********************************************************************
	 * Rearranges the elements in a so that a[k] is the kth smallest element,
	 * and a[0] through a[k-1] are less than or equal to a[k], and a[k+1]
	 * through a[n-1] are greater than or equal to a[k].
	 ***********************************************************************/
	public static Comparable select(Comparable[] a, int k) {
		if (k < 0 || k >= a.length) {
			throw new RuntimeException("Selected element out of bounds");
		}
		StdRandom.shuffle(a);
		int lo = 0, hi = a.length - 1;
		while (hi > lo) {
			int i = partition(a, lo, hi);
			if (i > k)
				hi = i - 1;
			else if (i < k)
				lo = i + 1;
			else
				return a[i];
		}
		return a[lo];
	}

	/***********************************************************************
	 * Helper sorting functions
	 ***********************************************************************/

	// is v < w ?
	private static boolean less(Comparable v, Comparable w) {
		return (v.compareTo(w) < 0);
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

		// generate array of N random reals between 0 and 1
		int N = Integer.parseInt(args[0]);
		Double[] a = new Double[N];
		for (int i = 0; i < N; i++) {
			a[i] = Math.random();
		}

		// sort the array
		Quick.sort(a);

		// display results
		for (int i = 0; i < N; i++) {
			System.out.println(a[i]);
		}
		System.out.println();
		System.out.println("isSorted = " + isSorted(a));
		System.out.println();

		// display results using select
		for (int i = 0; i < N; i++) {
			Double ith = (Double) Quick.select(a, i);
			System.out.println(ith);
		}
		System.out.println();
	}
}
