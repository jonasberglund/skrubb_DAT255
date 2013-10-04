package se.chalmers.h_sektionen;

import se.chalmers.h_sektionen.utils.MenuItems;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class FaultreportActivity extends BaseActivity {
	
    @Override
	protected void onResume() {

		setCurrentView(MenuItems.FAULTREPORT);
		createView();
		
		super.onResume();
	}
	
	private void createView(){
		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_faultreport, null));
    }
	
	/**
	 * Starts an e-mail client with all fields filled in.
	 * Message is taken from the view, subject is decided by a checkbox status.
	 * @param view the view to get the message from.
	 */
	public void sendFaultreportEmail(View view){
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setType("text/plain");
		
		EditText editText = (EditText) findViewById(R.id.faultreport_edit_message);	//fetch the text from the view
		String message = editText.getText().toString();							
		
		intent.putExtra(Intent.EXTRA_SUBJECT, R.string.faultreport_subject);
		intent.putExtra(Intent.EXTRA_TEXT, message);
		
		CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox1);
		if(checkBox.isChecked()){
			intent.setData(Uri.parse("mailto:"+getString(R.string.faultreport_dataEmail))); //set the address to the datacenter.
		}
		else{
			intent.setData(Uri.parse("mailto:"+getString(R.string.faultreport_buildingEmail))); //set the address to the buildingcenter.
		}
		
		try{
			startActivity(intent);	
		}catch (android.content.ActivityNotFoundException ex) { //if no email clients on the phone, post a toast to let the user know.
			Toast.makeText(FaultreportActivity.this, R.string.faultreport_noEmailClientFound, Toast.LENGTH_SHORT).show();
		}
	}

}
