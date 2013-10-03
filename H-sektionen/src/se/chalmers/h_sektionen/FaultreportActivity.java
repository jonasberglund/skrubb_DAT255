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
		createLunchView();
		
		super.onResume();
	}
	
	private void createLunchView(){
		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_faultreport, null));
    }
	
	public void sendFaultreportEmail(View view){
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setType("text/plain");
		
		EditText editText = (EditText) findViewById(R.id.faultreport_edit_message);	//fetch the text...
		String message = editText.getText().toString();							//...and make it into a string
		
		intent.putExtra(Intent.EXTRA_SUBJECT, "Felanmälan");					//the subject for the email
		intent.putExtra(Intent.EXTRA_TEXT, message); 							//Adds the text from the editText field.
		
		CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox1);
		if(checkBox.isChecked()){
			intent.setData(Uri.parse("mailto:"+getString(R.string.dataEmail))); //set the address to the datacenter.
		}
		else{
			intent.setData(Uri.parse("mailto:"+getString(R.string.buildingEmail))); //set the address to the buildingcenter.
		}
		
		try{
			startActivity(intent);	//try to start the intent, if there are any email clients on the phone.
		}catch (android.content.ActivityNotFoundException ex) { //if not, post a toast to let the user know.
			Toast.makeText(FaultreportActivity.this, "Det verkar inte finnas någon E-mail applikation på din telefon.", Toast.LENGTH_SHORT).show();
		}
	}

}
