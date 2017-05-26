package app;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Set;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;


public class CopyFiles {
	
	static final String MY_USER = "cgeorgiev";
	static final String MY_PASS = "cgeorgiev";
	static final String MY_WEB = "http://serverbuild1.minervanetworks.com:8080/";
	
	public static final String singelInputFile = "http://serverbuild1.minervanetworks.com:8080/"
			+ "job/11.IncomingMsgGateway_ML/ws/notification-server-simulator/pox.xml";
	
	
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
	
	public static void copyFromURL() throws IOException {

		URL u = new URL(singelInputFile);
		URLConnection uc = u.openConnection();
		String contentType = uc.getContentType();
		System.out.println(contentType);
		int contentLength = uc.getContentLength();
//		if (contentType.startsWith("text/") || contentLength == -1) {
//			throw new IOException("This is not a binary file.");
//		}
		InputStream raw = uc.getInputStream();
		InputStream in = new BufferedInputStream(raw);
		byte[] data = new byte[contentLength];
		int bytesRead = 0;
		int offset = 0;
		while (offset < contentLength) {
			bytesRead = in.read(data, offset, data.length - offset);
			if (bytesRead == -1)
				break;
			offset += bytesRead;
		}
		in.close();

		if (offset != contentLength) {
			throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
		}

		String filename = "exit.txt";
		FileOutputStream out = new FileOutputStream(filename);
		out.write(data);
		out.flush();
		out.close();
	}

	public static void main(String[] args) throws Exception {
		WebClient webClient = myLogIn();
		copyFromURL();
	}
}
