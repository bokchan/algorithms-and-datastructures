package experiments;

import java.math.BigInteger;



public class FlightMealsBigInt {
	static BigInteger numMeals = BigInteger.ZERO;
	static int passengers = 3;
	static int[] aMeals = new int[] {2,2,2};
	static BigInteger subCount = BigInteger.ZERO;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//For each type of meal
		for (int i = 0; i < aMeals.length; i++) {
			//For each meal
			int m =  aMeals[i];
			for (int k = 0; k < m; k++) {
				// Check that there is still enough meals left 
				if (getMealCount(aMeals, i)>= passengers ) {
					numMeals.add(mealcombinations(passengers, aMeals.clone(), i));
					//Decrement the number of a meals of a specific type 
					aMeals[i]--;
				}  else break;
			} 
			aMeals[i] = 0;	

		}
		System.out.println(numMeals);
	}


	private static BigInteger mealcombinations(int p, int[] meals, int meal) {	
		subCount = BigInteger.ZERO;
		
		return binom(p,meals[meal]).multiply(mealSubcombinations(p-meals[meal], meals, meal+1));	
	}

	private static BigInteger mealSubcombinations(int p, int[] meals, int meal) {
		// we have reached the last mealtype
		if ( meal > meals.length - 1  ) return BigInteger.ONE;
		
		// Case more than one person left 
		if (p > 1)  {
			for (int i = meal; i < meals.length; i++) {

				int m = meals[i];
				for (int k = 0; k< m; k++) {
					if (getMealCount(meals, i)>= p ) {
						if (i < aMeals.length-1) {
							subCount.add(binom(p, meals[i]).multiply(mealSubcombinations(p-meals[i], meals.clone(), i+1)));
							meals[i]--;
						} else {
							subCount.add(binom(p, meals[i]).multiply(mealSubcombinations(p-meals[i], meals.clone(), i-1)));
							meals[i]--;
						}
					}
				}
			}	
			return subCount;
		}
		else if (p == 1) {
			// One person left. Number of combinations equal the number of food types left.    
			return binom(meals.length -meal, p);
		} else {				
			// Zero persons left.   
			return binom(p, meals[meal]);

		}
	}

	/***
	 * Gets the number of meals from a given index in the mealarray
	 * @param meals
	 * @param i
	 * @return
	 */
	private static int getMealCount (int[] meals, int i) {
		int c = 0;
		for (;i <  meals.length; i++) c+=meals[i];
		return c;
	}

	/***
	 * Returns binomial C chooses k 
	 * @param C
	 * @param k
	 * @return
	 */
	private static BigInteger binom(int C, int k) {
		if (C <= k ) return BigInteger.ONE;
		return (factorial(C).divide((factorial(k)).divide(factorial(C-k))));
	}

	/***
	 * Returns f!
	 * @param f
	 * @return
	 */
	private static BigInteger factorial(int f) {
		double log = 0.0;
		for (int i = 1; i <= f; i++ ) {
			log += Math.log10(i);
		}
		return BigInteger.valueOf((long)Math.ceil(Math.pow(10,log)));
	}


}