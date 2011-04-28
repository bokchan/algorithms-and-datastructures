


public class RandomTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for (int n = 0; n < 1000; n++) {
		int i = StdRandom.uniform(5);
		int j = StdRandom.uniform(5);
		j = i == j ? (3 * (i + j)) % 5 : j;

		int k = StdRandom.uniform(5);
		k =  i == k | j == k ? 3 * (i + j) % 5 : k;
		System.out.println(i + ":"+ j + ":" + k);
		
		if (i == k | i == j | j == k) System.out.println("False");
			
		}
		System.out.println("true");
		String s = "I'm  very glad";
		
		for (String w : s.split("[^\\w&&[^']]+"))
			System.out.println(w.trim());
	}
	
}
