package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CopyLinesRegex {
	
	public static final String inputFile = "ServerPom_DEPENDENCIES.txt";
	public static final String outputFile = "Jars_From_Pom.txt";
	
	public static String pattern1 = "(http:\\/\\/serverbuild1\\.minervanetworks.com:8080\\/job\\/)(\\w+.?_?\\/?)+";
	
	public static String pattern2 = "((\\s+)<groupId>)";
	public static Pattern r2 = Pattern.compile(pattern2, Pattern.DOTALL);
	
	private static List<String> getPomsFromTextFile() throws IOException  {
		List<String> myPoms = new ArrayList<>();
		File file = new File(inputFile);
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String lineFromFile = scanner.nextLine();
			myPoms.add(lineFromFile);
		}
		scanner.close();
		return myPoms;
	}

	
	public static void copyStringInTxt(String line) throws IOException {
		
		
		FileWriter file = new FileWriter(outputFile, true);
		PrintWriter out = new PrintWriter(file, true);
		out.write(line);
		System.out.println(line);
		out.close();
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("Start");
		List<String> myJars = getPomsFromTextFile();
		List<String> finalJars = new ArrayList<>();
//		for (String line : myJars) {
//			if (line.matches(pattern1)) {
//				System.out.println(line);
//			}
//			if (line.matches(pattern2)) {
//				System.out.println(line);
//			} else {
//				
//			}
//		}
		
		for (int i = 0; i < myJars.size(); i++) {
			if (myJars.get(i).matches(pattern1)) {
				System.out.println(myJars.get(i));
			}
			if (myJars.get(i).matches(pattern2)) { // check for <groupId>
				// check for com.minerva
				String temp = myJars.get(++i);
				String[] tempComp = temp.split("\\.");
				if (tempComp.length > 1 && tempComp[1].startsWith("minerva")) {
					
					System.out.println(myJars.get(3+i) + ".jar"); // get the jar file
				}
			}
		}
		
		for (String jar : finalJars) {
			System.out.println(jar); 
		}
	}
}
