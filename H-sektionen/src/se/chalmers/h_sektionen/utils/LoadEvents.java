package se.chalmers.h_sektionen.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class LoadEvents extends AsyncTask<Void, Void, Void[]>{
	
	
	@Override
    protected void onPreExecute() {
       // Before starting background task
       // Show Progress Dialog etc,.
    }

	


	protected void onPostExecute(Void unused) {
        // On completing background task
        // closing progress dialog etc,.
    }



	@Override
	protected Void[] doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}





}