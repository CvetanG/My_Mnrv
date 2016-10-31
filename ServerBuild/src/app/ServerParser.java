package app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class ServerParser {

	static final String MY_USER = "cgeorgiev";
	static final String MY_PASS = "cgeorgiev";
	static final String MY_WEB = "http://serverbuild1.minervanetworks.com:8080/login?from=%2F/";
//	static final String MY_WEB = "http://serverbuild1.minervanetworks.com:8080/";
	
	static String project = "70.iTvMgrAC_ML";

	public static void myLogIn() throws Exception {
		try (final WebClient webClient = new WebClient()) {

			// Get the first page
			final HtmlPage page1 = webClient.getPage(MY_WEB);

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
			
	        /*
	        List<?> listAllProjects = page2.getByXPath("//a[@class='model-link inside']");
	        Set<String> setAllProjects = new LinkedHashSet<>();
	        
	        for (Object object : listAllProjects) {
	        	setAllProjects.add(object.toString().split("\\/")[1]);
			}
	        for (String project : setAllProjects) {
	        	System.out.println(project);
	        }
	        */
	        
			final HtmlPage page3 = webClient.getPage(MY_WEB
					+ "job/"
					+ project
					+ "/configure"
					);

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
	}

	public static void main(String[] args) throws Exception {
		myLogIn();
	}
}
