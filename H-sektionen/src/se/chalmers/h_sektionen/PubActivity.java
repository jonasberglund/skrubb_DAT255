package se.chalmers.h_sektionen;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.h_sektionen.utils.Event;
import se.chalmers.h_sektionen.utils.ExpandAnimation;
import se.chalmers.h_sektionen.utils.LoadData;
import se.chalmers.h_sektionen.utils.MenuItems;
import se.chalmers.h_sektionen.utils.PubArrayAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class PubActivity extends BaseActivity {

	PubArrayAdapter pubFeedAdapter;
	ListView pubsFeed;
	

	
	@Override
	protected void onResume() {
		setCurrentView(MenuItems.PUB);
		createPubView();
		
		super.onResume();
	}
    
	private void createPubView(){

		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_pub, null));
		
		pubsFeed = (ListView) findViewById(R.id.pubs_feed);;
		
    	refreshEvents();
    	addActionListner();

	}
	
	public void refreshEvents(){
		new LoadPubInBg().execute();
	}
	
	private void addActionListner(){
		 pubsFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
	                View toolbar = view.findViewById(R.id.toolbarPubs);
	                ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);
	                toolbar.startAnimation(expandAni);
	            }
	        });
	}
	
	public class LoadPubInBg extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute(){
			runTransparentLoadAnimation();
		}
		
		@Override
		protected String doInBackground(String... params){
			List<Event> pubs = new ArrayList<Event>();
			new LoadData();
			
			pubs = LoadData.loadPubs();
			pubFeedAdapter = new PubArrayAdapter(PubActivity.this, R.layout.pubs_feed_item, pubs);
		
			return "Done";
		}
		
		@Override
		protected void onPostExecute(String s){
			pubsFeed.setAdapter(pubFeedAdapter);
    		stopAnimation();
		}
	}

}
