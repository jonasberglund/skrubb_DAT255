package se.chalmers_h.h_sektionen;

import java.util.List;

import org.json.JSONException;

import se.chalmers_h.h_sektionen.adapters.PubArrayAdapter;
import se.chalmers_h.h_sektionen.containers.Event;
import se.chalmers_h.h_sektionen.utils.ExpandAnimation;
import se.chalmers_h.h_sektionen.utils.LoadData;
import se.chalmers_h.h_sektionen.utils.MenuItems;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Activity that displays pub events at Lindholmen.
 * @Author Jonas Berglund
 * @Copyright (c) 2013 Anders Johansson, Olle Svensson, Robin Tornquist, Rikard Ekbom, Oskar Gustavsson, Jonas Berglund
 * @Licens Apache
 */
public class PubActivity extends BaseActivity {

	private PubArrayAdapter pubFeedAdapter;
	private ListView pubsFeed;
	private AsyncTask<String, String, Boolean> loadPubsTask;

	/** Notify BaseActivity this view is set and create this view */
	@Override
	protected void onResume() {
		super.onResume();
		setCurrentView(MenuItems.PUB);
		createPubView();
	}
	
	/**
	 * On pause: cancel the AsyncTask that downloads the calendar.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if (loadPubsTask != null && !loadPubsTask.isCancelled()) {
			loadPubsTask.cancel(true);
		}
	}

	/** Create pub view */
	private void createPubView(){
		
		if (connectedToInternet()){
			getFrameLayout().removeAllViews();
			getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_pub, null));
			
			pubsFeed = (ListView) findViewById(R.id.pubs_feed);;
			
			//Adding header picture to array adapter
			ImageView img = new ImageView(PubActivity.this);
			img.setAdjustViewBounds(true);
			img.setImageResource(R.drawable.pubf);
			pubsFeed.addHeaderView(img,null,false);
			
			// Need to set adapter to make the ListView visible.
			pubsFeed.setAdapter(null);
			
			loadPubsTask = new LoadPubInBg().execute();
	    	addActionListener();
		} else {
			setErrorView(getString(R.string.INTERNET_CONNECTION_ERROR_MSG));
		}

	}
	
	/** Add action listener */
	private void addActionListener(){
		pubsFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				View toolbar = view.findViewById(R.id.toolbarPubs);
				ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);
				toolbar.startAnimation(expandAni);
			}
		});
	}
	
	/** Loading all events i background activity (AsyncTask) */
	public class LoadPubInBg extends AsyncTask<String, String, Boolean> {

		/**
		 * Runs a loading animation
		 */
		@Override
		protected void onPreExecute(){
			runTransparentLoadAnimation();
		}
		
		/** Do background work */
		@Override
		protected Boolean doInBackground(String... params){
			
			try {
				//Load data and creating adapter
				List<Event> pubs = LoadData.loadPubs();
				pubFeedAdapter = new PubArrayAdapter(PubActivity.this, R.layout.pubs_feed_item, pubs);
				return true;
			} catch (JSONException e){
				return false;
			}
		}
		
		/** Display the events in the activity view if successful */
		@Override
		protected void onPostExecute(Boolean success){
			
    		stopAnimation();
    		
    		if (success){
				pubsFeed.setAdapter(pubFeedAdapter);
    		} else {
    			setErrorView(getString(R.string.GET_FEED_ERROR_MSG));
    		}
		}
	}

}
