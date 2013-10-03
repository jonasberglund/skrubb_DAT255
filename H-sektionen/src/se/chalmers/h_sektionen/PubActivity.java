package se.chalmers.h_sektionen;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.h_sektionen.utils.Event;
import se.chalmers.h_sektionen.utils.ExpandAnimation;
import se.chalmers.h_sektionen.utils.LoadData;
import se.chalmers.h_sektionen.utils.MenuItems;
import se.chalmers.h_sektionen.utils.PubArrayAdapter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
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

		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_pub, null));
		
		pubsFeed = (ListView) findViewById(R.id.pubs_feed);;
		
    	refreshEvents();
    	addActionListner();

	}
	
	/** Adding action listner to all events */
	public void refreshEvents(){
		new LoadPubInBg().execute();
	}
	
	/** Adding action listner to all events and */
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
	public class LoadPubInBg extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute(){
			runTransparentLoadAnimation();
		}
		
		@Override
		protected String doInBackground(String... params){
			List<Event> pubs = new ArrayList<Event>();
			new LoadData();

			//Load data and creating adapter 
			pubs = LoadData.loadPubs();
			pubFeedAdapter = new PubArrayAdapter(PubActivity.this, R.layout.pubs_feed_item, pubs);
		
			return "Done";
		}
		
		@Override
		protected void onPostExecute(String s){
			
			//Adding header picture to array adapter
			ImageView img = new ImageView(PubActivity.this);
			img.setAdjustViewBounds(true);
			img.setImageResource(R.drawable.pubf);
			pubsFeed.addHeaderView(img,null,false);
			
			pubsFeed.setAdapter(pubFeedAdapter);
    		stopAnimation();
		}
	}

}
