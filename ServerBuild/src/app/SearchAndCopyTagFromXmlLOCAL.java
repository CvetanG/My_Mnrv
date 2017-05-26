package app;

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

public class SearchAndCopyTagFromXmlLOCAL {

//	public static String filename = "pom.xml";

	// file/tag/prop
	//	public static String expression = "/Employees/Employee[@emplid='3333']/email";
//	public static final String tagToFind = "/project/dependencies";
	public static final String tagToFind = "/project/dependencies/dependency/artifactId";
	public static final String fileWithPomList = "pom.xmls.txt";
	
	public static String[] depArray = tagToFind.split("\\/");
	public static String dep = depArray[depArray.length - 1];
	
	public static StringBuilder outputFile;
//	public static String outputFile = "LocalPomDep.txt";
	
//	public static final String singelInputFile = "temp.xml";
	public static final String singelInputFile = "ServerPom_DEPENDENCIES.txt";
	
	
	private static List<String> getPomsFromTextFile() throws IOException  {
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

	public static void parseXmlAndCopyToTxt(String fileName) throws SAXException, IOException, XPathExpressionException, ParserConfigurationException {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;

		try {
			builder = builderFactory.newDocumentBuilder();
			
			InputStream inputStream = new FileInputStream(fileName);
		
			Document xmlDocument = builder.parse(inputStream);


			XPath xPath =  XPathFactory.newInstance().newXPath();


			//read a string value
//			String email = xPath.compile(expression).evaluate(xmlDocument);

			//read an xml node using xpath
			Node node = (Node) xPath.compile(tagToFind).evaluate(xmlDocument, XPathConstants.NODE);

			//read a nodelist using xpath
			NodeList nodes = (NodeList) xPath.compile(tagToFind).evaluate(xmlDocument, XPathConstants.NODESET);

			Document newXmlDocument = builder.newDocument();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node02 = nodes.item(i);
				Node copyNode = newXmlDocument.importNode(node02, true);
				newXmlDocument.appendChild(copyNode);
				//root.appendChild(copyNode);
			}
			
			
			printXmlDocument(newXmlDocument);
			copyXmlDocument(newXmlDocument, fileName);

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
		
		outputFile = new StringBuilder();
		outputFile.append("LocalPom_");
		outputFile.append(dep);
		outputFile.append(".txt");
		DOMImplementationLS domImplementationLS =(DOMImplementationLS) document.getImplementation();
		LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
		String string = lsSerializer.writeToString(document);
		
		FileWriter file = new FileWriter(outputFile.toString(), true);
		PrintWriter out = new PrintWriter(file, true);
		out.write(string);
//		out.write('\n');
		System.out.println(string);
		out.close();
	}
	
	public static void main(String[] args) throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {
		parseXmlAndCopyToTxt(singelInputFile);
		System.out.println("End!!!");
	}
}
