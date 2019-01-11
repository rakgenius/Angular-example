package assignment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;

import Model.Item;

@RestController
public class BooksController {
	
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	static String url = "";

	public JSONObject getJSONFromUrl(String url) {

	    try {
	        HttpClient httpClient = HttpClientBuilder.create().build();
	        HttpGet httpPost = new HttpGet(url);

	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        is = httpEntity.getContent();

	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    try {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                is, "iso-8859-1"), 8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	            //System.out.println(line);
	        }
	        is.close();
	        json = sb.toString();

	    } catch (Exception e) {
	       
	    }

	    try {
	        jObj = new JSONObject(json);
	    } catch (JSONException e) {
	        System.out.println("error on parse data in jsonparser.java");
	    }

	    return jObj;

	}

	public List<Item> getBooks(String name, int limit) {
		if (name == null || name.equalsIgnoreCase(""))
			return null;
		
		String newName = name.replaceAll(" ", "%20");
		
		// maximum query limit supported per request by google is 40
		if (limit > 40)
			limit = 40;
		
		String url = "https://www.googleapis.com/books/v1/volumes?q=" + newName + "&maxResults=" + limit;
		List<Item> result = new ArrayList<>();
		String authorName = "";
		
		try {
			JSONObject object = getJSONFromUrl(url);
			if (object == null) {
				return null;
			}
			
			if (object.has("items")) {
				JSONArray items = (JSONArray) object.getJSONArray("items");

				int count = Math.min(items.length(), limit);
				for (int i = 0; i < count ; i++) {
					Object obj = items.get(i);
					JSONObject obj2 = (JSONObject) obj;
					JSONObject volumeInfo = (JSONObject) obj2.get("volumeInfo");
					String title = volumeInfo.getString("title");
					
					// if the result contains authors then use it.
					// sometimes authors will be null
					if (volumeInfo.has("authors")) {
						JSONArray authors = (JSONArray) volumeInfo.getJSONArray("authors");
						if (authors != null) {
							authorName = authors.getString(0);
						}
					} else {
						name = "N/A";
					}
					Item item = new Item(title, authorName, "Book");
					result.add(item);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception happened " + e.getMessage());
			return null;
		}
		
		// sort alphabetically
		Collections.sort(result, new Comparator<Item>() {
			public int compare(Item o1, Item o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}
		});
		
		return result;
	}
}
