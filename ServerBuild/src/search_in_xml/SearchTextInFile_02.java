package search_in_xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class SearchTextInFile_02 {
	
	private static int count = 0;
	
	private static final String ext = "Name=\"Chapter\"";
	private static List<String> myLines = new ArrayList<>();
//	private static List<String> finalResult = new ArrayList<>();
	
	public static String searchedFilename = "ADI.xml";
//	public static final String inputDir = "/home/cvetan/Documents/Bugs/22. ITF-12863 End credits time/adi/abc.com/PCKG0000000000032327";
	public static final String inputDir = "/home/cvetan/Documents/Bugs/22. ITF-12863 End credits time/adi";

	
	public static boolean isContaining(String tobeCheck) {
		if (tobeCheck.contains(ext)) {
			return true;
		}
		return false;
	}
			
	private static void searchForText(String myDir) throws IOException  {
		File file = new File(myDir);
		Scanner scanner = new Scanner(file);
		
		
		while (scanner.hasNextLine()) {
		String lineFromFile = scanner.nextLine();
			myLines.add(lineFromFile);
		}
		scanner.close();
		
		for (String line : myLines) {
			if (isContaining(line)) {
				System.out.println(myDir);
				System.out.println(line);
//				finalResult.add(line);
				count++;
			} 
		}
		
//		exportTxtFile(finalResult);
		
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
	
	public static void duration(long startTime, long endTime) {
		long totalTime = endTime - startTime;
		
		int seconds = (int) (totalTime / 1000) % 60 ;
		int minutes = (int) ((totalTime / (1000*60)) % 60);
		int milisec = (int) (totalTime - ((seconds * 1000) + (minutes * 60 * 1000)));
		
		StringBuilder sb = new StringBuilder(64);
        sb.append(minutes);
        sb.append(" min, ");
        sb.append(seconds);
        sb.append(" sec, ");
        sb.append(milisec);
        sb.append(" milsec.");
        
		System.err.println("Elapsed time: " + sb.toString());
		System.out.println();
	}
	
	public static List<Path> getURLs(String myDir) {
		List<Path> filePaths = new ArrayList<>(); 
		try (Stream<Path> paths = Files.walk(Paths.get(myDir))) {
		      paths.forEach(filePaths::add);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		return filePaths;
	}
	
	public static boolean isFinishing(String tobeCheck) {
		if (tobeCheck.endsWith(searchedFilename)) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Start Program");
		System.err.println("Searching for expresion: " + ext);
		long startTime = System.currentTimeMillis();
		// SETUP
		Runnable notifier = new Runnable() {
			private int counter = 0;
		    public void run() {
		    	if (counter % 4 != 0) {
		    		System.out.print(".");
		    		counter++;
				} else {
					System.out.print('X');
					counter++;
				}
		    }
		};
		
//		Runnable notifier = new Runnable() {
//			private int counter = 0;
//			char[] animationChars = new char[] {'|', '/', '-', '\\'};
//			public void run() {
//				counter++;
//				int cur = counter % 4;
//				System.out.print(animationChars[cur] + "\n");
//			}
//		};

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		
		
		// IN YOUR WORK THREAD
		scheduler.scheduleAtFixedRate(notifier, 1, 1, TimeUnit.SECONDS);
		
		List<Path> myPaths = getURLs(inputDir);
		for (Path path : myPaths) {
			if (isFinishing(path.toString())) {
					searchForText(path.toString());
			}
		}
		System.out.println();
		if (count > 0) {
			System.out.println("Results Found: " + count);
		} else {
			System.out.println("No Results Found: ");
		}
		
		scheduler.shutdownNow();
		long endTime   = System.currentTimeMillis();
		duration(startTime, endTime);
		System.out.println("End!!!");
	}
	
	
}
