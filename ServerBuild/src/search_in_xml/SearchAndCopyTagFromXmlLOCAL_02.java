package search_in_xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.xml.sax.SAXException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class SearchAndCopyTagFromXmlLOCAL_02 {

	public static String searchedFilename = "ADI.xml";

	// file/tag/prop
	//	public static String expression = "/Employees/Employee[@emplid='3333']/email";
//	public static final String tagToFind = "/project/dependencies";
	public static final String tagToFind = "Metadata";
//	public static final String fileWithPomList = "pom.xmls.txt";
	
	public static String[] depArray = tagToFind.split("\\/");
	public static String dep = depArray[depArray.length - 1];
	
	public static String outputFile = "Founds.txt";
	
//	public static final String singelInputFile = "temp.xml";
	public static final String singelInputFile = "/home/cvetan/Documents/Bugs/22. ITF-12863 End credits time/adi/abc.com/PCKG0000000000032327";
//	File allSearchedFiles = new File(singelInputFile + "/" + searchedFilename);
	
	/*private static List<String> getPomsFromTextFile() throws IOException  {
		List<String> myPoms = new ArrayList<>();
		File file = new File(fileWithPomList);
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String lineFromFile = scanner.nextLine();
			myPoms.add(lineFromFile);
		}
		scanner.close();
		return myPoms;
	}*/

	public static void parseXmlAndCopyToTxt(String fileName) throws SAXException, IOException, XPathExpressionException, ParserConfigurationException {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;

		try {
			builder = builderFactory.newDocumentBuilder();
			
			InputStream inputStream = new FileInputStream(fileName);
		
			Document xmlDocument = builder.parse(inputStream);


			XPath xPath =  XPathFactory.newInstance().newXPath();


			//read a string value
			String email = xPath.compile(tagToFind).evaluate(xmlDocument);
			System.out.println("email: " + email);
			
			//read an xml node using xpath
			Node node = (Node) xPath.compile(tagToFind).evaluate(xmlDocument, XPathConstants.NODE);
			System.out.println("node: " + node);

			//read a nodelist using xpath
			NodeList nodes = (NodeList) xPath.compile(tagToFind).evaluate(xmlDocument, XPathConstants.NODESET);

			Document newXmlDocument = builder.newDocument();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node02 = nodes.item(i);
				Node copyNode = newXmlDocument.importNode(node02, true);
				newXmlDocument.appendChild(copyNode);
//				root.appendChild(copyNode);
			}
			
			
			printXmlDocument(newXmlDocument);
//			copyXmlDocument(newXmlDocument, fileName);

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();  
		}
	}
	

	public static void printXmlDocument(Document document) {
		DOMImplementationLS domImplementationLS =(DOMImplementationLS) document.getImplementation();
		LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
		String string = lsSerializer.writeToString(document);
		System.out.println(string);
	}
	
	
	public static void copyXmlDocument(Document document, String fileName) throws IOException {
		
		DOMImplementationLS domImplementationLS =(DOMImplementationLS) document.getImplementation();
		LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
		String string = lsSerializer.writeToString(document);
		
		FileWriter file = new FileWriter(outputFile, true);
		PrintWriter out = new PrintWriter(file, true);
		out.write(string);
//		out.write('\n');
		System.out.println(string);
		out.close();
	}
	
	public static void duration(long startTime, long endTime) {
		long totalTime = endTime - startTime;
		
		int seconds = (int) (totalTime / 1000) % 60 ;
		int minutes = (int) ((totalTime / (1000*60)) % 60);
		int milisec = (int) (totalTime - ((seconds * 1000) + (minutes * 60 * 1000)));
		
//		System.out.print("Elapsed time: ");
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
	
	public static void main(String[] args) throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {
		System.out.println("Start Program");
		long startTime = System.currentTimeMillis();
		
		parseXmlAndCopyToTxt(singelInputFile + "/" + searchedFilename);
		
		long endTime   = System.currentTimeMillis();
		duration(startTime, endTime);
		System.out.println("End!!!");
	}
}
