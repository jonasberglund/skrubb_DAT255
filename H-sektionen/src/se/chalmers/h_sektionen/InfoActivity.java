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
	
	@Override
	protected void onResume() {
		
		setCurrentView(MenuItems.INFO);
		new GetInfoTask().execute("http://jpv-net.dyndns.org:1337/H-Sektionen/info/");
		
		super.onResume();
	}
	
	private class GetInfoTask extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			runLoadAnimation();
		}
		
		@Override
		protected JSONObject doInBackground(String... params) {
			JSONLoader jsonLoader =  new JSONLoader(params[0]);
			return jsonLoader.getJSONFromUrl();
		}
		
		@Override
		protected void onPostExecute(JSONObject json) {
			super.onPostExecute(json);
			
			if (json != null) {
				try {
					JSONArray members = json.getJSONArray("members");
		    		
		    		List<ContactCard> contactCards = new ArrayList<ContactCard>();
		    		
		    		for (int i=0; i<members.length(); i++) {
		    			String name = members.getJSONObject(i).getString("name");
		    			String position = members.getJSONObject(i).getString("position");
		    			String email = members.getJSONObject(i).getString("email");
		    			String picAddr = members.getJSONObject(i).getString("picture");
		    			String phoneNumber = members.getJSONObject(i).getString("phone");
		    			contactCards.add(new ContactCard(name, position, email, phoneNumber, picAddr));
		    		}
		    		
		    		getFrameLayout().removeAllViews();
					getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_info, null));
		    		
		    		ListView contactListView = (ListView) findViewById(R.id.contact_info_list);
		    		contactListView.setCacheColorHint(Color.WHITE);
		    		
		    		// Links
		    		LinearLayout linksLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.info_links, null);
		    		TextView linksTextView = (TextView)linksLayout.findViewById(R.id.links);
		    		linksTextView.setMovementMethod(LinkMovementMethod.getInstance());
		    		
		    		StringBuilder htmlLinks = new StringBuilder();
		    		
		    		JSONArray links = json.getJSONArray("links");
		    		
		    		for (int i=0; i<links.length(); i++) {
		    			htmlLinks.append("<a href=\"");
		    			htmlLinks.append(links.getJSONObject(i).getString("href"));
		    			htmlLinks.append("\">");
		    			htmlLinks.append(links.getJSONObject(i).getString("name"));
		    			htmlLinks.append("</a><br />");
		    		}
		    		
		    		linksTextView.setText(Html.fromHtml(htmlLinks.toString()));
		    		
		    		// Opening Hours
		    		TextView openingHoursTextView = (TextView)linksLayout.findViewById(R.id.opening_hours);
		    		
		    		StringBuilder openingHoursString = new StringBuilder();
		    		JSONArray openingHours = json.getJSONArray("openinghours");
		    		
		    		for (int i=0; i<openingHours.length(); i++) {
		    			openingHoursString.append("<b>");
		    			openingHoursString.append(openingHours.getJSONObject(i).getString("name"));
		    			openingHoursString.append("</b>:<br />");
		    			openingHoursString.append(openingHours.getJSONObject(i).getString("opentime"));
		    			openingHoursString.append("<br />");
	
			    		openingHoursTextView.setText(Html.fromHtml(openingHoursString.toString()));
		    		}
		    		contactListView.addHeaderView(linksLayout);
		    		
		    		contactListView.setAdapter(new ContactCardArrayAdapter(InfoActivity.this, R.layout.contact_list_item, contactCards));
	    		} catch (Exception e) {
	    			
	    		}
	    		
			} else {
				setErrorView();
			}
		}
		
	}
	
}