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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class SearchAndCopyTagFromXmlSERVER {


	static final String MY_USER = "cgeorgiev";
	static final String MY_PASS = "cgeorgiev";
	static final String MY_WEB = "http://serverbuild1.minervanetworks.com:8080/";
	
	
	// file/tag/prop
	//	public static String expression = "/Employees/Employee[@emplid='3333']/email";
	public static final String tagToFind = "/project/dependencies";
	public static final String fileWithPomList = "pom.xmls.txt";
	
	public static String[] depArray = tagToFind.split("\\/");
	public static String dep = depArray[depArray.length - 1];
	
	public static StringBuilder outputFile;
	
//	public static final String singelInputFile = "pom.xml";
	public static final String singelInputFile = "http://serverbuild1.minervanetworks.com:8080/job/11.IncomingMsgGateway_ML/ws/notification-server-simulator/pom.xml";
	
	public static WebClient myLogIn() throws Exception {
		try (final WebClient webClient = new WebClient()) {

			// Get the first page
			final HtmlPage page1 = webClient.getPage(MY_WEB + "login?from=%2F/");

			// Get the form that we are dealing with and within that form, 
			// find the submit button and the field that we want to change.
			final HtmlForm form = page1.getFormByName("login");


			@SuppressWarnings("deprecation")
			final HtmlButton button = (HtmlButton) form.getHtmlElementsByTagName("button").get(0);

			final HtmlTextInput userField = form.getInputByName("j_username");
			final HtmlPasswordInput passField = form.getInputByName("j_password");

			// Change the value of the text field
			userField.setValueAttribute(MY_USER);
			passField.setValueAttribute(MY_PASS);

			// Now submit the form by clicking the button and get back the second page.
			
			final HtmlPage page2 = button.click();
			
			Set<Cookie> cookie = webClient.getCookieManager().getCookies();

	        if(cookie != null){

	            Iterator<Cookie> i = cookie.iterator();

	            while (i.hasNext()) {

	                webClient.getCookieManager().addCookie(i.next());

	            }

	        }
	        return webClient;
		}
	}
	
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

			Document newXmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node02 = nodes.item(i);
				Node copyNode = newXmlDocument.importNode(node02, true);
				newXmlDocument.appendChild(copyNode);
				//root.appendChild(copyNode);
			}

			copyXmlDocument(newXmlDocument, fileName);

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();  
		}
	}
	
/*
	public static void printXmlDocument(Document document) {
		DOMImplementationLS domImplementationLS =(DOMImplementationLS) document.getImplementation();
		LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
		String string = lsSerializer.writeToString(document);
		System.out.println(string);
	}
	*/
	
	public static void copyXmlDocument(Document document, String fileName) throws IOException {
		
		outputFile = new StringBuilder();
		outputFile.append("ServerPom_");
		outputFile.append(dep.toUpperCase());
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
	
	public static void main(String[] args) throws Exception {
//		List<String> myListPoms= getPomsFromTextFile();
//		
//		for (String pom : myListPoms) {
//			
//			parseXmlAndCopyToTxt(pom);
//		}
		WebClient webClient = myLogIn();
		URL url = new URL(singelInputFile);
		URLConnection conn = url.openConnection();
		
		parseXmlAndCopyToTxt(singelInputFile);
	}
}
