package chapter1;

import edu.princeton.cs.stdlib.StdOut;


public class Exercise_1_1_18 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StdOut.println(mystery(2, 25));
		StdOut.println(mysteryIterative(2, 25));
		StdOut.println(mysteryMultiplication(2, 25));
		StdOut.println(mystery(3, 11));

	}
	
	public static int mystery(int a, int b) {
		StdOut.print("(" + a + "," + b + ") ");
		if (b == 0) return 0;
		if (b % 2 == 0) return mystery(a+a, b/2);
		return mystery(a+a, b/2) + a;
		
	}
	
	public static int mysteryMultiplication(int a, int b) {
		StdOut.print("(" + a + "," + b + ") ");
		if (b == 0) return 1;
		if (b % 2 == 0) return mysteryMultiplication(a*a, b/2);
		return mysteryMultiplication(a*a, b/2) * a;
		
	}
	
	public static int mysteryIterative(int a, int b) {
		int sum = 0;
		for(int i = b; i > 0; i /= 2 ) {
			if (i%2 == 0) {
				a = a * 2;
			} else {
				sum += a;
				a = 2* a;
			}
		} 
		return sum;
	}

}
