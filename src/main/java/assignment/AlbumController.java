package assignment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Model.Item;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;
import Model.Item;
@RestController
public class AlbumController {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	static String url = "";
	
	// function to fetch the json data from the api
	public JSONObject getJSONFromUrl(String url) {

	    // Making HTTP request
	    try {
	        HttpClient httpClient = HttpClientBuilder.create().build();
	        HttpPost httpPost = new HttpPost(url);

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
	           // System.out.println(line);
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
	
	// function to return all the albums related to particular artist
	public List<Item> getAlbums(String name, int limit) {
		if (name == null || name.equalsIgnoreCase("")) {
			return null;
		}
		
		// trim all the spaces and replace it with "+"
		String newName = name.replaceAll(" ", "+");
		// first search for artist name to get his/her id
		String url = "https://itunes.apple.com/search?term=" + newName + "&limit=" + limit;
		List<Item> result = new ArrayList<>();
		int artistId = 0;

		try {
			// fetch json
			JSONObject object = getJSONFromUrl(url);
			if (object == null)
				return null;

			if (object.has("results")) {
				// iterate over the results
				JSONArray jsonArrayData = (JSONArray) object.getJSONArray("results");

				// there are some issues using iterator with junit. so using the for loop
				for (int i = 0; i < jsonArrayData.length(); i++) {
					Object obj = jsonArrayData.get(i);
					JSONObject obj2 = (JSONObject) obj;
					if (obj2.has("artistId"))
						artistId = obj2.getInt("artistId");

					if (artistId != 0)
						break;
				}

				// in some cases artistid will be 0. in that case we cant search for any albums
				// using the id. so just return
				if (artistId == 0)
					return result;

				// now find the albums of the artistId
				url = "https://itunes.apple.com/lookup?id=" + artistId + "&entity=album&limit=" + (limit + 1);
				object = getJSONFromUrl(url);
				if (object == null)
					return null;

				// Fetch the result upto the limit specified by user. 
				jsonArrayData = (JSONArray) object.getJSONArray("results");
				int count = Math.min(jsonArrayData.length(), limit + 1);

				// the first entry doesnt contain album name. so we need to skip it and get the
				// next five entries
				for (int i = 0; i < count; i++) {
					Object obj = jsonArrayData.get(i);
					JSONObject obj2 = (JSONObject) obj;
					if (obj2.has("collectionType")) {
						String album = obj2.getString("collectionType");
						if (album != null && album.equalsIgnoreCase("Album")) {
							String artist = obj2.getString("artistName");
							String albumName = obj2.getString("collectionName");
							Item item = new Item(albumName, artist, "Album");
							result.add(item);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("===exception happened ===" + e.getMessage());
			return null;
		}
		
		// sort based in title
		Collections.sort(result, new Comparator<Item>() {
			public int compare(Item o1, Item o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}
		});
		
		return result;
	}
}
