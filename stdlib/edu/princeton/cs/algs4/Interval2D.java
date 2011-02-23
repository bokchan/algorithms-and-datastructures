package edu.princeton.cs.algs4;

import java.awt.geom.Point2D;

import edu.princeton.cs.stdlib.StdOut;

/*************************************************************************
 *  Compilation:  javac Interval2D.java
 *  Execution:    java Interval2D
 *  
 *  2-dimensional interval data type.
 *
 *************************************************************************/

public class Interval2D {
    private final Interval1D x;
    private final Interval1D y;

    public Interval2D(Interval1D x, Interval1D y) {
        this.x = x;
        this.y = y;
    }

    // does this interval intersect that one?
    public boolean intersects(Interval2D that) {
        if (!this.x.intersects(that.x)) return false;
        if (!this.y.intersects(that.y)) return false;
        return true;
    }

    // does this interval contain x?
    public boolean contains(Point2D p) {
        return x.contains(p.getX())  && y.contains(p.getY());
    }

    // area of this interval
    public double area() {
        return x.length() * y.length();
    }
        
    public String toString() {
        return x + " x " + y;
    }



    // test client
    public static void main(String[] args) {
        Interval1D interval1 = new Interval1D(15.0, 33.0);
        Interval1D interval2 = new Interval1D(45.0, 60.0);
        Interval2D interval = new Interval2D(interval1, interval2);
        StdOut.println(interval);
    }
}