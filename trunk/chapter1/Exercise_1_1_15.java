package chapter1;

import edu.princeton.cs.stdlib.StdOut;
import edu.princeton.cs.stdlib.StdRandom;

public class Exercise_1_1_15 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int size = 20;
		int range = 10;
		int[] a = new  int[size];
		for (int i= 0; i< size; i++) {
			a[i] = StdRandom.uniform(range);
		}
		int[] res = new int[range];
		res = histogram(a, range);
		int sum = 0;
		for (int i : res) {
			StdOut.print(i + " ");
			sum += i;
		}
		StdOut.println();
		StdOut.println("Sum of occurances " + sum);
		StdOut.println("Length of input " + a.length);
		
	}
	
	/**
	 * Counts the occurance of each distinct integer in a  
	 * @param a, array int[] 
	 * @param M, range of ints in a 
	 */
	public static int[] histogram(int a[], int M) {
		int[] res = new int[M];
		for (int i = 0; i < a.length; i++) {
			int val = a[i]; 
			res[val] = res[val] +1;
		}
		return res;
	}

}
