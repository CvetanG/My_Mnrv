package confluence;

import javax.naming.AuthenticationException;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;

public class JTricksRESTClient02 {
	
	private static final String BASE_URL = "https://minerva.atlassian.net/wiki";
	private static final String USERNAME = "cgeorgiev";
	private static final String PASSWORD = "cvetan012982";
	private static final String pageId = "145948788";

	private static final String auth = new String(Base64.encode((USERNAME + ":" + PASSWORD).getBytes()));
	
	public static void parseJsonAndDo(String pageId) throws JSONException {
		
		
		try {
			//Get Projects
			String projects = invokeGetMethod(auth, BASE_URL + "/rest/api/content/" + pageId + "?expand=body.storage.value,version");
//			JSONArray projectArray = new JSONArray(projects);
//			for (int i = 0; i < projectArray.length(); i++) {
//				JSONObject proj = projectArray.getJSONObject(i);
//				System.out.println("Title: " + proj.getString("title"));
//			}
			
			// org.json.simple
//			JSONParser parser = new JSONParser();
//			JSONObject page = (JSONObject) parser.parse(projects);
			
			// org.json
			JSONObject page = new JSONObject(projects);
//			System.out.println(page.toString());
			
			String value = page.getJSONObject("body")
					.getJSONObject("storage")
					.getString("value");
			

			System.out.println("Value: " + value);

			String newValue = value.replaceAll("(.jar)</p><p>", "$1<br/>");

			System.out.println("New Value: " + newValue);

			
			String title = page.getString("title");
//			System.out.println("Title: " + title);
			
			int vers =  Integer.valueOf(page.getJSONObject("version").getString("number"));
			vers++;
//			System.out.println("VersionNumber: " + vers);
			
			/*
			//Create Issue
			String createIssueData = "{\"fields\":{\"project\":{\"key\":\"DEMO\"},\"summary\":\"REST Test\",\"issuetype\":{\"name\":\"Bug\"}}}";
			String issue = invokePostMethod(auth, BASE_URL+"/rest/api/2/issue", createIssueData);
			System.out.println(issue);
			JSONObject issueObj = new JSONObject(issue);
			String newKey = issueObj.getString("key");
			System.out.println("Key:" + newKey);
			*/
			
			//Update Issue
//			String editedIssueData = "{\"fields\":{\"assignee\":{\"name\":\"test\"}}}";
			String editedIssueData = "{\"id\": \"" + pageId + "\", \"type\": \"page\", \"title\": \"" + title + "\", \"space\": { \"key\": \"60RS\" }, \"body\": { \"storage\": { \"value\": \"" + newValue +"\", \"representation\": \"storage\" } }, \"version\": { \"number\": " + vers+" } }";
			
			invokePutMethod(auth, (BASE_URL + "/rest/api/content/" + pageId), editedIssueData);
			
			System.out.println(editedIssueData);
			
			/*
			invokeDeleteMethod(auth, BASE_URL+"/rest/api/2/issue/DEMO-13");
			*/
			
		} catch (AuthenticationException e) {
			System.out.println("Username or Password wrong!");
			e.printStackTrace();
		} catch (ClientHandlerException e) {
			System.out.println("Error invoking REST method");
			e.printStackTrace();
		}

	}
	
	private static void invokePutMethod(String auth, String url, String data) throws AuthenticationException, ClientHandlerException {
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json").accept("application/json").put(ClientResponse.class, data);
		int statusCode = response.getStatus();
		System.out.println("Invoking PUT Method - Status Code: " + statusCode);
		if (statusCode == 401) {
			throw new AuthenticationException("Invalid Username or Password");
		}
	}
	
	private static String invokeGetMethod(String auth, String url) throws AuthenticationException, ClientHandlerException {
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
				.accept("application/json").get(ClientResponse.class);
		int statusCode = response.getStatus();
		System.out.println("Invoking GET Method - Status Code: " + statusCode);
		if (statusCode == 401) {
			throw new AuthenticationException("Invalid Username or Password");
		}
		return response.getEntity(String.class);
	}
	/*
	private static String invokePostMethod(String auth, String url, String data) throws AuthenticationException, ClientHandlerException {
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
				.accept("application/json").post(ClientResponse.class, data);
		int statusCode = response.getStatus();
		System.out.println("Invoking POST Method - Status Code: " + statusCode);
		if (statusCode == 401) {
			throw new AuthenticationException("Invalid Username or Password");
		}
		return response.getEntity(String.class);
	}
	
	
	private static void invokeDeleteMethod(String auth, String url) throws AuthenticationException, ClientHandlerException {
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
				.accept("application/json").delete(ClientResponse.class);
		int statusCode = response.getStatus();
		System.out.println("Invoking DELETE Method - Status Code: " + statusCode);
		if (statusCode == 401) {
			throw new AuthenticationException("Invalid Username or Password");
		}
	}
	*/
	public static void main(String[] args) throws JSONException {
		System.out.println("Start");
		parseJsonAndDo(pageId);
		System.out.println("End");
	}

}
