package chapter1;

import edu.princeton.cs.stdlib.StdOut;

public class Exercise_1_2_6 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StdOut.println(isCircularShift("TGACGAC", "ACTGACG"));

	}
	
	public static boolean isCircularShift(String s, String t) {
		if (s == t) return true;
		String x = s;
		for (int i = 0 ; i < s.length(); i++) {
			String prefix = x.substring(s.length()-1);
			String suffix = x.substring(0, s.length()-1);
			x = prefix.concat(suffix);
			if (t.equals(x)) {
				return true;				
			} 
		}
		
		return false;
		
	}

}
