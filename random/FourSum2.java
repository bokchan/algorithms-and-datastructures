package random;
import java.util.Arrays;

import edu.princeton.cs.stdlib.StdOut;

/*************************************************************************
 *  Compilation:  javac FourSum.java
 *  Execution:    java FourSum < input.txt
 *
 *  A program with N^4 running time. Read in N long integers
 *  and counts the number of 4-tuples that sum to exactly 0.
 *
 *  Limitations
 *  -----------
 *     - we ignore integer overflow
 *
 *************************************************************************/

public class FourSum2 {

    // print distinct 4-tuples (i, j, k, l) such that a[i] + a[j] + a[k] + a[l] = 0
    public static int printAll(int[] a) {
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                for (int k = j+1; k < N; k++) {
                    for (int l = k+1; l < N; l++) {
                        if (a[i] + a[j] + a[k] + a[l] == 0) {
                            StdOut.println(a[i] + " " + a[j] + " " + a[k] + " " + a[l]);
                        }
                    }
                }
            }
        }
        return cnt;
    } 

    // return number of distinct 4-tuples (i, j, k, l) such that a[i] + a[j] + a[k] + a[l] = 0
    public static int count(int[] a) {
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                for (int k = j+1; k < N; k++) {
                    for (int l = k+1; l < N; l++) {
                        if (a[i] + a[j] + a[k] + a[l] == 0) {
                            cnt++;
                        }
                    }
                }
            }
        }
        return cnt;
    } 

    public static void main(String[] args)  { 
        //int N = StdIn.readInt(); 
        //long[] a = new long[N];
        int[] a = new int[] {1,2,3,4,-1,-5,-8,-9,-3};
        Arrays.sort(a);
        StdOut.println(Arrays.toString(a));
//        for (int i = 0; i < N; i++) {
//            a[i] = StdIn.readLong();
//        }
        int cnt = count(a);
        StdOut.println(cnt);
        if (cnt < 10) {
            printAll(a);
        }
    } 
} 
