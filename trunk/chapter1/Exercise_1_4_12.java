package chapter1;

import java.util.Arrays;

import edu.princeton.cs.stdlib.StdOut;

public class Exercise_1_4_12 {
	// Declare instance variables
	int[] a;
	int[] b;
	int size = 0;
	int b_idx = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Testing  
		int runs = 1;
		int initial = 100000;

		for (int p = 1; p < 2; p++) {
			int N = initial * p; 
			for (int i = 0; i < runs; i++) {
				Exercise_1_4_12 e = new Exercise_1_4_12(N);
				e.intersect();
				e.compare();
			}
			StdOut.println("*********\n");
		}
	}

	public Exercise_1_4_12(int N) {

		size = N;
		// Init arrays 
		a = new int[N];
		b = new int[N];
		// Populate arrays 
		for (int i = 0; i < N; i++) {	
			a[i]= (int)( Math.random() * (N));
			b[i]= (int)( Math.random() * (N));
		}

		a = new int[] {1,2,3};
		b = new int[] {3,4,5};

		// Sort arrays 
		Arrays.sort(a);
		Arrays.sort(b);
	} 

	/***
	 * 
	 * @param n, compare value from array a 
	 * @return returns true if found otherwise false
	 */
	public boolean find(int n) { 
		int b_tmp;
		// Init b_tmp to save one array access in inner if 
		while (b_idx < size && (b_tmp = b[b_idx]) <= n) {
			if (b_tmp == n) {
				b_idx++;

				return true;
			}
			b_idx++;
		}
		return false;
	}

	public boolean binarySearch(int n ) {
		return Arrays.binarySearch(a, n) > 0;
	}

	public void compare() {
		int aidx = 0; 
		int bidx = 0;

		while(aidx < a.length && bidx < b.length) {
			if (a[aidx] == b[bidx]) {
				StdOut.println(b[bidx]);
			} else if (a[aidx] > b[bidx]) {
				bidx++;
			}
			else {
				aidx++;
			} 
		}	

	}

	/***
	 * Iterate through array a, and for each distinct value find matching values in b  
	 */
	public void intersect() {

		int a_idx = 0;
		int a_prev = a[a_idx];
		int a_current = 0;

		while (a_idx < size)
		{
			a_current = a[a_idx];

			if (a_idx == 0 || a_current != a_prev ) {
				if (find(a_current))
					// Print out the number found in both a and b 
					StdOut.println(a_current); 
				a_prev = a_current; 
				a_idx++;
			} else {
				a_idx++;
			}
		}
	} 
}