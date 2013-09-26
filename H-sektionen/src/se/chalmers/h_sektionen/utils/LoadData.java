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
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;


public class LoadData {
	
	public static List<Events> loadEvents(){
		
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
			
			//List<String> posts = new ArrayList<String>();
			List<Events> events = new ArrayList<Events>();
			
			
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
					events.add(new Events(title, description, where, date[0]));
					
				}
			}
			
			//return posts.toArray(new String[posts.size()]);
			return events;
			
		} catch (JSONException e) {
			e.printStackTrace();
			String p = Log.getStackTraceString(e);
			List<Events> s = new ArrayList<Events>();
			s.add(new Events("Anslut till internet..", data, p, "inte bra"));
					
			return s;
		}
	}
	
	//Old
	public static String getData(){
		
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

	    return builder.toString();
	}
	
	public static String[] parseEventsData(String data){
		try {
			JSONObject json_obj = new JSONObject(data).getJSONObject("feed");
			JSONArray json_arr = json_obj.getJSONArray("entry");
			
			List<String> posts = new ArrayList<String>();
			
			for (int i = 0; i < json_arr.length(); i++){
				String message = json_arr.getJSONObject(i).getJSONObject("title").optString("$t");
				if (!message.equals(""))
					posts.add(message);
			}
			
			return posts.toArray(new String[posts.size()]);
			
		} catch (JSONException e) {
			e.printStackTrace();
			String p = Log.getStackTraceString(e);
			String[] s = {"Det haer gick inte bra", data, p};
			return s;
		}
	}
	


}
