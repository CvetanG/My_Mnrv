package app;

import java.util.ArrayList;
import java.util.List;

public class MyReplace {
	public static void main(String[] args) {
		String myString = "Cvetan's";
		List<String> myStrings = new ArrayList<>();
		myStrings.add(myString);
//		System.out.println(myString);
		
		System.out.println(myStrings.get(0));
		for (int i = 0; i < myStrings.size(); i++) {
			myStrings.set(i, myStrings.get(i).replace("'", "''"));
		}
		
		System.out.println(myStrings.get(0));
	}
}
