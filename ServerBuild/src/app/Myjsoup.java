package app;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Myjsoup{
	
	static final String MY_USER = "cgeorgiev";
	static final String MY_PASS = "cgeorgiev";
	static final String MY_WEB = "http://serverbuild1.minervanetworks.com:8080/";
	
	static String project = "70.iTvMgrAC_ML";

    private Map<String, String> loginCookies;

    public Myjsoup() {
        login();
    }

    private void login() {
        try {
            Connection.Response res = Jsoup.connect("https://www.indemed.com/Action/Login/LoginAction.cfm?refer=MyAccount&qs=")
                    .data("j_username",       MY_USER)
                    .data("j_password",       MY_PASS)
                    .method(Method.POST)
                    .execute();

            loginCookies = res.cookies();
        } catch (MalformedURLException ex) {
            System.out.println("The URL specified was unable to be parsed or uses an invalid protocol. Please try again.");
            System.exit(1);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + "\nAn exception occurred.");
            System.exit(1);
        }
    }

    public Document getDoc(String project){
        try {
        	Document curPage = Jsoup.connect(MY_WEB + project + "/configure").cookies(loginCookies).get();
        	System.out.println(curPage);
            return curPage;

        } catch (IOException e) {
        	e.printStackTrace();
        }
        return null;
    }

    public String getPrice(Document doc){
        try {
            Elements stuff = doc.select("#tr_51187955");
            stuff = stuff.select("div.product-price");
            String newStuff = stuff.toString();
            newStuff = newStuff.substring(newStuff.indexOf("$")); // throws exception because "$" is not in the String.
            newStuff = newStuff.substring(0, newStuff.indexOf(" "));
            return newStuff;
        } catch (Exception arg0) {
            return "";
        }
    }

    public static void main(String[] args){
        Myjsoup test = new Myjsoup();
        Document doc = test.getDoc(project);
        System.out.println("\n\n\n\n\n\n\n\n\n\n"); //to separate the return lines
        System.out.println(test.getPrice(doc));
    }
}