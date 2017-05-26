package app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class ServerParser {

	static final String MY_USER = "cgeorgiev";
	static final String MY_PASS = "cgeorgiev";
	static final String MY_WEB = "http://serverbuild1.minervanetworks.com:8080/";
	
//	static String project = "70.iTvMgrAC_ML";
	
//	public static FileWriter file = new FileWriter("output.txt", true);
//	public static PrintWriter out = new PrintWriter(file, true);
//	public static FileWriter file;
//	public static PrintWriter out;
	
	public static StringBuilder newMyUrl;
	public static StringBuilder newMyPage;
	
	public static Set<String> setAllProjects = new LinkedHashSet<>();
	
	public static final String[] folderToFind = new String[]{
			"jms"
	};

//	public static final String[] ext = new String[]{
//			".jar", ".xml"
//	};
	
	
	public static final String[] ext = new String[]{
			".war", ".ear", ".jar"
	};
	
	
	public static final String[] EXCL_START = new String[]{
			"classes" // and other formats you need
	};
	
//	public static final String[] EXCL_END = new String[]{
//			"..", "." // and other formats you need
//	};
	
	// To skip this folder
	public static boolean isContain(String tobeCheck) {
		for (String myString : EXCL_START) {
			if (tobeCheck.startsWith(myString)) {
				return true;
			}
		} 
//		for (String myString : EXCL_END) {
//			if (tobeCheck.endsWith(myString)) {
//				return true;
//			}
//		} 
		return false;
	}
	
	// Extension to find
	public static boolean isFinishing(String tobeCheck) {
		for (String myString : ext) {
			if (tobeCheck.endsWith(myString)) {
				return true;
			}
		} 
//		for (String myString : EXCL_END) {
//			if (tobeCheck.endsWith(myString)) {
//				return true;
//			}
//		} 
		return false;
	}
	
	// Folder to find
	public static boolean isContainFolder(String tobeCheck) {
		for (String myString : folderToFind) {
			if (tobeCheck.equals(myString)) {
				return true;
			}
		} 
		return false;
	}
	

	public static WebClient myLogIn() throws Exception {
		try (final WebClient webClient = new WebClient()) {

			// Get the first page
			final HtmlPage page1 = webClient.getPage(MY_WEB + "login?from=%2F/");

			//		        System.out.println(page1.asXml());

//			List<HtmlForm> formlist = (List<HtmlForm>) page1.getForms();
//			System.out.println(formlist.toString());
			//		        System.out.println(inputlist.toString());



			// Get the form that we are dealing with and within that form, 
			// find the submit button and the field that we want to change.
			final HtmlForm form = page1.getFormByName("login");
//			System.out.println(form.asXml());


			@SuppressWarnings("deprecation")
			final HtmlButton button = (HtmlButton) form.getHtmlElementsByTagName("button").get(0);

//			System.out.println(button.asXml());


			final HtmlTextInput userField = form.getInputByName("j_username");
			final HtmlPasswordInput passField = form.getInputByName("j_password");

			// Change the value of the text field
			userField.setValueAttribute(MY_USER);
			passField.setValueAttribute(MY_PASS);

			// Now submit the form by clicking the button and get back the second page.
			
			final HtmlPage page2 = button.click();
//			System.out.println(page2.asXml());
			
			Set<Cookie> cookie = webClient.getCookieManager().getCookies();

	        if(cookie != null){

	            Iterator<Cookie> i = cookie.iterator();

	            while (i.hasNext()) {

	                webClient.getCookieManager().addCookie(i.next());

	            }

	        }
			
	        //skip getting project names
	        List<?> listAllProjects = page2.getByXPath("//a[@class='model-link inside']");
	        
	        for (Object object : listAllProjects) {
	        	setAllProjects.add(object.toString().split("\\/")[1]);
			}
	        
	        
	        /*
			final HtmlPage page3 = webClient.getPage(MY_WEB
					+ "job/"
					+ project
					+ "/configure"
					);*/
			
	        return webClient;
	        
			
		}
			
			// try to get HTMLTable
//			labels.get(0).getParentNode().getNextSibling().asText()
//			HtmlTable table = (HtmlTable)page3.getFirstByXPath("//table[@class='fileList']");
//			for (int i = 0; i < (table.getRows().size() - 1); i++) {
//				
//				HtmlTableRow tr = table.getRow(i); 
//				
//				HtmlTableCell td00 = tr.getCell(0);
//				NamedNodeMap td00att= td00.getAttributes();
//				System.out.println("attedude " + td00att.getLength());
//				System.out.println(cell00.asXml());
//				HtmlTableCell td01 = tr.getCell(1);
//				System.out.println(cell01.asXml());
				
//				cell00.getAttributeNode("class");
//				
//				Map<String, DomAttr> attributes = cell00.getAttributesMap();
////				for (Map.Entry entry : attributes.entrySet()) {
////				    System.out.println(entry.getKey() + ", " + entry.getValue());
////				}
//				List<String> keys = new ArrayList<String>(attributes.keySet());
//				for (String key: keys) {
//				    System.out.println(key + ": " + attributes.get(key).asText());
//				}
				
//				if (td00.getAttributes() != null && td00.getAttribute("class").equals("icon-text icon-sm")) {
//			    	System.out.println(td01.asText());
//			    }
				
//				List<HtmlTableCell> cells = new ArrayList<>(); 
//				HtmlTableCell cell00 = row.getCell(0); 
//				HtmlTableCell cell01 = row.getCell(1);
//				cells.add(cell00);
//				cells.add(cell01);
//				
//				for (HtmlTableCell cell : cells) {
//					
//					    
//					}
//			    }
//				for (HtmlTableCell cell : row.getCell(2)) {
//			        System.out.println(cell.asText());
//			    }
//			}
//			System.out.println(page3.asXml());

/*			
			List<HtmlForm> formlist = (List<HtmlForm>) page1.getForms();
			System.out.println(formlist.toString());
			//	        List<HtmlForm> inputlist = (List<HtmlForm>) page1.getElementsByIdAndOrName(idAndOrName)();
			//	        System.out.println(inputlist.toString());



			// Get the form that we are dealing with and within that form, 
			// find the submit button and the field that we want to change.
			//	        final HtmlForm form = page1.getFormByName("simple_form signin-account-form validatable");
			final HtmlForm form = (HtmlForm) page1.getFirstByXPath("//form[@id='new_user']");
			System.out.println(form.asXml());

			//	        HtmlButton submitButton = (HtmlButton)page1.createElement("button");
			//	        submitButton.setAttribute("type", "submit");
			//	        form.appendChild(submitButton);

			final HtmlSubmitInput button = (HtmlSubmitInput) form.getInputsByValue("Sign In").get(0);
			//	        final HtmlSubmitInput button = (HtmlSubmitInput) form.getInputByName("commit");
			//	        final HtmlSubmitInput button = form.getFirstByXPath("//form[@id='new_user']");;

			System.out.println(button.asXml());


			final HtmlPasswordInput passField = form.getInputByName("user[password]");
			//	        final HtmlTextInput textField = form.getFirstByXPath("//input[@id='user_password']");


			// Now submit the form by clicking the button and get back the second page.
			final HtmlPage page2 = button.click();
			//	        HtmlPage newPage = submitButton.click();
			System.out.println(page2.asXml());
			*/
			
		
	}
public static List<String> listFilesRecursively(WebClient webClient, String myUrl, String myPage) throws FailingHttpStatusCodeException, IOException {
		
		List<String> myFileList = new ArrayList<>();
		
		newMyUrl = new StringBuilder() ;
		newMyPage = new StringBuilder();
		
//		file = new FileWriter("output.txt", true);
//		out = new PrintWriter(file, true);
		
		HtmlPage page = webClient.getPage(myPage);
		
		@SuppressWarnings("unchecked")
		List<DomElement> listAllFiles = (List<DomElement>) page.getByXPath("//img[@class='icon-text icon-sm']");
		for (DomElement myFile : listAllFiles) {
			System.out.println("File: " + myUrl + "/" + myFile.getParentNode().getNextSibling().asText());
			myFileList.add("File: " + myUrl + "/" + myFile.getParentNode().getNextSibling().asText());
		}
		
		@SuppressWarnings("unchecked")
		List<DomElement> listAllFolders = (List<DomElement>) page.getByXPath("//img[@class='icon-folder icon-sm']");
		
		
		for (DomElement object : listAllFolders) {
			if(isContain(object.getParentNode().getNextSibling().asText())) {
				continue;
			} else {
	        	System.out.println("Folder: " + myUrl + "/" + object.getParentNode().getNextSibling().asText());
//	        	myFileList.add("Folder: " + myUrl + "/" + object.getParentNode().getNextSibling().asText());
	        	
	            newMyUrl.append(myUrl);
	            newMyUrl.append("/");
	            newMyUrl.append(object.getParentNode().getNextSibling().asText());
	            
	            newMyPage.append(myPage);
	            newMyPage.append("/");
	            newMyPage.append(object.getParentNode().getNextSibling().asText());
	        	
	            listFilesRecursively(webClient, newMyUrl.toString(), newMyPage.toString());
        	
			}
		}
		
		return myFileList;
		
	}
	
	public static List<String> myFileList = new ArrayList<>();
	public static List<String> findFilesRecursively(WebClient webClient, String myUrl, String myPage) throws FailingHttpStatusCodeException, IOException {
		
		
		newMyUrl = new StringBuilder() ;
		newMyPage = new StringBuilder();
		
//		file = new FileWriter("output.txt", true);
//		out = new PrintWriter(file, true);
		
		HtmlPage page = webClient.getPage(myPage);
		
		@SuppressWarnings("unchecked")
		List<DomElement> listAllFiles = (List<DomElement>) page.getByXPath("//img[@class='icon-text icon-sm']");
		for (DomElement myFile : listAllFiles) {
			if(isFinishing(myFile.getParentNode().getNextSibling().asText())) {
				System.out.println("File: " + myUrl + "/" + myFile.getParentNode().getNextSibling().asText());
				myFileList.add(myUrl + "/" + myFile.getParentNode().getNextSibling().asText());
			}
		}
		
		@SuppressWarnings("unchecked")
		List<DomElement> listAllFolders = (List<DomElement>) page.getByXPath("//img[@class='icon-folder icon-sm']");
		
		
		for (DomElement object : listAllFolders) {
			if(isContain(object.getParentNode().getNextSibling().asText())) {
				continue;
			} else {
	        	System.out.println("Searching in: " + myUrl + "/" + object.getParentNode().getNextSibling().asText());
//	        	myFileList.add("Folder: " + myUrl + "/" + object.getParentNode().getNextSibling().asText());
	        	
	            newMyUrl.append(myUrl);
	            newMyUrl.append("/");
	            newMyUrl.append(object.getParentNode().getNextSibling().asText());
	            
	            newMyPage.append(myPage);
	            newMyPage.append("/");
	            newMyPage.append(object.getParentNode().getNextSibling().asText());
	        	
	            findFilesRecursively(webClient, newMyUrl.toString(), newMyPage.toString());
        	
			}
		}
		
		return myFileList;
		
	}
	
	public static List<String> findFiles(WebClient webClient, String myUrl, String myPage) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
		List<String> myFileList = new ArrayList<>();
		
		HtmlPage page = webClient.getPage(myPage);
		
		@SuppressWarnings("unchecked")
		List<DomElement> listAllFiles = (List<DomElement>) page.getByXPath("//img[@class='icon-text icon-sm']");
		for (DomElement object : listAllFiles) {
			if(isFinishing(object.getParentNode().getNextSibling().asText())) {
				System.out.println("File: " + myUrl + "/" + object.getParentNode().getNextSibling().asText());
				myFileList.add(myUrl + "/" + object.getParentNode().getNextSibling().asText());
			} else {
				continue;
			}
		}
		
		return myFileList;
	}
	
	public static List<String> listFiles(WebClient webClient, String myUrl, String myPage) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		List<String> myFileList = new ArrayList<>();
		HtmlPage page = webClient.getPage(myPage);
		@SuppressWarnings("unchecked")
		List<DomElement> listAllFiles = (List<DomElement>) page.getByXPath("//img[@class='icon-text icon-sm']");
		for (DomElement object : listAllFiles) {
				System.out.println("File: " + myUrl + "/" + object.getParentNode().getNextSibling().asText());
				myFileList.add(myUrl + "/" + object.getParentNode().getNextSibling().asText());
		}
		return myFileList;
	}
	
	// To List Recursively Folders
	public static List<String> listFoldersRecursively(WebClient webClient, String myUrl, String myPage) throws FailingHttpStatusCodeException, IOException {

		List<String> myFolderList = new ArrayList<>();

		newMyUrl = new StringBuilder() ;
		newMyPage = new StringBuilder();

		final HtmlPage page = webClient.getPage(myPage);
		@SuppressWarnings("unchecked")
		List<DomElement> listAllFolders = (List<DomElement>) page.getByXPath("//img[@class='icon-folder icon-sm']");
		for (DomElement object : listAllFolders) {
			if(isContain(object.getParentNode().getNextSibling().asText())) {
				continue;
			} else {
				myFolderList.add(myUrl + "/" + object.getParentNode().getNextSibling().asText());
				System.out.println("Folder: " + myUrl + "/" + object.getParentNode().getNextSibling().asText());
				newMyUrl.append(myUrl);
				newMyUrl.append("/");
				newMyUrl.append(object.getParentNode().getNextSibling().asText());

				newMyPage.append(myPage);
				newMyPage.append("/");
				newMyPage.append(object.getParentNode().getNextSibling().asText());

				listFoldersRecursively(webClient, newMyUrl.toString(), newMyPage.toString());
			}
		}
		return myFolderList;
	}
	
	// search recursively for folder
	public static List<String> searchFoldersRecursively(WebClient webClient, String myUrl, String myPage) throws FailingHttpStatusCodeException, IOException {
		
		List<String> myFolderList = new ArrayList<>();
		
		newMyUrl = new StringBuilder() ;
		newMyPage = new StringBuilder();
		
		final HtmlPage page = webClient.getPage(myPage);
		@SuppressWarnings("unchecked")
		List<DomElement> listAllFolders = (List<DomElement>) page.getByXPath("//img[@class='icon-folder icon-sm']");
		for (DomElement object : listAllFolders) {
			// check for searched folder
			if(isContainFolder(object.getParentNode().getNextSibling().asText())) {
				myFolderList.add(myUrl + "/" + object.getParentNode().getNextSibling().asText());
				System.out.println("FOLDER FOUND: " + myUrl + "/" + object.getParentNode().getNextSibling().asText());
				break;
			} else {
				newMyUrl.append(myUrl);
	            newMyUrl.append("/");
	            newMyUrl.append(object.getParentNode().getNextSibling().asText());
	            
	            newMyPage.append(myPage);
	            newMyPage.append("/");
	            newMyPage.append(object.getParentNode().getNextSibling().asText());
	        	
	            searchFoldersRecursively(webClient, newMyUrl.toString(), newMyPage.toString());
			}
		}
		return myFolderList;
	}
	
	public static void exportTxtFile(List<String> myList) throws IOException {
		
//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		Date date = new Date();
		
		FileWriter file = new FileWriter("output.txt", true);
		PrintWriter out = new PrintWriter(file, true);
//		out.write(dateFormat.format(date) + '\n');
		for (String myFile : myList) {
			out.write(myFile + '\n');
//			System.out.println(" ===== END PROJECT PARSING =====");
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
		sb.append("Elapsed time: ");
        sb.append(minutes);
        sb.append(" min, ");
        sb.append(seconds);
        sb.append(" sec. ");
        sb.append(milisec);
        sb.append(" milsec.");
        
		System.err.println(sb.toString());
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Start Program");
		long startTime = System.currentTimeMillis();
		
		WebClient webClient = myLogIn();
		
		Collection<String> removeCandidates = new LinkedList<>();
		removeCandidates.add("0.Clean_ML"); // All
		removeCandidates.add("0.Populate_Properties_ML"); //All
		removeCandidates.add("11.IncomingMsgGateway_ML"); //Done
		removeCandidates.add("11a.RPM_IncomingMsgGateway_ML"); //Done
		removeCandidates.add("11b.QoeConnector_ML"); //Done
		removeCandidates.add("12a.RPM_Poller_ML"); //Done
		removeCandidates.add("13.applets_ML"); //Done
		removeCandidates.add("14.Recommendations_adapter_ML"); //Done
		removeCandidates.add("14a.xTV-cas_ML"); //Done
		removeCandidates.add("14b.xTV-cas_ML_rpm"); //Done
		removeCandidates.add("14c.xTV-WS_ML"); //Done
		removeCandidates.add("14d.OC4J_dataservices_ML"); //Done
		
		removeCandidates.add("14e.RPM_OC4J_dataservices_ML"); //None
		removeCandidates.add("14f.RPM_VODDS_ML"); //None
		removeCandidates.add("14h.Billing_Volia_ML"); //None
		removeCandidates.add("14i.Billing_ITCC_ML"); //None
		removeCandidates.add("14.Widevine_CAS_ML"); //None
		removeCandidates.add("15.EPG-localepg_ML"); //Done
		removeCandidates.add("15a.RPM_localepg_ML"); //Done
		removeCandidates.add("15b.RPM_EPGIngest_ML"); //Done
		removeCandidates.add("16.Dispatcher_ML"); //Done
		removeCandidates.add("16a.RPM_Dispatcher_ML"); //Done
		removeCandidates.add("17.Billing_ML"); //Done
		removeCandidates.add("17a.RPM_billing_ML"); //Done
		removeCandidates.add("18.RPM_hospitalitydb_ML"); //Done
		removeCandidates.add("18b.hospitality_ML"); //Done
		removeCandidates.add("18c.RPM_hospitality_ML"); //Done
		removeCandidates.add("19.RPM_streamManagement_ML"); //Done
		removeCandidates.add("20.Enhydra_Caller_Info_ML"); //Done
		removeCandidates.add("25.Enhydra_VSM_ML"); //Done
		removeCandidates.add("25a.RPM_VSM_ML"); //Done
		
		removeCandidates.add("990.Create_RPM_ISO_ML"); //All
		removeCandidates.add("990a.Create_RPM_ISO_GN"); //All
		removeCandidates.add("990a.Nightly_Builds_Alviso_ML"); //All
		removeCandidates.add("990b.Nightly_Builds_Sofia_ML"); //All
		removeCandidates.add("990c.Nightly_Builds_Catania_ML"); //All
		removeCandidates.add("991.Publish_to_grover_ML"); //ISO
		removeCandidates.add("991a.Publish_IMG_to_grover_ML"); //ISO
		removeCandidates.add("991a.Publish_to_sofia_ML"); //ISO
		removeCandidates.add("991b.Publish_langskin_to_grover_ML"); //ISO
		removeCandidates.add("991e.Publish_Hospitality_to_grover_ML991c.Publish_warmstandby_to_grover_ML"); //ISO
		removeCandidates.add("991f.Publish_NRTCReporting_to_grover_ML"); //ISO
		removeCandidates.add("991g.Publish_QoE_to_grover_ML"); //ISO
		removeCandidates.add("991h.Publish_streamManagement_to_grover_ML"); //ISO
		removeCandidates.add("992.Label_ML"); //Label
		removeCandidates.add("999a.ML_Autoinstall");  //All


		setAllProjects.removeAll(removeCandidates);
		
//		for (String project : setAllProjects) {
		
		String[] projects = new String[] {
				"12.HAMessaging_ML"
		};
			for (String project : projects) {
				
				String myUrl= project + "/ws";
		        
		        String myPage = MY_WEB + "job/" + myUrl;
		        System.out.println("Connecting to: " + myPage);
		        
//		        System.out.println("list files recursevely");
//		        List<String> myListFiles = listFilesRecursively(webClient, myUrl, myPage);
		        //	        exportTxtFile(myListFiles);
		        
		        System.out.println("find files recursevely");
		        List<String> myFoundFiles = findFilesRecursively(webClient, myUrl, myPage);
		        for (String string : myFoundFiles) {
					System.out.println(string);
				}
//		        if(myFoundFiles.size() > 0) {
//		        	exportTxtFile(myFoundFiles);	
//		        }
//		        myFileList.clear();
		        
//		        System.out.println("find files");
//		        List<String> myFindFiles = findFiles(webClient, myUrl, myPage);
		        //	        exportTxtFile(myFindFiles);	
		        
//		        System.out.println("list files");
//		        List<String> myListFiles = listFiles(webClient, myUrl, myPage);
	//	        exportTxtFile(myListFiles);	
		        
	//	        System.out.println("list folder recursively");
	//	        List<String> myListFolder = listFoldersRecursively(webClient, myUrl, myPage);
	//	        exportTxtFile(myListFolder);
		        
//		        System.out.println("search for folder recursively");
//		        List<String> myFoundFolder = searchFoldersRecursively(webClient, myUrl, myPage);
	//	        exportTxtFile(myFoundFolder);
		        
	        }
		
		// to get the first item from Set 		
//		String project = setAllProjects.iterator().next();
			
		long endTime   = System.currentTimeMillis();
		duration(startTime, endTime);
		System.out.println("End Program");
	}
}
