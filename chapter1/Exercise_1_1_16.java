package chapter1;

import edu.princeton.cs.stdlib.StdOut;

public class Exercise_1_1_16 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StdOut.println(exR1(6));
	}
	
	public static String exR1(int n) {
		if (n <= 0) return "";
		return exR1(n-3) + n + exR1(n-2) + n;
	}

}
