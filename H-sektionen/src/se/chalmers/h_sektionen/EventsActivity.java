package se.chalmers.h_sektionen;

import java.util.ArrayList;
import java.util.List;


import se.chalmers.h_sektionen.utils.*;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_events);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//TextView txt = (TextView) findViewById(R.id.title);  
		//Typeface font = Typeface.createFromAsset(getAssets(), "Chantelli_Antiqua.ttf");  
		//txt.setTypeface(font);  
		
    	try {
    		
    		ListView eventsFeed = (ListView) findViewById(R.id.events_feed);
    		//ArrayAdapter<String> feedAdapter = new ArrayAdapter<String>(this, R.layout.events_feed_item, new LoadEvents().execute().get());
			List<Events> events = new ArrayList<Events>();
			
			events = new LoadEvents().execute().get();
			
			
    		ArrayAdapter<Events> feedAdapter = new EventsArrayAdapter(this, R.layout.events_feed_item, events);
    		eventsFeed.setAdapter(feedAdapter);
    		
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
		

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.events, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
}
