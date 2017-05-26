package app;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CopyFiles02 {
	
	static final String MY_USER = "cgeorgiev";
	static final String MY_PASS = "cgeorgiev";
	static final String MY_WEB = "http://serverbuild1.minervanetworks.com:8080/";
	
	
	public static final String singelInputFile = "http://serverbuild1.minervanetworks.com:8080/"
			+ "job/11.IncomingMsgGateway_ML/ws/notification-server-simulator";
	
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
	
	public static void copyFiles() throws Exception {
		try {
			

			WebClient webClient = myLogIn();

			HtmlPage currentPage = (HtmlPage) webClient.getPage(singelInputFile);
			
			HtmlAnchor curAnch = currentPage.getFirstByXPath("//a[@href='pom.xml']");
			XmlPage xml = curAnch.click();
			
			System.out.println(xml.asXml());


		} catch (FailingHttpStatusCodeException ex) {
			ex.printStackTrace();
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		
		copyFiles();

	}
}
