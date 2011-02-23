package chapter1;

import edu.princeton.cs.stdlib.StdOut;

public class Exercise_1_1_7 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double t = 9.0;
		while (Math.abs(t-(9/t)) > 0.001) {
			t =  (9.0 / t + t ) / 2;
		}
		StdOut.printf("%.5f\n", t);
		
		int sum = 0;
		for (int i = 1; i< 1000; i++) {
			for (int j = 0; j < i; j++) {
				sum++;
			}
		}
		StdOut.println(sum);
		
		sum = 0; 
		for (int i = 1	; i< 1000; i*=2) {
			for (int j = 0; j< i; j++) {
				sum++;
			}
			StdOut.println(i);
		}
		StdOut.println(sum);
		
		
		}

}
