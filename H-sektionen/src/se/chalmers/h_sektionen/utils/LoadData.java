package se.chalmers.h_sektionen.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;


public class LoadData {
	
	public static List<Event> loadEvents(){
		
		StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet("https://www.google.com/calendar/feeds/5id1508tk2atummuj0vq33d7lc@group.calendar.google.com/public/full?alt=json&" +
	    		"orderby=starttime&" +
	    		"sortorder=ascending&" +
	    		"futureevents=true&" + //Overrides start-min and max
	    		//"start-min=2013-08-31T10:57:00-08:00&" +
	    		//"start-max=2013-09-31T10:57:00-08:00" +
	    		"");
	    try {
	        HttpResponse response = client.execute(httpGet);
	        HttpEntity entity = response.getEntity();
	        InputStream content = entity.getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	        
	        String line;
	        while ((line = reader.readLine()) != null) {
	          builder.append(line);
	        }
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    
	    String data = builder.toString();
		
		try {
			JSONObject json_obj = new JSONObject(data).getJSONObject("feed");
			JSONArray json_arr = json_obj.getJSONArray("entry");
			
			List<Event> events = new ArrayList<Event>();
			
			
			for (int i = 0; i < json_arr.length(); i++){
				String title = json_arr.getJSONObject(i).getJSONObject("title").optString("$t");
				String description = json_arr.getJSONObject(i).getJSONObject("content").optString("$t");
				String time = json_arr.getJSONObject(i).getJSONArray("gd$when").getJSONObject(0).optString("startTime");
				String where = json_arr.getJSONObject(i).getJSONArray("gd$where").getJSONObject(0).optString("valueString");
				
				String[] date = time.split("T");
				
				if (!title.equals("")){
					/*posts.add(title + "\n\nVad: " + description
							+ "\n\nVar : " + where +
							"\n\nTid: " + "13:37" + 
							"\nDatum: " + date[0]);*/
					events.add(new Event(title, description, where, date[0]));
					
				}
			}
			
			//return posts.toArray(new String[posts.size()]);
			return events;
			
		} catch (JSONException e) {
			e.printStackTrace();
			String p = Log.getStackTraceString(e);
			List<Event> s = new ArrayList<Event>();
			s.add(new Event("Anslut till internet..", data, p, "inte bra"));
					
			return s;
		}
	}

	public static ArrayList<NewsItem> loadNews(){
		DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
		HttpPost httppost = new HttpPost("http://jpv-net.dyndns.org:1337/H-Sektionen/newsfeed/");
		//HttpPost httppost = new HttpPost("https://graph.facebook.com/109143889143301/feed?access_token=161725214029162%7CBzWvqgod38ZodPCz5Shub0PTld0");
		
		// Depends on your web service
		httppost.setHeader("Accept", "application/json");

		InputStream inputStream = null;
		String result = null;
		try {
		    HttpResponse response = httpclient.execute(httppost);           
		    HttpEntity entity = response.getEntity();

		    inputStream = entity.getContent();
		    // json is UTF-8 by default
		    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
		    StringBuilder sb = new StringBuilder();

		    String line = null;
		    while ((line = reader.readLine()) != null)
		    {
		        sb.append(line + "\n");
		    }
		    result = sb.toString();
		} catch (Exception e) { 
		    e.printStackTrace();
		}
		finally {
		    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
		}
		
		List<NewsItem> posts = new ArrayList<NewsItem>();
		
		try {
			JSONObject json_obj = new JSONObject(result);
			JSONArray json_arr = json_obj.getJSONArray("data");
			
			for (int i = 0; i < json_arr.length(); i++){
				String message = json_arr.getJSONObject(i).optString("message");
				String[] date = json_arr.getJSONObject(i).optString("created_time").split("T");
				String image = json_arr.getJSONObject(i).optString("picture");
				if ((!message.equals("")) && (!message.equals(""))){
					posts.add(new NewsItem(message, date[0], image));
				}
			}
			
			return (ArrayList<NewsItem>) posts;
		} catch (JSONException e) {
			e.printStackTrace();
			posts.add(new NewsItem(Log.getStackTraceString(e), "infinity", "bild"));
		}
		
		return (ArrayList<NewsItem>) posts;
	}
}
