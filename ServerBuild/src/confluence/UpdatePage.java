package confluence;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@SuppressWarnings("deprecation")
public class UpdatePage {

	private static final String BASE_URL = "https://minerva.atlassian.net/wiki";
	private static final String USERNAME = "cgeorgiev";
	private static final String PASSWORD = "cvetan012982";
	private static final String ENCODING = "utf-8";
	

	private static String getContentRestUrl(final Long contentId, final String[] expansions) throws UnsupportedEncodingException {
		final String expand = URLEncoder.encode(StringUtils.join(expansions, ","), ENCODING);

		return String.format("%s/rest/api/content/%s?expand=%s&os_authType=basic&os_username=%s&os_password=%s",
				BASE_URL,
				contentId,
				expand,
				URLEncoder.encode(USERNAME, ENCODING),
				URLEncoder.encode(PASSWORD, ENCODING));
		/*return String.format("%s/rest/api/content/%s?expand=%s",
				BASE_URL,
				contentId,
				expand);*/
	}
	
	// https://minerva.atlassian.net/wiki/rest/api/content/145948707?expand=space,body.view,version,container
	
	public static void main(final String[] args) throws Exception {
		final long pageId = 145948710;

		@SuppressWarnings("deprecation")
		HttpClient client = new DefaultHttpClient();

		// Get current page version
		String pageObj = null;
		HttpEntity pageEntity = null;
		try {
//			HttpGet getPageRequest = new HttpGet(getContentRestUrl(pageId, new String[] {"body.storage", "version", "ancestors"}));
			HttpGet getPageRequest = new HttpGet(getContentRestUrl(pageId, new String[] {"space", "body.view", "version", "container"}));
			HttpResponse getPageResponse = client.execute(getPageRequest);
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

		// Parse response into JSON
//		JSONObject page = new JSONObject(pageObj);
		
		JSONParser parser = new JSONParser();
		JSONObject page = (JSONObject) parser.parse(pageObj);
		
//		System.out.println(page.toString());

		// Update page
		// The updated value must be Confluence Storage Format (https://confluence.atlassian.com/display/DOC/Confluence+Storage+Format), NOT HTML.
//		page.get("body");
//		page.get("storage");
		System.out.println(page.get("value").toString());
//		page.put("value", "hello, world");
//		page.getJSONObject("body").getJSONObject("storage").put("value", "hello, world");

//		int currentVersion = page.getJSONObject("version").getInt("number");
//		page.getJSONObject("version").put("number", currentVersion + 1);

		// Send update request
//		HttpEntity putPageEntity = null;
//
//		try {
//			HttpPut putPageRequest = new HttpPut(getContentRestUrl(pageId, new String[]{}));
//
//			StringEntity entity = new StringEntity(page.toString(), ContentType.APPLICATION_JSON);
//			putPageRequest.setEntity(entity);
//
//			HttpResponse putPageResponse = client.execute(putPageRequest);
//			putPageEntity = putPageResponse.getEntity();
//
//			System.out.println("Put Page Request returned " + putPageResponse.getStatusLine().toString());
//			System.out.println("");
//			System.out.println(IOUtils.toString(putPageEntity.getContent()));
//		} finally {
//			EntityUtils.consume(putPageEntity);
//		}
	}
}
