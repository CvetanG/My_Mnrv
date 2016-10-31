package app;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class BonuslyStart {
	
	static final String MY_EMAIL = "cgeorgiev@minervanetworks.com";
	static final String MY_PASS = "cvetan012982";
	
	public static void submittingForm() throws Exception {
		
	    try (final WebClient webClient = new WebClient()) {

	        // Get the first page
	        final HtmlPage page1 = webClient.getPage(""
	        		+ "https://bonus.ly/users/sign_in?email="
	        		+ MY_EMAIL);

	        System.out.println(page1.asXml());
	        
	        List<HtmlForm> formlist = (List<HtmlForm>) page1.getForms();
	        for (HtmlForm htmlForm : formlist) {
				
	        	System.out.println(htmlForm.toString());
			}
	        
//	        List<HtmlForm> inputlist = (List<HtmlForm>) page1.getElementsByIdAndOrName(idAndOrName)();
//	        System.out.println(inputlist.toString());
	        
//	        final HtmlForm form = page1.getFormByName("simple_form signin-account-form validatable");
	        final HtmlForm form = formlist.get(0);
//	        final HtmlForm form = (HtmlForm) page1.getFirstByXPath("//form[@id='new_user']");
	        System.out.println(form.asXml());
	        		
//	        HtmlButton submitButton = (HtmlButton)page1.createElement("button");
//	        submitButton.setAttribute("type", "submit");
//	        form.appendChild(submitButton);
	        
//	        final HtmlSubmitInput button = (HtmlSubmitInput) form.getInputsByValue("Sign In").get(0);
	        final HtmlSubmitInput button = (HtmlSubmitInput) form.getInputByName("commit");
//	        final HtmlSubmitInput button = form.getFirstByXPath("//form[@id='new_user']");;
	        
	        System.out.println(button.asXml());
	        
	        
	        final HtmlPasswordInput passField = form.getInputByName("user[password]");
//	        final HtmlTextInput textField = form.getFirstByXPath("//input[@id='user_password']");

	        // Change the value of the text field
	        passField.setValueAttribute(MY_PASS);

	        // Now submit the form by clicking the button and get back the second page.
	        final HtmlPage page2 = button.click();
//	        HtmlPage newPage = submitButton.click();
	        System.out.println(page2.asXml());
	        Set<Cookie> cookie = webClient.getCookieManager().getCookies();

	        if(cookie != null){

	            Iterator<Cookie> i = cookie.iterator();

	            while (i.hasNext()) {

	                webClient.getCookieManager().addCookie(i.next());

	            }

	        }
	    }
	}
	
	public static void main(String[] args) throws Exception {
		submittingForm();
	}
}
