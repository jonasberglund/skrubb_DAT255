package se.chalmers_h.h_sektionen.adapters;

import java.io.IOException;
import java.io.InputStream;

import se.chalmers_h.h_sektionen.R;
import se.chalmers_h.h_sektionen.utils.MenuModel;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * MenuArrayAdapter- adapter between the Menu-ui and data.
 * @Author Anders Johansson
 * @Copyright (c) 2013 Anders Johansson, Olle Svensson, Robin Tornquist, Rikard Ekbom, Oskar Gustavsson, Jonas Berglund
 * @Licens Apache
 */
public class MenuArrayAdapter extends ArrayAdapter<String> {

	private final Context context;
	private final String[] Ids;
	private final int rowResourceId;
	
	/**
	 * Constructor
	 * @param context - Reference to Context of calling activity.
	 * @param textViewResourceId - id of the textview.
	 * @param objects - Array of objects to set into menu
	 */
	public MenuArrayAdapter(Context context,int textViewResourceId,String[] objects){
		
		super(context, textViewResourceId, objects);

        this.context = context;
        this.Ids = objects;
        this.rowResourceId = textViewResourceId;
	}
	
			/**
			 * Builds the view of the menu.
			 *  
			 * @param position id of the order in the menu, View - reference to unused view in the recycler  , viewGroup parent view.
			 * @return View 
			 * 
			 */
	 @Override
	    public View getView(int position, View convertView, ViewGroup parent) {

	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	        View rowView = inflater.inflate(rowResourceId, parent, false);
	        ImageView imageView = (ImageView) rowView.findViewById(R.id.MenuImageView);
	        TextView textView = (TextView) rowView.findViewById(R.id.MenuTextView);

	        // get image address
	        int id = Integer.parseInt(Ids[position]);
	        String imageFile = MenuModel.GetbyId(id).IconFile;

	        //set text for textView
	        textView.setText(MenuModel.GetbyId(id).Name);
	        
	        // get input stream
	        InputStream ims = null;
	        try {
	            ims = context.getAssets().open(imageFile);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        // load image as Drawable
	        Drawable d = Drawable.createFromStream(ims, null);

	        // set image to ImageView
	        imageView.setImageDrawable(d);
	        return rowView;

	    }
}
