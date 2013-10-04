package se.chalmers.h_sektionen;

import java.util.List;

import org.json.JSONException;


import se.chalmers.h_sektionen.utils.*;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
/**
 * Activity that displays the events for H-sektionen.
 */
public class EventsActivity extends BaseActivity {
	
	EventsArrayAdapter feedAdapter;
	ListView eventsFeed;
	
	/** On resume */
	@Override
	protected void onResume() {
		setCurrentView(MenuItems.EVENTS);
		createEventsView();
		super.onResume();
	}
    
	/** Create events view */ 
	private void createEventsView(){
		
		if (connectedToInternet()){
			getFrameLayout().removeAllViews();
			getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_events, null));
			
			eventsFeed = (ListView) findViewById(R.id.events_feed);
			
			new LoadEventsInBg().execute();
	    	addActionListener();
		} else {
			setErrorView(getString(R.string.INTERNET_CONNECTION_ERROR_MSG));
		}

	}
	
	/** Add action listener */
	private void addActionListener(){
		
		 eventsFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
	                View toolbar = view.findViewById(R.id.toolbarEvents);
	                ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);
	                toolbar.startAnimation(expandAni);
	            }
	        });
	}
	
	/** Loading all events in background activity (AsyncTask) */
	public class LoadEventsInBg extends AsyncTask<String, String, Boolean>{

		/** 
		 * Runs a loading animation.
		 */
		@Override
		protected void onPreExecute(){
			runTransparentLoadAnimation();
		}
		
		/** Do background work */
		@Override
		protected Boolean doInBackground(String... params) {
			try {
				List<Event> events = LoadData.loadEvents();
				feedAdapter = new EventsArrayAdapter(EventsActivity.this, R.layout.events_feed_item, events);
				return true;
			} catch (JSONException e) {return false;}
		}

		/** Display the events in the activity view if successful */
		@Override
		protected void onPostExecute(Boolean success){
			stopAnimation();
			
			if (success){
				//Adding header picture to array adapter
				ImageView img = new ImageView(EventsActivity.this);
				img.setAdjustViewBounds(true);
				img.setImageResource(R.drawable.events);
				eventsFeed.addHeaderView(img,null,false);
				eventsFeed.setAdapter(feedAdapter);
			} else {
				setErrorView(getString(R.string.GET_FEED_ERROR_MSG));
			}
		}
	}

}




