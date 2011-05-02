package random;
import java.util.Arrays;

import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.stdlib.StdOut;
import edu.princeton.cs.stdlib.Stopwatch;

public class FourSum {
	static int[] even;
	static int[] odd;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[] a = null;
		// Generates N distinct integers 
		a = generateRandom(1000);

		StringBuilder sb = new StringBuilder();
		Stopwatch w = new Stopwatch();
		Arrays.sort(a);

		sb.append("Array sorting:\t" + w.elapsedTime()+ "\t\t\n");
		sb.append("method: \t\t\ttime\t\tfound quadruples\n");
		int setCount = findQuadrupleBinarySearch(a);
		sb.append("findQuadrupleBinarySearch:\t" + w.elapsedTime()+ "\t\t" + setCount + "\n");
		setCount = splitArrayQuadruple(a);
		sb.append("splitArrayQuadruple:\t\t" + w.elapsedTime()+ "\t\t" + setCount + "\n");
		setCount = FourSum2.count(a);
		sb.append("FourSum2:\t\t" + w.elapsedTime()+ "\t\t" + setCount + "\n");

		// Print out results 

		StdOut.println(sb.toString());
	}

	/***
	 * Splits an array in even and odd numbers  
	 * @param array 
	 */
	private static void splitArray(int[] array) {

		// initialize two arrays of size array.length 
		int[] tmp_even = new int[array.length];
		int[] tmp_odd = new int[array.length];
		//counters for odd and even numbers   
		int counteven = 0;
		int countodd = 0; 
		// Loop through array, spliiting even and odd numbers 
		for (int i=0;i<array.length; i++) {
			if (array[i] % 2 == 0) {
				tmp_even[counteven] = array[i];
				if (array[i] < 0) {

				}
				counteven++;
			}else {
				tmp_odd[countodd] = array[i];
				if (array[i] < 0) {

				}
				countodd++;
			} 

		} 
		// Initialize even numbers array 
		even = new int[counteven];

		// Fill the array 
		for(int i = 0; i< counteven; i++) {
			even[i] = tmp_even[i];
		}
		// Initialize the odd numbers array 
		odd = new int[countodd];

		for(int i = 0; i < countodd; i++) {
			odd[i] = tmp_odd[i];	
		}
	}

	private static int splitArrayQuadruple(int[] array) {
		// Split the array 
		splitArray(array);

		int setCount = 0;

		// Get the setcount for the odd and even numbers array  
		setCount += findQuadrupleBinarySearch(odd);
		setCount += findQuadrupleBinarySearch(even);


		int arrMainIdx = 0;
		int delta;
		// Loop through array  
		while(even.length - arrMainIdx > 1)
		
		{

			int innerIdx1 = arrMainIdx+1;
			// Go through all permutations in the even array with 4 elements     
			while(even.length - innerIdx1 > 0) {
				delta = even[arrMainIdx] + even[innerIdx1];
				setCount+= findMatchingTwoSum(delta, odd);
				innerIdx1++;
			}  
			arrMainIdx++;
		}
		return setCount;
	}


	/***
	 * Searches an array for two numbers whose sum =-sum
	 *   
	 * @param sum 
	 * @param array the array to search 
	 * @return
	 */
	private static int findMatchingTwoSum(int sum, int[] array) {
		// Find upperbound 
		int upperbound = array[array.length-1];

		int setCount = 0;
		for (int i=0; i< array.length-1; i++){
			int delta = sum + array[i];
			// Check if a matching number can be found
			if (delta + upperbound < 0 || delta + array[i+1] > 0) continue;
			int rank = BinarySearch.rank(-delta, array); 
			if (rank > i) {
				setCount++;
			}
		}
		return setCount;
	} 

	/***
	 * Uses binarysearch to find quadruples equal to zero 
	 * @param array an array to search for quadruples
	 * @return
	 */
	private static int findQuadrupleBinarySearch(int[] array) {
		/*** 
		 * Loop through each value from low to high
		 * 2 sum in first inner loop 
		 * 3 sum in second inner loop
		 * Use binarysearch to find the fourth value
		 */
		int upperbound = array[array.length-1];
		int zerosets = 0;
		int mainLow = 0;
		int mainHigh = array.length-1;

		for (int i = 0; i < array.length; i++) {
			// Go through the array from bottom to top
			// Find the first sum of high and low  
			int innerHigh = mainHigh;
			int innerLow = mainLow;
			// For each low iterate from top to low  
			for (int j = innerHigh; j > innerLow+2; j--) {
				// Find the sum of high and low
				int deltaTwo = array[mainLow] + array[j];

				for (int k = j-1; k> innerLow+1; k--) {
					// Find the sum of the three distinct elements
					int deltaThree = deltaTwo + array[k];

					// break loop if fourth element can't be found 
					if (deltaThree + array[k-1] < 0  || deltaThree + array[mainLow+1] > 0) continue;
					// If deltaThree exists in the array there is a quadruple with sum 0					
					int rank = BinarySearch.rank(-deltaThree, array);
					if (rank > -1 && rank > mainLow && rank < k) {
						//StdOut.printf("{%s,%s,%s,%s}\n", array[mainLow], array[j], array[k], array[rank] );
						zerosets++;
					}
				} 
			}
			// Increment the lower value
			mainLow++;
		}
		return zerosets;
	} 

	/***
	 * Generates N distinct integers 
	 * @param size number of integrs 
	 * @param array 
	 * @return
	 */
	public static int[] generateRandom(int size) {
		int[] array = new int[size];
		double bounds = size;
		// TODO Auto-generated method stub
		bounds = size;
		Set set = new HashSet();

		int count= 0;
		while (count < size) {
			int i = (int) StdRandom.uniform(-bounds, bounds);
			if (set.add(i)) {
				array[count] = i; 
				count++;
			}
		}		
		// Since input is generated as random ints 
		return array;
		// Comparison of results between my algorithm and the algorithm in Sedgewick 
	}
}