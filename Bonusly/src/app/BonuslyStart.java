package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;



public class BonuslyStart {
	/*
	 * https://bonus.ly/api/v1/bonuses?access_token=9c42d3b6fa57f059bd1c52f64628c0fc
	 * https://bonus.ly/api/v1/users?access_token=9c42d3b6fa57f059bd1c52f64628c0fc
	 * 
	 * myID - 57c701e0e2e3d9791fdf8230
	*/
	
	static final String MY_EMAIL = "cgeorgiev@minervanetworks.com";
	static final String MY_PASS  = "cvetan012982";
	
	static final String ACCESS_TOKEN = "9c42d3b6fa57f059bd1c52f64628c0fc";
	static final String ACCESS_TOKEN_TEXT = "access_token=9c42d3b6fa57f059bd1c52f64628c0fc";
	
	public static List<String> users = new ArrayList<>();
	public static List<String> hashtags = new ArrayList<>();
	public static List<String> actions = new ArrayList<>();
	
	public static void populateData() {
		users.add("@pdedinski");
		users.add("@ihlebarski");
		users.add("@bkacharmazov");
		users.add("@nvassilev");
		users.add("@tpopov"); 
		users.add("@nmagunski");
		users.add("@arussev");
		users.add("@mkalachev");
		users.add("@vivanov");
		users.add("@ipetrov");
		
		hashtags.add("#teamwork");
		hashtags.add("#problem-solving");
		hashtags.add("#innovation");
		hashtags.add("#vision");
		hashtags.add("#efficiency");
		hashtags.add("#greatjob");
		hashtags.add("#dedication");
		hashtags.add("#professionalism");
		
		actions.add("for supporting info.");
		actions.add("for helping me with a problem.");
		actions.add("explaining Java BO bussines logic.");
		actions.add("for supporting me solving DB issue.");
		actions.add("kind support fixing my VM DB scheme.");
		actions.add("");
	}
	
	public static String randomText(List<String> list) {
		int random = (int) (((list.size() - 1) * Math.random()) + 1);
		String result = list.get(random);
		
		// not to repeat the users
		if (list.equals(users)) {
			list.remove(random);
		}
		return result;
	}
	
	/* This method is used for "Post request" (submit in the website) */
	public static void submittingForm(String message) {

		try {
		        
			DefaultHttpClient httpClient = new DefaultHttpClient();
			String uri = "https://bonus.ly/api/v1/bonuses?" + ACCESS_TOKEN_TEXT;
			
			HttpPost postRequest = new HttpPost(uri);
			
			// website message format
			String payload = "{\"reason\":\"+10 " + message + "\"}";
			StringEntity entity = new StringEntity(payload);
			postRequest.setEntity(entity);

			postRequest.addHeader("accept", "application/json");
			postRequest.addHeader("content-type", "application/json");

			HttpResponse response;
			response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			} else {
				System.out.println(response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* This method is used for "Get requests" for uri */
	public static void getRequest(String uri) {

		try {
		        
			DefaultHttpClient httpClient = new DefaultHttpClient();
			
			HttpGet getRequest = new HttpGet(uri);
			
			HttpResponse response;
			response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			} else {
				System.out.println(response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		populateData();
		int timesToGive = 5;
		List<String> messages = new ArrayList<String>();
		
		/* To add a hard coded message(s) in "messages List<String>"
		 * uncomment line bellow and 
		 * comment user(s) in "users List<String>"
		*/
		
//		messages.add("@arussev for #dedication for helping me with a problem.!");
//		messages.add("@mkalachev for #professionalism supported me with some PLSQL queries!");
		
		int messagesToAdd = timesToGive - messages.size();
		for (int i = 0; i < messagesToAdd; i++) {
			StringBuilder messageSB = new StringBuilder();
			messageSB.append(randomText(users));
			messageSB.append(" for ");
			messageSB.append(randomText(hashtags));
			messageSB.append(" ");
			messageSB.append(randomText(actions));
			messages.add(messageSB.toString());
		}
		
		/* Print generated methods in console */
		for (String message : messages) {
			System.out.println(message);
		}
		
		/* Submit generated methods in website */
//		for (String message : messages) {
//			submittingForm(message);
//			System.out.println("Submitted message: " + message);
//		}
		
		String uri = "https://bonus.ly/api/v1/users?" + ACCESS_TOKEN_TEXT;
		getRequest(uri);
	}
}
