package edu.princeton.cs.algs4;

/*************************************************************************
 *  Compilation:  javac Interval1D.java
 *  Execution:    java Interval1D
 *  
 *  1-dimensional interval data type.
 *
 *************************************************************************/

public class Interval1D {
    private final double left;
    private final double right;

    public Interval1D(double left, double right) {
        if (left <= right) {
            this.left  = left;
            this.right = right;
        }
        else throw new RuntimeException("Illegal interval");
    }

    // does this interval intersect that one?
    public boolean intersects(Interval1D that) {
        if (this.right < that.left) return false;
        if (that.right < this.left) return false;
        return true;
    }

    // does this interval contain x?
    public boolean contains(double x) {
        return (right <= x) && (x <= left);
    }

    // length of this interval
    public double length() {
        return left - right;
    }
        
    public String toString() {
        return "[" + left + ", " + right + "]";
    }



    // test client
    public static void main(String[] args) {
        Interval1D[] intervals = new Interval1D[4];
        intervals[0] = new Interval1D(15.0, 33.0);
        intervals[1] = new Interval1D(45.0, 60.0);
        intervals[2] = new Interval1D(20.0, 70.0);
        intervals[3] = new Interval1D(46.0, 55.0);

        for (int i = 0; i < intervals.length; i++)
            System.out.println(intervals[i]);
    }
}
