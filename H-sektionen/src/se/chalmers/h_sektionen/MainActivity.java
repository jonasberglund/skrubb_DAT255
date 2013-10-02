package se.chalmers.h_sektionen;

import java.util.ArrayList;

import se.chalmers.h_sektionen.utils.CacheCompass;
import se.chalmers.h_sektionen.utils.DataSource;
import se.chalmers.h_sektionen.utils.LoadData;
import se.chalmers.h_sektionen.utils.MenuItems;
import se.chalmers.h_sektionen.utils.MockTemp;
import se.chalmers.h_sektionen.utils.NewsAdapter;
import se.chalmers.h_sektionen.utils.NewsItem;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends BaseActivity {
	
	NewsAdapter newsAdapter;
	ListView newsFeed;
	private CacheCompass cacheCompass;
	
	
	@Override
	 protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		cacheCompass = CacheCompass.getInstance(this);
		
	}
	
	
	@Override
	protected void onResume() {

		super.onResume();
		setCurrentView(MenuItems.NEWS);
		createNewsView();
	}
    
    private void createNewsView(){
    	getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_news, null));
		
		newsFeed = (ListView) findViewById(R.id.news_feed);
		
		refresh(new DataSource<ArrayList<NewsItem>>(){
			@Override
			public ArrayList<NewsItem> getData(){
				//return (ArrayList<NewsItem>) MockTemp.parseData(MockTemp.getData());
				return LoadData.loadNews();
			}
		});
    }
    
	public void refresh(DataSource<ArrayList<NewsItem>> ds){
		new LoadNews().execute(ds);
	}
    
    private class LoadNews extends AsyncTask<DataSource<ArrayList<NewsItem>>, String, String>{
		
		
		@Override
		protected void onPreExecute(){
			runTransparentLoadAnimation();
		}
		
		@Override
		protected String doInBackground(DataSource<ArrayList<NewsItem>>... ds) {
			
			ArrayList<NewsItem> list = ds[0].getData();
			newsAdapter = new NewsAdapter(MainActivity.this, R.layout.news_feed_item, list);
						
			return "Done";
		}
		
		@Override
        protected void onPostExecute(String s){

			newsFeed.setAdapter(newsAdapter);
			stopAnimation();
			
		}
    }
}
