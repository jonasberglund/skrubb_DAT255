package se.chalmers.h_sektionen.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.graphics.Bitmap;

import se.chalmers.h_sektionen.containers.ContactCard;
import se.chalmers.h_sektionen.containers.Event;
import se.chalmers.h_sektionen.containers.InfoContainer;
import se.chalmers.h_sektionen.containers.NewsItem;


/**
 * Class for retrieving and parsing data for different feeds
 * @Author Jonas Berglund, Olle Svensson, Robin Tornquist
 * @Copyright (c) 2013 Anders Johansson, Olle Svensson, Robin Tornquist, Rikard Ekbom, Oskar Gustavsson, Jonas Berglund
 * @Licens Apache
 */
public class LoadData {

	/**
	 * Retrieves and parses event posts
	 * @return List containing events
	 */
	public static List<Event> loadEvents() throws JSONException {
		
		String data = getJSON(Constants.GOOGLEEVENTS);
		
		JSONObject json_obj = new JSONObject(data).getJSONObject("feed");
		JSONArray json_arr = json_obj.getJSONArray("entry");
			
		List<Event> events = new ArrayList<Event>();
			
		//Collection the right content from JSON
		for (int i = 0; i < json_arr.length(); i++){
			String fullDate;
			String title = json_arr.getJSONObject(i).getJSONObject("title").optString("$t");
			String description = json_arr.getJSONObject(i).getJSONObject("content").optString("$t");
			String time = json_arr.getJSONObject(i).getJSONArray("gd$when").getJSONObject(0).optString("startTime");
			String endTime = json_arr.getJSONObject(i).getJSONArray("gd$when").getJSONObject(0).optString("endTime");
			String where = json_arr.getJSONObject(i).getJSONArray("gd$where").getJSONObject(0).optString("valueString");
			
			fullDate = formDate(time, endTime);
			time = toDate(time);
			
			//Add events if it has i title
			if (!title.equals("")){
				events.add(new Event(title, description, where, time + ". ", fullDate));
			}
		}	
		return events;
	}
	
	/**
	 * Retrieves and parses pub events
	 * @return List containing pub events
	 */
	public static List<Event> loadPubs() throws JSONException {
		
		String data = getJSON(Constants.PUBEVENTS);
		
		JSONObject json_obj = new JSONObject(data).getJSONObject("feed");
		JSONArray json_arr = json_obj.getJSONArray("entry");
			
		List<Event> events = new ArrayList<Event>();
			
			
		for (int i = 0; i < json_arr.length(); i++){
			String title = json_arr.getJSONObject(i).getJSONObject("title").optString("$t");
			String description = json_arr.getJSONObject(i).getJSONObject("content").optString("$t");
			String time = json_arr.getJSONObject(i).getJSONArray("gd$when").getJSONObject(0).optString("startTime");
			String endTime = json_arr.getJSONObject(i).getJSONArray("gd$when").getJSONObject(0).optString("endTime");
			String where = json_arr.getJSONObject(i).getJSONArray("gd$where").getJSONObject(0).optString("valueString");
			
			String fullDate = formDate(time, endTime);
			time = toDate(time);
			
			if (!title.equals("")){
				events.add(new Event(title, description, where, time + ". ", fullDate));
			}
		}
			
		return events;
	}

	/**
	 * Retrieves and parses news feed posts
	 * @return List containing news feed posts
	 * @throws JSONException 
	 */
	public static ArrayList<NewsItem> loadNews(int descending, boolean highresPictures) throws JSONException{
		
		//Get JSON string from server
		String result = getJSON(Constants.NEWSFEED + descending); 
		
		List<NewsItem> posts = new ArrayList<NewsItem>();
		
		//Parse JSON string
			JSONObject json_obj = new JSONObject(result);
			JSONArray json_arr = json_obj.getJSONArray("data");
			
			for (int i = 0; i < json_arr.length(); i++){
				//Get post message
				String message = json_arr.getJSONObject(i).optString("message");
				
				//Get date
				String[] date = json_arr.getJSONObject(i).optString("created_time").split("T");
				
				//Get image url
				String image = json_arr.getJSONObject(i).optString("picture");
				
				if(highresPictures && !image.equals("")){
					image = image.replace("s.jpg", "n.jpg");
					image = image.replace("s.png", "n.png");
				}
				
				//Add to posts if valid content
				if ((!message.equals("")) && (!date.equals(""))){
					posts.add(new NewsItem(message, date[0], image));
				}
			}
		
		return (ArrayList<NewsItem>) posts;
	}
	
