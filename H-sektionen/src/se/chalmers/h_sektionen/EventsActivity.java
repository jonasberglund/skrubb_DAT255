package se.chalmers.h_sektionen;

import java.util.ArrayList;
import java.util.List;


import se.chalmers.h_sektionen.utils.*;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventsActivity extends BaseActivity {
	
	EventsArrayAdapter feedAdapter;
	ListView eventsFeed;

	@Override
	protected void onResume() {

		setCurrentView(MenuItems.EVENTS);
		createEventsView();
		
		super.onResume();
	}
    
	private void createEventsView(){

		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_events, null));
		
		
		//setContentView(R.layout.view_events);
		 eventsFeed = (ListView) findViewById(R.id.events_feed);;
		
		//TextView txt = (TextView) findViewById(R.id.title);  
		//Typeface font = Typeface.createFromAsset(getAssets(), "Chantelli_Antiqua.ttf");  
		//txt.setTypeface(font);  
		
    	try {
    		
    		//ListView eventsFeed = (ListView) findViewById(R.id.events_feed);
    		//ArrayAdapter<String> feedAdapter = new ArrayAdapter<String>(this, R.layout.events_feed_item, new LoadEvents().execute().get());
			//List<Event> events = new ArrayList<Event>();
			
			//events = new LoadEvents().execute().get();

    		
    		
    		//feedAdapter.refreshEvents();
    		refreshEvents();
    		
    		 // Creating an item click listener, to open/close our toolbar for each item
            eventsFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                    View toolbar = view.findViewById(R.id.toolbarEvents);

                    // Creating the expand animation for the item
                    ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);

                    // Start the animation on the toolbar
                    toolbar.startAnimation(expandAni);
                }
            });
    	}
		catch (Exception e) {
			e.printStackTrace();
		}
    	
    	 
	}
	
	public void refreshEvents(){
		new LoadEventsInBg().execute();
	}
	
	public class LoadEventsInBg extends AsyncTask<String, String, String>{
		
		List<Event> events = new ArrayList<Event>();
		
		
		@Override
		protected String doInBackground(String... params) {
			new LoadData();
			events = LoadData.loadEvents();
		 
			return "Done";
		}
		
		@Override
		protected void onPostExecute(String s){
			

			//for(Event e : events){
			//	feedAdapter.add(e);
			//}
			//EventsArrayAdapter.this.notifyDataSetChanged();
			
			//feedAdapter.notifyDataSetChanged();

			
			feedAdapter = new EventsArrayAdapter(EventsActivity.this, R.layout.events_feed_item, events);
			
			
			eventsFeed.setAdapter(feedAdapter);
    		
    		stopAnimation();
		}
		
			
		
		@Override
		protected void onPreExecute(){
			runTransparentLoadAnimation();
			//runLoadAnimation();
		}
	}

}




