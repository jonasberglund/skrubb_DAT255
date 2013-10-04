package se.chalmers.h_sektionen;

import java.util.List;

import org.json.JSONException;

import se.chalmers.h_sektionen.utils.Event;
import se.chalmers.h_sektionen.utils.ExpandAnimation;
import se.chalmers.h_sektionen.utils.LoadData;
import se.chalmers.h_sektionen.utils.MenuItems;
import se.chalmers.h_sektionen.utils.PubArrayAdapter;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class PubActivity extends BaseActivity {

	PubArrayAdapter pubFeedAdapter;
	ListView pubsFeed;

	/** Notify BaseActivity this view is set and create this view */
	@Override
	protected void onResume() {
		setCurrentView(MenuItems.PUB);
		createPubView();
		super.onResume();
	}

	/** Create pub view */
	private void createPubView(){
		
		if (connectedToInternet()){
			getFrameLayout().removeAllViews();
			getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_pub, null));
			
			pubsFeed = (ListView) findViewById(R.id.pubs_feed);;
			
			new LoadPubInBg().execute();
	    	addActionListner();
		} else {
			setErrorView(getString(R.string.INTERNET_CONNECTION_ERROR_MSG));
		}

	}
	
	/** Add action listner */
	private void addActionListner(){
		 pubsFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
		            	View toolbar = view.findViewById(R.id.toolbarPubs);
		                ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);
		                toolbar.startAnimation(expandAni);
	            }
	        });
	}
	
	/** Loading all events i background activity (AsyncTask) */
	public class LoadPubInBg extends AsyncTask<String, String, Boolean>{

		/** What to do before background loading */
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
		
		/** After background work is done */
		@Override
		protected void onPostExecute(Boolean success){
			
    		stopAnimation();
    		
    		if (success){
			//Adding header picture to array adapter
				ImageView img = new ImageView(PubActivity.this);
				img.setAdjustViewBounds(true);
				img.setImageResource(R.drawable.pubf);
				pubsFeed.addHeaderView(img,null,false);
				pubsFeed.setAdapter(pubFeedAdapter);
    		} else {
    			setErrorView(getString(R.string.GET_FEED_ERROR_MSG));
    		}
		}
	}

}
