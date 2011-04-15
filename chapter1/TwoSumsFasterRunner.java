package chapter1;

import java.io.IOException;

import edu.princeton.cs.stdlib.StdOut;

public class TwoSumsFasterRunner 
{
	public static void main(String[] args) throws IOException
	{
		TwoSumsFaster twosum = new TwoSumsFaster();
		twosum.array  = TwoSumsFaster.createArray("C:\\1Kints.txt");
		StdOut.println(twosum.find());
	}
}