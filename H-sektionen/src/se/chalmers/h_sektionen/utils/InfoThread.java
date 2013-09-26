package se.chalmers.h_sektionen.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class InfoThread extends Thread {
	
	private StringBuilder sb;
	
	public InfoThread(StringBuilder sb) {
		this.sb = sb;
	}
	
	public void run() {
		try {
			//URL url = new URL("http://jpv-net.dyndns.org:1337/H-Sektionen/info/");
			URL url = new URL("http://htek.comli.com/info/");
			URLConnection conn = url.openConnection();
			conn.addRequestProperty("Accept", "application/json");
			conn.connect();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			
		} catch (Exception e) {
			sb = null;
		}
	}
	
}
