package se.chalmers.h_sektionen.utils;

import java.util.ArrayList;

import se.chalmers.h_sektionen.R;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends ArrayAdapter<NewsItem> {
	
	private ArrayList<NewsItem> objects;
	
	public NewsAdapter(Context context, int resource, ArrayList<NewsItem> objects){
		super(context, resource, objects);
		this.objects = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		
		//Assign the view we are converting to a local variable
		View v = convertView;
		
		
		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
		if(v == null){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.news_feed_item, null);
		}
		
		NewsItem item = objects.get(position);
		
		if (item != null){
			
			TextView message = (TextView) v.findViewById(R.id.item_message);
			TextView date = (TextView) v.findViewById(R.id.item_date);
			ImageView image = (ImageView) v.findViewById(R.id.item_image);
			
			if (message != null){
				message.setText(item.getMessage());
			}
			if (date != null){
				date.setText(item.getDate());
			}
		}
		
		return v;
	}
	

	
	
	}
