package se.chalmers.h_sektionen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import se.chalmers.h_sektionen.utils.ContactCard;
import se.chalmers.h_sektionen.utils.ContactCardArrayAdapter;
import se.chalmers.h_sektionen.utils.JSONLoader;
import se.chalmers.h_sektionen.utils.MenuItems;

public class InfoActivity extends BaseActivity {
	private List<ContactCard> contactCards = null;
	private StringBuilder htmlLinks = null;
	private StringBuilder openingHoursString = null;
	
	@Override
	protected void onResume() {
		
		setCurrentView(MenuItems.INFO);
		new GetInfoTask().execute("http://jpv-net.dyndns.org:1337/H-Sektionen/info/");
		
		super.onResume();
	}
	
	private class GetInfoTask extends AsyncTask<String, String, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			runLoadAnimation();
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			JSONLoader jsonLoader =  new JSONLoader(params[0]);
			JSONObject json = jsonLoader.getJSONFromUrl();
			
			if (json != null) {
				try {
					
					// Board members
					JSONArray members = json.getJSONArray("members");
					contactCards = new ArrayList<ContactCard>();
		    		
		    		for (int i=0; i<members.length(); i++) {
		    			String name = members.getJSONObject(i).getString("name");
		    			String position = members.getJSONObject(i).getString("position");
		    			String email = members.getJSONObject(i).getString("email");
		    			String picAddr = members.getJSONObject(i).getString("picture");
		    			String phoneNumber = members.getJSONObject(i).getString("phone");
		    			contactCards.add(new ContactCard(name, position, email, phoneNumber, picAddr));
		    		}
		    		
		    		//Links
		    		JSONArray links = json.getJSONArray("links");
		    		htmlLinks = new StringBuilder();
		    		
		    		for (int i=0; i<links.length(); i++) {
		    			htmlLinks.append("<a href=\"");
		    			htmlLinks.append(links.getJSONObject(i).getString("href"));
		    			htmlLinks.append("\">");
		    			htmlLinks.append(links.getJSONObject(i).getString("name"));
		    			htmlLinks.append("</a><br />");
		    		}
		    		
		    		// Opening hours
		    		JSONArray openingHours = json.getJSONArray("openinghours");
		    		openingHoursString = new StringBuilder();
		    		
		    		for (int i=0; i<openingHours.length(); i++) {
		    			openingHoursString.append("<b>");
		    			openingHoursString.append(openingHours.getJSONObject(i).getString("name"));
		    			openingHoursString.append("</b>:<br />");
		    			openingHoursString.append(openingHours.getJSONObject(i).getString("opentime"));
		    			openingHoursString.append("<br />");
		    		}
		    		
				} catch (Exception e) {
					return false;
				}
			}
			
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean done) {
			super.onPostExecute(done);
			
			if (done) {
				getFrameLayout().removeAllViews();
				getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_info, null));
				
				ListView contactListView = (ListView) findViewById(R.id.contact_info_list);
	    		contactListView.setCacheColorHint(Color.WHITE);
	    		
	    		LinearLayout linksLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.info_links, null);
	    		TextView linksTextView = (TextView)linksLayout.findViewById(R.id.links);
	    		TextView openingHoursTextView = (TextView)linksLayout.findViewById(R.id.opening_hours);
	    		
	    		linksTextView.setMovementMethod(LinkMovementMethod.getInstance());
	    		linksTextView.setText(Html.fromHtml(htmlLinks.toString()));
	    		
	    		openingHoursTextView.setText(Html.fromHtml(openingHoursString.toString()));
	    		contactListView.addHeaderView(linksLayout);
	    		contactListView.setAdapter(new ContactCardArrayAdapter(InfoActivity.this, R.layout.contact_list_item, contactCards));
			
			} else {
				setErrorView();
			}
		}
		
	}
	
}