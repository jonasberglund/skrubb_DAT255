package se.chalmers.h_sektionen;

import se.chalmers.h_sektionen.utils.MenuItems;
import se.chalmers.h_sektionen.utils.MockTemp;
import se.chalmers.h_sektionen.utils.NewsAdapter;
import android.widget.ListView;

public class MainActivity extends BaseActivity {
    
	@Override
	protected void onResume() {

		setCurrentView(MenuItems.NEWS);
		createNewsView();
		
		super.onResume();
	}
    
    private void createNewsView(){
    	getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_news, null));
    	
    	ListView newsFeed;
        NewsAdapter newsAdapter;
    
		newsFeed = (ListView) findViewById(R.id.news_feed);
		newsAdapter = new NewsAdapter(this, MockTemp.parseData(MockTemp.getData()), getResources());
		newsFeed.setAdapter(newsAdapter);
    }

}
