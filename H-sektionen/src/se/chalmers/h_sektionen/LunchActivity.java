package se.chalmers.h_sektionen;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import se.chalmers.h_sektionen.utils.Constants;
import se.chalmers.h_sektionen.utils.MenuItems;

import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;
/**
 * Activity responsible of the lunch menu view.
 */
public class LunchActivity extends BaseActivity {

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
		    textView.setText(getString(R.string.INTERNET_CONNECTION_ERROR_MSG));
				
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
		protected String doInBackground(String... string) {
			StringBuilder sb = new StringBuilder();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			
			sb.append("<h1>");
			sb.append(getString(R.string.title_kokboken));
			sb.append("</h1>");
			sb.append(getLunchFromRSS(Constants.KOKBOKEN_BASE+sdf.format(new Date())+".rss", "item", null, "description"));
			
			sb.append("<h1>");
			sb.append(getString(R.string.title_ls_kitchen));
			sb.append("</h1>");
			sb.append(getLunchFromRSS(Constants.LS_KITCHEN_RSS, "item", "title", "description"));
			
			return sb.toString();
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
	private String getLunchFromRSS(String urlString, String root, String title, String desc) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(conn.getInputStream(), null);
			
			StringBuilder lunchStringBuilder = new StringBuilder();
			
			Set<String> titles = new HashSet<String>();
			
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				
				if ( eventType == XmlPullParser.START_TAG && parser.getName().equals(root) )
					while ( !(eventType == XmlPullParser.END_TAG && parser.getName().equals(root)) ) {
						eventType = parser.next();
						if (title != null && eventType == XmlPullParser.START_TAG && parser.getName().equals(title)) {
							String tempTitle = parser.nextText();
							if (!titles.contains(tempTitle)) {
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
}



