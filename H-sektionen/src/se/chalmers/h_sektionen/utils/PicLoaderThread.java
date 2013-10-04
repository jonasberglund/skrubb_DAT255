

package se.chalmers.h_sektionen.utils;

import java.io.InputStream;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * PicLoaderThread downloads a picture to a Bitmap object from a given url.
 * If fail, the pic variable is set to null.
 */
public class PicLoaderThread extends Thread {

	private Bitmap pic;
	private String urlString;

	/**
	 * Initiates the thread.
	 * @param urlString The URL to the picture. As a suggestion, use the png-format.
	 */
	public PicLoaderThread(String urlString) {
		this.urlString = urlString;
	}

	/**
	 * Downloads the picture and puts it into a Bitmap object.
	 * If the picture could not be decoded, the pic variable will be set to null.
	 */
	public void run() {
		InputStream in = null;

		try {
			in = new URL(urlString).openStream();
			pic = BitmapFactory.decodeStream(in);

		} catch (Exception e) {
			pic = null;
		}
	}

	/**
	 * @return The picture that was downloaded.
	 */
	public Bitmap getPicture() {
		return pic;
	}

}
