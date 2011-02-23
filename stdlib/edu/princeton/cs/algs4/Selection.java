package edu.princeton.cs.algs4;
/*************************************************************************
 * Compilation: javac Selection.java Execution: java Selection N
 * 
 * Generate N random numbers between 0 and 1 and selection sort them.
 * 
 *************************************************************************/

public class Selection {

	// selection sort
	public static void sort(Comparable[] a) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			int min = i;
			for (int j = i + 1; j < N; j++) {
				if (less(a[j], a[min]))
					min = j;
			}
			exch(a, i, min);
			assert isSorted(a, 0, i);
		}
		assert isSorted(a);
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

	// is the array sorted from a[lo] to a[hi]
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
		sort(a);

		// display results
		for (int i = 0; i < N; i++) {
			System.out.println(a[i]);
		}
		System.out.println("isSorted = " + isSorted(a));
	}

}
