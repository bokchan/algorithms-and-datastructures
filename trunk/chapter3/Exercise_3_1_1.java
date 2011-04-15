package chapter3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.princeton.cs.algs4.ST;
public class Exercise_3_1_1 {
	private ST<String,Double> gradescore;
	private ST<String, Integer> grades; 
	/**
	 * @param args
	 * @throws NumberFormatException 
	 * @throws IOException 
	 */
	
	public Exercise_3_1_1() throws NumberFormatException, IOException {
		grades = new ST<String, Integer>();
		init();
		getInput();
		
		for (String key : grades.keys()) {
			System.out.printf("Grade:\t %s\t#: %s\t\n",key, grades.get(key).intValue());
		}
		
		System.out.println("Average: " + getAverage());
	} 

	private double getAverage() {
		double sum = 0.0;
		int count = 0;
		for (String key: grades.keys()) {
			count += grades.get(key).intValue(); 
			sum += grades.get(key).intValue() * gradescore.get(key).doubleValue(); 
		}
		return sum / count ;
		
	}
	
	
	private void getInput() throws NumberFormatException, IOException {
		String grade = ""; 
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Input grade");
		while ((grade = reader.readLine()) != null) {
			if (grade.equals("exit")) return;
			if (grades.contains(grade)) {
				grades.put(grade, grades.get(grade).intValue() + 1);
			} else 
			grades.put(grade, 1);
			System.out.println("Input grade");
		}
	}
	
	private void init() {
		
		gradescore = new ST<String,Double>();
		gradescore.put("A+", 4.33);
		gradescore.put("A", 4.00);
		gradescore.put("A-", 3.67);
		gradescore.put("B+", 3.33);
		gradescore.put("B", 3.00);
		gradescore.put("B-", 2.67);
		gradescore.put("C+", 2.33);
		gradescore.put("C", 2.00);
		gradescore.put("C-", 1.67);
		gradescore.put("D", 1.00);
		gradescore.put("F", 0.00);
		
	}
	
	public static void main(String[] args) throws IOException {
		Exercise_3_1_1 ex = new Exercise_3_1_1();
		
	}

}
