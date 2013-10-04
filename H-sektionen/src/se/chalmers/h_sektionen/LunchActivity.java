package se.chalmers.h_sektionen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import se.chalmers.h_sektionen.utils.Constants;
import se.chalmers.h_sektionen.utils.MenuItems;

import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
/**
 * Activity responsible of the lunch menu view.
 */
public class LunchActivity extends BaseActivity {

    private static final String DEBUG_TAG = "HttpExample";
    private String[] lunchUrls = Constants.LUNCH_URLS; 
	private TextView textView;
	
	/**
	 * On resume.
	 */
	@Override
	protected void onResume() {

		setCurrentView(MenuItems.LUNCH);
		createLunchView();
		
		textView = (TextView)findViewById(R.id.LunchTextView);
		
		if (connectedToInternet())
			new DownLoadWebPage().execute("");
		else 
		    textView.setText(Constants.INTERNET_CONNECTION_ERROR_MSG);
				
		super.onResume();
	}
	/**
	 * Creates the lunch view.
	 */
	private void createLunchView(){
		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_lunch, null));
    	
    }
	
	/**
	 * Thread that starts the download and parsing function
	 * of the lunch menus.
	 */
	public class DownLoadWebPage extends AsyncTask<String, String, String>{
		/**
		 * Runs a loading animation
		 */
		@Override
		protected void onPreExecute(){
			runTransparentLoadAnimation();
		}
		/**
		 * Starts the download of a web page and returns
		 * the string value of the data which was parsed. 
		 */
		@Override
		protected String doInBackground(String... urls) {
			
			try {
                return downloadUrl();
            } catch (IOException e) {
                return "Det gick ej att hämta sidan";
            }

		}
		/**
		 * Receives the result string and sets it in
		 * the text view in the activity view.
		 */
		@Override
		protected void onPostExecute(String result){
    		textView.setText(Html.fromHtml(result));
    		stopAnimation();
		}
	}
	/**
	 * Starts a connection to the web page and call
	 * the parser to create the string to be returned.
	 * @return string with the text to be displayed.
	 * @throws IOException
	 */
	private String downloadUrl() throws IOException {
	    InputStream is = null;
	    StringBuffer sb = new StringBuffer();    
	    try {
	    	for(String url : lunchUrls){
		        URL currentUrl = new URL(url);
		        HttpURLConnection conn = (HttpURLConnection) currentUrl.openConnection();
		        
		        conn.setReadTimeout(50000 /* milliseconds */);
		        conn.setConnectTimeout(10000 /* milliseconds */);
		        conn.setRequestMethod("GET");
		        conn.setDoInput(true);
		        // Starts the query
		        conn.connect();
		        int response = conn.getResponseCode();
		        Log.d(DEBUG_TAG, "The response is: " + response);
		        is = conn.getInputStream();
		        
		     // Convert the InputStream into a string
		        if(url.equals(Constants.KOKBOKLUNCH))
		        	sb.append(parseKokBoken(is));
		        else
		        	sb.append(parseLunchPage(is));
		        
		        is = null;
	    	}
	        return sb.toString();
	    	   
	    } finally {
	        if (is != null) 
	            is.close();	         
	    }
	}
	/**
	 * Parses the data of the web page into a string.
	 * @param the input stream that contains the data that will be parsed.
	 * @return the string that will be displayed in the activity view.
	 * @throws IOException
	 */
	public String parseLunchPage(InputStream stream) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null)
			sb.append(line);
	
		// Find the title of the restaurant
		int pos = sb.indexOf("<h1 class=\"title\">")+18;
	
		String title = sb.substring(pos);
		title = title.substring(0, title.indexOf("</h1>"));
		
		// Find the menu
		if (sb.indexOf("<h2>Dagens lunch</h2>")==-1 || sb.indexOf("</tbody>")==-1)
			return "";
		
		pos = sb.indexOf("<h2>Dagens lunch</h2>");
		String dagensLunch = sb.substring(pos);
		
		pos = dagensLunch.indexOf("</tbody>");
		dagensLunch = dagensLunch.substring(0, pos);
		
		dagensLunch = dagensLunch.replaceAll("<strong>", "<b>");
		dagensLunch = dagensLunch.replaceAll("</strong>", "</b>");
		
		sb = new StringBuilder();
		sb.append("<h1>");
		sb.append(title);
		sb.append("</h1>");
		while((pos = dagensLunch.indexOf("<b>")) != -1) {
			dagensLunch = dagensLunch.substring(pos);
			sb.append(dagensLunch.substring(0, dagensLunch.indexOf("<div")));
			sb.append("<br />");
			dagensLunch = dagensLunch.substring(3);
		}
		
		return sb.toString();
	
	} 
	/**
	 * Parses the web page that displays the lunch from Kokboken.
	 * @param the input stream that contains the data that will be parsed.
	 * @return the string that will be displayed in the activity view.
	 * @throws IOException
	 */
	public String parseKokBoken(InputStream stream) throws IOException{
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));		
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null)
			sb.append(line);
		
		// Find the menu
		int pos = sb.indexOf("id=\"rss_dagenslunch\">")+21;
		String dagensLunch = sb.substring(pos);
		dagensLunch = dagensLunch.substring(dagensLunch.indexOf("<span class=\"desc\">")+19);
		pos = dagensLunch.indexOf("@");
		dagensLunch = dagensLunch.substring(0, pos);
		dagensLunch = "<h1>Kokboken</h1>"+dagensLunch;
		return dagensLunch;
	}
}



