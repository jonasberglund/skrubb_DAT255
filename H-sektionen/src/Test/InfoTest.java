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

import se.chalmers.h_sektionen.containers.ContactCard;
import se.chalmers.h_sektionen.utils.Constants;

/**
 * Test Info View
 * @Author Robin Tornquist
 * @Copyright (c) 2013 Anders Johansson, Olle Svensson, Robin Tornquist, Rikard Ekbom, Oskar Gustavsson, Jonas Berglund
 * @Licens Apache
 */
public class InfoTest {

	public InfoTest() {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This test simply tests to connect to the server.
	 */
	@Test
	public void serverConnectionTest() {
		try {
			URL url = new URL(Constants.SERVER_ADDRESS);
			URLConnection conn = url.openConnection();
			conn.connect();
			
		} catch (Exception e) {
			fail("Could not connect to server");
		}
	}
	
	/**
	 * This test simply tests to create a ContactCard object and then read the data.
	 */
	@Test
	public void createContactCardTest() {
		ContactCard c = new ContactCard("Kalle Anka", "Stadare", "kalle@ankeborg.se", "070-1231232", null);
		assertTrue("Name fail", c.getName().equals("Kalle Anka"));
		assertTrue("Position fail", c.getPosition().equals("Stadare"));
		assertTrue("Email fail", c.getEmail().equals("kalle@ankeborg.se"));
		assertTrue("Phone number fail", c.getPhoneNumber().equals("070-1231232"));
	}
	
	/**
	 * This test downloads a JSON string from the server, checks if keys that should
	 * be found, are found.
	 */
	@Test
	public void JSONFromServerTest() {
		try {
			URL infoURL = new URL(Constants.INFO);
			URLConnection conn = infoURL.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder jsonStringBuilder = new StringBuilder();
			
			String line;
			while ((line = in.readLine()) != null)
				jsonStringBuilder.append(line);
			
			in.close();
			
			JSONParser parser = new JSONParser();
			JSONObject o = (JSONObject) parser.parse(jsonStringBuilder.toString());
			
			// Check if expected values are found in the JSONObject.
			assertTrue("key \"members\" was not found", o.containsKey("members"));
			assertTrue("key \"openinghours\" was not found", o.containsKey("openinghours"));
			assertTrue("key \"links\" was not found", o.containsKey("links"));
			
		} catch (Exception e) {
			// Could also be connection error, see the test result of serverConnectionTest().
			fail("Could not parse JSON");
		}
	}
}
