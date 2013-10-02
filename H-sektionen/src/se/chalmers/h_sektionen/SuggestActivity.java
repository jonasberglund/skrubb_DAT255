package se.chalmers.h_sektionen;

import se.chalmers.h_sektionen.utils.MenuItems;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SuggestActivity extends BaseActivity {

	@Override
	protected void onResume() {

		setCurrentView(MenuItems.SUGGEST);
		createLunchView();
		
		super.onResume();
	}
	
	private void createLunchView(){
		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_suggest, null));
    	
    }
	
	public void sendSuggestEmail(View view){
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setType("text/plain");
		
		EditText editText = (EditText) findViewById(R.id.suggest_edit_message);	//fetch the text from the user
		String message = editText.getText().toString();							//...and make it into a string
		
		intent.putExtra(Intent.EXTRA_SUBJECT, "Förslag till H-sektionen");		//the subject for the email
		intent.putExtra(Intent.EXTRA_TEXT, message); 							//Adds the text from the editText field.
		intent.setData(Uri.parse("mailto:"+getString(R.string.suggest_emailReceiver))); //Sets the receiver of the email
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 						
		
		try{
			startActivity(intent);	//try to start the intent, if there are any email clients on the phone.
		}catch (android.content.ActivityNotFoundException ex) { //if not, post a toast to let the user know.
			Toast.makeText(SuggestActivity.this, "Det verkar inte finnas någon E-mail applikation på din telefon.", Toast.LENGTH_SHORT).show();
		}
	}

}
