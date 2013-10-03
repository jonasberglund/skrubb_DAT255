package se.chalmers.h_sektionen;

import se.chalmers.h_sektionen.utils.MenuItems;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BaseActivity extends ActionBarActivity {

	private static String[] menuTitles;
    private static DrawerLayout mDrawerLayout;
    private static ListView mDrawerList;
    private static FrameLayout frameLayout;
    private static int currentView;
    
    @Override
    protected void onStart() {
    	setContentView(R.layout.activity_main);        
        setupActionBar();
        
        menuTitles = getResources().getStringArray(R.array.menu_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setCacheColorHint(Color.BLACK);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, menuTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		//Parse.com Add your Parse API keys
		Parse.initialize(this, "f4nb9heDlUu0uBmPiOJlYCXxlNnHftMkoBRkurLN", 
				"y7raMOFCv6mkDLm953GFBuRI6P3XAzDYtvbgzmm4");
		
		//Parse.com inform the Parse Push Service that it is ready for notifications.
		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		//Parse.com track statistics around application opens
		ParseAnalytics.trackAppOpened(getIntent());
        
        super.onStart();
    }
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
        	mDrawerLayout.closeDrawers();
        	if(currentView != position){
        		switch (position) {
		        	case MenuItems.NEWS:
		        		startActivityByClass(MainActivity.class);
		        		break;
		        	case MenuItems.LUNCH:
		        		startActivityByClass(LunchActivity.class);
		        		break;
		        	case MenuItems.PUB:
		        		startActivityByClass(PubActivity.class);
		        		break;
		        	case MenuItems.INFO:
		        		startActivityByClass(InfoActivity.class);
		        		break;
		        	case MenuItems.EVENTS:
		        		startActivityByClass(EventsActivity.class);
		        		break;
		        	case MenuItems.SUGGEST:
		        		startActivityByClass(SuggestActivity.class);
		        		break;
		        	case MenuItems.FAULTREPORT:
		        		startActivityByClass(FaultreportActivity.class);
		        	default:
		        		return;
		        }
        	}
        }
    }
    
    private void startActivityByClass(Class<? extends BaseActivity> c) { 
    	Intent i =  new Intent(this, c);
    	i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    	i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    	startActivity(i);
    }
    
    private void setupActionBar() {
    	ActionBar ab = getSupportActionBar();
    	ab.setDisplayShowCustomEnabled(true);
    	ab.setDisplayShowTitleEnabled(false);
    	ab.setIcon(R.drawable.ic_action_overflow);
    	
    	LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	View v = inflator.inflate(R.layout.action_bar_title, null);

//    	Om vi vill ha  schysst font till titlen tydligen
//    	TextView titleTV = (TextView) v.findViewById(R.id.title);
//    	Typeface font = Typeface.createFromAsset(getAssets(), "fonts/your_custom_font.ttf");
//    	titleTV.setTypeface(font);
    	
    	ab.setCustomView(v);
    	
    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
    		enableHomeButton();
    	} else {
    		ab.setHomeButtonEnabled(true);
    	}
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void enableHomeButton() {
    	getActionBar().setHomeButtonEnabled(true);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		 switch(item.getItemId()) {		 	
		 	case android.R.id.home:
		 		toggleMenu();
		 		return true;
		 	default:
		 		return super.onOptionsItemSelected(item);
		 }
	}
	
	private void toggleMenu() {
		if(mDrawerLayout.isDrawerOpen(Gravity.LEFT))
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		else
			mDrawerLayout.openDrawer(Gravity.LEFT);
	}
	
	protected FrameLayout getFrameLayout() {
		return frameLayout;
	}
	
	static protected void setCurrentView(int currentView) {
		BaseActivity.currentView = currentView;
	}
	
	protected void runLoadAnimation() {
		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_loading, null));
		
		((ImageView)findViewById(R.id.load_image)).startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_centre));
	}
	
	protected void runTransparentLoadAnimation() {
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_loading, null));
		
		((ImageView)findViewById(R.id.load_image)).startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_centre));
	}
	
	protected void stopAnimation(){
		ImageView img = ((ImageView)findViewById(R.id.load_image));
		TextView txt = (TextView) findViewById(R.id.load_label);
		
		View view = getLayoutInflater().inflate(R.layout.view_loading, null);
		getFrameLayout().removeView(view);
		
		img.clearAnimation();
		img.setVisibility(View.GONE);
		txt.setVisibility(View.GONE);
	}
	
	
	protected void setErrorView(String message) {
		View errorView = getLayoutInflater().inflate(R.layout.view_error, null);
		TextView errorTextView = (TextView) errorView.findViewById(R.id.error_text_view);
		errorTextView.setText(message);
		
		getFrameLayout().removeAllViews();
		getFrameLayout().addView(errorView);
	}
	
	@Override
	public void onBackPressed() {
		if(currentView != MenuItems.NEWS)
			startActivityByClass(MainActivity.class);
		else{
		    Intent i = new Intent(Intent.ACTION_MAIN);
		    i.addCategory(Intent.CATEGORY_HOME);
		    startActivity(i);
		}
		super.onBackPressed();
	}
	
	/**
	 * Checks if device is connected to Internet.
	 * 
	 * @return True if connected, otherwise false
	 */
	protected boolean connectedToInternet(){
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		
		return (ni != null && ni.isConnected()) ? true : false;
	}

}