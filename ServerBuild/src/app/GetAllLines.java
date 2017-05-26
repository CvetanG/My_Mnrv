package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class GetAllLines {
	
	public static final String fileWithPomList = "aaa.txt";
	
	private static List<String> getLinesFromTextFile() throws IOException  {
		List<String> myPoms = new ArrayList<>();
		File file = new File(fileWithPomList);
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String lineFromFile = scanner.nextLine();
			myPoms.add(lineFromFile);
		}
		scanner.close();
		return myPoms;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("Start");
		List<String> myJars = getLinesFromTextFile();
		Set<String> cleanJars = new TreeSet<>();
		for (String jar : myJars) {
			
			cleanJars.add(jar.toLowerCase());
//			String[] splitText = jar.split("/");
//			System.out.println(splitText[splitText.length - 1]); 
//			cleanJars.add(splitText[splitText.length - 1]); 
		}
		for (String jar : cleanJars) {
			
			System.out.println(jar); 
		}
	}
}
