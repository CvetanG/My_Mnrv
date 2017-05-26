package confluence;

import javax.naming.AuthenticationException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Test {
	public static void main(String[] args) {
		
	DefaultHttpClient client = new DefaultHttpClient();
	HttpResponse response = null;
	try {
	    HttpGet request = new HttpGet("http://localhost:7991/rest/build-status/1.0/commits/77561c970294e814ddca6ad6106a67d98661cd36");
	    String encoding = Base64.encodeBase64String("<myuser>:<mypassword>".getBytes());
	    request.setHeader("Authorization", "Basic " + encoding);
	    System.out.println("request: " + request.getRequestLine());
	    for (Header header: request.getAllHeaders()) {
	        System.out.println(header);
	    }
	    response = client.execute(request);
	    System.out.println("status code: " + response.getStatusLine().getStatusCode());
	    for (Header header: response.getAllHeaders()) {
	        System.out.println(header);
	    }  
	} catch (Exception e) {
	    System.out.println("-> request problem: " + e.getMessage());
	} finally {
	    client.getConnectionManager().shutdown();
	}
	
	}
	
	Client client = Client.create();
	WebResource webResource = client.resource(url);
	ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
			.accept("application/json").get(ClientResponse.class);
	int statusCode = response.getStatus();
	if (statusCode == 401) {
		throw new AuthenticationException("Invalid Username or Password");
	}
	return response.getEntity(String.class);
	
}
