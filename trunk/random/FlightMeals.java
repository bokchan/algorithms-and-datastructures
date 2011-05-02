package random;
/***
 * 
 * @author Andreas Bok Andersen
 *
 */
public class FlightMeals {
	static double numMeals = 0;
	static int passengers = 160;
	static int[] aMeals = new int[] {45,45,45,45};
	static long subCount = 0;

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
					numMeals += mealcombinations(passengers, aMeals.clone(), i);
					//Decrement the number of a meals of a specific type 
					aMeals[i]--;
				}  else break;
			} 
			aMeals[i] = 0;	

		}
		
		System.out.println("Number of combinations: " + numMeals);
	}


	private static double mealcombinations(int p, int[] meals, int meal) {	
		subCount = 0;
		return binom(p,meals[meal]) *  mealSubcombinations(p-meals[meal], meals, meal+1);	
	}

	private static double mealSubcombinations(int p, int[] meals, int meal) {
		// we have reached the last mealtype
		if ( meal > meals.length - 1  ) return 1;
		
		// Case more than one person left 
		if (p > 1)  {
			for (int i = meal; i < meals.length; i++) {

				int m = meals[i];
				for (int k = 0; k< m; k++) {
					if (getMealCount(meals, i)>= p ) {
						if (i < aMeals.length-1) {
							subCount +=  binom(p, meals[i]) * mealSubcombinations(p-meals[i], meals.clone(), i+1);
							meals[i]--;
						} else {
							subCount +=  binom(p, meals[i]) * mealSubcombinations(p-meals[i], meals.clone(), i-1);
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
	private static long binom(int C, int k) {
		if (C <= k ) return 1;
		return (factorial(C)/((factorial(k)*factorial(C-k))));
	}

	/***
	 * Returns f!
	 * @param f
	 * @return
	 */
	private static Long factorial(int f) {
		double log = 0.0;
		for (int i = 1; i <= f; i++ ) {
			log += Math.log10(i);
		}
		return (long) Math.ceil(Math.pow(10,log));
	}


}