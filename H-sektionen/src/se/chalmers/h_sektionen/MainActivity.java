package se.chalmers.h_sektionen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import se.chalmers.h_sektionen.utils.ContactCard;
import se.chalmers.h_sektionen.utils.ContactCardArrayAdapter;
import se.chalmers.h_sektionen.utils.InfoThread;

import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import se.chalmers.h_sektionen.utils.ExpandAnimation;
import se.chalmers.h_sektionen.utils.LoadData;
import se.chalmers.h_sektionen.utils.LoadEvents;
import se.chalmers.h_sektionen.utils.MenuItems;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import se.chalmers.h_sektionen.utils.MockTemp;
import se.chalmers.h_sektionen.utils.NewsAdapter;
import se.chalmers.h_sektionen.utils.NewsItem;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ProgressBar;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;

public class MainActivity extends BaseActivity {
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setCurrentView(MenuItems.NEWS);
		createNewsView();
	}
    
    private void createNewsView(){
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_news, null));
    	
    	ListView newsFeed;
        NewsAdapter newsAdapter;
    
		newsFeed = (ListView) findViewById(R.id.news_feed);
		newsAdapter = new NewsAdapter(this, MockTemp.parseData(MockTemp.getDummyData(getAssets())), getResources());
		newsFeed.setAdapter(newsAdapter);
    }
	
    private void setupInfoView() {
    	try {
    		StringBuilder sb =  new StringBuilder();
    		
    		Thread t = new InfoThread(sb);
    		t.start();
    		t.join();
    		
    		JSONObject json = new JSONObject(sb.toString());
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
    		}
    		
    		openingHoursTextView.setText(Html.fromHtml(openingHoursString.toString()));
    		
    		contactListView.addHeaderView(linksLayout);
    		
    		contactListView.setAdapter(new ContactCardArrayAdapter(this, R.layout.contact_list_item, contactCards));
    		
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private void createEventsView(){
    	getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_events, null));
    	
    	List<TreeMap<String, String>> data = new ArrayList<TreeMap<String, String>>();
    	
    	try {
    		
    		ListView eventsFeed = (ListView) findViewById(R.id.events_feed);
    		ArrayAdapter<String> feedAdapter = new ArrayAdapter<String>(this, R.layout.events_feed_item, new LoadEvents().execute().get());
			eventsFeed.setAdapter(feedAdapter);
			
			eventsFeed.setOnItemClickListener(new EventsItemClickListener());
    	}
		catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	
    }

    private class EventsItemClickListener implements ListView.OnItemClickListener{

    	public void onItemClick(AdapterView parent, View view, int position, long id) {

    		/*View toolbar = view.findViewById(R.id.toolbar);
            // Creating the expand animation for the item
            ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);
            // Start the animation on the toolbar
            toolbar.startAnimation(expandAni);*/

		  }
		
		
	}

}
