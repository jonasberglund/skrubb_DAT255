package se.chalmers.h_sektionen.adapters;

import java.util.List;
import se.chalmers.h_sektionen.R;
import se.chalmers.h_sektionen.containers.Event;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


public class PubArrayAdapter extends ArrayAdapter<Event> {
   
	
	private final Context context;
	private final List<Event> pubs;
	private final int resource;
	
	/**
	 * Create array adapter
	 * @param context
	 * @param resource
	 * @param pubs
	 */
	public PubArrayAdapter(Context context, int resource, List<Event> pubs) {
		super(context, resource, pubs);
		this.context = context;
		this.resource = resource;
		this.pubs = pubs;
	}
	
	/** Get view */
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View row = convertView;
		PubsHolder holder = null;
	
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			
		if (row == null) {
				row = inflater.inflate(resource, parent, false);
			
				holder = new PubsHolder();
			
				holder.title = (TextView)row.findViewById(R.id.titlePub);
				holder.description  = (TextView)row.findViewById(R.id.descriptionPub);
				holder.place = (TextView)row.findViewById(R.id.placePub);
				holder.date = (TextView)row.findViewById(R.id.datePub);
				
				// Resets the toolbar to be closed
			    View toolbar = row.findViewById(R.id.toolbarPubs);
			    ((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -50;
			    toolbar.setVisibility(View.GONE);
				
				
				row.setTag(holder);
			
		} else {
			holder = (PubsHolder)row.getTag();
		}
		
		Event pub = pubs.get(position);
		holder.title.setText(pub.getTitle());
		holder.description.setText(pub.getDescription());
		//holder.place.setText("\n Var: " + pub.getPlace());
		holder.date.setText(String.valueOf(pub.getDate()) );

		return row;
	}
	
	/** Holder for a pub */
	static class PubsHolder {
		TextView title;
		TextView description;
		TextView place;
		TextView date;
	}
	

	
}
