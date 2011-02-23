package chapter1;

import edu.princeton.cs.stdlib.StdOut;

public class Permutations {
	/***
	 * 
	 * @param N 
	 * @param zeroidx
	 * @param k
	 */
	private static int findQuadruples(int[] N, int zeroidx, int k) {
		int zerosets = 0;
		//int binom = fact(N.length) /(fact(k)*fact((N.length - k)));
		int arrMainIdx = 0;
		while(N.length - arrMainIdx > k-1) 
		{
			int innerIdx1 = arrMainIdx+1;
			int innerIdx2 = innerIdx1 +1;
			int innerIdx3 = innerIdx2 +1;
			while(N.length - innerIdx3 > 0) {
				int[] set = new int[k];
				int setIdx = 0;
				set[setIdx] = N[arrMainIdx];
				setIdx++;
				set[setIdx] = N[innerIdx1];
				setIdx++; 
				set[setIdx] = N[innerIdx2];
				setIdx++;
				set[setIdx] = N[innerIdx3];

				if (innerIdx3-1 == innerIdx2 & innerIdx2-1 == innerIdx1) {	
					innerIdx3++;
					innerIdx1=arrMainIdx+1;
					innerIdx2 = innerIdx1+1;
				} else if(innerIdx2 < innerIdx3-1){
					innerIdx2++;
				} else  if (innerIdx1 < innerIdx2-1) {
					innerIdx1++;
					innerIdx2 = innerIdx1+1;
				}

				setIdx = 0;				
				if (isZeroSet(set))  {
					zerosets++;
				}
				set = new int[k];
			}  
			arrMainIdx++;
		}
		return zerosets;
	}
	
	private static boolean isZeroSet(int[] a) {
		int sum = 0;
		for (int i : a) {
			sum+= i;
		}
		return (sum == 0); 
	}
	
	private static void printlnSubSets(int[] arr) {
		Object[] sets = new Object[binom(arr.length,4)] ;
		int setCount = 0;

		// Make a set with four elements 
		// set low for array
		int arrMainIdx = 0;
		while(arr.length - arrMainIdx > 3) 
		{	 
			int innerIdx1 = arrMainIdx+1; 
			int innerIdx2 = innerIdx1 +1;
			int innerIdx3 = innerIdx2 +1;
			while( innerIdx1 < innerIdx2 && innerIdx2 < innerIdx3 && innerIdx3 < arr.length) {
				int[] set = new int[4];
				int setIdx = 0;
				set[setIdx] = arr[arrMainIdx];
				setIdx++;
				set[setIdx] = arr[innerIdx1];
				setIdx++; 
				set[setIdx] = arr[innerIdx2];
				setIdx++;
				set[setIdx] = arr[innerIdx3];

				StdOut.printf("%s %s %s %s\n", arrMainIdx, innerIdx1, innerIdx2, innerIdx3);
				if (innerIdx3-1 == innerIdx2 & innerIdx2-1 == innerIdx1) {	
					innerIdx3++;
					innerIdx1=arrMainIdx+1;
					innerIdx2 = innerIdx1+1;
				} else if(innerIdx2 < innerIdx3-1){
					innerIdx2++;
				} else  if (innerIdx1 < innerIdx2-1) {
					innerIdx1++;
					innerIdx2 = innerIdx1+1;
				}


				setCount++;
				setIdx = 0;
				set = new int[4];
			} 
			// increment main arr index 
			arrMainIdx++;
		}
		StdOut.println(setCount);
	}
	
	private static int binom(int n, int k) {
		int binom = fact(n) /(fact(k)*fact((n - k)));
		return binom;
	}
	
	private static int fact(int n) {

		// Base Case: 
		//    If n <= 1 then n! = 1.
		if (n <= 1) {
			return 1;
		}
		// Recursive Case:  
		//    If n > 1 then n! = n * (n-1)!
		else {
			return n * fact(n-1);
		}
	}


}
