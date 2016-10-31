package app;

import java.util.Scanner;

import util.RexLoginValidator;

public class Test {

	public static void main(String[] args) {
		
		
		Scanner inputReader = new Scanner(System.in);
		System.out.print("Insert Username to Check: ");
		String username = inputReader.nextLine();
		
		
		RexLoginValidator uval = new RexLoginValidator();
//		RexLoginValidator.validate(username);
		
		System.out.println(username + ": " + uval.validate(username));

	}
}
