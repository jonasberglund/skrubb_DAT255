package se.chalmers.h_sektionen.utils;

import java.net.URI;
import java.util.List;

import se.chalmers.h_sektionen.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter{
	
	private Activity activity;
	private List data;
	private static LayoutInflater inflater = null;
	public Resources resources;
	NewsItem item = null;
	int i=0;

	public NewsAdapter(Activity activity, List<NewsItem> data, Resources resources){
		this.activity = activity;
		this.data = data;
		this.resources = resources;
		
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public int getCount(){
		
		//if(data.size()<=0)
        //    return 1;
        //return data.size();
		return data.size();
	}
	
	public Object getItem(int position){
		return position;
	}
	
	public long getItemId(int position){
		return position;
	}
	
	public static class ItemHolder{
		public TextView message;
		public TextView date;
		public ImageView image;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		ItemHolder holder;
		
		if (convertView == null){
			
			view = inflater.inflate(R.layout.news_feed_item, null);
			
			holder = new ItemHolder();
			holder.message = (TextView) view.findViewById(R.id.item_message);
			holder.date = (TextView) view.findViewById(R.id.item_date);
			holder.image = (ImageView) view.findViewById(R.id.item_image);
			
			view.setTag(holder);
		} else {
			holder = (ItemHolder) view.getTag();
		}
		
		//ERROR CHECK
		if (data.size() < 1){
			holder.message.setText("No data");
		} else {
			item = null;
			item = (NewsItem) data.get(position);
			holder.message.setText(item.getMessage());
			holder.date.setText(item.getDate());
			holder.image.setImageURI(Uri.parse(item.getImage()));
		}
		
		return view;
	}

}
