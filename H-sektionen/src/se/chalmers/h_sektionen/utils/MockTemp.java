package se.chalmers.h_sektionen.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MockTemp {
	
	public static String getData(){
		DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
		HttpPost httppost = new HttpPost("https://graph.facebook.com/oauth/access_token?client_id=161725214029162&client_secret=bace54110f0ba764dd7f98af20f5bfea&grant_type=client_credentials");
		// Depends on your web service
		httppost.setHeader("Content-type", "application/json");

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
		    // Oops
		}
		finally {
		    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
		}
		
		return result;
	}
	
	public static String[] parseData(String data){
		try {
			JSONObject json_obj = new JSONObject(data);
			JSONArray json_arr = json_obj.getJSONArray("data");
			
			List<String> posts = new ArrayList<String>();
			
			for (int i = 0; i < json_arr.length(); i++){
				posts.add(json_arr.getJSONObject(i).getString("message"));
			}
			
			return posts.toArray(new String[posts.size()]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
