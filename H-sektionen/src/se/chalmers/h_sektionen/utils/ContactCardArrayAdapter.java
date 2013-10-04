package se.chalmers.h_sektionen.utils;

import java.util.List;

import se.chalmers.h_sektionen.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ContactCardArrayAdapter inserts ContactCards into a ListView
 */
public class ContactCardArrayAdapter extends ArrayAdapter<ContactCard> {

	private final Context context;
	private final List<ContactCard> contactCards;
	private final int resource;
	
	/**
	 * Constructor
	 * @param context The context
	 * @param resource The resource to be inflated.
	 * @param objects The ContactCards to 
	 */
	public ContactCardArrayAdapter(Context context, int resource, List<ContactCard> objects) {
		super(context, resource, objects);
		this.context = context;
		this.contactCards = objects;
		this.resource = resource;
	}
	
	/**
	 * Fills a row in the ListView with data.
	 * @param position The row number
	 * @param convertView
	 * @param parent
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		ContactCardHolder holder = null;
		
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		
		if (row == null) {
			row = inflater.inflate(resource, parent, false);
			
			holder = new ContactCardHolder();
			
			holder.name = (TextView)row.findViewById(R.id.name);
			holder.pos  = (TextView)row.findViewById(R.id.position);
			holder.email = (TextView)row.findViewById(R.id.email);
			holder.picture = (ImageView)row.findViewById(R.id.picture);
			holder.phoneNumber = (TextView)row.findViewById(R.id.phone_number);
			
			row.setTag(holder);
			
		} else {
			holder = (ContactCardHolder)row.getTag();
		}
		
		ContactCard card = contactCards.get(position);
		holder.name.setText(card.getName());
		holder.pos.setText(card.getPosition());
		holder.email.setText(card.getEmail());
		holder.phoneNumber.setText(card.getPhoneNumber());
		holder.picture.setImageBitmap(card.getPic());

		return row;
	}
	
	/**
	 * ContactCardHolder is meant to hold the views in a contact_list_view.xml view.
	 */
	static class ContactCardHolder {
		ImageView picture;
		TextView name;
		TextView pos;
		TextView email;
		TextView phoneNumber;
	}
	
}
