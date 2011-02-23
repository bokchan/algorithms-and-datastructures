package chapter1;

import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.algs4.Interval2D;
import edu.princeton.cs.stdlib.StdOut;
import edu.princeton.cs.stdlib.StdRandom;

public class Exercise_1_2_3 {
	static Interval2D[] intervals ;
	static int intersect = 0;
	int contain = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int N = 10;
		int min = 0;
		int max = 100;
		intervals = new Interval2D[N];
		for (int i = 0; i<N; i++) {
			Interval1D i1 = getInterval();
			Interval1D i2 = getInterval();
			Interval2D i2d = new Interval2D(i2, i2);
			intervals[i] = i2d;
		}
		
		intersects();
		StdOut.println(intersect);

	}
	public static Interval1D getInterval() {
		double low = StdRandom.uniform();
		double high = StdRandom.uniform();
		double tmp = Math.min(low, high); 
		high = Math.max(low, high);
		low = tmp;
		return new Interval1D(low, high);
	}
	
	public static void intersects() {
		
		for (int i = 0 ; i < intervals.length-1; i++) {
			Interval2D i1 = intervals[i];
			for (int j = i+1; j< intervals.length; j++) {
				Interval2D i2=  intervals[j];
				if (i1.intersects(i2)) intersect++;
			}
		}
	}
}
