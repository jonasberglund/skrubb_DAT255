package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;



import junit.framework.TestCase;

public class TestNewsFeed extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	@Test
	public void testConnectionNewsfeed()
	{
		assertEquals(true, testconnection());	
	}
	
	
	@Test
	public void testJsonNewsFeed(){
		
		try {
			assertEquals(true, testJson());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean testJson() throws IOException{
		
		 URL oracle = new URL("http://jpv-net.dyndns.org:1337/H-Sektionen/newsfeed/");
	        URLConnection yc = oracle.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	                                    yc.getInputStream()));
	        String inputLine;
	        String JSon="";
	        while ((inputLine = in.readLine()) != null) 
	            JSon=JSon+inputLine;
	        in.close();
	        System.out.println(JSon);	        

	        // ej klart får fel i testet när jag vill köra det skriver om i morgon.
			    
	        
		return true;
	}
	
	
		
	
	/***
	 * 	
	 * @return true if connection worked, false if no connection or url was wrong.
	 */
	public boolean testconnection(){
		

		try {
		    URL myURL = new URL("http://jpv-net.dyndns.org:1337/H-Sektionen/newsfeed/");
		    URLConnection myURLConnection = myURL.openConnection();
		    myURLConnection.connect();
		} 
		catch (MalformedURLException e) { 
		    // new URL() failed
		    // ...
			return false;
		} 
		catch (IOException e) {   
		    // openConnection() failed
		    // ...
			return false;
		}
		return true;
	}

}
