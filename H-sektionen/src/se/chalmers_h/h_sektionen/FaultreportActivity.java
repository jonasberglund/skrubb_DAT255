package se.chalmers_h.h_sektionen;

import se.chalmers_h.h_sektionen.utils.MenuItems;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity that displays a view for reporting faults.
 * @Author Oskar Gustavsson
 * @Copyright (c) 2013 Anders Johansson, Olle Svensson, Robin Tornquist, Rikard Ekbom, Oskar Gustavsson, Jonas Berglund
 * @Licens Apache
 */
public class FaultreportActivity extends BaseActivity {
	
	/**
	 * Creates the view by calling createFaultView();
	 */
	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		createFaultView();
	}
	
	/**
	 * On resume. Sets the static currentView variable in BaseActivty.
	 */
    @Override
	protected void onResume() {
    	super.onResume();
		setCurrentView(MenuItems.FAULTREPORT);
	}
	
    /**
     * Create fault report view.
     */
	private void createFaultView(){
		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_faultreport, null));
    }
	
	/**
	 * Starts an e-mail client with all fields filled in.
	 * Message is taken from the view, subject is in a string and receiver is decided by a checkbox status.
	 * @param view the view to get the message from.
	 */
	public void sendFaultreportEmail(View view){
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setType("text/plain");
		
		EditText editText = (EditText) findViewById(R.id.faultreport_edit_message);	//fetch the text from the view
		String message = editText.getText().toString();							
		
		intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.faultreport_subject));
		intent.putExtra(Intent.EXTRA_TEXT, message);
		
		CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox1);
		if(checkBox.isChecked()){
			intent.setData(Uri.parse("mailto:"+getString(R.string.faultreport_dataEmail))); //set the address to the data center.
		}
		else{
			intent.setData(Uri.parse("mailto:"+getString(R.string.faultreport_buildingEmail))); //set the address to the building center.
		}
		
		try{
			startActivity(intent);	
		}catch (android.content.ActivityNotFoundException ex) { //if no email clients on the phone, post a toast to let the user know.
			Toast.makeText(FaultreportActivity.this, R.string.noEmailClientFound, Toast.LENGTH_SHORT).show();
		}
	}

}
