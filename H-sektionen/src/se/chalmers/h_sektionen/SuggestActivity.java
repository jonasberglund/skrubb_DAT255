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
		EditText editText = (EditText) findViewById(R.id.suggest_edit_message);	//fetch the text...
		String message = editText.getText().toString();							//...and make it into a string
		intent.putExtra(Intent.EXTRA_SUBJECT, "F�rslag till H-sektionen");		//the subject for the email
		intent.putExtra(Intent.EXTRA_TEXT, message); 							//Adds the text from the editText field.
		intent.setData(Uri.parse("mailto:n�gon@n�got.com")); // the address to send to. CHANGE THIS!
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
		try{
			startActivity(intent);	//try to start the intent, if there are any email clients on the phone.
		}catch (android.content.ActivityNotFoundException ex) { //if not, post a toast to let the user know.
			Toast.makeText(SuggestActivity.this, "Det verkar inte finnas n�gon E-mail applikation p� din telefon.", Toast.LENGTH_SHORT).show();
		}
	}

}
