package se.chalmers.h_sektionen;

import se.chalmers.h_sektionen.utils.MenuItems;
import android.os.Bundle;

/**
 * Activity that displays a view for reporting faults.
 */
public class AboutActivity extends BaseActivity {
	
	/**
	 * Creates the view by calling createFaultView();
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
     * Create fault report view.
     */
	private void createAboutView(){
		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_about, null));
    }
	
	/**
	 * Starts an e-mail client with all fields filled in.
	 * Message is taken from the view, subject is decided by a checkbox status.
	 * @param view the view to get the message from.
	 */
	
}
