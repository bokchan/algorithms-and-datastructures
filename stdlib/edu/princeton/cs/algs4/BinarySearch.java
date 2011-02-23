package edu.princeton.cs.algs4;

/*************************************************************************
 *  Compilation:  javac BinarySearch.java
 *  Execution:    java BinarySearch whitelist.txt < input.txt
 *
 *
 *  
 *************************************************************************/

import java.util.Arrays;

import edu.princeton.cs.stdlib.In;
import edu.princeton.cs.stdlib.StdIn;
import edu.princeton.cs.stdlib.StdOut;

public class BinarySearch {

	// precondition: array a[] is sorted
	public static int rank(int key, int[] a) {
		int lo = 0;
		int hi = a.length - 1;
		int count = 1;
		while (lo <= hi) {
			// Key is in a[lo..hi] or not present.
			int mid = lo + (hi - lo) / 2;
			if (key < a[mid]) {
				//StdOut.println(count);
				hi = mid - 1;
				count++;
				} 
			
			else if (key > a[mid]) {
				//StdOut.println(count);
				count++;
				lo = mid + 1;
			} 
			else
			{
				//StdOut.println(count);
				return mid;
			} 
		}
		return -1;
	}

	public static void main(String[] args) {
		int[] whitelist = In.readInts(args[0]);
		Arrays.sort(whitelist);

		// read key; print if not in whitelist
		while (!StdIn.isEmpty()) {
			int key = StdIn.readInt();
			if (rank(key, whitelist) == -1)
				StdOut.println(key);
		}
	}
}
