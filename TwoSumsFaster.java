import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


public class TwoSumsFaster 
{ 
	int[] array=null;
	public int find()
	{
		Arrays.sort(array);
		int count =0;
		int start = 0;
		int end = array.length-1;
		int sum;

		while (start<end)
		{
			sum = array[start] + array[end];
			if (sum == 0)
			{
				count++;
				start++;
				end--;
			}
			else if(sum > 0)
			{
				end--;
			} else
			{
				start++;	
			} 
		}
		return count;
	}

	public static int[] createArray(String filename) throws IOException
	{
		Scanner scanner = new Scanner(new File(filename));
		int[] array = new int[scanner.nextInt()];
		
		for (int i = 0; i < array.length; i++) {
			int number = scanner.nextInt();
			array[i] = number;
		}
		 
		return array;
	}
}	

class TwoSumsFasterRunner 
{
	public static void main(String[] args) throws IOException
	{
		TwoSumsFaster twosum = new TwoSumsFaster();
		twosum.array  = TwoSumsFaster.createArray(args[0].toString());
		StdOut.println(twosum.find());
	}
}