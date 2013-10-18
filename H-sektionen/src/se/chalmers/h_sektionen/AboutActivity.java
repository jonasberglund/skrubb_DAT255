package se.chalmers.h_sektionen;

import se.chalmers.h_sektionen.utils.MenuItems;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

/**
 * Activity that displays a view for reporting faults.
 */
public class AboutActivity extends BaseActivity {
	
	/**
	 * Creates the view by calling createAboutView();
	 */
	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		createAboutView();
	}
	
	/**
	 * On resume. Sets the static currentView variable in BaseActivty.
	 */
    @Override
	protected void onResume() {
    	super.onResume();
		setCurrentView(MenuItems.ABOUT);
	}
	
    /**
     * Create about view.
     */
	private void createAboutView(){
		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_about, null));
    }
	
	/**
	 * Open a link in a browser.
	 * @param view the view that the method was called from.
	 */
	public void startVideo(View view){
		Intent intent = new Intent(Intent.ACTION_VIEW ,Uri.parse(getString(R.string.video)));
		startActivity(intent);
	}

	
}
