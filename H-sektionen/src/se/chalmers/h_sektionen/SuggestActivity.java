package se.chalmers.h_sektionen;

import se.chalmers.h_sektionen.utils.MenuItems;
import android.os.Bundle;

public class SuggestActivity extends BaseActivity {

	@Override
	protected void onResume() {

		setCurrentView(MenuItems.SUGGEST);
		createLunchView();
		
		super.onResume();
	}
	
	private void createLunchView(){
		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_suggest, null));
    	
    }

}
