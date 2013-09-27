package se.chalmers.h_sektionen.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;

public class JSONLoader {
	private String urlString;
	
	public JSONLoader(String urlString) {
		this.urlString = urlString;
	}
	
	public JSONObject getJSONFromUrl() {
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.addRequestProperty("Accept", "application/json");
			conn.connect();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			
			return new JSONObject(sb.toString());
			
		} catch (Exception e) {
			return null;
		}
	}
	
}
