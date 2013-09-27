

package se.chalmers.h_sektionen.utils;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PicLoaderThread extends Thread {

	private Bitmap pic;
	private String urlString;

	public PicLoaderThread(String urlString) {
		this.urlString = urlString;
	}

	public void run() {
		InputStream in = null;

		try {
			in = new URL(urlString).openStream();
			pic = BitmapFactory.decodeStream(in);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Bitmap getPicture() {
		return pic;
	}

}
