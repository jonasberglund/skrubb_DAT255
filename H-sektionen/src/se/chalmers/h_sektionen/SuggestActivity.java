package se.chalmers.h_sektionen;

import se.chalmers.h_sektionen.utils.MenuItems;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity that displays the view for the user to input
 * suggestions.
 */
public class SuggestActivity extends BaseActivity {

	/**
	 * On resume.
	 */
	@Override
	protected void onResume() {

		setCurrentView(MenuItems.SUGGEST);
		createSuggestView();
		
		super.onResume();
	}
	
	/**
	 * Creates the suggest view.
	 */
	private void createSuggestView(){
		getFrameLayout().removeAllViews();
		getFrameLayout().addView(getLayoutInflater().inflate(R.layout.view_suggest, null));
    }
	
	/**
	 * Starts an e-mail client and fill in all fields with text from the view and strings.
	 * @param view the view to fetch the message text from.
	 */
	public void sendSuggestEmail(View view){
		
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setType("text/plain");

		EditText editText = (EditText) findViewById(R.id.suggest_edit_message);	//fetch the text from the view
		String message = editText.getText().toString();							
		
		intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.suggest_subject));		
		intent.putExtra(Intent.EXTRA_TEXT, message); 							
		intent.setData(Uri.parse("mailto:"+getString(R.string.suggest_emailReceiver))); //Sets the receiver of the email						
		
		try{
			startActivity(intent);	
			}
		catch (android.content.ActivityNotFoundException ex) { //if no E-mail client is found, post a toast to let the user know.
			Toast.makeText(SuggestActivity.this,getString(R.string.suggest_noEmailClientFound), Toast.LENGTH_SHORT).show();
		}
	}
}
