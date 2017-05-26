package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SearchTextInFile {
	
//	private static final String ext = "jar";
	private static final String ext = "xml";
	private static final String ext2 = "pom.xml";
	private static List<String> myLines = new ArrayList<>();
	private static List<String> finalResult = new ArrayList<>();
	
	public static boolean isFinishing(String tobeCheck) {
//		if (tobeCheck.endsWith(ext)) {
		if (tobeCheck.endsWith(ext) && !tobeCheck.endsWith(ext2)) {
			return true;
		}
		return false;
	}
			
	private static void searchForText() throws IOException  {
		File file = new File("output.txt");
		Scanner scanner = new Scanner(file);
		
		
		while (scanner.hasNextLine()) {
		String lineFromFile = scanner.nextLine();
			myLines.add(lineFromFile);
		}
		scanner.close();
		
		for (String line : myLines) {
			if (isFinishing(line)) {
				System.out.println(line);
				finalResult.add(line);
			} 
		}
		
		exportTxtFile(finalResult);
		
	}
	
	public static void exportTxtFile(List<String> listString) throws IOException {
		
		StringBuilder fileName = new StringBuilder();
		fileName.append(ext);
		fileName.append("s.txt");
		FileWriter file = new FileWriter(fileName.toString(), true);
		PrintWriter out = new PrintWriter(file, true);
		for (String myFile : listString) {
			out.write(myFile + '\n');
		}
		out.write('\n');
		out.close();

	}

	public static void main(String[] args) throws IOException {
		searchForText();
	}
}
