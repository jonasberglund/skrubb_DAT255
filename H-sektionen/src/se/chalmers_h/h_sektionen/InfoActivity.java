package se.chalmers_h.h_sektionen;

import se.chalmers_h.h_sektionen.adapters.ContactCardArrayAdapter;
import se.chalmers_h.h_sektionen.containers.InfoContainer;
import se.chalmers_h.h_sektionen.utils.LoadData;
import se.chalmers_h.h_sektionen.utils.MenuItems;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * InfoActivity takes care about the info view
 * @Author Robin Tornquist
 * @Copyright (c) 2013 Anders Johansson, Olle Svensson, Robin Tornquist, Rikard Ekbom, Oskar Gustavsson, Jonas Berglund
 * @Licens Apache
 */
public class InfoActivity extends BaseActivity {
	
	private AsyncTask<String, String, InfoContainer> getInfoTask;
	
	/**
	 * Sets the static "currentView" variable in the super class, BaseActivity.
	 * The method also start the AsyncTask that fetches the information data.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		setCurrentView(MenuItems.INFO);
		if(connectedToInternet()) {
			getFrameLayout().removeAllViews();
			getFrameLayout().addView(getLayoutInflater().inflate(R.layout.info_links, null));
			getInfoTask = new GetInfoTask().execute();
			
		} else {
			setErrorView(getString(R.string.INTERNET_CONNECTION_ERROR_MSG));
		}
	}
	
	/**
	 * Cancel the GetInfoTask task if the activity get paused.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if (getInfoTask != null && !getInfoTask.isCancelled()) {
			getInfoTask.cancel(true);
		}
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
			runTransparentLoadAnimation();
		}
		
		/**
		 * Uses the LoadData class to download information and then returns it like
		 * an InfoContainer object
		 * @return null if the parsing did not succeed, else an InfoContainer object
		 */
		@Override
		protected InfoContainer doInBackground(String... params) {
			return LoadData.loadInfo(InfoActivity.this);
		}
		
		/**
		 * runs setUpView() and stops the load animation.
		 * @param container An InfoContainer object with info to be showed.
		 */
		@Override
		protected void onPostExecute(InfoContainer container) {
			super.onPostExecute(container);
			stopAnimation();
			setUpView(container);
		}
		
	}
	
	/**
	 * Sets up the view with data from doInBackground method.
	 * If doInBackground returns null, an error view is going to show up.
	 * @param container An InfoContainer object with info to be showed.
	 */
	private void setUpView(InfoContainer container) {
		// If there was no error (connection or JSON string error), set up the view.
		if (container != null) {
			getFrameLayout().removeAllViews();
			getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_info, null));

			ListView contactListView = (ListView) findViewById(R.id.contact_info_list);
			contactListView.setCacheColorHint(Color.WHITE);

			// Set the links text view.
			LinearLayout linksLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.info_links, null);
			TextView linksTextView = (TextView)linksLayout.findViewById(R.id.links);
			TextView openingHoursTextView = (TextView)linksLayout.findViewById(R.id.opening_hours);

			// Make links clickable
			linksTextView.setMovementMethod(LinkMovementMethod.getInstance());
			linksTextView.setText(Html.fromHtml(container.getHtmlLinks()));

			// Set the opening hours text view.
			openingHoursTextView.setText(Html.fromHtml(container.getOpeningHoursString()));
			contactListView.addHeaderView(linksLayout);
			
			// Set contacts list view
			contactListView.setAdapter(new ContactCardArrayAdapter(this, R.layout.contact_list_item, container.getContactCards()));
		} else {
			setErrorView(getString(R.string.INFO_ERROR));
		}
	}
	
}