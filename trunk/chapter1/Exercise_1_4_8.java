package chapter1;

import java.util.Arrays;

import edu.princeton.cs.stdlib.StdRandom;
import edu.princeton.cs.stdlib.Stopwatch;

public class Exercise_1_4_8 {

	int[] input;

	public Exercise_1_4_8(int[] input) {

		this.input = input;
	}

	/***
	 * Finds number of pairs in a sorted array 
	 * @return
	 */
	private int findPairsSorted() {
		int pairs = 0;
		int i = 0;
		int tmp = input[i];
		int subset = 1;
		i++;
		//int[] a = new int[] {1,1, 2, 3,3,3,3, 7,7,7,9,9,10,11,13,13,13};
		while(i < input.length) {
			if (tmp == input[i]) {
				subset++;
				i++;
			} else {
				pairs+=binom(subset);
				tmp = input[i];
				subset = 1;
				i++;
			}
		}
		pairs+=binom(subset);
		return pairs;
	}

	private static int binom(int n) {
		
		return (n*(n-1)) / 2;
	}

	/***
	 * Finds number of pairs on an unsorted array.  
	 * @return
	 */
	private int findPairs() {
		int pairs = 0;
		String foundValues = "";
		int subset = 0;

		for (int i = 0; i < input.length; i++) {
			int temp = input[i];
			if (foundValues.indexOf(String.valueOf("|"+ temp + "|"))<0) {
				subset=1;
				foundValues+=  "|" + temp + "|"; 
				for (int j = i+1; j < input.length; j++) {
					if (input[j] == temp) {
						subset++;
					}
				}
				pairs += binom(subset);
			} 

		}
		return pairs;
	}

	public static void main(String[] args) {		
				
		Integer N = (int) Math.pow(2, 25);
		int[] a = new int[N]; 
		
		Stopwatch w = new Stopwatch();
		for (int i = 0; i< N; i++) {
			a[i] = StdRandom.uniform(N);
		}
		System.out.println("Array filled: " + w.elapsedTime());
		Exercise_1_4_8 e = new Exercise_1_4_8(a);
		
		Arrays.sort(a);
		System.out.println("Array sorted: " + w.elapsedTime());
		
		
		w = new Stopwatch();
		System.out.println(e.findPairsSorted() + "# of pairs ");
		System.out.println(N + " elements ; "+ w.elapsedTime() + ";time\n");
			}
}