package se.chalmers.h_sektionen;

import java.util.ArrayList;

import se.chalmers.h_sektionen.utils.MenuItems;
import se.chalmers.h_sektionen.utils.MockTemp;
import se.chalmers.h_sektionen.utils.NewsAdapter;
import se.chalmers.h_sektionen.utils.NewsItem;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class MainActivity extends Activity {
    private String[] menuTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private FrameLayout frameLayout;
   
    
    
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        menuTitles = getResources().getStringArray(R.array.menu_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
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
//            Intent intent = new Intent(parent.getContext(), LunchActivity.class);
//        	startActivity(intent);
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
        		break;
        	case MenuItems.EVENTS:
        		frameLayout.addView(inflater.inflate(R.layout.view_events, null));
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
