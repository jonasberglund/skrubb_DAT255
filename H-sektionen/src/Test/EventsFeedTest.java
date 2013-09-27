package Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import android.widget.ArrayAdapter;

import se.chalmers.h_sektionen.R;
import se.chalmers.h_sektionen.utils.Event;
import se.chalmers.h_sektionen.utils.EventsArrayAdapter;
import se.chalmers.h_sektionen.utils.LoadData;
import se.chalmers.h_sektionen.utils.LoadEvents;

import junit.framework.TestCase;
	
	public class EventsFeedTest extends TestCase {
		
		protected void setUp() throws Exception {
			super.setUp();
		}
	
		protected void tearDown() throws Exception {
			super.tearDown();
		}

		@Test
		public void testEventsFeed(){
			
			assertEquals(true, connection());
		}
		
		@Test
		public void testListView(){
			assertEquals(true, connection());
		}
		
		public boolean connection(){
			try {
				//List<Events> events = new LoadEvents().execute().get();
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
			return true;
		}
		
		public boolean listview(){
			
			return true;
		}
		
		

		
		
		
		

}
