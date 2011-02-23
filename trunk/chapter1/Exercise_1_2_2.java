package chapter1;

import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.stdlib.StdOut;
import edu.princeton.cs.stdlib.StdRandom;

public class Exercise_1_2_2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = 10;
		Interval1D[] intervals = new Interval1D[N];
		for (int i = 0; i<N; i++) {
			double low = StdRandom.uniform(0, N);
			double high = StdRandom.uniform(0, N);
			double tmp = Math.min(low, high); 
			high = Math.max(low, high);
			low = tmp;
			intervals[i] = new Interval1D(low, high);
			
		}
		intersects(intervals);
	}
	
	public static void intersects(Interval1D[] intervals) {
		for (int i = 0; i< intervals.length-1; i++) {
			Interval1D i1 = intervals[i]; 
			for (int j = i+1; j < intervals.length; j++) {
				Interval1D i2 = intervals[j];
				if (i1.intersects(i2)) 
					StdOut.println(i1.toString() + " " + i2.toString());				
			}
		}
		
	}
	

}
