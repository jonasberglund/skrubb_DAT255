package se.chalmers_h.h_sektionen;

import java.util.ArrayList;

import se.chalmers_h.h_sektionen.adapters.NewsArrayAdapter;
import se.chalmers_h.h_sektionen.containers.NewsItem;
import se.chalmers_h.h_sektionen.utils.Connectivity;
import se.chalmers_h.h_sektionen.utils.LoadData;
import se.chalmers_h.h_sektionen.utils.MenuItems;
import se.chalmers_h.h_sektionen.utils.OnBottomScrollListener;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Main Activity that creates the news view
 * @Author Olle Svensson, Anders Johansson
 * @Copyright (c) 2013 Anders Johansson, Olle Svensson, Robin Tornquist, Rikard Ekbom, Oskar Gustavsson, Jonas Berglund
 * @Licens Apache
 */

public class MainActivity extends BaseActivity {
	
	private NewsArrayAdapter newsAdapter;
	private ListView newsFeed;
	private View aniFooter;
	private boolean loadingFirstTime;
	private boolean currentlyLoading;
	private int descending;
	private AsyncTask<Integer, String, ArrayList<NewsItem>> loadNewsTask;
	
	/**
	 * Superclass method onResume
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
		newsAdapter = new NewsArrayAdapter(MainActivity.this, R.layout.news_feed_item, new ArrayList<NewsItem>());
		loadingFirstTime = true;
		currentlyLoading = false;
		setCurrentView(MenuItems.NEWS);
		createNewsView();
		descending = 0;
	}
	
	/**
	 * On pause: cancel the AsyncTask that downloads the lunch menu.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if (loadNewsTask != null && !loadNewsTask.isCancelled()) {
			loadNewsTask.cancel(true);
			currentlyLoading = false;
			descending = 0;
		}
	}
    
	/**
	 * Creates news view GUI
	 */
    private void createNewsView(){
    	
    	if (connectedToInternet()){
    		
	    	getFrameLayout().removeAllViews();
			getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_news, null));
			
			newsFeed = (ListView) findViewById(R.id.news_feed);
			aniFooter = getLayoutInflater().inflate(R.layout.footer_animation, null);
			
			//Initialize header
			ImageView imgHeader = new ImageView(MainActivity.this);
			imgHeader.setAdjustViewBounds(true);
			imgHeader.setImageResource(R.drawable.news);

			//Add to list view
			newsFeed.addHeaderView(imgHeader,null,false);
			newsFeed.setAdapter(newsAdapter);

			//Add scroll listener
			newsFeed.setOnScrollListener(new OnBottomScrollListener(){
				@Override
				protected void doOnScrollCompleted() {
					if (!currentlyLoading){
						loadNewsTask = new LoadNewsInBg().execute(++descending);
					}			
				}});
			
			loadNewsTask = new LoadNewsInBg().execute(descending);
    	} else {
    		setErrorView(getString(R.string.INTERNET_CONNECTION_ERROR_MSG));
    	}
    }
    
    /**
     * Adds a loading animation to the listview footer.
     */
    private void addFooterAnimation(){
		newsFeed.addFooterView(aniFooter,null,false);
    }
    
    /**
     * Removes loading animation from listview footer.
     */
    private void removeFooterAnimation(){
    	newsFeed.removeFooterView(aniFooter);
    }
    
    /**
     * AsyncTask for loading news feed posts in background.
     */
    private class LoadNewsInBg extends AsyncTask<Integer, String, ArrayList<NewsItem>>{
		
		
    	/**
		 * Superclass method onPreExecte
		 */
		@Override
		protected void onPreExecute(){
			
			currentlyLoading = true;
			
			if (loadingFirstTime){
				runTransparentLoadAnimation();
			} else {
				addFooterAnimation();
			}
		}
		
		/**
		 * Superclass method doInBackground
		 */
		@Override
		protected ArrayList<NewsItem> doInBackground(Integer... descending) {	
			try {
				return LoadData.loadNews(descending[0], Connectivity.isConnectedFast(MainActivity.this));			
			} catch (Exception e){
				return null;
			}
		}
		
		/**
		 * Superclass method onPostExecute
		 */
		@Override
        protected void onPostExecute(ArrayList<NewsItem> list){
			
			//Check if new posts were loaded successfully
			if (list != null){
				if (loadingFirstTime){
					stopAnimation();
					loadingFirstTime = false;
				} else {
					removeFooterAnimation();
				}
				
				//Add new posts to adapter and notify list view
				currentlyLoading = false;
				newsAdapter.addAll(list);
				newsAdapter.notifyDataSetChanged();
				
			} else {
				setErrorView(getString(R.string.GET_FEED_ERROR_MSG));
			}
			
		}
    }
}
