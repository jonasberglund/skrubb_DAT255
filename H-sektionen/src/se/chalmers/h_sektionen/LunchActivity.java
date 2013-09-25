package se.chalmers.h_sektionen;

import se.chalmers.h_sektionen.utils.MenuItems;
import android.os.Bundle;

public class LunchActivity extends BaseActivity {

	@Override
	protected void onResume() {

		setCurrentView(MenuItems.LUNCH);
		createLunchView();
		
		super.onResume();
	}
	
	private void createLunchView(){
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_lunch, null));
    	
    }

}
