package chapter1;
import edu.princeton.cs.stdlib.In;
import edu.princeton.cs.stdlib.StdOut;

public class UFCount {
	public static void main(String[] args) {

		In in = new In("src\\chapter1\\tiny1_5_1.txt");

		int N = in.readInt();
		UF uf = new UF(N);

		// read in a sequence of pairs of integers (each in the range 0 to N-1),
		// calling find() for each pair: If the members of the pair are not already
		// call union() and print the pair.
		while (!in.isEmpty()) {
			int p = in.readInt();
			int q = in.readInt();
			if (!uf.find(p, q)) 
				uf.union(p, q);
			StdOut.println("# of components" + uf.count());
		}
		
		for (int i = 0; i < N; i++) {
			StdOut.print(i);
		}
		
		StdOut.println("");
		
		int[] a = uf.getArray();
		for (int i : a)
		StdOut.print(i);
	}
} 