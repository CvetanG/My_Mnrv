package app;

import java.util.Scanner;


public class Sum {
	public static void main(String[] args) {
		
		Scanner inputReader = new Scanner(System.in);
		System.out.print("Insert a Number: ");
		int myInt = inputReader.nextInt();
		
		
		
		System.out.println(myInt * 5);
		inputReader.close();
	}
}
