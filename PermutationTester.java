import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class PermutationTester {

	/**
	 * @param args
	 */

	private static HashSet<Integer> hash;
	private static Long perm = 0L;
	
	private static int N = 0;
	private static int count = 0;
	public static void main(String[] args) {
		N = 4;
		hash = new HashSet<Integer>();
		perm = factorial(N);
		
		Object[] o = new Object[N];
		for (int i = 0;i < N;i++) o[i] = i+1; 
		System.out.println("N!" + perm);
		hash.add(Arrays.deepHashCode(o));
		System.out.println(Arrays.toString(o));
		permutate(o,0);
		System.out.println(count);
	}


	/***
	 * Generates permutations of the input Object[] 
	 * @param o
	 * @param idx
	 */
	public static void permutate(Object[] o, int idx) {
		//System.out.println(idx);
		System.out.println("hash: " + hash.size());
		if (hash.size() < perm) {
			count++;
			Object[] tmp = threeEx(o, idx);
			System.out.println("o: "+ Arrays.toString(tmp));
			if (!hash.contains(Arrays.deepHashCode(tmp))) hash.add(Arrays.deepHashCode(tmp));
			
			tmp = twoEx(tmp, idx);
			System.out.println("o: "+ Arrays.toString(tmp));
			
			if (!hash.contains(Arrays.deepHashCode(tmp))) hash.add(Arrays.deepHashCode(tmp));
			
			if (idx == o.length-1) idx = 0; 
			else idx +=1;			
			permutate(tmp, idx);
		}
	}
	
	private static String toString(Object[] o) {
		String out = "";
		for (Object i : o) out += i.toString();
		return out;
	}

	/***
	 * Rotates three variables in the Object[].
	 * Chooses 3 variables starting at o[i]. 
	 * If i exceeds N-1 a new array is copied with all elements shifted right   
	 * @param o
	 * @param i
	 * @return
	 */
	public static Object[] threeEx(Object[] o, int i) {
			
			Object tmp = o[i % N];
			o[i] = o[(i+1) % N];
			o[(i+1) % N] = tmp;
			tmp = o[(i+2) % N];
			o[(i+2) % N] = o[i %N];
			o[i % N] = tmp;
			return o;
	}

	/***
	 * Swaps variable o[i] and o[i+1]. 
	 * If i exceeds N-1 o[0] and o[i] are swapped  
	 * @param o
	 * @param i
	 * @return
	 */
	private static Object[] twoEx(Object[] o, int i) {
			Object tmp = o[i % N];
			int j = (i+1) % N;
			o[i] = o[j];
			o[j] = tmp;
			return o;
	} 

	/***
	 * Returns f!
	 * @param f
	 * @return
	 */
	private static Long factorial(int f) {
		double log = 0.0;
		for (int i = 1; i <= f; i++ ) {
			log += Math.log10(i);
		}
		return (long) Math.ceil(Math.pow(10,log));
	}
}
