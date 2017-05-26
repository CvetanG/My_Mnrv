package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

public class SearchAndCopyTagFromXmlSERVER2 {


	static final String MY_USER = "cgeorgiev";
	static final String MY_PASS = "cgeorgiev";
	static final String MY_WEB = "http://serverbuild1.minervanetworks.com:8080/";
	
	
	// file/tag/prop
	//	public static String expression = "/Employees/Employee[@emplid='3333']/email";
//	public static final String tagToFind = "/project/dependencies";
//	public static final String tagToFind = "/project/dependencies/dependency/artifactId";
	public static final String tagToFind = "/project";
	public static final String fileWithPomList = "pom.xmls.txt";
	public static final String filesToParse = "pom.xml";
	
	public static String[] depArray = tagToFind.split("\\/");
	public static String dep = depArray[depArray.length - 1];
	
	public static StringBuilder outputFile;
	
//	public static final String singelInputFile = "pom.xml";
//	public static final String singelInputFile = "http://serverbuild1.minervanetworks.com:8080/"
//			+ "job/11.IncomingMsgGateway_ML/ws/notification-server-simulator";
	
	public static final String tempXmlFile = "temp.xml";
	
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
			
//			final HtmlPage page2 = button.click();
			button.click();
			
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

	public static void getXmlPage(String singelInputFile, WebClient webClient) throws Exception {
		System.out.println("Connecting to: " + singelInputFile + "/" + filesToParse);
		
			HtmlPage currentPage = (HtmlPage) webClient.getPage(singelInputFile);
			
			HtmlAnchor curAnch = currentPage.getFirstByXPath("//a[@href='" + filesToParse + "']");
			XmlPage xml = curAnch.click();
			
			FileWriter file = new FileWriter(tempXmlFile, false);
			PrintWriter out = new PrintWriter(file, true);
			out.write(xml.asXml());
//			out.write('\n');
//			System.out.println("temp.xml Done!");
			out.close();
			
//			InputStream inputStream = new FileInputStream("temp.xml");
//			
//			Document xmlDocument = builder.parse(inputStream);
			
//			return xml;
		
	}
	
	public static Document parseXml(String fileName) throws SAXException, IOException, XPathExpressionException, ParserConfigurationException {
		
//		System.out.println("Start Parsing: " + fileName);
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document newXmlDocument = null;

		try {
			builder = builderFactory.newDocumentBuilder();
			
			InputStream inputStream = new FileInputStream(fileName);
		
			Document xmlDocument = builder.parse(inputStream);


			XPath xPath =  XPathFactory.newInstance().newXPath();


			//read a string value
//			String email = xPath.compile(expression).evaluate(xmlDocument);

			//read an xml node using xpath
//			Node node = (Node) xPath.compile(tagToFind).evaluate(xmlDocument, XPathConstants.NODE);

			//read a nodelist using xpath
			NodeList nodes = (NodeList) xPath.compile(tagToFind).evaluate(xmlDocument, XPathConstants.NODESET);

			newXmlDocument = builder.newDocument();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node02 = nodes.item(i);
				Node copyNode = newXmlDocument.importNode(node02, true);
				newXmlDocument.appendChild(copyNode);
				//root.appendChild(copyNode);
			}

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();  
		}
		
		return newXmlDocument;
	}
	
	public static void printXmlDocument(Document document) {
		DOMImplementationLS domImplementationLS =(DOMImplementationLS) document.getImplementation();
		LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
		String string = lsSerializer.writeToString(document);
		System.out.println(string);
	}
	
	public static void copyXmlDocument(Document document, String pomPath) throws IOException {
		
		outputFile = new StringBuilder();
		outputFile.append("ServerPom_");
		outputFile.append(dep.toUpperCase());
		outputFile.append(".txt");
		DOMImplementationLS domImplementationLS =(DOMImplementationLS) document.getImplementation();
		LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
		String string = lsSerializer.writeToString(document);
		
		FileWriter file = new FileWriter(outputFile.toString(), true);
		PrintWriter out = new PrintWriter(file, true);
		out.write('\n');
		out.write(pomPath);
		out.write('\n');
		out.write(string);
		out.write('\n');
//		System.out.println(string);
		out.close();
	}
	
	public static void duration(long startTime, long endTime) {
		long totalTime = endTime - startTime;
		
		int seconds = (int) (totalTime / 1000) % 60 ;
		int minutes = (int) ((totalTime / (1000*60)) % 60);
		int milisec = (int) (totalTime - ((seconds * 1000) + (minutes * 60 * 1000)));
		
		System.out.print("Elapsed time: ");
		StringBuilder sb = new StringBuilder(64);
        sb.append(minutes);
        sb.append(" min, ");
        sb.append(seconds);
        sb.append(" sec. ");
        sb.append(milisec);
        sb.append(" milsec.");
        
		System.err.println(sb.toString());
		System.out.println();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("Start Program");
		long startTime = System.currentTimeMillis();
		
		WebClient webclient = myLogIn();
		
//		final String singelInputFile = "http://serverbuild1.minervanetworks.com:8080/"
//				+ "job/11.IncomingMsgGateway_ML/ws/notification-server-simulator";
		
		List<String> myPomsPaths= getPomsFromTextFile();
		for (String myString : myPomsPaths) {
			
			String myStringSub = myString.substring(0, myString.length() - 8);
			String singelInputFile = "http://serverbuild1.minervanetworks.com:8080/job/"
					+ myStringSub;
			getXmlPage(singelInputFile, webclient);
			
			Document newXmlDocument = parseXml(tempXmlFile);
			
//		printXmlDocument(newXmlDocument);
			copyXmlDocument(newXmlDocument, singelInputFile + "/" + filesToParse);
		}
		
		
		long endTime   = System.currentTimeMillis();
		duration(startTime, endTime);
		System.out.println("End Program");
		
	}
}
