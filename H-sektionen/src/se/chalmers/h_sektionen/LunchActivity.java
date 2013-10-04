package se.chalmers.h_sektionen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import se.chalmers.h_sektionen.utils.Constants;
import se.chalmers.h_sektionen.utils.MenuItems;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

public class LunchActivity extends BaseActivity {

    private static final String DEBUG_TAG = "HttpExample";
    private String[] lunchUrls = Constants.LUNCH_URLS; 
	private TextView textView;

	@Override
	protected void onResume() {

		setCurrentView(MenuItems.LUNCH);
		createLunchView();
		
		textView = (TextView)findViewById(R.id.LunchTextView);
		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		    if (networkInfo != null && networkInfo.isConnected()) {
		        new DownLoadWebPage().execute("test");
		    } else {
		        textView.setText("Ingen internetanslutning funnen");
		    }
		
		super.onResume();
	}
	
	private void createLunchView(){
		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_lunch, null));
    	
    }
public class DownLoadWebPage extends AsyncTask<String, String, String>{
		@Override
		protected void onPreExecute(){
			runTransparentLoadAnimation();
		}
		
		@Override
		protected String doInBackground(String... urls) {
			
			try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Det gick ej att hämta sidan";
            }

		}
		
		@Override
		protected void onPostExecute(String result){
    		textView.setText(Html.fromHtml(result));
    		stopAnimation();
		}
	}

	private String downloadUrl(String myurl) throws IOException {
	    InputStream is = null;
	    StringBuffer sb = new StringBuffer();    
	    try {
	    	for(String aUrl : lunchUrls){
		        URL url = new URL(aUrl);
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
		        if(aUrl.equals(Constants.KOKBOKLUNCH))
		        	sb.append(fetchKokBoken(is));
		        else
		        	sb.append(readIt(is));
		        
		        is = null;
	    	}
//	        String contentAsString = readIt(is);
	        return sb.toString();
//	        		contentAsString;
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}

	public String readIt(InputStream stream) throws IOException{
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
	
	public String fetchKokBoken(InputStream stream) throws IOException{
	
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



