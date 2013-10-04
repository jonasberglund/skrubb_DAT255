package Test;

import org.junit.Test;

import se.chalmers.h_sektionen.utils.Event;

import junit.framework.TestCase;
	
	public class EventsFeedTest extends TestCase {
		
		/** Set up */
		protected void setUp() throws Exception {
			super.setUp();
		}
	
		/** Tear down */
		protected void tearDown() throws Exception {
			super.tearDown();
		}

		/** Test to create a new envet */
		@Test
		public void testCreateNewEvent(){
			
			String title = "Oktoberfest";
			String desc = "En fest med mycket bira";
			String place = "I Tyskland";
			String date = "2013-09-27";
			
			Event ev = new Event(title, desc, place, date);
			
			if(!title.equals(ev.getTitle()))
				fail("Title is not equal");
			
			if(!desc.equals(ev.getDescription()))
				fail("Description is not equal");
			
			if(!place.equals(ev.getPlace()))
				fail("Place is not equal");
			
			if(!date.equals(ev.getDate()))
				fail("Date is not equal");
		}
}
