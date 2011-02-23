package chapter1;

import java.util.Arrays;

import edu.princeton.cs.algs4.Date;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.stdlib.StdIn;
import edu.princeton.cs.stdlib.StdOut;

public class Exercise_1_3_16 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StdOut.println(Arrays.toString(readDates()));
	}
	
	public static Date[] readDates() {
		
		Queue<Date> q = new Queue<Date>();
		String input = StdIn.readString();
		while(!input.equals("quit")) {
			Date d = null; 
			try { 
				q.enqueue(new Date(input));
			} catch (Exception e){
				StdOut.println("BAD INPUT");
			}
			input = StdIn.readString();
		}
		Date[] dates = new Date[q.size()];
		int size = q.size();
		for(int i = 0; i < size; i++) {
			dates[i] = q.dequeue();
		}
		return dates;
	}
}
