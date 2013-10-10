package se.chalmers.h_sektionen;

import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import se.chalmers.h_sektionen.adapters.ContactCardArrayAdapter;
import se.chalmers.h_sektionen.containers.InfoContainer;
import se.chalmers.h_sektionen.utils.LoadData;
import se.chalmers.h_sektionen.utils.MenuItems;

/**
 * InfoActivity takes care about the info view
 */
public class InfoActivity extends BaseActivity {
	
	/**
	 * Sets the static "currentView" variable in the super class, BaseActivity.
	 * The method also start the AsyncTask that fetches the information data.
	 */
	@Override
	protected void onResume() {
		
		setCurrentView(MenuItems.INFO);
		new GetInfoTask().execute();
		
		super.onResume();
	}
	
	
	/**
	 * GetInfoTask loads the info data and sets up the view.
	 */
	private class GetInfoTask extends AsyncTask<String, String, InfoContainer> {

		/**
		 * Runs super method and starts the load animation.
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			runLoadAnimation();
		}
		
		/**
		 * Requests a JSONObject, tries to parse it and puts the data into arrays.
		 * @return false if the parsing did not succeed, else true
		 */
		@Override
		protected InfoContainer doInBackground(String... params) {
			
			if(!connectedToInternet())
				return null;
				
			return LoadData.loadInfo(InfoActivity.this);
		}
		
		/**
		 * Sets up the view with data from doInBackground method.
		 * If doInBackground returns false, an error view is going to show up.
		 * @param done True if doInBackground could parse and prepare the data correct, else false.
		 */
		@Override
		protected void onPostExecute(InfoContainer container) {
			super.onPostExecute(container);
			
			// If there was no error (connection or JSON string error), set up the view.
			if (container != null) {
				getFrameLayout().removeAllViews();
				getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_info, null));
				
				ListView contactListView = (ListView) findViewById(R.id.contact_info_list);
	    		contactListView.setCacheColorHint(Color.WHITE);
	    		
	    		LinearLayout linksLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.info_links, null);
	    		TextView linksTextView = (TextView)linksLayout.findViewById(R.id.links);
	    		TextView openingHoursTextView = (TextView)linksLayout.findViewById(R.id.opening_hours);
	    		
	    		linksTextView.setMovementMethod(LinkMovementMethod.getInstance());
	    		linksTextView.setText(Html.fromHtml(container.getHtmlLinks()));
	    		
	    		openingHoursTextView.setText(Html.fromHtml(container.getOpeningHoursString()));
	    		contactListView.addHeaderView(linksLayout);
	    		contactListView.setAdapter(new ContactCardArrayAdapter(InfoActivity.this, R.layout.contact_list_item, container.getContactCards()));
			} else {
				setErrorView(getString(R.string.INFO_ERROR));
			}
		}
		
	}
	
}