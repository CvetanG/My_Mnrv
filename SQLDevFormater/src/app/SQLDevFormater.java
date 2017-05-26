package app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SQLDevFormater {

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	static void writeFile(String text, String filename) throws FileNotFoundException {
		try(  PrintWriter out = new PrintWriter(filename)  ){
		    out.println( text );
		}
	}
	
	static String replaceSQL(String str) {
		String newString = str
				.replaceAll("('\\s+\\|\\|\\s+CHR\\(10\\)[\\s+]?[\\|]?[\\|]?)", "")
//				.replaceAll()
				;
		return newString;
	}

	public static void main(String[] args) throws IOException {
		String inpuFile = "input.txt";
		String outputFile = "output.txt";
		String stringFromFile = readFile(inpuFile, StandardCharsets.UTF_8);
		String outputString = replaceSQL(stringFromFile);
		
		// System.out.println(output);
		writeFile(outputString, outputFile);
		System.out.println("Done!");

	}

}
