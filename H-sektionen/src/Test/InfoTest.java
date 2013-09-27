package Test;

import static org.junit.Assert.*;

import java.net.URL;
import java.net.URLConnection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.chalmers.h_sektionen.utils.ContactCard;

public class InfoTest {

	public InfoTest() {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void connectTest() {
		try {
			URL url = new URL("http://jpv-net.dyndns.org:1337");
			URLConnection conn = url.openConnection();
			conn.connect();
			
		} catch (Exception e) {
			fail("Not connected to server");
		}
	}
	
	@Test
	public void noConnectionTest() {
		try {
			URL url = new URL("http://jpvvv-net.dyndns.org:1337");
			URLConnection conn = url.openConnection();
			conn.connect();
			
			fail("Should not work.");
			
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void createContactCardTest() {
		ContactCard c = new ContactCard("Kalle Anka", "Städare", "kalle@ankeborg.se", "070-1231232", "http://jpv-net.dyndns.org:1337/H-Sektionen/info/pics/jonas.png");
		assertTrue("Name fail", c.getName().equals("Kalle Anka"));
		assertTrue("Position fail", c.getPosition().equals("Städare"));
		assertTrue("Email fail", c.getEmail().equals("kalle@ankeborg.se"));
		assertTrue("Phone number fail", c.getPhoneNumber().equals("070-1231232"));
		
	}
}
