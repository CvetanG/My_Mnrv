package confluence;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class UpdatePage03 {
	
	static final String MY_USER = "cgeorgiev";
	static final String MY_PASS = "cvetan012982";
	static final String MY_WEB = "https://minerva.atlassian.net/login";
//	static final String MY_WEB = "https://minerva.atlassian.net/wiki/pages/editpage.action?pageId=";
	
//	static final String PAGE_ID = "145948707";
//	https://minerva.atlassian.net/wiki/rest/api/content/145948707?expand=space,body.view,version,container
	
	public static WebClient myLogInPage() throws Exception {
		
		try (final WebClient webClient = new WebClient()) {

			// Get the first page
			final HtmlPage page1 = webClient.getPage(MY_WEB);

			// Get the form that we are dealing with and within that form, 
			// find the submit button and the field that we want to change.
			final HtmlForm form = page1.getFirstByXPath("//form[@id='form-crowd-login']");

			
			@SuppressWarnings("deprecation")
			final HtmlButton button = (HtmlButton) form.getHtmlElementsByTagName("button").get(0);
			System.out.println(button.asXml());

			final HtmlTextInput userField = form.getInputByName("username");
			final HtmlPasswordInput passField = form.getInputByName("password");

			// Change the value of the text field
				
			userField.setValueAttribute(MY_USER);
			passField.setValueAttribute(MY_PASS);
//			System.out.println(userField.asText());
//			System.out.println(passField.asText());

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
	
	public static void main(String[] args) throws Exception {
		WebClient webClient = myLogInPage();
		String singelInputFile = "https://minerva.atlassian.net/wiki/rest/api/content/145948710?expand=space,body.view,version,container";
		
		HtmlPage currentPage = (HtmlPage) webClient.getPage(singelInputFile);
		
		List<NameValuePair> response =currentPage.getWebResponse().getResponseHeaders();
		for (NameValuePair header : response) {
		     System.out.println(header.getName() + " = " + header.getValue());
		 }
		
		/*String pageObj = null;
		WebEntity pageEntity = null;
		try {
			WebRequest webRequest = new WebRequest(myUrl);
			WebResponse getPageResponse = webClient.loadWebResponse(webRequest);
			
			pageEntity = getPageResponse.getEntity();

			pageObj = IOUtils.toString(pageEntity.getContent());

			System.out.println("Get Page Request returned " + getPageResponse.getStatusLine().toString());
			System.out.println("");
			System.out.println(pageObj);
		} finally {
			if (pageEntity != null) {
				EntityUtils.consume(pageEntity);
			}
		}
		
		JSONParser parser = new JSONParser();
		JSONObject page = (JSONObject) parser.parse(pageObj);
		
		System.out.println(page.get("value").toString());*/
	}
	
}
