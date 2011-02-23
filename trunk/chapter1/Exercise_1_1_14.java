package chapter1;

import edu.princeton.cs.stdlib.StdOut;

public class Exercise_1_1_14 {
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StdOut.println(lg(1000));
	}
	
	
	public static int lg(int N) {
		int lgCount = 0;
		int n = 2;
		while (n < N) {
			n *= 2;
			lgCount++;
		}
		
		return lgCount;
	} 

}
