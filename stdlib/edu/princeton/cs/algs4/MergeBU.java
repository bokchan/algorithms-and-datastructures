package edu.princeton.cs.algs4;
/*************************************************************************
 * Compilation: javac MergeBU.java Execution: java MergeBU N
 * 
 * Generate N pseudo-random numbers between 0 and 1 and mergesort them. Uses
 * bottom-up mergesort.
 * 
 *************************************************************************/

public class MergeBU {

	// stably merge a[lo..m] with a[m+1..hi] using aux[lo..hi]
	private static void merge(Comparable[] a, Comparable[] aux, int lo, int m,
			int hi) {

		// copy to aux[]
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}

		// merge back to a[]
		int i = lo, j = m + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > m)
				a[k] = aux[j++];
			else if (j > hi)
				a[k] = aux[i++];
			else if (less(aux[j], aux[i]))
				a[k] = aux[j++];
			else
				a[k] = aux[i++];
		}

	}

	// bottom-up mergesort
	public static void sort(Comparable[] a) {
		int N = a.length;
		Comparable[] aux = new Comparable[N];
		for (int n = 1; n < N; n = n + n) {
			for (int i = 0; i < N - n; i += n + n) {
				int lo = i;
				int m = i + n - 1;
				int hi = Math.min(i + n + n - 1, N - 1);
				merge(a, aux, lo, m, hi);
			}
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
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}

	// generate N real numbers between 0 and 1, and mergesort them
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		Double[] a = new Double[N];
		for (int i = 0; i < N; i++)
			a[i] = Math.random();
		MergeBU.sort(a);
		for (int i = 0; i < N; i++)
			System.out.println(a[i]);

		System.out.println(MergeBU.isSorted(a));
	}
}
