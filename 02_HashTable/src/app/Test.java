package app;

import java.util.Hashtable;

public class Test {
	public static void main(String[] args) {
		/*Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();
		numbers.put("one", 1);
		numbers.put("two", 2);
		numbers.put("three", 3);
		
		numbers.put("two", 22);
		numbers.replace("two", 222);
		
		
		System.out.println(numbers.get("two"));
		System.out.println(numbers.size());
		System.out.println(numbers.values());*/
		
		/*int a = 7;
		
		a = 8;
		
		System.out.println(a);*/
		
		/*String s1 = "William";
		
		String s2 = s1;
		
		System.out.println("String 1: " + s1);
		System.out.println("String 2: " + s2);
		
		s2 = "Charles";

		System.out.println("String 1: " + s1);
		System.out.println("String 2: " + s2);*/
		
		factorial(5);
	}
	public static int factorial(int n) {
		int result;
		if (n <= 1) // base case
			return 1;
		else {
			result = (n * factorial(n - 1));
			return result;
		}
	}
}
