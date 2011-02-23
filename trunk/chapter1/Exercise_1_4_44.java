package chapter1;

import java.util.ArrayList;

import edu.princeton.cs.stdlib.StdOut;
import edu.princeton.cs.stdlib.StdRandom;


/***
 * Birthday problem 
 * @author Andreas
 *
 */
public class Exercise_1_4_44 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int testruns = 1000000;
		int[] results = new int[testruns];
		int N = 100000;
		
		 
		for (int i=0; i< testruns; i++) {
			int count = 0;
			ArrayList<Integer>  a= new ArrayList<Integer>();
			int tmp = (int) StdRandom.uniform(0, N-1);
			while(!a.contains(tmp)) {
				a.add(tmp);
				count++;
				tmp = (int) StdRandom.uniform(0,N-1);
				results[i] = count;
			} 			
		}
		int sum = 0;
		for (int i : results ) {
			sum +=i;
		} 
		StdOut.println(sum/(results.length));
		

	}
	/***
	 * Random numbers 
	 * Count the number of integers generated  before the first repetition   
	 */
	
}
