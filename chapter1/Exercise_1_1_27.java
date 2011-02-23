package chapter1;

import edu.princeton.cs.stdlib.StdOut;
import edu.princeton.cs.stdlib.StdRandom;

public class Exercise_1_1_27 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int size = 10;
		int range = 10000;
		int[][] a = new  int[size][2];
		for (int i= 0; i< size; i++) {
			a[i][0] = StdRandom.uniform(range);
			a[i][1] = StdRandom.uniform(range);
			StdOut.println("{" + a[i][0] + "," + a[i][1] + "}");
		}
		
	
		boolean[] res = prime(a);
		for (boolean b : res) {
			StdOut.print(b + "|");
		}
	}
	public static boolean[] prime(int[][] NN) { 
		boolean[] res = new boolean[NN.length];
		for (int i=0; i<NN.length; i++) {
			int a = NN[i][0];
			int b = NN[i][1];
			if (a < b) {
				for (int k = 2; k <= a; k++) {
					if (a%k ==0 && b%k == 0) { 
						res[i] = true;
						continue;
					} 
				}  
			} else {
				for (int k = 2; k <= b; k++) {
					if (a%k ==0 && b%k == 0) { 
						res[i] = true;
						continue;
					}
				}
			} 
		}
		
		return res;
	}

}
