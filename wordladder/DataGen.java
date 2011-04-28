package wordladder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DataGen {
	static String linebreak = System.getProperty("line.separator");
	static String dir = System.getProperty("user.dir");
	static String pathsep = System.getProperty("file.separator");
	static String filename =  "";

	private String[] alphabet = {
			"a","b","c","d",
			"e","f","g","h",
			"i","j","k","l",
			"m","n","o","p",
			"q","r","s","t",
			"u","v","x","y","z"};
	private ArrayList<String> ALPHABET ;
	private int alphasize = 0;
	private static String alphabetname = ""; 

	public boolean generateData(int size, int keylength, String type) throws IOException {
		setAlphabet(type);
		filename = size + "_" +  alphabetname + "_" + keylength +  "_output.txt";
		
		try {
		FileOutputStream fos = new FileOutputStream(filename);

		for (int i = 0; i< size;i++) {
			fos.write(generateKey(keylength).getBytes());
			fos.write(linebreak.getBytes());
		}

		fos.flush();
		fos.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void setAlphabet(String s) {
		if (s.equals("w")) {
			ALPHABET = new ArrayList<String>(Arrays.asList(alphabet));
			alphabetname = "alpha";
		}
		if (s.equals("d")) { 
			for (int i = 0; i< 10; i++) ALPHABET.add(String.valueOf(i));
			alphabetname = "num";
		} 
		if (s.equals("wd")) {
			ALPHABET = new ArrayList<String>(Arrays.asList(alphabet));
			alphabetname = "alphanum";
			for (int i = 0; i< 10; i++) 
				ALPHABET.add(String.valueOf(i));
		}
		alphasize = ALPHABET.size();
	} 

	private String generateKey(int k) {
		String key = "";

		for (int i = 0; i < k; i++) {
			key += ALPHABET.get(uniform(alphasize));
		} 
		return key;
	}
	
	public static int uniform(int N) {
        return (int) (Math.random() * N);
    }

	public static void main(String args[]) throws IOException {
		
		DataGen dfg = new DataGen();
		if (args.length< 6) {
			System.out.println(gethelp());
			System.exit(0);

		}
		HashMap<String, Object> params = new HashMap<String, Object>();

		for (int i = 0 ; i< args.length; i+=2) {
			params.put(args[i], args[i+1]);
		}
		boolean success = false;  	
		success = dfg.generateData(
				Integer.valueOf(params.get("-size").toString()), 
				Integer.valueOf(params.get("-ksize").toString()), 
				params.get("-ktype").toString());
		 
		if (success) System.out.println("Wrote datafile to: " + dir + pathsep + filename); 
		else System.out.print("An error happened. Please try again");
	}

	private static String gethelp() {
		StringBuilder sb = new StringBuilder();

		sb.append("*********************************************************************************\n");
		sb.append("*										*\n");
		sb.append("*	USAGE									*\n");
		sb.append("*	java DataGen -size [int>0] -ksize [int>0] -ktype [{w,d,wd}]: 		*\n");
		sb.append("*	-size: size of the input. An integer > 0 				*\n");
		sb.append("*	-ksize: length of key. 							*\n");
		sb.append("*	-ktype: the character class of the key. 				*\n");
		sb.append("*	w = [a-z], d = [0-9], wd = [a-z0-9] 					*\n");
		sb.append("*										*\n");
		sb.append("*	Example: Datafile with 10000 entries. Key is 5 alphanumeric chars	*\n");
		sb.append("*	java DataGen -size 10000 -ksize 5 -ktype wd				*\n");
		sb.append("*										*\n");
		sb.append("*********************************************************************************\n");
		return sb.toString();
	}
}