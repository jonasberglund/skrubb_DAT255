package se.chalmers_h.h_sektionen.adapters;

import java.util.ArrayList;
import java.util.Collection;

import se.chalmers_h.h_sektionen.R;
import se.chalmers_h.h_sektionen.containers.NewsItem;
import se.chalmers_h.h_sektionen.utils.CacheCompass;
import se.chalmers_h.h_sektionen.utils.PicLoaderThread;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * News Array Adapter
 * @Author Olle Svensson
 * @Copyright (c) 2013 Anders Johansson, Olle Svensson, Robin Tornquist, Rikard Ekbom, Oskar Gustavsson, Jonas Berglund
 * @Licens Apache
 */
public class NewsArrayAdapter extends ArrayAdapter<NewsItem> {
	
	private ArrayList<NewsItem> items;
	
	/**
	 * Constructor.
	 * @param context
	 * @param resource
	 * @param objects
	 */
	public NewsArrayAdapter(Context context, int resource, ArrayList<NewsItem> items){
		super(context, resource, items);
		this.items = items;
	}
	
	/** @return  Returns the data set of news items stored by the adapter */
	public ArrayList<NewsItem> getItems(){
		return items;
	}
	
	/**
	 * Overrided method and added manually because method unsupported in minimum required API.
	 */
	@Override
	public void addAll(Collection<? extends NewsItem> collection){
		for (NewsItem item : collection){
			items.add(item);
		}
	}
	
	/**
	 * Overrided method getView
	 * Sets message and date to view and loads image in background.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		//Assign the view to a local variable
		View view = convertView;
		
		//Inflate view if null
		if(view == null){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.news_feed_item, null);
		}
		
		//Get item to display in view
		NewsItem item = items.get(position);
		
		if (item != null){
			
			TextView message = (TextView) view.findViewById(R.id.item_message);
			TextView date = (TextView) view.findViewById(R.id.item_date);
			ImageView image = (ImageView) view.findViewById(R.id.item_image);
			
			if (message != null){
				message.setText(item.getMessage());
			}
			if (date != null){
				date.setText(item.getDate());
			}
			if (image != null){
			
				//Remove current image
				image.setImageBitmap(null);
				
				//Load new image in background
				if (item.getImageAdr()!=null && !item.getImageAdr().equals("")) {
					new LoadImagesInBg().execute(view, item.getImageAdr());
				}
			}
			
		}
		
		return view;
	}
	
	/**
	 * AsyncTask for loading image in background and displaying it in corresponding view.
	 */
	private class LoadImagesInBg extends AsyncTask<Object, String, Bitmap> {

	    private View view;
	    private Bitmap bitmap = null;

	    /**
	     * Superclass method doInBackground
	     */
	    @Override
	    protected Bitmap doInBackground(Object... parameters) {

	        view = (View) parameters[0];
	        String uri = (String)parameters[1];	
					
					//Download image if not in cache
					if(CacheCompass.getInstance(getContext()).getBitmapCache().get(uri)==null){
					
						PicLoaderThread pcl = new PicLoaderThread(uri);
						pcl.start();
						try {
							pcl.join();
							
							//Save picture to bitmap and cache
							bitmap = pcl.getPicture();
							CacheCompass.getInstance(getContext()).getBitmapCache().put(uri, bitmap);
						} catch (Exception e) {}	
					} else {
						//If already in cache, get from cache
						bitmap = CacheCompass.getInstance(getContext()).getBitmapCache().get(uri);
					}
	        
	        return bitmap;
	    }

	    /**
	     * Superclass method onPostExecute
	     */
	    @Override
	    protected void onPostExecute(Bitmap bitmap) {
	    	
	    	//Set downloaded image to view if both are valid
	        if (bitmap != null && view != null) {
	            ImageView image = (ImageView) view.findViewById(R.id.item_image);
	            image.setImageBitmap(bitmap);
	        }
	    }
	}

	
	
	}
