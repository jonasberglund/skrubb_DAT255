package se.chalmers.h_sektionen.utils;

import android.os.AsyncTask;

public class LoadEvents extends AsyncTask<Void, Void, String[]>{

	protected String[] doInBackground(Void...voids) {

		return LoadData.loadEvents();
		
	}
	
	@Override
    protected void onPreExecute() {
       // Before starting background task
       // Show Progress Dialog etc,.
		
		
		
    }

	protected void onPostExecute(Void unused) {
		
        // On completing background task
        // closing progress dialog etc,.
    }




	
	


}