	public static String loadInfoAsJSON() {
		return getJSON(Constants.INFO);
	}
	
	/**
	 * 
	 * @return A HTML formed string containing info about the lunch today.
	 */
	public static String loadLunch() {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		
		sb.append("<h1>");
		sb.append(Constants.KOKBOKEN_TITLE);
		sb.append("</h1>");
		
		// Append data from Kokboken RSS, but skip the title.
		sb.append(loadLunchFromRSS(Constants.KOKBOKEN_BASE+sdf.format(new Date())+".rss", "item", null, "description", false));
		
		sb.append("<h1>");
		sb.append(Constants.LS_KITCHEN_TITLE);
		sb.append("</h1>");
		
		// Append data from Ls RSS.
		sb.append(loadLunchFromRSS(Constants.LS_KITCHEN_RSS, "item", "title", "description", false));
		
		return sb.toString();
	}
	
	/**
	 * XML-pattern:
	 * <root>
	 *     <title>Title</title>
	 *     <desc>The dish</desc>
	 * </root>
	 * 
	 * @param urlString An URL String pointing to an XML document containing the lunch RSS.
	 * @param root The tag name of the root element containing the lunch
	 * @param title The tag name of some title, if set to null, the title element will be skipped.
	 * @param desc The tag name of some description, if set to null, the description element will be skipped.
	 * @return An HTML string containing what is for lunch today.
	 */
	private static String loadLunchFromRSS(String urlString, String root, String title, String desc, boolean reprintTitle) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(conn.getInputStream(), null);
			
			StringBuilder lunchStringBuilder = new StringBuilder();
			
			// This set is used to not reprint the title if already printed (if reprintTitle is set to True).
			Set<String> titles = new HashSet<String>();
			
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				
				if ( eventType == XmlPullParser.START_TAG && parser.getName().equals(root) )
					while ( !(eventType == XmlPullParser.END_TAG && parser.getName().equals(root)) ) {
						eventType = parser.next();
						if (title != null && eventType == XmlPullParser.START_TAG && parser.getName().equals(title)) {
							String tempTitle = parser.nextText();
							if (reprintTitle || !titles.contains(tempTitle)) {
								lunchStringBuilder.append("<h3>");
								lunchStringBuilder.append(tempTitle);
								titles.add(tempTitle);
								lunchStringBuilder.append("</h3>");
							}
						}
						
						if (desc != null && eventType == XmlPullParser.START_TAG && parser.getName().equals(desc)) {
							String dish = parser.nextText();
							int pos = dish.indexOf('@');
							if (pos != -1)
								dish = dish.substring(0, pos);
 							
							lunchStringBuilder.append(dish);
							lunchStringBuilder.append("<br /><br />");
						}
					}
				
