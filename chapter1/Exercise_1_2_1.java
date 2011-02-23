package chapter1;

import java.awt.geom.Point2D;

import edu.princeton.cs.stdlib.StdOut;
import edu.princeton.cs.stdlib.StdRandom;



public class Exercise_1_2_1 {
	static Point2D.Double[] points ;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int N = 10;
		points = new Point2D.Double[N];
		Exercise_1_2_1 e = new Exercise_1_2_1();
		e.drawPoints(N);
		Object[] res = e.pairDelta();
		StdOut.println("{" + res[0].toString() + ", " + res[1] + "} " + res[2]);
		
	}

	public static void drawPoints(int N) {	
		for (int i=0; i < N; i++) {
			double x = StdRandom.uniform() * N;
			double y = StdRandom.uniform() * N;
			Point2D.Double p = new Point2D.Double();
			p.setLocation(x, y);
			points[i] = p;
			StdOut.println();		
			}
	}

	public static Object[] pairDelta() {
		Double min = -1.00;
		Point2D.Double p1 = new Point2D.Double();
		Point2D.Double p2 = new Point2D.Double();
		Object[] res = new Object[3];
		for (int i = 0; i< points.length-1; i++) 
		{
			double x1 = points[i].getX(); 
			double y1 = points[i].getY();
			for (int j = i+1; j< points.length; j++) {

				double x2 = points[j].getX();
				double y2 = points[j].getY();
				double tmp = Point2D.distance(x1, y1, x2, y2);
				if (i == 0) { min = tmp;
					p1 = points[i];
					p2 = points[j];
				} else if (tmp < min) {
					min = tmp;
					p1 = points[i];
					p2 = points[j];
				}
			}
		}
		res[0] = p1;
		res[1] = p2;
		res[2] = min;
		return res;
	}

}
