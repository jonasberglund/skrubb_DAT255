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

import android.text.format.Time;
import android.util.Log;


public class LoadData {
	
	public static List<Event> loadEvents(){
		
		String data = getJSON(Constants.GOOGLEEVENTS);
		
		try {
			JSONObject json_obj = new JSONObject(data).getJSONObject("feed");
			JSONArray json_arr = json_obj.getJSONArray("entry");
			
			List<Event> events = new ArrayList<Event>();
			
			
			for (int i = 0; i < json_arr.length(); i++){
				String title = json_arr.getJSONObject(i).getJSONObject("title").optString("$t");
				String description = json_arr.getJSONObject(i).getJSONObject("content").optString("$t");
				String time = json_arr.getJSONObject(i).getJSONArray("gd$when").getJSONObject(0).optString("startTime");
				String where = json_arr.getJSONObject(i).getJSONArray("gd$where").getJSONObject(0).optString("valueString");
				
				//If time is not an all day event
				if(time.length() > 10){
					time = fromDate(time) + ". ";
				}
					
				
				if (!title.equals("")){
					events.add(new Event(title, description, where, time));
				}
			}
			
			return events;
			
		} catch (JSONException e) {
			e.printStackTrace();
			String p = Log.getStackTraceString(e);
			List<Event> s = new ArrayList<Event>();
			s.add(new Event("Anslut till internet..", data, p, "inte bra"));
					
			return s;
		}
	}
	
	public static List<Event> loadPubs(){
		
		String data = getJSON(Constants.PUBEVENTS);
		
		try {
			JSONObject json_obj = new JSONObject(data).getJSONObject("feed");
			JSONArray json_arr = json_obj.getJSONArray("entry");
			
			List<Event> events = new ArrayList<Event>();
			
			
			for (int i = 0; i < json_arr.length(); i++){
				String title = json_arr.getJSONObject(i).getJSONObject("title").optString("$t");
				String description = json_arr.getJSONObject(i).getJSONObject("content").optString("$t");
				String time = json_arr.getJSONObject(i).getJSONArray("gd$when").getJSONObject(0).optString("startTime");
				String where = json_arr.getJSONObject(i).getJSONArray("gd$where").getJSONObject(0).optString("valueString");
				
				if(time.length() > 10){
					//String[] date = time.split("T");
					//time = date[0] + ", kl: " + date[1].substring(0,5) + " - SENT. ";
					
					time = fromDate(time) + " - SENT. ";
				}
				
			  
				
				if (!title.equals("")){
					events.add(new Event(title, description, where, time));
				}
			}
			
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
		HttpPost httppost = new HttpPost(Constants.NEWSFEED);
		
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
			//posts.add(new NewsItem(Log.getStackTraceString(e), "infinity", "bild"));
		} catch (NullPointerException ne){
			posts.add(new NewsItem("Kunde inte hämta nyhetsflödet", "Anslut till internet..", "X"));
			
		}
		
		return (ArrayList<NewsItem>) posts;
	}



	public static String getJSON(String url){
		
		StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(url);
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
		
		
		return data;
	}
	
	public static String fromDate(String s){
		Time t = new Time();

		
		
		String[] date = s.split("T");
		String time = date[1].substring(0,5);
		String[] dates = date[0].split("-"); 
		//String year = dates[0];
		//Months month = Months.values()[Integer.parseInt(dates[1])+1];
		String month = dates[1];
		String day = dates[2];
		
		int week = t.getWeekNumber();
		
		return day + "/" + month + ", kl: " + time;
	}
}



