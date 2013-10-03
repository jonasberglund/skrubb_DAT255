package se.chalmers.h_sektionen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;


import se.chalmers.h_sektionen.utils.*;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class EventsActivity extends BaseActivity {
	
	EventsArrayAdapter feedAdapter;
	ListView eventsFeed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onStart(){
		super.onStart();
	}

	/** Notify BaseActivity this view is set and create this view */
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
	    	addActionListner();
		} else {
			setErrorView(Constants.INTERNET_CONNECTION_ERROR_MSG);
		}

	}
	
	/** Adding action listner to all events and */
	private void addActionListner(){
		
		 eventsFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
	                View toolbar = view.findViewById(R.id.toolbarEvents);
	                ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);
	                toolbar.startAnimation(expandAni);
	            }
	        });
	}
	
	/** Loading all events i background activity (AsyncTask) */
	public class LoadEventsInBg extends AsyncTask<String, String, Boolean>{

		@Override
		protected void onPreExecute(){
			runTransparentLoadAnimation();
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			try {
				List<Event> events = LoadData.loadEvents();
				feedAdapter = new EventsArrayAdapter(EventsActivity.this, R.layout.events_feed_item, events);
				return true;
			} catch (JSONException e) {return false;}
		}

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
				setErrorView(Constants.GET_FEED_ERROR_MSG);
			}
		}
	}

}




