package se.chalmers.h_sektionen;

import java.util.ArrayList;

import org.json.JSONException;

import se.chalmers.h_sektionen.utils.Constants;
import se.chalmers.h_sektionen.utils.DataSource;
import se.chalmers.h_sektionen.utils.LoadData;
import se.chalmers.h_sektionen.utils.MenuItems;
import se.chalmers.h_sektionen.utils.NewsAdapter;
import se.chalmers.h_sektionen.utils.NewsItem;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends BaseActivity {
	
	NewsAdapter newsAdapter;
	ListView newsFeed;
    
	@Override
	protected void onResume() {

		super.onResume();
		setCurrentView(MenuItems.NEWS);
		createNewsView();
	}
    
    private void createNewsView(){
    	
    	if (connectedToInternet()){
	    	getFrameLayout().removeAllViews();
			getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_news, null));
			
			newsFeed = (ListView) findViewById(R.id.news_feed);
			
			new LoadNewsInBg().execute("");
    	} else {
    		setErrorView(Constants.INTERNET_CONNECTION_ERROR_MSG);
    	}
    }
    
    private class LoadNewsInBg extends AsyncTask<String, String, Boolean>{
		
		
		@Override
		protected void onPreExecute(){
			runTransparentLoadAnimation();
		}
		
		@Override
		protected Boolean doInBackground(String... s) {
			try {
				ArrayList<NewsItem> list = LoadData.loadNews();
				newsAdapter = new NewsAdapter(MainActivity.this, R.layout.news_feed_item, list);
				return true;
			} catch (Exception e){return false;}
						
		}
		
		@Override
        protected void onPostExecute(Boolean success){
			stopAnimation();
			
			if (success){
				ImageView img = new ImageView(MainActivity.this);
				img.setAdjustViewBounds(true);
				img.setImageResource(R.drawable.news);
				newsFeed.addHeaderView(img,null,false);
				newsFeed.setAdapter(newsAdapter);
			} else {
				setErrorView(Constants.GET_FEED_ERROR_MSG);
			}
			
		}
    }
}
