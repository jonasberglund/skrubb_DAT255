package se.chalmers.h_sektionen;

import se.chalmers.h_sektionen.utils.LoadData;
import se.chalmers.h_sektionen.utils.MenuItems;

import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;
/**
 * Activity responsible of the lunch menu view.
 */
public class LunchActivity extends BaseActivity {

	private TextView textView;
	
	/**
	 * On resume.
	 */
	@Override
	protected void onResume() {

		setCurrentView(MenuItems.LUNCH);
		createLunchView();
		
		textView = (TextView)findViewById(R.id.LunchTextView);
		
		if (connectedToInternet())
			new DownLoadWebPage().execute();
		else 
		    textView.setText(getString(R.string.INTERNET_CONNECTION_ERROR_MSG));
				
		super.onResume();
	}
	/**
	 * Creates the lunch view.
	 */
	private void createLunchView(){
		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_lunch, null));
    	
    }
	
	/**
	 * Thread that starts the download and parsing function
	 * of the lunch menus.
	 */
	public class DownLoadWebPage extends AsyncTask<String, String, String>{
		/**
		 * Runs a loading animation
		 */
		@Override
		protected void onPreExecute(){
			runTransparentLoadAnimation();
		}
		
		/**
		 * Starts the download of a web page and returns
		 * the string value of the data which was parsed. 
		 */
		@Override
		protected String doInBackground(String... string) {
			
			return LoadData.loadLunch();
		}
		
		/**
		 * Receives the result string and sets it in
		 * the text view in the activity view.
		 */
		@Override
		protected void onPostExecute(String result){
    		textView.setText(Html.fromHtml(result));
    		stopAnimation();
		}
	}
}



