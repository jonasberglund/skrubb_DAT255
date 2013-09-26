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
import android.content.Intent;
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
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {
    private String[] menuTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private FrameLayout frameLayout;
    	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        
        setupActionBar();
        
        menuTitles = getResources().getStringArray(R.array.menu_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setCacheColorHint(Color.BLACK);
        
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, menuTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		//Parse.com Add your Parse API keys
		Parse.initialize(this, "f4nb9heDlUu0uBmPiOJlYCXxlNnHftMkoBRkurLN", 
				"y7raMOFCv6mkDLm953GFBuRI6P3XAzDYtvbgzmm4");
		
		//Parse.com inform the Parse Push Service that it is ready for notifications.
		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		//Parse.com track statistics around application opens
		ParseAnalytics.trackAppOpened(getIntent());
	}
	
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {

        	frameLayout.removeAllViews();
        	LayoutInflater inflater = getLayoutInflater();
        	
        	switch (position) {
        	case MenuItems.NEWS:
        		createNewsView();
        		break;
        	case MenuItems.LUNCH:
        		frameLayout.addView(inflater.inflate(R.layout.view_lunch, null));
        		break;
        	case MenuItems.PUB:
        		frameLayout.addView(inflater.inflate(R.layout.view_pub, null));
        		break;
        	case MenuItems.INFO:
        		frameLayout.addView(inflater.inflate(R.layout.view_info, null));
        		setupInfoView();
        		break;
        	case MenuItems.EVENTS:
        		createEventsView();
        		break;
        	case MenuItems.VOTE:
        		frameLayout.addView(inflater.inflate(R.layout.view_vote, null));
        		break;
        	case MenuItems.SUGGEST:
        		frameLayout.addView(inflater.inflate(R.layout.view_suggest, null));
        		break;	
        	default:
        		return;
        	}
        	mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }
    
    private void createNewsView(){
		frameLayout.addView(getLayoutInflater().inflate(R.layout.view_news, null));
    	
    	ListView newsFeed;
        NewsAdapter newsAdapter;
    
		newsFeed = (ListView) findViewById(R.id.news_feed);
		newsAdapter = new NewsAdapter(this, MockTemp.parseData(MockTemp.getDummyData(getAssets())), getResources());
		newsFeed.setAdapter(newsAdapter);
    }
	
    private void setupInfoView() {
    	try {
    		StringBuilder sb =  new StringBuilder();
    		
//    		URL url = new URL("http://10.0.2.2/info/");
//    		URLConnection conn = url.openConnection();
//    		conn.addRequestProperty("Accept", "application/json");
//    		conn.connect();
//    		
//    		
//    		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//    		
//    		String line;
//    		while ((line = br.readLine()) != null) {
//    			sb.append(line);
//    		}
//    		br.close();
    		
    		Thread t = new InfoThread(sb);
    		t.start();
    		t.join();
    		
    		JSONObject json = new JSONObject(sb.toString());
    		JSONArray members = json.getJSONArray("members");
    		
    		List<ContactCard> contactCards = new ArrayList<ContactCard>();
    		
    		for (int i=0; i<members.length(); i++) {
    			//System.out.println(members.getJSONObject(i).getString("name"));
    			String name = members.getJSONObject(i).getString("name");
    			String position = members.getJSONObject(i).getString("position");
    			String email = members.getJSONObject(i).getString("email");
    			String picAddr = members.getJSONObject(i).getString("picture");
    			String phoneNumber = members.getJSONObject(i).getString("phone");
    			contactCards.add(new ContactCard(name, position, email, phoneNumber, picAddr));
    		}
    		
    		ListView contactListView = (ListView) findViewById(R.id.contact_info_list);
    		contactListView.setCacheColorHint(Color.WHITE);
    		
    		contactListView.setAdapter(new ContactCardArrayAdapter(this, R.layout.contact_list_item, contactCards));
    		contactListView.setClickable(false);

    		contactListView.getRootView().invalidate();
    		
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private void createEventsView(){
    	//frameLayout.addView(getLayoutInflater().inflate(R.layout.view_events, null));
    	
    	Intent intent = new Intent(this, EventsActivity.class);
    	startActivity(intent);
    	
    	
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
    
    private void setupActionBar() {
    	ActionBar ab = getSupportActionBar();
    	ab.setDisplayShowCustomEnabled(true);
    	ab.setDisplayShowTitleEnabled(false);
    	ab.setIcon(R.drawable.ic_action_overflow);
    	
    	LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	View v = inflator.inflate(R.layout.action_bar_title, null);

//    	Om vi vill ha  schysst font till titlen tydligen
//    	TextView titleTV = (TextView) v.findViewById(R.id.title);
//    	Typeface font = Typeface.createFromAsset(getAssets(), "fonts/your_custom_font.ttf");
//    	titleTV.setTypeface(font);
    	
    	ab.setCustomView(v);
    	
    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
    		enableHomeButton();
    	} else {
    		ab.setHomeButtonEnabled(true);
    	}
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void enableHomeButton() {
    	getActionBar().setHomeButtonEnabled(true);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		 switch(item.getItemId()) {		 	
		 	case android.R.id.home:
		 		toggleMenu();
		 		return true;
		 	default:
		 		return super.onOptionsItemSelected(item);
		 }
	}
	
	private void toggleMenu() {
		if(mDrawerLayout.isDrawerOpen(Gravity.LEFT))
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		else
			mDrawerLayout.openDrawer(Gravity.LEFT);
	}

}



