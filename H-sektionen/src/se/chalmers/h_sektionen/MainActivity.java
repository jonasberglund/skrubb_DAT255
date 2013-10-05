package se.chalmers.h_sektionen;

import java.util.ArrayList;

import org.json.JSONException;
import se.chalmers.h_sektionen.utils.CacheCompass;

import se.chalmers.h_sektionen.utils.Constants;
import se.chalmers.h_sektionen.utils.LoadData;
import se.chalmers.h_sektionen.utils.MenuItems;
import se.chalmers.h_sektionen.utils.NewsAdapter;
import se.chalmers.h_sektionen.utils.NewsItem;
import se.chalmers.h_sektionen.utils.OnBottomScrollListener;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends BaseActivity {
	
	private NewsAdapter newsAdapter;
	private ListView newsFeed;
	private boolean initializeListView;
	private int descending;
	
	/**
	 * Superclass method onCreate
	 */
	@Override
	 protected void onCreate(Bundle savedInstanceState){	
		super.onCreate(savedInstanceState);
		newsAdapter = new NewsAdapter(MainActivity.this, R.layout.news_feed_item, new ArrayList<NewsItem>());
	}
	
	
	/**
	 * Superclass method onResume
	 */
	@Override
	protected void onResume() {

		super.onResume();
		initializeListView = true;
		setCurrentView(MenuItems.NEWS);
		createNewsView();
		descending = 0;
	}
    
	/**
	 * Creates news view GUI
	 */
    private void createNewsView(){
    	
    	if (connectedToInternet()){
	    	getFrameLayout().removeAllViews();
			getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_news, null));
			
			newsFeed = (ListView) findViewById(R.id.news_feed);
			
			newsFeed.setOnScrollListener(new OnBottomScrollListener(){
				@Override
				protected void doOnScrollCompleted() {
					new LoadNewsInBg().execute(++descending);
					
				}});
			
			new LoadNewsInBg().execute(descending);
    	} else {
    		setErrorView(Constants.INTERNET_CONNECTION_ERROR_MSG);
    	}
    }
    
    /**
     * AsyncTask for loading news feed posts in background.
     */
    private class LoadNewsInBg extends AsyncTask<Integer, String, Boolean>{
		
		
    	/**
		 * Superclass method onPreExecte
		 */
		@Override
		protected void onPreExecute(){
			if (initializeListView){
				runTransparentLoadAnimation();
			}
		}
		
		/**
		 * Superclass method doInBackground
		 */
		@Override
		protected Boolean doInBackground(Integer... descending) {	
			try {
				newsAdapter.addAll(LoadData.loadNews(descending[0]));
				return true;
			} catch (Exception e){
				return false;
			}
		}
		
		/**
		 * Superclass method onPostExecute
		 */
		@Override
        protected void onPostExecute(Boolean feedLoadedSuccessfully){
			
			if (feedLoadedSuccessfully){
				if (initializeListView){
					stopAnimation();
					
					//Initialize header
					ImageView imgHeader = new ImageView(MainActivity.this);
					imgHeader.setAdjustViewBounds(true);
					imgHeader.setImageResource(R.drawable.news);
					
					//Intialize footer
					ImageView imgFooter = new ImageView(MainActivity.this);
					imgFooter.setAdjustViewBounds(true);
					imgFooter.setImageResource(R.drawable.loadmore);
					
					//Add to list view
					newsFeed.addHeaderView(imgHeader,null,false);
					newsFeed.addFooterView(imgFooter,null,false);
					newsFeed.setAdapter(newsAdapter);
					initializeListView = false;
				} else {
					newsAdapter.notifyDataSetChanged();
				}
			} else {
				setErrorView(Constants.GET_FEED_ERROR_MSG);
			}
			
		}
    }
}
