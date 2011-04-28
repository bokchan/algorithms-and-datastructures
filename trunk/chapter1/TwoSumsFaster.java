package chapter1;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TwoSumsFaster 
{ 
	int[] array=null;
	public static int length;

	public int find()
	{
		Arrays.sort(array);
		int N = array.length;
		int count =0;
		int high = array[N-1];
		int low = array[0];
		int start = 0;
		int end = N-1;
		int sum=100;

		while (high>low)
		{
			sum = high + low;
			if(sum>0)
			{
				// high skal være værdien på pladsen "end" i arrayet 
				// derfor skal du starte med at tælle "end" ned 
				// og så derefter sætte high til
				 end--;
				 high = array[end];
				
			}
			else if(sum<0)
			{
				// low skal være værdien på pladsen "start" i arrayet 
				// derfor skal du starte med at tælle "start" op 
				// og så derefter sætte low til
				 start++;
				 low = array[start];
				
			}
			else if(high==0 && low==0)
			{
				continue;
			}
			else if (sum==0)
			{
				count++;
				// her skal end og start tælles og og ned 
				// og high og low sættes 
				 end-- ;
				 start++;
				 high = array[end];
				 low = array[start];
				
			}
		}
		return count;
	}

	public static int[] createArray(String filename) throws IOException
	{
		Scanner scanner = new Scanner(new File(filename));
		int [] array = new int [scanner.nextInt()];
		for(int i=0; i < array.length; i++)  
		{ 
			if (i==0)
			{
				length = array[i];
			}
			else
			{
				int number = scanner.nextInt();
				array[i] = number;
			}
		}

		return array;
	}
}