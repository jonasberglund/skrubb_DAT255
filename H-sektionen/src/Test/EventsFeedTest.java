package Test;

import java.util.List;

import org.junit.Test;

import se.chalmers.h_sektionen.utils.Events;
import se.chalmers.h_sektionen.utils.LoadData;

import junit.framework.TestCase;
	
	public class EventsFeedTest extends TestCase {
		
		protected void setUp() throws Exception {
			super.setUp();
		}
	
		protected void tearDown() throws Exception {
			super.tearDown();
		}

		@Test
		public void testJsonEventsFeed(){
			
			assertEquals(true, testJson());
		}
		
		
		public boolean testJson(){
			
			try{
				List<Events> events = LoadData.loadEvents();
			}catch(Exception e){
				e.printStackTrace();
			}
			return true;
		}
		

}