				eventType = parser.next();
				
			}

			return lunchStringBuilder.toString();
			
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 
	 * @param con An Android context
	 * @return An InfoContainer object containing a list of ContactCard objects, a string
	 * containing links and a string containing opening hours.
	 */
	public static InfoContainer loadInfo(Context con) {
		
		try {
			List<ContactCard> contactCards = null;
			StringBuilder htmlLinks = null;
			StringBuilder openingHoursString = null;
			
			JSONObject json = new JSONObject(LoadData.loadInfoAsJSON());

			// Board members
			JSONArray members = json.getJSONArray("members");
			contactCards = new ArrayList<ContactCard>();

			for (int i=0; i<members.length(); i++) {
				String name = members.getJSONObject(i).getString("name");
				String position = members.getJSONObject(i).getString("position");
				String email = members.getJSONObject(i).getString("email");
				String picAddr = members.getJSONObject(i).getString("picture");
				String phoneNumber = members.getJSONObject(i).getString("phone");
				Bitmap pic;

				// Download the picture if it does not exists in the cache.
				if(CacheCompass.getInstance(con).getBitmapCache().get(picAddr)==null){

					PicLoaderThread pcl = new PicLoaderThread(picAddr);
					pcl.start();

					try {
						pcl.join();
						pic = pcl.getPicture();
						pic.prepareToDraw();
						CacheCompass.getInstance(con).getBitmapCache().put(picAddr, pic);

					} catch (Exception e) {
						pic = null;
					}

				} else {
					pic = CacheCompass.getInstance(con).getBitmapCache().get(picAddr);
				}

				contactCards.add(new ContactCard(name, position, email, phoneNumber, pic));
			}

			// Create a HTML-string containing links. The TextView can handle HTML
			JSONArray links = json.getJSONArray("links");
			htmlLinks = new StringBuilder();
			
			for (int i=0; i<links.length(); i++) {
				htmlLinks.append("<a href=\"");
				htmlLinks.append(links.getJSONObject(i).getString("href"));
				htmlLinks.append("\">");
				htmlLinks.append(links.getJSONObject(i).getString("name"));
				htmlLinks.append("</a><br>");
				if(!(i == links.length()-1))
					htmlLinks.append("<br>");
			}

			// Create a HTML-string containing opening hours. 
			JSONArray openingHours = json.getJSONArray("openinghours");
			openingHoursString = new StringBuilder();

			for (int i=0; i<openingHours.length(); i++) {
				openingHoursString.append("<b>");
				openingHoursString.append(openingHours.getJSONObject(i).getString("name"));
				openingHoursString.append("</b>:<br />");
				openingHoursString.append(openingHours.getJSONObject(i).getString("opentime"));
				openingHoursString.append("<br />");
			}

			return new InfoContainer(contactCards, htmlLinks.toString(), openingHoursString.toString());

		} catch (Exception e) {
			// Incorrect JSON string.
			return null;
		}
	}

	/**
	 * Retrieves data from url and returns it in a string
	 * @param url The url to retrieve data from
	 * @return String containing the retrieved data
	 */
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
	    } catch (Exception e) {}
		
		return builder.toString(); 
	}
	
	/**
	 * Convert date string to another format
	 * @param time
	 * @param endTime
	 * @return String with date and time
	 */
	public static String formDate(String time, String endTime){
		
		if(time.length() > "1967-09-03".length()){
			String[] date = time.split("T");
			String startTime = date[1].substring(0,5);
			String[] dates = date[0].split("-"); 
			String month = dates[1];
			String day = dates[2];
			
			String eTime = endTime.split("T")[1].substring(0,5);
			
			return day + "/" + month + ", kl: " + startTime + " - " + eTime;	
		}
		else{
			String[] date = time.split("T");
			String[] dates = date[0].split("-"); 
			String month = dates[1];
			String day = dates[2];
			
			return day + "/" + month;
		}
		
	}
	
	/**
	 * Convert date and time to relative date
	 * @param time
	 * @return String of days left till event
	 */
	
	public static String toDate(String time) {
		
		Calendar cal = GregorianCalendar.getInstance();
		Calendar now = GregorianCalendar.getInstance();
		
		String[] date = time.split("T");
		String[] dates = date[0].split("-"); 

		String year = dates[0];
		String month = dates[1];
		String day = dates[2];

		cal.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
		

		int years = cal.get(Calendar.YEAR) - now.get(Calendar.YEAR);
		int months = cal.get(Calendar.MONTH) - now.get(Calendar.MONTH);
		int days = cal.get(Calendar.DAY_OF_MONTH)
				- now.get(Calendar.DAY_OF_MONTH);
		
		
		//If time is not an all day event
		String clock = "";
		if(time.length() > "1967-09-03".length()){
			clock = " kl: " +  date[1].substring(0,5);
		}
		
		
		if (years == 1)
			return Constants.NEXT_YEAR;
		else if (years > 1)
			return "Om " + years + Constants.YEAR;
		else if (months == 1)
			return Constants.NEXT_MONTH;
		else if (months > 1)
			return "Om " + months + Constants.MONTH;
		else if (days == 1)
			return Constants.TOMORROW + clock;
		else if (days > 1)
			return "Om " + days + " dagar";
		
		return Constants.TODAY + clock;
	}
}
