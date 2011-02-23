package chapter1;

import java.util.ArrayList;

import edu.princeton.cs.stdlib.StdOut;
import edu.princeton.cs.stdlib.StdRandom;

public class Exercise_1_4_45 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int testruns = 1000;
		int[] results = new int[testruns];
		int N = 1000;
		int sigma = (N-1)*(N)/2;
		// 0,1,2,3,4,5 = 15
		//  
		for (int i=0; i< testruns; i++) {
			ArrayList<Integer> a = new ArrayList<Integer>();
			/***
			 * If an integer is not found 
			 * add to an array of integers
			 * add integer to sum  
			 */
			int sum = 0;
			int count = 0;
			int tmp = (int) StdRandom.uniform(0,N);
			count++;
			while(sum != sigma) {
				if (!a.contains(tmp)) {
					a.add(tmp);
					sum+=tmp;
					results[i] = count;
				}
				count++;
				tmp = (int) StdRandom.uniform(0, N);
			} 			
		}
		
		int sum = 0;
		for (int i : results ) {
			sum+= i;
		} 
		StdOut.println(sum/(results.length));
	}

}
