package chapter1;

import edu.princeton.cs.stdlib.In;
import edu.princeton.cs.stdlib.StdOut;

public class QFCount {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		In in = new In("src\\chapter1\\tiny1_5_1.txt");
	

		int N = in.readInt();
		QF qf = new QF(N);

		// read in a sequence of pairs of integers (each in the range 0 to N-1),
		// calling find() for each pair: If the members of the pair are not already
		// call union() and print the pair.
		while (!in.isEmpty()) {
			int p = in.readInt();
			int q = in.readInt();
			if (!qf.find(p, q)) 
				qf.unite(p, q);
			StdOut.println("# of array accesses: " + qf.getArrayAccessCount());
			
		}
		for (int i = 0; i < N; i++) {
			StdOut.print(i);
		}
		
		StdOut.println("");
		
		int[] a = qf.getId();
		for (int i : a)
		StdOut.print(i);
				
	}

}
