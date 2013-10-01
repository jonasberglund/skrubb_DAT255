

package se.chalmers.h_sektionen.utils;

import java.io.InputStream;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
 * PicLoaderThread downloads a picture to a Bitmap object from a given url.
 * If fail, the pic variable is set to null.
 */
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
			//e.printStackTrace();
			pic = null;
		}
	}

	public Bitmap getPicture() {
		return pic;
	}

}
