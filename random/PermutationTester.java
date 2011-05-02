package random;
import java.util.Arrays;
import java.util.HashSet;

public class PermutationTester {

	/****
	 * For N = 9
	 * E = New randomly generated configuration 
P(E)= 208/5,7319223985890652557319223985891e-4
	 */
	private static HashSet<Integer> hash; // HashSet holding unique permutations
	private static Long perm = 0L; // N! 
	private static int N = 0 ; // Size of the variable domain 
	private static int count = 0; // Number of iterations before N! permutaions is found  
	private static Object[] o; // Object array of vars
	private static long sState1 = binom(N, N-3);
	private static long sState2 = binom(N, N-2);
	
	public static void main(String[] args) {
		N = 9;
		hash = new HashSet<Integer>(); 
		perm = factorial(N);

		o = new Object[N];
		for (int i = 0;i < N;i++) o[i] = i+1; 
		System.out.println("N!" + perm);
		hash.add(Arrays.deepHashCode(o));
		System.out.println(Arrays.toString(o));
		permutate();
		System.out.println("hashsize:" + hash.size());
		System.out.println("count:" +  count);
	}

	/***
	 * Generates permutations of the Object[]  
	 * @param o
	 * @param idx
	 */
	private static void permutate() {

		System.out.println("hash: " + hash.size());
		while (hash.size() < perm) {
			System.out.println("Hashsize: " +  hash.size());
			count++;

			threeEx();
			if (!hash.contains(Arrays.deepHashCode(o))) hash.add(Arrays.deepHashCode(o));
			//System.out.println(toString(o));
			twoEx();
			if (!hash.contains(Arrays.deepHashCode(o))) hash.add(Arrays.deepHashCode(o));
			//System.out.println(toString(o));
		}
	}

	/***
	 * Returns a string representation of 
	 * @param o
	 * @return
	 */
	private static String toString(Object[] o) {
		
		Object[] tmp = o.clone(); 
		return Arrays.toString(tmp);
	}

	/***
	 * Rotates three variables right in an Object[].
	 * Chooses 3 consecutive indices % N starting at a random 
	 * number between 0 and N, where N is the size of the Object[]  
	 */
	private  static void threeEx() {		
		int idx1 = (int) uniform(N);
		Object tmpTwo = o[idx1];
		o[idx1] = o[(idx1+1) % N];
		o[(idx1+1) % N] = tmpTwo;
		tmpTwo = o[(idx1+2) % N];
		o[(idx1+2) % N] = o[idx1];
		o[idx1] = tmpTwo;
		tmpTwo = null;
	}

	/***
	 * Swaps variable o[i] and o[i+1], where i is a random int between 0 and N-1 
	 * If i equals N-1 o[i] and o[0] are swapped  
	 */
	private static void twoEx() {
		int idx1 = (int) uniform(N);
		int idx2 = uniform(N);
		Object tmp = o[idx1];
		o[idx1] = o[idx2];
		o[idx2] = tmp;		
		tmp = null;
	} 

	/***
	 * Returns f!
	 * @param f
	 * @return
	 */
	private static Long factorial(int f) {
		long fac =1;
		for (int i = 1; i <= f; i++) {
			fac *= i;
		}
		return fac;
	}
	
	/**
     * Return an integer uniformly between 0 and N-1.
     */
    private static int uniform(int N) {
        return (int) (Math.random() * N);
    }
    
    /***
	 * Returns binomial C chooses k 
	 * @param C
	 * @param k
	 * @return
	 */
	private static long binom(int C, int k) {
		if (C <= k ) return 1;
		return (factorial(C)/((factorial(k)*factorial(C-k))));
	}
}
