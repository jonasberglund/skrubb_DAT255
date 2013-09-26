package se.chalmers.h_sektionen;

import java.util.ArrayList;
import java.util.List;

import java.util.TreeMap;

import se.chalmers.h_sektionen.utils.LoadEvents;
import se.chalmers.h_sektionen.utils.MenuItems;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import se.chalmers.h_sektionen.utils.MockTemp;
import se.chalmers.h_sektionen.utils.NewsAdapter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
		newsAdapter = new NewsAdapter(this, MockTemp.parseData(MockTemp.getDummyData(getAssets())), getResources());
		newsFeed.setAdapter(newsAdapter);
    }

}
