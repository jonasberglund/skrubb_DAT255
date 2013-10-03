package Test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.chalmers.h_sektionen.utils.Constants;
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
	public void connectionTest() {
		try {
			URL url = new URL(Constants.SERVER_ADDRESS);
			URLConnection conn = url.openConnection();
			conn.connect();
			
		} catch (Exception e) {
			fail("Not connected to server");
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
	
	@Test
	public void JSONTest() {
		try {
			URL oracle = new URL(Constants.INFO);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String JSon = "";
			
			String inputLine;
			while ((inputLine = in.readLine()) != null) 
				JSon=JSon+inputLine;
			
			in.close();
			System.out.println(JSon);
			
			JSONParser parser = new JSONParser();
			
			JSONObject o = (JSONObject) parser.parse(JSon);
			
			// Check if expected values are found in the JSONObject.
			assertTrue("key \"members\" was not found", o.containsKey("members"));
			assertTrue("key \"openinghours\" was not found", o.containsKey("openinghours"));
			assertTrue("key \"links\" was not found", o.containsKey("links"));
			
		} catch (Exception e) {
			fail("Could not parse JSON");
		}
	}
}
