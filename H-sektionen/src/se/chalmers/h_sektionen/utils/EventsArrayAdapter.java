package se.chalmers.h_sektionen.utils;

import java.util.List;
import se.chalmers.h_sektionen.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


public class EventsArrayAdapter extends ArrayAdapter<Event> {
   
	
	private final Context context;
	private final List<Event> events;
	private final int resource;
	
	public EventsArrayAdapter(Context context, int resource, List<Event> events) {
		super(context, resource, events);
		this.context = context;
		this.resource = resource;
		this.events = events;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View row = convertView;
		EventsHolder holder = null;
		
	
		
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			
		if (row == null) {
				row = inflater.inflate(resource, parent, false);
			
				holder = new EventsHolder();
			
				holder.title = (TextView)row.findViewById(R.id.title);
				holder.description  = (TextView)row.findViewById(R.id.descriptionEvents);
				holder.place = (TextView)row.findViewById(R.id.place);
				holder.date = (TextView)row.findViewById(R.id.date);
				
				// Resets the toolbar to be closed
			    View toolbar = row.findViewById(R.id.toolbarEvents);
			    ((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -50;
			    toolbar.setVisibility(View.GONE);
				
				
				row.setTag(holder);
			
		} else {
			holder = (EventsHolder)row.getTag();
		}
		
		Event event = events.get(position);
		holder.title.setText(event.getTitle());
		holder.date.setText(String.valueOf(event.getDate()) );
		//if(!event.getDescription().equals(""))
			holder.description.setText("Beskrivning: " + event.getDescription() + "\n");
		//if(!event.getPlace().equals(""))
			holder.place.setText("Var: " + event.getPlace() + "\n");


		return row;
	}
	
	static class EventsHolder {
		TextView title;
		TextView description;
		TextView place;
		TextView date;
	}
	

	
}
