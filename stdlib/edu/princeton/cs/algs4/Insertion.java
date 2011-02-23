package edu.princeton.cs.algs4;
/*************************************************************************
 *  Compilation:  javac Insertion.java
 *  Execution:    java Insertion N
 *  
 *  Insertion sort N random real numbers between 0 and 1.
 *
 *************************************************************************/

import java.util.Comparator;

public class Insertion {

	// use natural order and Comparable interface
	public static void sort(Comparable[] a) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
				exch(a, j, j - 1);
			}
			assert isSorted(a, 0, i);
		}
		assert isSorted(a);
	}

	// use a custom order and Comparator interface - see Section 3.5
	public static void sort(Object[] a, Comparator c) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			for (int j = i; j > 0 && less(c, a[j], a[j - 1]); j--) {
				exch(a, j, j - 1);
			}
			assert isSorted(a, c, 0, i);
		}
		assert isSorted(a, c);
	}

	// return a permutation that gives the elements in a[] in ascending order
	// do not change the original array a[]
	public static int[] indexSort(Comparable[] a) {
		int N = a.length;
		int[] index = new int[N];
		for (int i = 0; i < N; i++)
			index[i] = i;

		for (int i = 0; i < N; i++)
			for (int j = i; j > 0 && less(a[index[j]], a[index[j - 1]]); j--)
				exch(index, j, j - 1);

		return index;
	}

	/***********************************************************************
	 * Helper sorting functions
	 ***********************************************************************/

	// is v < w ?
	private static boolean less(Comparable v, Comparable w) {
		return (v.compareTo(w) < 0);
	}

	// is v < w ?
	private static boolean less(Comparator c, Object v, Object w) {
		return (c.compare(v, w) < 0);
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	// exchange a[i] and a[j] (for indirect sort)
	private static void exch(int[] a, int i, int j) {
		int swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	/***********************************************************************
	 * Check if array is sorted - useful for debugging
	 ***********************************************************************/
	private static boolean isSorted(Comparable[] a) {
		return isSorted(a, 0, a.length - 1);
	}

	// is the array sorted from a[lo] to a[hi]
	private static boolean isSorted(Comparable[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}

	private static boolean isSorted(Object[] a, Comparator c) {
		return isSorted(a, c, 0, a.length - 1);
	}

	// is the array sorted from a[lo] to a[hi]
	private static boolean isSorted(Object[] a, Comparator c, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (less(c, a[i], a[i - 1]))
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
		sort(a);

		// display results
		for (int i = 0; i < N; i++) {
			System.out.println(a[i]);
		}
		System.out.println("isSorted = " + isSorted(a));
	}
}
