package se.chalmers.h_sektionen;

import java.util.ArrayList;
import java.util.List;

import java.util.TreeMap;

import se.chalmers.h_sektionen.utils.LoadEvents;
import se.chalmers.h_sektionen.utils.MenuItems;
import se.chalmers.h_sektionen.utils.MockTemp;
import se.chalmers.h_sektionen.utils.NewsAdapter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends BaseActivity {
    
	@Override
	protected void onResume() {

		setCurrentView(MenuItems.NEWS);
		createNewsView();
		
		super.onResume();
	}
    
    private void createNewsView(){
    	getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_news, null));
    	
    	ListView newsFeed;
        NewsAdapter newsAdapter;
    
		newsFeed = (ListView) findViewById(R.id.news_feed);
		newsAdapter = new NewsAdapter(this, MockTemp.parseData(MockTemp.getDummyData(getAssets())), getResources());
		newsFeed.setAdapter(newsAdapter);
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
